package com.hackathon;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Remove this line as specific JavaFX imports are already present

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.image.BufferedImage;


public class UI extends Application {

    @Override
    public void start(Stage stage) {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(400);
        imageView.setPreserveRatio(true);

        Button pasteButton = new Button("Paste Screenshot from Clipboard");
        pasteButton.setOnAction(e -> {
            try {
                java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                if (clipboard.isDataFlavorAvailable(DataFlavor.imageFlavor)) {
                    BufferedImage bufferedImage = (BufferedImage) clipboard.getData(DataFlavor.imageFlavor);
                    Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
                    imageView.setImage(fxImage);
                } else {
                    System.out.println("No image in clipboard!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        VBox root = new VBox(10, pasteButton, imageView);
        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);
        stage.setTitle("Clipboard Screenshot Viewer");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

