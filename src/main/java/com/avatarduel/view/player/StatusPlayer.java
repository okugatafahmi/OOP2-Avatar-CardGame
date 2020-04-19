package com.avatarduel.view.player;

import com.avatarduel.model.player.Player;

import javafx.geometry.Pos;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class PlayerStatus extends GridPane {

    public PlayerStatus() {
        super.setMinSize(170, 50);
        super.setMaxSize(170, 50);
        super.setAlignment(Pos.CENTER);
        super.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        Text hp = new Text()
    }

    public void update() {

    }
}