package com.rewa.app;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class WeeklyToDoSection {

    private Map<DayOfWeek, ListView<Task>> dailyTaskLists = new HashMap<>();

    public VBox getSection() {
        // Section title
        Label title = new Label("📅 Week-Due Tasks");
        title.setFont(Font.font("Arial", 18));
    
        // Input fields
        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Task description");
    
        DatePicker dueDatePicker = new DatePicker();
        dueDatePicker.setPromptText("Due date");
    
        Button addButton = new Button("+");
        addButton.setOnAction(e -> {
            String description = descriptionField.getText();
            LocalDate dueDate = dueDatePicker.getValue();  // May be null
    
            if (description.isEmpty() || dueDate == null) {
                return;
            }
    
            LocalDate today = LocalDate.now();
            if (dueDate.isBefore(today)) {
                System.out.println("Cannot add tasks with a past due date!");
                return;
            }
    
            Task newTask = new Task(description, dueDate);
            DayOfWeek day = dueDate.getDayOfWeek();
            ListView<Task> list = dailyTaskLists.get(day);
    
            if (list != null) {
                list.getItems().add(newTask);
            }
    
            descriptionField.clear();
            dueDatePicker.setValue(null);
        });
    
        // Input row (text field + date picker + button)
        HBox inputRow = new HBox(10, descriptionField, dueDatePicker, addButton);
        inputRow.setStyle("-fx-alignment: center-left;");
    
        // Tasks area
        VBox rows = new VBox(10);
    
        for (DayOfWeek day : List.of(
                DayOfWeek.SUNDAY,
                DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY)) {
    
            Label dayLabel = new Label(day.toString().substring(0, 1) + day.toString().substring(1).toLowerCase());
            dayLabel.setFont(Font.font("Arial", 14));
            dayLabel.setPrefWidth(100);
    
            ListView<Task> taskList = new ListView<>();
            taskList.setPrefHeight(80);
            dailyTaskLists.put(day, taskList); // Save in the map
    
            taskList.setCellFactory(listView -> new ListCell<Task>() {
                private final Label taskLabel = new Label();
                private final Button doneButton = new Button("Done");
                private final HBox content = new HBox(10);
    
                {
                    content.getChildren().addAll(taskLabel, doneButton);
                    content.setStyle("-fx-alignment: CENTER_LEFT; -fx-padding: 5;");
                    doneButton.setOnAction(e -> {
                        Task task = getItem();
                        if (task != null && !task.isDone()) {
                            task.setDone(true);
                            updateItem(task, false);
                        }
                    });
                }
    
                @Override
                protected void updateItem(Task task, boolean empty) {
                    super.updateItem(task, empty);
                    if (empty || task == null) {
                        setGraphic(null);
                    } else {
                        String status = task.isDone() ? "[✓] " : "[ ] ";
                        taskLabel.setText(status + task.getDescription());
                        doneButton.setVisible(!task.isDone());
                        setGraphic(content);
                    }
                }
            });
    
            HBox row = new HBox(10, dayLabel, taskList);
            row.setStyle("-fx-alignment: center-left;");
    
            rows.getChildren().add(row);
        }
    
        // Full section layout
        VBox section = new VBox(15, title, inputRow, rows);
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
