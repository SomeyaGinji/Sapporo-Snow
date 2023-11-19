package org.vaadin.example;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.vaadin.example.data.ShovelingPlace;

@Route(value = "check-shoveling-place")
public class CheckShovelingPlaceView extends VerticalLayout {

    // shovelingPlace をセッションから取得
    ShovelingPlace shovelingPlace = (ShovelingPlace) VaadinSession.getCurrent().getAttribute("shovelingPlace");

    public CheckShovelingPlaceView(){

        // 入力された雪かき場所を画面に表示する
        String str = "雪かき場所として "+
                shovelingPlace.getWard() + " "+
                shovelingPlace.getTown() + " " +
                shovelingPlace.getJyo() + "条 " +
                shovelingPlace.getTyo() + "丁目 " +
                shovelingPlace.getBan() + "番 " +
                shovelingPlace.getGou() + "号 " +
                shovelingPlace.getOthers() + "を登録しました！";

        add(new H1(str));

        // Topページへ遷移するボタン
        Button button = new Button("Topへ");
        button.addClickListener(click -> {
            getUI().ifPresent(ui -> ui.navigate(""));
        });

        add(button);

    }

}
