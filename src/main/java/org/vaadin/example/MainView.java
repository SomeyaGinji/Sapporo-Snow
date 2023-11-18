package org.vaadin.example;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.web.client.RestTemplate;


/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and use @Route
 * annotation to announce it in a URL as a Spring managed bean.
 * <p>
 * A new instance of this class is created for every new user and every browser
 * tab/window.
 * <p>
 * The main view contains a text field for getting the user name and a button
 * that shows a greeting message in a notification.
 */

@Route
public class MainView extends VerticalLayout {

    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     *
     * @param service
     *            The message service. Automatically injected Spring managed
     *            bean.
     */

    private final String apiUrl = "http://localhost:5000/api/data";  // PythonサーバーのAPIエンドポイントに合わせて変更してください

    public MainView() {

        // Use TextField for standard text input
//        TextField textField = new TextField("Your name");
//        textField.addClassName("bordered");

        // Button click listeners can be defined as lambda expressions
        Button button = new Button("雪かきしたい！", e -> {
            getUI().ifPresent(ui -> ui.navigate("select-snow-shoveling"));
        });

        // Theme variants give you predefined extra styles for components.
        // Example: Primary button has a more prominent look.
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // You can specify keyboard shortcuts for buttons.
        // Example: Pressing enter in this view clicks the Button.
        button.addClickShortcut(Key.ENTER);

        // Use custom CSS classes to apply styling. This is defined in
        // styles.css.
        addClassName("centered-content");

        add(button);

        Button button2 = new Button("Get Data from Python");
        button2.addClickListener(event -> {
            // RestTemplateを使用してPythonサーバーからデータを取得
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(apiUrl, String.class);
            Notification.show("Data from Python: " + response);
        });

        add(button2);

//        Button button3 = new Button("Get Current Location", event -> {
//            // JavaScriptを呼び出して現在位置を取得
//            getPage().executeJs("navigator.geolocation.getCurrentPosition("
//                    + "function(position) {"
//                    + "  var latitude = position.coords.latitude;"
//                    + "  var longitude = position.coords.longitude;"
//                    + "  $0.displayLocation(latitude, longitude);"
//                    + "},"
//                    + "function(error) {"
//                    + "  $0.displayError(error.message);"
//                    + "});", this);
//        });
//
//        add(button3);

    }

    // JavaScriptから呼び出されるメソッド: 位置情報を表示
    public void displayLocation(double latitude, double longitude) {
        Notification.show("Current Location: Latitude " + latitude + ", Longitude " + longitude);
    }

    // JavaScriptから呼び出されるメソッド: エラーメッセージを表示
    public void displayError(String errorMessage) {
        Notification.show("Error getting location: " + errorMessage);
    }


}
