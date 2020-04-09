
package com.avatarduel.gui;

import com.avatarduel.model.card.*;

import javafx.geometry.Pos;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * Class yang bertanggung jawab sebagai satu kotak field tempat menaruh kartu
 * player
 */
public class FieldView extends GridPane {
    private Card card;

    public FieldView() {
        this.card = null;
        super.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        super.setMinSize(75, 100);
        super.setMaxSize(75, 100);
        super.setAlignment(Pos.CENTER);
    }

    public FieldView(Card card) {
        this.card = card;
    }

    public Card getCard() {
        return this.card;
    }
}