
package com.avatarduel.gui;

/**
 * Class yang bertanggung jawab sebagai satu kotak field tempat menaruh kartu player
 */
import com.avatarduel.model.card.Character;
import com.avatarduel.model.enums.*;

import javafx.scene.text.Text;


public class CharacterField extends Field {
    private Stance currentStance;
    
    public CharacterField() {
        currentStance = Stance.ATTACK;
        super.getChildren().add(new Text("Character"));
    }
    
    public CharacterField(Character card) {
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