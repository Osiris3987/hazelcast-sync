package com.example.hackathon_becoder_backend.vaadin.dataViewPage.legalEntity;

import com.example.hackathon_becoder_backend.service.LegalEntityService;
import com.example.hackathon_becoder_backend.vaadin.MainLayout;
import com.example.hackathon_becoder_backend.vaadin.VaadinAuthService;
import com.example.hackathon_becoder_backend.web.dto.legalEntity.LegalEntityDto;
import com.example.hackathon_becoder_backend.web.mapper.LegalEntityMapper;
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

@PageTitle("GetLegalEntitiesByClient")
@Route(value = "vaadin/getLegalEntitiesByClient", layout = MainLayout.class)
public class GetLegalEntitiesByClient extends VerticalLayout implements AfterNavigationObserver {

    private final VaadinAuthService vaadinAuthService;
    private Button fetchComments;
    public GetLegalEntitiesByClient(@Autowired LegalEntityService service,
                                    @Autowired LegalEntityMapper legalEntityMapper,
                                    @Autowired VaadinAuthService vaadinAuthService) {
        this.vaadinAuthService= vaadinAuthService;
        TextField id = new TextField("Get transaction");

        add(id);

        final Grid<LegalEntityDto> commentsGrid = new Grid<LegalEntityDto>(LegalEntityDto.class);
        fetchComments = new Button("Get LegalEntity by id",
                e -> commentsGrid.setItems(legalEntityMapper.toDtoList(service.getAllLegalEntitiesByClientId(UUID.fromString(id.getValue())))));
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
