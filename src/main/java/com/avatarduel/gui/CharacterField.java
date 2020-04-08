
package com.avatarduel.gui;

/**
 * Class yang bertanggung jawab sebagai satu kotak field tempat menaruh kartu player
 */
import com.avatarduel.model.card.*;
import com.avatarduel.model.card.Character;
import com.avatarduel.model.enums.*;


public class CharacterField extends Field {
    private Stance currentStance;
    
    public CharacterField() {
        super();
        currentStance = Stance.ATTACK;
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