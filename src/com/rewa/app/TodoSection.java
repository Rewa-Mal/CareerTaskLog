package com.rewa.app;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.time.LocalDate;

import javafx.scene.control.*;

public class TodoSection {

    private ListView<Task> todoList = new ListView<>();
    // private WeekDueSection weekDueSection;

    public TodoSection() {
        // this.weekDueSection = weekDueSection;
    }

    public VBox getSection() {
        // Section title
        Label label = new Label("✅ To-Do");
        label.setFont(Font.font("Arial", 18));

        // Task input
        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Task description");

        DatePicker dueDatePicker = new DatePicker();
        dueDatePicker.setPromptText("Due date");

        Button addButton = new Button("+");
        addButton.setOnAction(e -> {
            String description = descriptionField.getText();
            LocalDate dueDate = dueDatePicker.getValue();  // May be null
        
            if (description.isEmpty()) {
                // Task must at least have a description
                return;
            }
        
            Task newTask = new Task(description, dueDate);
            todoList.getItems().add(newTask);
        
            // Clear fields
            descriptionField.clear();
            dueDatePicker.setValue(null);
        });
        
        Button markDoneButton = new Button("Done");
        markDoneButton.setOnAction(e -> {
            Task selectedTask = todoList.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                selectedTask.setDone(true);
                todoList.refresh();
            }
        });

        todoList.setCellFactory(listView -> new ListCell<Task>() {
            private final Label taskLabel = new Label();
            private final Button doneButton = new Button("Mark as Done");
            private final HBox content = new HBox(10);
        
            {
                content.getChildren().addAll(taskLabel, doneButton);
                content.setStyle("-fx-alignment: CENTER_LEFT; -fx-padding: 5;");
                doneButton.setOnAction(e -> {
                    Task task = getItem();
                    if (task != null && !task.isDone()) {
                        task.setDone(true);
                        updateItem(task, false); // Refresh the display
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
                    String dueInfo = task.getDueDate() == null ?
                        "(No due date)" :
                        "(Due: " + task.getDueDate() + ", " + task.getDayOfWeek() + ")";
                    taskLabel.setText(status + task.getDescription() + " " + dueInfo);
        
                    doneButton.setVisible(!task.isDone()); // Hide if already done
                    setGraphic(content);
                }
            }
        });

        
        HBox inputRow = new HBox(10, descriptionField, dueDatePicker, addButton);
        VBox section = new VBox(10, label, inputRow, todoList);
        section.setStyle("-fx-padding: 10;");

        return section;
    }

    public ListView<Task> getTodoList() {
        return todoList;
    }
}
