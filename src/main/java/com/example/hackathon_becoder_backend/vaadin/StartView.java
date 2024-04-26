package com.example.hackathon_becoder_backend.vaadin;


import com.example.hackathon_becoder_backend.service.AuthService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;

@Route(value = "", layout = MainLayout.class)
public class StartView extends VerticalLayout{
    public StartView(){
        H2 title = new H2();
        Button loginButton = new Button("Login in");
        Button signUpButton = new Button("Sign up");
        FormLayout formLayout = new FormLayout(title, loginButton, signUpButton);

        formLayout.setMaxWidth("500px");
        formLayout.getStyle().set("margin", "0 auto");
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("490px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));


        formLayout.setColspan(title, 2);

        loginButton.addClickListener(e ->
                loginButton.getUI().ifPresent(ui ->
                        ui.navigate("login"))
        );

        signUpButton.addClickListener(e ->
                signUpButton.getUI().ifPresent(ui ->
                        ui.navigate("signUp"))
        );

        add(formLayout);
    }
}
