package org.vaadin.example;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.UI;

@Route(value = "select-snow-shoveling")
public class SelectSnowShovelingView {

    private TextField name;
    private Button sayHello;

    public SelectSnowShovelingView(){
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });
        sayHello.addClickShortcut(Key.ENTER);

        UI.getCurrent().add(name, sayHello);

    }

}
