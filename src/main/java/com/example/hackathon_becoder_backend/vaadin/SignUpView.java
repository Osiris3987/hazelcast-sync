package com.example.hackathon_becoder_backend.vaadin;

import com.example.hackathon_becoder_backend.domain.client.Client;
import com.example.hackathon_becoder_backend.principal.AuthenticationFacade;
import com.example.hackathon_becoder_backend.service.AuthService;
import com.example.hackathon_becoder_backend.service.ClientService;
import com.example.hackathon_becoder_backend.web.dto.auth.JwtRequest;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;

@Route("signUp")
public class SignUpView extends VerticalLayout {

    private PasswordField passwordField1;
    private PasswordField passwordField2;
    private AuthService authService;

    private BeanValidationBinder<Client> binder;

    /**
     * Flag for disabling first run for password validation
     */
    private boolean enablePasswordValidation;

    /**
     * We use Spring to inject the backend into our view
     */
    public SignUpView(@Autowired AuthService authService,
                      @Autowired ClientService clientService,
                      @Autowired VaadinAuthService vaadinAuthService) {
        H3 title = new H3("Signup form");

        TextField username = new TextField("Username");
        TextField name = new TextField("Name");

        passwordField1 = new PasswordField("Wanted password");
        passwordField2 = new PasswordField("Password again");

        Span errorMessage = new Span();

        Button submitButton = new Button("Sign up!");
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        /*
         * Build the visible layout
         */

        FormLayout formLayout = new FormLayout(title, username, name, passwordField1, passwordField2
                , errorMessage, submitButton);

        // Restrict maximum width and center on page
        formLayout.setMaxWidth("500px");
        formLayout.getStyle().set("margin", "0 auto");

        // Allow the form layout to be responsive. On device widths 0-490px we have one
        // column, then we have two. Field labels are always on top of the fields.
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("490px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));

        // These components take full width regardless if we use one column or two (it
        // just looks better that way)
        formLayout.setColspan(title, 2);
        formLayout.setColspan(errorMessage, 2);
        formLayout.setColspan(submitButton, 2);

        // Add some styles to the error message to make it pop out
        errorMessage.getStyle().set("color", "var(--lumo-error-text-color)");
        errorMessage.getStyle().set("padding", "15px 0");

//        submitButton.addClickListener(e->{
//            var client = new Client();
//            client.setName();
//            clientService.create()
//        });
        // Add the form to the page
        add(formLayout);

        binder = new BeanValidationBinder<Client>(Client.class);

        // Basic name fields that are required to fill in
        binder.forField(username).asRequired().bind("username");
        binder.forField(name).asRequired().bind("name");


        // Another custom validator, this time for passwords
        binder.forField(passwordField1).asRequired().withValidator(this::passwordValidator).bind("password");

        passwordField2.addValueChangeListener(e -> {
            enablePasswordValidation = true;
            binder.validate();
        });


        binder.setStatusLabel(errorMessage);

        // And finally the submit button
        Client client = new Client();
        submitButton.addClickListener(e -> {
            try {
                // Create empty bean to store the details into
                // Run validators and write the values to the bean
                binder.writeBean(client);
                var jwtRequest = new JwtRequest();
                jwtRequest.setPassword(client.getPassword());
                jwtRequest.setUsername(client.getUsername());
                // Call backend to store the data
                clientService.create(client);
                authService.login(jwtRequest);
                vaadinAuthService.signIn();
                vaadinAuthService.setClient(client);
                showSuccess(client);
            } catch (Exception e1) {
                showFail(client);
            }
        });

    }

    private void showFail(Client client) {
        Notification notification = Notification.show("Data wasn't saved");
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        notification.open();
    }

    /**
     * We call this method when form submission has succeeded
     */
    private void showSuccess(Client client)  {
        Notification notification = Notification.show("Data saved, welcome " + client.getName());
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        notification.open();
        passwordField1.getUI().ifPresent(ui ->
                ui.navigate("homePage"));
    }

    /**
     * Method to validate that:
     * <p>
     * 1) Password is at least 8 characters long
     * <p>
     * 2) Values in both fields match each other
     */
    private ValidationResult passwordValidator(String pass1, ValueContext ctx) {

        /*
         * Just a simple length check. A real version should check for password
         * complexity as well!
         */
        if (pass1 == null || pass1.length() < 8) {
            return ValidationResult.error("Password should be at least 8 characters long");
        }

        if (!enablePasswordValidation) {
            // user hasn't visited the field yet, so don't validate just yet, but next time.
            enablePasswordValidation = true;
            return ValidationResult.ok();
        }

        String pass2 = passwordField2.getValue();

        if (pass1 != null && pass1.equals(pass2)) {
            return ValidationResult.ok();
        }

        return ValidationResult.error("Passwords do not match");
    }


}