package com.avatarduel.gui;

import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class CardInHand extends HBox {

    public CardInHand() {
        super.getChildren().add(new Text("Ini kartu di tangan"));
    }
}