
package com.avatarduel.gui;

import com.avatarduel.model.card.Character;
import com.avatarduel.model.field.Stance;

import javafx.scene.text.Text;


public class CharacterFieldView extends FieldView {
    private Stance currentStance;
    
    public CharacterFieldView() {
        currentStance = Stance.ATTACK;
        super.getChildren().add(new Text("Character"));
    }
    
    public CharacterFieldView(Character card) {
        super(card);
        currentStance = Stance.ATTACK;
    }

    public int getStanceValue() {
        if (this.currentStance == Stance.ATTACK) {
            return ((Character)this.getCard()).getAttack();
        } else {
            return ((Character)this.getCard()).getDefense();
        }
    }
}