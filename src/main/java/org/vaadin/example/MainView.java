package org.vaadin.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
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

@Route(value = "")
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

    private final String apiUrl = "http://localhost:5000";  // PythonサーバーのAPIエンドポイントに合わせて変更してください

    public MainView() {

        // Python側から送られるJSONデータ取得のための準備
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(apiUrl, String.class);

        // JSONデータを処理
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);

            // "snowfall"キーの下の値を取得
            String snowfall = jsonNode.get("24時間降雪量 現在値(cm)").asText();
            // ここで取得したデータを使用する処理を追加
            add(new H1("明日の予測降雪量は"+snowfall+"cmです。"));
            // 予想降雪量の値をセッションに保存
            VaadinSession.getCurrent().setAttribute("snowfall", Double.parseDouble(snowfall));

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Button click listeners can be defined as lambda expressions
        Button button = new Button("雪かきしてほしい！", e -> {
            getUI().ifPresent(ui -> ui.navigate("select-snow-shoveling"));
        });

        Button button1 = new Button("雪かきしたい！", e -> {
            getUI().ifPresent(ui -> ui.navigate("show-snow-shoveling"));
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

        add(button,button1);

    }

}
