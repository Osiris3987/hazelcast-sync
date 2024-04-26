package com.example.hackathon_becoder_backend.vaadin;

import com.example.hackathon_becoder_backend.vaadin.dataViewPage.clients.GetAllClientsView;
import com.example.hackathon_becoder_backend.vaadin.dataViewPage.clients.GetClientByIdView;
import com.example.hackathon_becoder_backend.vaadin.dataViewPage.clients.GetTransactionByClientIDView;
import com.example.hackathon_becoder_backend.vaadin.dataViewPage.legalEntity.GetLegalEntitiesByClient;
import com.example.hackathon_becoder_backend.vaadin.dataViewPage.legalEntity.GetTransactionsByLegalEntityView;
import com.example.hackathon_becoder_backend.vaadin.dataViewPage.legalEntity.PostLegalEntityAssignView;
import com.example.hackathon_becoder_backend.vaadin.dataViewPage.legalEntity.PostLegalEntityView;
import com.example.hackathon_becoder_backend.vaadin.dataViewPage.transactions.PostTransactionView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.RouterLink;
import org.springframework.beans.factory.annotation.Autowired;

@CssImport("styles/style.css")
public class MainLayout extends AppLayout implements AfterNavigationObserver {

    private final H1 pageTitle;
    private RouterLink start;
    private RouterLink login;
    private RouterLink signUp;
    private final Button buttonLogOut;

    private UnorderedList list = new UnorderedList();


    public MainLayout(
            @Autowired
            VaadinAuthService vaadinAuthService
    ) {
        buttonLogOut = new Button("Log out");
        buttonLogOut.getStyle().set("background-color", "white");
        buttonLogOut.getStyle().set("color", "black");
        buttonLogOut.getStyle().set("font-weight", "bold");
        buttonLogOut.addClickListener(buttonClickEvent -> {
                    vaadinAuthService.logOut();
                    buttonLogOut.getUI().ifPresent(ui ->
                            ui.navigate(""));
                }
        );
        start = new RouterLink("Start Page", StartView.class);
        login = new RouterLink("Login Page", LoginView.class);
        signUp = new RouterLink("Sign Up Page", SignUpView.class);
        if (!vaadinAuthService.isAuth()) {
            start.setEnabled(true);
            login.setEnabled(true);
            signUp.setEnabled(true);

            list = new UnorderedList(new ListItem(start), new ListItem(login), new ListItem(signUp));
        } else {
            start.setEnabled(false);
            login.setEnabled(false);
            signUp.setEnabled(false);
            initTransactionApi();
            initClientApi();
            initLegalEntityApi();
        }
        final Nav navigation = new Nav();
        H2 navTitle = new H2("Решения");
        navTitle.getStyle().set("text-align", "center");
        navTitle.getStyle().set("margin-top", "15px");
        navTitle.getStyle().set("color", "black");
        navigation.add(navTitle);
        navigation.add(list);
        addToDrawer(navigation);
        setPrimarySection(Section.DRAWER);
        setDrawerOpened(false);

        // Header
        pageTitle = new H1("Start Page");
        final Header header = new Header(new DrawerToggle());
        if (vaadinAuthService.isAuth()) {
            header.add(buttonLogOut);
        }
        addToNavbar(header);
    }

    private RouterLink[] getRouterLinks() {
        return new RouterLink[]{start, login, signUp};
    }

    private void initClientApi() {
        RouterLink getAllClients = new RouterLink("getAllClients", GetAllClientsView.class);
        getAllClients.getStyle().set("color", "black");
//        RouterLink deleteClient =  new RouterLink("deleteClient", DeleteClientView.class);
        RouterLink getClientById = new RouterLink("getClientById", GetClientByIdView.class);
        getClientById.getStyle().set("color", "black");
        RouterLink getTransactionByClientID = new RouterLink("getTransactionByClientID", GetTransactionByClientIDView.class);
        getTransactionByClientID.getStyle().set("color", "black");
        list.add(new ListItem(getAllClients));
//        list.add(deleteClient);
        list.add(new ListItem(getClientById));
        list.add(new ListItem(getTransactionByClientID));
    }

    private void initTransactionApi() {
        RouterLink postTransaction = new RouterLink("postTransaction", PostTransactionView.class);
        postTransaction.getStyle().set("color", "black");
        list.add(new ListItem(postTransaction));
    }

    //
    private void initLegalEntityApi() {
        RouterLink postLegalEntity = new RouterLink("postLegalEntity", PostLegalEntityView.class);
        postLegalEntity.getStyle().set("color", "black");
//        RouterLink deleteLegalEntity =  new RouterLink("deleteLegalEntity", DeleteLegalEntityView.class);
        RouterLink postLegalEntityAssign = new RouterLink("postLegalEntityAssign", PostLegalEntityAssignView.class);
        postLegalEntityAssign.getStyle().set("color", "black");
        RouterLink getTransactionsByLegalEntity = new RouterLink("getTransactionsByLegalEntity", GetTransactionsByLegalEntityView.class);
        getTransactionsByLegalEntity.getStyle().set("color", "black");
        RouterLink getLegalEntitiesByClient = new RouterLink("getLegalEntitiesByClient", GetLegalEntitiesByClient.class);
        getLegalEntitiesByClient.getStyle().set("color", "black");
        list.add(new ListItem(postLegalEntity));
//        list.add(deleteLegalEntity);
        list.add(new ListItem(postLegalEntityAssign));
        list.add(new ListItem(getTransactionsByLegalEntity));
        list.add(new ListItem(getLegalEntitiesByClient));
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        for (final RouterLink routerLink : getRouterLinks()) {
            routerLink.getStyle().set("color", "black");
            if (routerLink.getHighlightCondition().shouldHighlight(routerLink, event)) {
                pageTitle.setText(routerLink.getText());
            }
        }


    }
}