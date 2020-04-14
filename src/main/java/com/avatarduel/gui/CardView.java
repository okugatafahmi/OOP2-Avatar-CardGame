package com.avatarduel.gui;

import java.io.FileInputStream; 

    import javafx.application.Application;
    import javafx.geometry.Pos;
    import javafx.scene.Scene;
    import javafx.scene.layout.Border;
    import javafx.scene.layout.BorderStroke;
    import javafx.scene.layout.BorderStrokeStyle;
    import javafx.scene.layout.BorderWidths;
    import javafx.scene.layout.CornerRadii;
    import javafx.scene.layout.GridPane;
    import javafx.scene.paint.Color;
    import javafx.scene.shape.Line;
    import javafx.scene.text.Text;
    import javafx.stage.Stage;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView; 

public class CardView extends GridPane{

    public static final GridPane card = new GridPane();
    
    // public static final Image image = new Image(new FileInputStream ("../../../../../../src/main/resources/com/avatarduel/card/image/character/Aang.png"));
    
    public CardView() {
        Image image = new Image(new FileInputStream(getClass().getResource("../card/image/character/Afiko.png").toString()));
        ImageView image_view = new ImageView(image);
        card.setBorder(
            new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        card.setMinSize(240, 320);
        card.setMaxSize(240, 320);        

        image_view.setX(50);
        image_view.setY(25);

        image_view.setFitHeight(455); 
        image_view.setFitWidth(500);
        image_view.setPreserveRatio(true);

        card.add(image_view, 0, 0, 1, 3);
        card.setHgap(20);
    }
}