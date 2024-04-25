package com.example.hackathon_becoder_backend.web.security;

import com.example.hackathon_becoder_backend.domain.client.Client;
import com.example.hackathon_becoder_backend.domain.client.Role;
import com.example.hackathon_becoder_backend.domain.exception.AccessDeniedException;
import com.example.hackathon_becoder_backend.service.ClientService;
import com.example.hackathon_becoder_backend.service.props.JwtProperties;
import com.example.hackathon_becoder_backend.web.dto.auth.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.jsonwebtoken.Jwts.parser;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;
    private final UserDetailsService userDetailsService;
    private final ClientService clientService;
    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String createAccessToken(UUID clientId, String userName, Set<Role> roles) {
        Claims claims = Jwts.claims().subject(userName).add("id", clientId).add("roles", resolveRoles(roles)).build();
        Date now = new Date();
        Date dateValidity = new Date(now.getTime() + jwtProperties.getAccess());
        return Jwts.builder().claims(claims)
                .issuedAt(now)
                .expiration(dateValidity)
                .signWith(key)
                .compact();
    }

    private List<String> resolveRoles(Set<Role> roles) {
        return roles.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public String createRefreshToken(UUID clientId, String userName) {
        Claims claims = Jwts.claims().subject(userName).add("id", clientId).build();
        Date now = new Date();
        Date dateValidity = new Date(now.getTime() + jwtProperties.getRefresh());
        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(dateValidity)
                .signWith(key)
                .compact();
    }

    public JwtResponse refreshUserTokens(String refreshToken) {
        JwtResponse jwtResponse = new JwtResponse();
        if (!validateToken(refreshToken)) {
            throw new AccessDeniedException();
        }
        UUID userId = UUID.fromString(getId(refreshToken));
        Client client = clientService.findById(userId);
        jwtResponse.setId(userId);
        jwtResponse.setUsername(client.getUsername());
        jwtResponse.setAccessToken(createAccessToken(userId, client.getUsername(), client.getRoles()));
        jwtResponse.setRefreshToken(createRefreshToken(userId, client.getUsername()));
        return jwtResponse;
    }

    public boolean validateToken(String token) {
        Jws<Claims> claims = parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);

        return !claims.getPayload().getExpiration().before(new Date());
    }

    private String getId(String token) {
        return parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("id")
                .toString();
    }

    private String getUserName(String token) {
        return parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Authentication getAuthentication(String token) {
        String userName = getUserName(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
