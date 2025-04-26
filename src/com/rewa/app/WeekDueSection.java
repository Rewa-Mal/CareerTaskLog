package com.rewa.app;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class WeekDueSection {

    private Map<DayOfWeek, ListView<Task>> dailyTaskLists = new HashMap<>();

    public VBox getSection() {
        // Section title
        Label title = new Label("📅 Week-Due Tasks");
        title.setFont(Font.font("Arial", 18));

        VBox rows = new VBox(10);

        for (DayOfWeek day : List.of(
                DayOfWeek.SUNDAY,
                DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY)
            ) {

            Label dayLabel = new Label(day.toString().substring(0, 1) + day.toString().substring(1).toLowerCase());
            dayLabel.setFont(Font.font("Arial", 14));
            dayLabel.setPrefWidth(100);

            ListView<Task> taskList = new ListView<>();
            taskList.setPrefHeight(80); 
            dailyTaskLists.put(day, taskList); // save it for use later

            HBox row = new HBox(10, dayLabel, taskList);
            row.setStyle("-fx-alignment: center-left;");

            rows.getChildren().add(row);
        }

        // task list
        VBox section = new VBox(15, title, rows);
        section.setStyle("-fx-padding: 20;");

        return section; 
    }

    public void addTaskIfDueThisWeek(Task task) {
        if (task.getDueDate() == null) return;

        if (isInCurrentWeek(task.getDueDate())) {
            DayOfWeek day = task.getDueDate().getDayOfWeek();
            ListView<Task> list = dailyTaskLists.get(day);

            if (list != null) {
                list.getItems().add(task);
            }
        }
    }

    private boolean isInCurrentWeek(LocalDate date) {
        LocalDate now = LocalDate.now();
        DayOfWeek firstDayOfWeek = DayOfWeek.MONDAY;

        LocalDate startOfWeek = now.with(firstDayOfWeek);
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        return (date.isEqual(startOfWeek) || date.isAfter(startOfWeek)) &&
            (date.isBefore(endOfWeek) || date.isEqual(endOfWeek));
    }

    
}
