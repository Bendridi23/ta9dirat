/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 *
 * @author benPC
 */
public class SmartN extends Application {
    private double xOffset=0;
    private double yOffset=0;

    private AnchorPane anchor;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Flat_Frame.fxml"));

        Scene scene = new Scene(root);
        // scene.getStylesheets().add("css");
        scene.getStylesheets().add(getClass().getResource("font_tajawal.css").toExternalForm());
       // stage.resizableProperty().setValue(false);
        Image img=new Image("/sample/logo.png");

        stage.getIcons().add(img);
        stage.setScene(scene);
        //stage.getScene().getWindow().hide();
        stage.setTitle("Smart Notes");
     //  stage.initStyle(StageStyle.DECORATED);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        root.setOnMousePressed(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                xOffset=event.getSceneX();
                yOffset=event.getSceneY();

            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX()-xOffset);
                stage.setY(event.getScreenY()-yOffset);
            }
        });


        // anchor.getScene().getWindow().hide();
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void Main(String[] args) {
        System.out.println("Version JAVA FX////>>"+System.getProperty("javafx.runtime.version"));
        launch(args);
    }

}
