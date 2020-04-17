
package com.avatarduel.view.field;

import com.avatarduel.model.field.Stance;
import com.avatarduel.view.card.CardView;

import javafx.scene.transform.Rotate;


public class CharacterFieldView extends SkillFieldView {
    private Stance currentStance;

    public CharacterFieldView(int column) {
        super(column);
        this.currentStance = Stance.ATTACK;
    }

    @Override
    public void setCardView(CardView cardView) {
        super.setCardView(cardView);
        if (currentStance == Stance.DEFEND) {
            cardView.getTransforms().add(new Rotate(90, cardView.getMinWidth()/2, cardView.getHeight()/2));
        }
    }

    public void changeStance() {
        if (this.currentStance == Stance.ATTACK) {
            this.currentStance = Stance.DEFEND;
        }
        else {
            this.currentStance = Stance.ATTACK;
        }
        if (this.getChildren().size() == 1) {
            CardView cardView = (CardView) this.getChildren().get(0);
            if (this.currentStance == Stance.ATTACK) {
                cardView.getTransforms().remove(0);
            }
            else {
                cardView.getTransforms().add(new Rotate(90, cardView.getMinWidth()/2, cardView.getHeight()/2));
            }
        }
    }
}