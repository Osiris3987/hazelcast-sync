package com.example.hackathon_becoder_backend.vaadin.dataViewPage.clients;

import com.example.hackathon_becoder_backend.domain.transaction.Transaction;
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
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;


@PageTitle("GetTransactionByClientIDView")
@Route(value = "vaadin/getTransactionByClientIDView", layout = MainLayout.class)
public class GetTransactionByClientIDView extends VerticalLayout implements AfterNavigationObserver {

    private final VaadinAuthService vaadinAuthService;
    private Button fetchComments;
    public GetTransactionByClientIDView(@Autowired TransactionService service,
                             @Autowired TransactionMapper transactionMapper,
                                        @Autowired VaadinAuthService vaadinAuthService) {
        this.vaadinAuthService= vaadinAuthService;

        TextField id = new TextField("Get Transaction by client id");
        id.setValue("f0caf844-5a61-43a7-b1c2-e66971f5e08a");
        add(id);

        final Grid<TransactionDto> commentsGrid = new Grid<TransactionDto>(TransactionDto.class);
          fetchComments = new Button("Get Transaction by Client id",
                e -> commentsGrid.setItems(transactionMapper.toDtoList(service.getAllByClientId(UUID.fromString(id.getValue())))));
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
