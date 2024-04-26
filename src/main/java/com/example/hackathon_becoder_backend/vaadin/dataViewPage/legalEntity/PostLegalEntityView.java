package com.example.hackathon_becoder_backend.vaadin.dataViewPage.legalEntity;

import com.example.hackathon_becoder_backend.domain.legal_entity.LegalEntity;
import com.example.hackathon_becoder_backend.domain.transaction.Transaction;
import com.example.hackathon_becoder_backend.domain.transaction.TransactionType;
import com.example.hackathon_becoder_backend.service.LegalEntityService;
import com.example.hackathon_becoder_backend.service.TransactionService;
import com.example.hackathon_becoder_backend.vaadin.MainLayout;
import com.example.hackathon_becoder_backend.vaadin.VaadinAuthService;
import com.example.hackathon_becoder_backend.web.dto.legalEntity.LegalEntityDto;
import com.example.hackathon_becoder_backend.web.dto.transaction.TransactionDto;
import com.example.hackathon_becoder_backend.web.mapper.LegalEntityMapper;
import com.example.hackathon_becoder_backend.web.mapper.TransactionMapper;
import com.vaadin.flow.component.Component;
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

@PageTitle("PostLegalEntityView")
@Route(value = "vaadin/postLegalEntityView", layout = MainLayout.class)
public class PostLegalEntityView extends VerticalLayout implements AfterNavigationObserver {

    private final VaadinAuthService vaadinAuthService;
    private Button fetchComments;

    public PostLegalEntityView(@Autowired LegalEntityService legalEntityService,
                               @Autowired LegalEntityMapper legalEntityMapper,
                               @Autowired VaadinAuthService vaadinAuthService) {
        this.vaadinAuthService= vaadinAuthService;
        var legalEntity = new LegalEntity();
        TextField name = new TextField("Name");
        name.setValue("ABC Corporation");
        NumberField balance = new NumberField("Balanse");
        balance.setValue(10000.5);
        TextField owner = new TextField("clientId");
        owner.setValue("yfnf72@gmail.com");

        FormLayout formLayout = new FormLayout(name, balance, owner);

        // Restrict maximum width and center on page
        formLayout.setMaxWidth("500px");
        formLayout.getStyle().set("margin", "0 auto");

        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("490px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));

        add(formLayout);

        final Grid<LegalEntityDto> commentsGrid = new Grid<LegalEntityDto>(LegalEntityDto.class);

         fetchComments = new Button("Post data",
                e -> {
                    legalEntity.setBalance(BigDecimal.valueOf(balance.getValue()));
                    legalEntity.setName(name.getValue());
                    legalEntity.setOwner(owner.getValue());
                    commentsGrid.setItems(legalEntityMapper.toDto(legalEntityService.create(legalEntity,owner.getValue())));
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