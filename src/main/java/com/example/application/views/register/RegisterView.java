package com.example.application.views.register;

import com.example.application.data.service.FirebaseService;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import java.util.concurrent.ExecutionException;

@PageTitle("Register")
@Route(value = "register")
@RouteAlias("register")
public class RegisterView extends VerticalLayout {
    public RegisterView(FirebaseService firebaseService) throws ExecutionException, InterruptedException {
        EmailField email = new EmailField("Email");
        PasswordField password = new PasswordField("Password");
        PasswordField password_repeat = new PasswordField("Repeat password");
        Span denied = new Span("Denied");
        denied.getElement().getThemeList().add("badge error");
        denied.setVisible(false);


        Button register = new Button("Login", event -> {
            denied.setVisible(false);
            if (password.equals(password_repeat)){
                denied.getElement().setText("Passwords don't match");
                denied.setVisible(true);

            } else {
                try {
                    firebaseService.createUser(email.getValue(), password.getValue());
                    UI.getCurrent().navigate("income");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            /**/

        });
        register.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");

        HorizontalLayout layout = new HorizontalLayout();

        FormLayout formLayout = new FormLayout();
        formLayout.setMaxWidth("400px");
        formLayout.setMaxHeight("200px");
        formLayout.add(email, password, password_repeat, register);

        HorizontalLayout footer = new HorizontalLayout();
        Span newUser = new Span("Already have an account?");
        Anchor login = new Anchor();
        login.setText("Sign in");
        //register.getElement().addEventListener(e -> register.getUI().ifPresent(ui -> ui.navigate("register")));
        footer.add(newUser, login);

        layout.setSizeFull();
        layout.setSizeFull();
        layout.add(formLayout);
        layout.setMaxHeight("300px");
        layout.setJustifyContentMode(JustifyContentMode.CENTER);

        add(new H1("Register"), layout, footer, denied);

    }
}
