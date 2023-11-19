package org.vaadin.example;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.grid.Grid.*;
import org.vaadin.example.data.ShovelingPlace;
import org.vaadin.example.repository.SnowRepository;
import org.vaadin.example.service.SnowService;
import com.vaadin.flow.data.selection.MultiSelect;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import javax.sql.DataSource;
import java.awt.*;
import java.util.List;
import com.vaadin.flow.component.dialog.Dialog;
import java.util.Set;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
@Route(value = "show-snow-shoveling")
public class ShowShovellingView extends VerticalLayout {
    private SnowService snowService;

    ShowShovellingView(SnowService snowService){
        this.snowService=snowService;

        Button addButton = new Button("決定");

        Grid<ShovelingPlace> grid = new Grid<>();
        grid.setSelectionMode(SelectionMode.SINGLE);
        SingleSelect<Grid<ShovelingPlace>, ShovelingPlace> personSelect = grid.asSingleSelect();
        grid.addColumn(ShovelingPlace::getWard).setHeader("区");
        grid.addColumn(ShovelingPlace::getTown).setHeader("町");
        grid.addColumn(ShovelingPlace::getBan).setHeader("条");
        grid.addColumn(ShovelingPlace::getTyo).setHeader("丁");
        grid.addColumn(ShovelingPlace::getBan).setHeader("番地");
        grid.addColumn(ShovelingPlace::getGou).setHeader("号");
        //grid.addColumn(ShovelingPlace::getSnow).setHeader("希望除雪量");
        grid.addColumn(ShovelingPlace::getOthers).setHeader("その他");
        List<ShovelingPlace> infomations = snowService.getShovelingPlaceList();
        grid.setItems(infomations);

        addButton.addClickListener(click -> {
                    //決定ボタン処理
            if(personSelect.isEmpty()){
                //未選択なら何もしない
            }else {
                ShovelingPlace selected=personSelect.getValue();
                long Id=selected.getId();

                ConfirmDialog dialog = new ConfirmDialog();
                String msg=createDialogText(selected);
                dialog.setHeader("確認画面");
                dialog.setText(msg);

                dialog.setCancelable(true);
                dialog.setCancelText("戻る");

                dialog.setConfirmText("決定");

                dialog.open();

                dialog.addConfirmListener(confirmEvent -> snowService.updateAvailability(Id));

            }
                }
        );
        add(    new H1("場所を選択"),
                grid,
                addButton
        );
    }
    String createDialogText(ShovelingPlace shovelingPlace){
        String msg="";
        msg=msg+shovelingPlace.getWard()+"区";
        if(shovelingPlace.getTown()!=null){msg=msg+shovelingPlace.getTown();}
        if(shovelingPlace.getJyo()!=null){msg=msg+shovelingPlace.getJyo()+"条";}
        if(shovelingPlace.getTyo()!=null){msg=msg+shovelingPlace.getTyo()+"丁";}
        if(shovelingPlace.getBan()!=null){msg=msg+shovelingPlace.getBan()+"番";}
        if(shovelingPlace.getGou()!=null){msg=msg+shovelingPlace.getGou()+"号";}
        if(shovelingPlace.getOthers()!=null){msg=msg+shovelingPlace.getOthers();}
        //if(shovelingPlace.getSnow()!=null){msg=msg+"\r\n希望除雪量:"+shovelingPlace.getSnow()+"cm"}
        return msg;
    }
}
