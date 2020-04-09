package com.avatarduel.gui;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class CardInHand extends HBox {

    public CardInHand() {
        super.setAlignment(Pos.CENTER);
        super.getChildren().add(new Text("Ini kartu di tangan"));
    }
}