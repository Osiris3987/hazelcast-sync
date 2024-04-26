package com.example.hackathon_becoder_backend.vaadin.dataViewPage.legalEntity;

import com.example.hackathon_becoder_backend.domain.legal_entity.LegalEntity;
import com.example.hackathon_becoder_backend.service.LegalEntityService;
import com.example.hackathon_becoder_backend.vaadin.MainLayout;
import com.example.hackathon_becoder_backend.vaadin.VaadinAuthService;
import com.example.hackathon_becoder_backend.web.dto.legalEntity.LegalEntityDto;
import com.example.hackathon_becoder_backend.web.mapper.LegalEntityMapper;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.UUID;

@PageTitle("PostLegalEntityAssignView")
@Route(value = "vaadin/postLegalEntityAssignView", layout = MainLayout.class)
public class PostLegalEntityAssignView extends VerticalLayout implements AfterNavigationObserver {

    private final VaadinAuthService vaadinAuthService;
    private Button fetchComments;

    public PostLegalEntityAssignView(@Autowired LegalEntityService legalEntityService,
                               @Autowired LegalEntityMapper legalEntityMapper,
                                     @Autowired VaadinAuthService vaadinAuthService) {
        this.vaadinAuthService= vaadinAuthService;
        TextField legalEntityId = new TextField("legalEntityId");
        legalEntityId.setValue("123e4567-e89b-12d3-a456-426655440000");
        TextField clientId = new TextField("clientId");
        clientId.setValue("123e4567-e89b-12d3-a456-426655440001");

        FormLayout formLayout = new FormLayout(legalEntityId, clientId);

        // Restrict maximum width and center on page
        formLayout.setMaxWidth("500px");
        formLayout.getStyle().set("margin", "0 auto");

        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("490px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));

        add(formLayout);

        final Grid<LegalEntityDto> commentsGrid = new Grid<LegalEntityDto>(LegalEntityDto.class);

          fetchComments = new Button("Post data",
                e -> {
                    commentsGrid.setItems(legalEntityMapper.toDto(legalEntityService.assignClientToLegalEntity(UUID.fromString(legalEntityId.getValue()),UUID.fromString(clientId.getValue()))));
                });
        fetchComments.addThemeVariants(ButtonVariant.LUMO_PRIMARY);


        add(fetchComments, commentsGrid);

    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        if(!vaadinAuthService.isAuth()){
            Notification notification = Notification.show("You aren't sign!");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.open();
            fetchComments.getUI().ifPresent(ui ->
                    ui.navigate(""));

        }
    }

}