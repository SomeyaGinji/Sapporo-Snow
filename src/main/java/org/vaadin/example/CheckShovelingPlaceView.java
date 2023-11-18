package org.vaadin.example;

import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import org.vaadin.example.data.ShovelingPlace;

@Route(value = "check-shoveling-place")
public class CheckShovelingPlaceView {

    // shovelingPlace をセッションから取得
    ShovelingPlace shovelingPlace = (ShovelingPlace) VaadinSession.getCurrent().getAttribute("shovelingPlace");

    public CheckShovelingPlaceView(){
        // 入力された雪かき場所を画面に表示する
        // 入力された雪かき場所は以下のようにしてとってこれる
        System.out.println(shovelingPlace.getBan());

        // Topページへ遷移するボタン


    }

}
