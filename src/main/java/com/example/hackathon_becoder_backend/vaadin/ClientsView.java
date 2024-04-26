package com.example.hackathon_becoder_backend.vaadin;


import com.example.hackathon_becoder_backend.service.ClientService;
import com.example.hackathon_becoder_backend.web.dto.client.ClientDto;
import com.example.hackathon_becoder_backend.web.mapper.ClientMapper;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("ClientsView")
@Route(value = "vaadin/clientsView", layout = MainLayout.class)
public class ClientsView extends Main {

    public ClientsView(@Autowired ClientService service,
                       @Autowired ClientMapper clientMapper) {
        final Grid<ClientDto> commentsGrid = new Grid<ClientDto>(ClientDto.class);

        final Button fetchComments = new Button("Fetch all comments",
                e -> commentsGrid.setItems(clientMapper.toDtoList(service.findAll())));
        fetchComments.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(fetchComments, commentsGrid);

    }

}