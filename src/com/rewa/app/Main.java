package com.rewa.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;

import com.rewa.app.TodoSection;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        // Current date
        LocalDate today = LocalDate.now();
        int weekNumber = today.get(WeekFields.ISO.weekOfWeekBasedYear());
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");
        Label dateLabel = new Label("Today: " + today.format(formatter));

        dateLabel.setFont(Font.font("Arial", 16));

        Label weekLabel = new Label("Week " + weekNumber);
        weekLabel.setFont(Font.font("Arial", 16));

        
        // Weekly To-do section
        WeeklyToDoSection weekDueSection = new WeeklyToDoSection();
        VBox weekDueBox = weekDueSection.getSection();

        // // To-do section
        // TodoSection todoSection = new TodoSection();
        // VBox todoBox = todoSection.getSection();

        // Archive section 
        Label archiveLabel = new Label("🔁 Archive Tasks");
        archiveLabel.setFont(Font.font("Arial", 18));
        ListView<String> archiveList = new ListView<>();


        // Layout
        HBox dataHBox = new HBox(20, dateLabel, weekLabel);
        HBox topRow = new HBox(20, weekDueBox);
        topRow.setStyle("-fx-alignment: top-left;");

        VBox layout = new VBox(15);
        layout.setStyle("-fx-padding: 20;");
        layout.getChildren().addAll(
            dataHBox, topRow, archiveLabel, archiveList
        );

        // Scene
        Scene scene = new Scene(layout, 860, 600);
        primaryStage.setTitle("Career Tasks Log");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
