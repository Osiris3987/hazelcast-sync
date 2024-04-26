package com.example.hackathon_becoder_backend.vaadin;


import com.example.hackathon_becoder_backend.service.AuthService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;

@Route(value = "", layout = MainLayout.class)
public class StartView extends VerticalLayout {
    public StartView() {
        StreamResource imageResource = new StreamResource("cat1.png",
                () -> getClass().getResourceAsStream("/images/cat2.jpg"));

        Image image = new Image(imageResource, "Cat");

        H2 title = new H2("</beCoder> 2024");
        title.setMinHeight("5px");
        H3 title2 = new H3("Команда \"Наш Слон\"");
        title2.setMinHeight("5px");
        H4 title4 = new H4("Микросервис с собственной базой данных для контроля баланса на счету юридического лица");
        FormLayout formLayout = new FormLayout(title, title2,title4,image);

        formLayout.setMaxWidth("800px");
        formLayout.getStyle().set("margin", "auto auto");
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("490px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));

        formLayout.setColspan(title2, 2);
        formLayout.setColspan(title4, 2);
        formLayout.setColspan(title, 2);
        formLayout.setColspan(image, 2);


        add(formLayout);
    }
}
