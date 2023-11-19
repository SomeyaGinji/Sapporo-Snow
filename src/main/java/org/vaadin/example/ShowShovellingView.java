package org.vaadin.example;

import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.grid.*;
import com.vaadin.flow.component.grid.Grid.*;
import com.vaadin.flow.server.VaadinSession;
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
        grid.addColumn(ShovelingPlace::getSnow).setHeader("希望除雪量");
        grid.addColumn(ShovelingPlace::getOthers).setHeader("その他");

        // 予想降雪量snowfallをセッションから取得
        Double snowfall = (Double) VaadinSession.getCurrent().getAttribute("snowfall");
        System.out.println("取得した降雪量："+snowfall);
        snowfall = 10.0; //テスト用
        List<ShovelingPlace> infomations = snowService.getShovelingPlaceList(snowfall.longValue());
        grid.setItems(infomations);
        GridListDataView<ShovelingPlace> dataView=grid.setItems(infomations);

        //検索フィールドのプロパティ
        TextField searchField = new TextField();
        searchField.setWidth("50%");
        searchField.setPlaceholder("Search");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> dataView.refreshAll());

        dataView.addFilter(e -> {
            String searchTerm = searchField.getValue().trim();

            if (searchTerm.isEmpty())
                return true;

            boolean matchesWard = matchesTerm(e.getWard(), searchTerm);
            boolean matchesTown = matchesTerm(e.getTown(), searchTerm);
            boolean matchesJyo = matchesTerm(e.getJyo(), searchTerm);
            boolean matchesTyo = matchesTerm(e.getTyo(), searchTerm);
            boolean matchesBan = matchesTerm(e.getBan(), searchTerm);
            boolean matchesGou =matchesTerm(e.getGou(),searchTerm);

            return matchesWard || matchesTown || matchesJyo || matchesTyo || matchesBan || matchesGou;
        });

        addButton.addClickListener(click -> {
            //決定ボタン処理
            if (personSelect.isEmpty()) {
                //未選択なら何もしない
            } else {
                ShovelingPlace selected = personSelect.getValue();
                long Id = selected.getId();

                // 選択された雪かき場所をセッションに保存
                VaadinSession.getCurrent().setAttribute("selectedPlace", selected);

                ConfirmDialog dialog = new ConfirmDialog();
                String msg = createDialogText(selected);
                dialog.setHeader("確認画面");
                dialog.setText(msg);
                dialog.setCancelable(true);
                dialog.setCancelText("戻る");
                dialog.setConfirmText("決定");
                dialog.open();

                dialog.addConfirmListener(confirmEvent -> {
                    snowService.updateAvailability(Id);
                    // 緑色のスタイルでNotificationを追加
                    Notification notification = Notification.show(msg + "の雪かきの依頼を受けました");
                    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    getUI().ifPresent(ui -> ui.navigate(""));
                });

            }

        });
        add(    new H1("場所を選択"),
                searchField,
                grid,
                addButton
        );
    }
    String createDialogText(ShovelingPlace shovelingPlace){
        String msg="";
        msg=msg+shovelingPlace.getWard();
        if(shovelingPlace.getTown()!=null){msg=msg+shovelingPlace.getTown();}
        if(shovelingPlace.getJyo()!=null){msg=msg+shovelingPlace.getJyo()+"条";}
        if(shovelingPlace.getTyo()!=null){msg=msg+shovelingPlace.getTyo()+"丁";}
        if(shovelingPlace.getBan()!=null){msg=msg+shovelingPlace.getBan()+"番";}
        if(shovelingPlace.getGou()!=null){msg=msg+shovelingPlace.getGou()+"号";}
        if(shovelingPlace.getOthers()!=null){msg=msg+shovelingPlace.getOthers();}
        if(shovelingPlace.getSnow()!=null){msg=msg+"\r\n希望除雪量(cm):"+shovelingPlace.getSnow()+"cm";}
        return msg;
    }
    private boolean matchesTerm(String value, String searchTerm) {
        try {
            return value.toLowerCase().contains(searchTerm.toLowerCase());
        }
        catch (NullPointerException e){
            return false;
        }
    }
}
