package com.example.hackathon_becoder_backend.web.security.expression;

import com.example.hackathon_becoder_backend.service.ClientService;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;

public class CustomSecurityExceptionHandler
        extends DefaultMethodSecurityExpressionHandler {

    private final AuthenticationTrustResolver trustResolver
            = new AuthenticationTrustResolverImpl();
    private ApplicationContext applicationContext;

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
            final Authentication authentication,
            final MethodInvocation invocation
    ) {
        CustomMethodSecurityExpressionRoot root
                = new CustomMethodSecurityExpressionRoot(authentication);
        root.setTrustResolver(trustResolver);
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setRoleHierarchy(getRoleHierarchy());
        root.setClientService(this.applicationContext.getBean(ClientService.class));
        return root;
    }

    @Override
    public void setApplicationContext(
            final ApplicationContext applicationContext
    ) {
        super.setApplicationContext(applicationContext);
        this.applicationContext = applicationContext;
    }
}
