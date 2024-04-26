package com.example.hackathon_becoder_backend.vaadin;

import com.example.hackathon_becoder_backend.domain.client.Client;
import com.example.hackathon_becoder_backend.service.AuthService;
import com.example.hackathon_becoder_backend.web.dto.auth.JwtRequest;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("login")
@PageTitle("Login")
public class LoginView extends VerticalLayout {

    public LoginView(
            @Autowired
            AuthService authService
    ) {
        addClassName("login-view");
        setSizeFull();

        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        LoginForm login = new LoginForm();
//        login.setAction("login");

        add(login);
        login.addLoginListener(loginEvent -> {
            var jwtRequest = new JwtRequest();
            jwtRequest.setUsername(loginEvent.getUsername());
            jwtRequest.setPassword(loginEvent.getPassword());
            try {
                authService.login(jwtRequest);
                showSuccess(jwtRequest);
                login.getUI().ifPresent(ui ->
                        ui.navigate("signUp"));
            }catch (Exception e){
                showFail(jwtRequest);
                login.getUI().ifPresent(ui ->
                        ui.navigate("/"));
            }

        });
    }

    private void showFail(JwtRequest jwtRequest) {
        Notification notification = Notification.show("Data wasn't saved, " + jwtRequest.getUsername());
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.open();
    }

    /**
     * We call this method when form submission has succeeded
     */
    private void showSuccess(JwtRequest jwtRequest)  {
        Notification notification = Notification.show("Data saved, welcome " + jwtRequest.getUsername());
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        notification.open();
    }

//    @Override
//    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
//        // inform the user about an authentication error
//        if(beforeEnterEvent.getLocation()
//
//
//                .getQueryParameters()
//                .getParameters()
//                .containsKey("error")) {
//            login.setError(true);
//        }
//    }
}