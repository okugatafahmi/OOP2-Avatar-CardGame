package com.avatarduel.view;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class CardInHand extends HBox {

    public CardInHand() {
        super.setAlignment(Pos.CENTER);
        super.setSpacing(15);
    }

    public void setFaceCard(boolean isFaceUp) {
        for (Node cardView : this.getChildren()) {
            if (isFaceUp){
                ((CardView) cardView).faceUp();
            }
            else {
                ((CardView) cardView).faceDown();
            }
        }
    }
}