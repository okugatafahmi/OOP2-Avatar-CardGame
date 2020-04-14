
package com.avatarduel.view;

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
    private CardView cardView;

    public FieldView() {
        this.cardView = null;
        super.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        super.setMinSize(90, 105);
        super.setMaxSize(90, 105);
        super.setAlignment(Pos.CENTER);
    }

    public void setCard(CardView cardView) {
        this.cardView = cardView;
    }

    public CardView getCard() {
        return this.cardView;
    }
}