package com.example.hackathon_becoder_backend.vaadin.dataViewPage.clients;

import com.example.hackathon_becoder_backend.service.ClientService;
import com.example.hackathon_becoder_backend.vaadin.MainLayout;
import com.example.hackathon_becoder_backend.vaadin.VaadinAuthService;
import com.example.hackathon_becoder_backend.web.dto.client.ClientDto;
import com.example.hackathon_becoder_backend.web.mapper.ClientMapper;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("GetAllClientsView")
@Route(value = "vaadin/getAllClientsView", layout = MainLayout.class)
public class GetAllClientsView extends Main implements AfterNavigationObserver {
    private final VaadinAuthService vaadinAuthService;
    private Button fetchComments;
    public GetAllClientsView(@Autowired ClientService service,
                       @Autowired ClientMapper clientMapper,
                             @Autowired VaadinAuthService vaadinAuthService) {
        this.vaadinAuthService= vaadinAuthService;
        final Grid<ClientDto> commentsGrid = new Grid<ClientDto>(ClientDto.class);

        fetchComments = new Button("Get all clients",
                e -> commentsGrid.setItems(clientMapper.toDtoList(service.findAll())));
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