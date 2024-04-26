package com.example.hackathon_becoder_backend.vaadin.dataViewPage.clients;

import com.example.hackathon_becoder_backend.service.ClientService;
import com.example.hackathon_becoder_backend.vaadin.MainLayout;
import com.example.hackathon_becoder_backend.web.dto.client.ClientDto;
import com.example.hackathon_becoder_backend.web.mapper.ClientMapper;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@PageTitle("GetClientByIdView")
@Route(value = "vaadin/getClientByIdView", layout = MainLayout.class)
public class GetClientByIdView extends VerticalLayout {
    public GetClientByIdView(@Autowired ClientService service,
                             @Autowired ClientMapper clientMapper) {

        TextField id = new TextField("Client id");

        add(id);

        final Grid<ClientDto> commentsGrid = new Grid<ClientDto>(ClientDto.class);
        final Button fetchComments = new Button("Get Client by id",
                e -> commentsGrid.setItems(clientMapper.toDto(service.findById(UUID.fromString(id.getValue())))));
        fetchComments.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(fetchComments, commentsGrid);
    }
}
