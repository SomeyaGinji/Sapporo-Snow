package org.vaadin.example;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.select.*;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.server.VaadinSession;
import org.vaadin.example.data.ShovelingPlace;
import org.vaadin.example.service.SnowService;

@Route(value = "select-snow-shoveling")
public class SelectSnowShovelingView extends VerticalLayout{

    private SnowService snowService;

    public SelectSnowShovelingView(SnowService snowService){

        this.snowService = snowService;

        // Topページへ戻るボタン
        Button backButton = new Button("戻る");
        backButton.addClickListener(click -> {
            getUI().ifPresent(ui -> ui.navigate(""));
        });

        add(backButton);

        //区のプロパティ
        Select<String> select = new Select<>();
        select.setLabel("区を選択してください");
        select.setItems("厚別区", "北区", "清田区", "白石区", "中央区", "手稲区", "豊平区", "西区", "東区", "南区");
        VerticalLayout UI = new VerticalLayout();

        //地名のプロパティ
        TextField ward = new TextField();
        ward.setLabel("地名を入力してください");

        TextField jou=CreateAddressField("条");
        TextField chou=CreateAddressField("丁目");
        TextField ban=CreateAddressField("番");
        TextField gou=CreateAddressField("号");
        TextField other=CreateAddressField("その他");

        Button addButton = new Button("決定");
        addButton.addClickListener(click -> {
            //決定ボタン押下時の処理
            ShovelingPlace shovelingPlace = new ShovelingPlace();
            shovelingPlace.setWard(select.getValue()); //区
            shovelingPlace.setTown(ward.getValue()); //町（地名）
            shovelingPlace.setJyo(jou.getValue());
            shovelingPlace.setTyo(chou.getValue());
            shovelingPlace.setBan(ban.getValue());
            shovelingPlace.setGou(gou.getValue());
            shovelingPlace.setOthers(other.getValue());
            // 予想降雪量snowfallをセッションから取得
            Double snowfall = (Double) VaadinSession.getCurrent().getAttribute("snowfall");
            System.out.println("取得した降雪量："+snowfall);
            snowService.insertShovelingPlace(shovelingPlace); //DBに雪かき場所を登録
            System.out.println("DBに雪かき場所を追加完了");
            // shovelingPlace をセッションに保存
            VaadinSession.getCurrent().setAttribute("shovelingPlace", shovelingPlace);
            getUI().ifPresent(ui -> ui.navigate("check-shoveling-place"));
        }
        );

        add(
                new H1("雪かきして欲しい場所は"),
                UI,
                new HorizontalLayout(
                        select,
                        ward
                ),


                new HorizontalLayout(
                    jou,chou,ban,gou,other
                ),
                addButton
        );
    }
    private TextField CreateAddressField(String label){
        TextField textField = new TextField();

        if(label.equals("その他")){
            textField.setWidth("50%");
        }else {
            Div div = new Div();
            div.setText(label);
            textField.setSuffixComponent(div);
            textField.setWidth("15%");
        }

        return textField;
    }

}
