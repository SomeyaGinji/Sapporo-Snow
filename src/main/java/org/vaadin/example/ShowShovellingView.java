package org.vaadin.example;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.grid.*;
import org.vaadin.example.data.ShovelingPlace;

@Route(value = "show-snow-shoveling")
public class ShowShovellingView extends VerticalLayout {
    ShowShovellingView(){
        VerticalLayout todosList = new VerticalLayout();
        TextField taskField = new TextField();

        add(
                todosList,
                new HorizontalLayout(
                        taskField
                )
        );
/*
        Grid<ShovelingPlace> grid = new Grid<>(ShovelingPlace.class, false);
        grid.addColumn(ShovelingPlace::getTown).setHeader("First name");
        grid.addColumn(ShovelingPlace::get).setHeader("Last name");
        grid.addColumn(ShovelingPlace::getEmail).setHeader("Email");
        grid.addColumn(ShovelingPlace::getProfession).setHeader("Profession");

        List<Person> people = DataService.getPeople();
        grid.setItems(people);

 */
    }
}
