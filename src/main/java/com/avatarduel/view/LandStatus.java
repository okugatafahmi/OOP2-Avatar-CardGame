package com.avatarduel.view;

import javafx.geometry.Pos;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class LandStatus extends GridPane {

    public LandStatus() {
        super.setMinSize(150, 100);
        super.setMaxSize(150, 100);
        super.setAlignment(Pos.CENTER);
        super.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        super.getChildren().add(new Text("Buat status land"));
    }

    public void update() {

    }
}