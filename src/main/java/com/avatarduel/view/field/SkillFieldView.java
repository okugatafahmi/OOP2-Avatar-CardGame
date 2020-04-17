
package com.avatarduel.view.field;

import com.avatarduel.view.card.CardView;

import javafx.geometry.Pos;
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
public class SkillFieldView extends StackPane {
    protected int column;

    public SkillFieldView(int column) {
        this.column = column;
        this.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.setMinSize(90, 95);
        this.setMaxSize(90, 95);
        this.setAlignment(Pos.CENTER);
    }

    /**
     * @return the column
     */
    public int getColumn() {
        return column;
    }

    /**
     * @param cardView the cardView to set
     */
    public void setCardView(CardView cardView) {
        this.getChildren().add(cardView);
    }

    /**
     * @return the cardView
     */
    public CardView getCardView() {
        return (CardView) this.getChildren().get(this.getChildren().size()-1);
    }
}