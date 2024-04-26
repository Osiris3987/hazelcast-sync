package com.example.hackathon_becoder_backend.vaadin.dataViewPage.transactions;

import com.example.hackathon_becoder_backend.domain.transaction.Transaction;
import com.example.hackathon_becoder_backend.domain.transaction.TransactionType;
import com.example.hackathon_becoder_backend.service.ClientService;
import com.example.hackathon_becoder_backend.service.TransactionService;
import com.example.hackathon_becoder_backend.vaadin.MainLayout;
import com.example.hackathon_becoder_backend.vaadin.VaadinAuthService;
import com.example.hackathon_becoder_backend.web.dto.client.ClientDto;
import com.example.hackathon_becoder_backend.web.dto.transaction.TransactionDto;
import com.example.hackathon_becoder_backend.web.mapper.ClientMapper;
import com.example.hackathon_becoder_backend.web.mapper.TransactionMapper;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
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

@PageTitle("PostTransactionView")
@Route(value = "vaadin/postTransactionView", layout = MainLayout.class)
public class PostTransactionView extends VerticalLayout  implements AfterNavigationObserver {

    private final VaadinAuthService vaadinAuthService;
    private Button fetchComments;

    public PostTransactionView(@Autowired TransactionService transactionService,
                               @Autowired TransactionMapper transactionMapper,
                               @Autowired VaadinAuthService vaadinAuthService) {
        this.vaadinAuthService= vaadinAuthService;
        var transatction = new Transaction();
        TextField type = new TextField("Type");
        type.setValue("REFILL");
        NumberField amount = new NumberField("Amount");
        amount.setValue(100.0);
        TextField clientId = new TextField("clientId");
        clientId.setValue("f0caf844-5a61-43a7-b1c2-e66971f5e08a");
        TextField legalEntityId = new TextField("legalEntityId");
        legalEntityId.setValue("3f71f34c-be0f-46eb-853c-fbc1fd60b284");
        FormLayout formLayout = new FormLayout(type, amount, clientId, legalEntityId);

        // Restrict maximum width and center on page
        formLayout.setMaxWidth("500px");
        formLayout.getStyle().set("margin", "0 auto");

        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
                new FormLayout.ResponsiveStep("490px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP));

        add(formLayout);

        final Grid<TransactionDto> commentsGrid = new Grid<TransactionDto>(TransactionDto.class);

         fetchComments = new Button("Post transaction data",
                e -> {
                    transatction.setAmount(BigDecimal.valueOf(amount.getValue()));
                    transatction.setType(TransactionType.valueOf(type.getValue()));
                    commentsGrid.setItems(transactionMapper.toDto(transactionService.create(transatction,(UUID.fromString(clientId.getValue())),(UUID.fromString(legalEntityId.getValue())))));
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