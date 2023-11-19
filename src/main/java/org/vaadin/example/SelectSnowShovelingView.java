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

@Route(value = "select-snow-shoveling")
public class SelectSnowShovelingView extends VerticalLayout{

    public SelectSnowShovelingView(){
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

        //希望積雪量に関するプロパティ
        NumberField snow=new NumberField();
        Div unit = new Div();
        snow.setStepButtonsVisible(true);
        snow.setLabel("希望除雪量");
        snow.setMin(0);
        unit.setText("cm");
        snow.setSuffixComponent(unit);

        Button addButton = new Button("決定");
        addButton.addClickListener(click -> {
            //決定ボタン処理
                    System.out.println(jou.getValue());
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
                snow,
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
