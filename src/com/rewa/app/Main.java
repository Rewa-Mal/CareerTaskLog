package com.rewa.app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // To-do section
        TodoSection todoSection = new TodoSection();
        VBox todoBox = todoSection.getSection();


        // Follow-up section
        Label followUpLabel = new Label("🔁 Follow-Up Tasks");
        followUpLabel.setFont(Font.font("Arial", 18));
        ListView<String> followUpList = new ListView<>();

        // Week-due section
        Label weekDueLabel = new Label("📅 Week-Due Tasks");
        weekDueLabel.setFont(Font.font("Arial", 18));
        ListView<String> weekDueList = new ListView<>();

        // Layout
        VBox layout = new VBox(15); // spacing
        layout.setStyle("-fx-padding: 20;");
        layout.getChildren().addAll(
            todoBox, followUpLabel, followUpList,
            weekDueLabel, weekDueList
        );


        // Scene
        Scene scene = new Scene(layout, 600, 600);
        primaryStage.setTitle("Career Task Log");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
