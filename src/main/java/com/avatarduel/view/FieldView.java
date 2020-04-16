
package com.avatarduel.view;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * Class that responsible for put card in field (just its view)
 */
public class FieldView extends StackPane {
    private CardView cardView;

    public FieldView() {
        this.cardView = null;
        this.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.setMinSize(90, 95);
        this.setMaxSize(90, 95);
        this.setAlignment(Pos.CENTER);
    }

    public void setCard(CardView cardView) {
        this.cardView = cardView;
    }

    public CardView getCard() {
        return this.cardView;
    }

    public void setClickHandler(EventHandler<MouseEvent> eventHandler) {
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);
    }
}