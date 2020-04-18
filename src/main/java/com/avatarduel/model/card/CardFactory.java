package com.avatarduel.model.card;

/**
 * Class that responsible to make sub-class of Card based on type
 */
public class CardFactory {
    /**
     * Retun card based on type (character, land, or skill)
     * 
     * @param type card type
     * @return card that be produced, return null if type is null or not equal with
     *         card types
     */
    public Card getCard(String type) {
        if (type == null) {
            return null;
        }
        if (type.toLowerCase().equals("character")) {
            return new Character();
        } else if (type.toLowerCase().equals("land")) {
            return new Land();
        // } else if (type.toLowerCase().equals("skill")) {
        //     return new Skill();
        } else if (type.toLowerCase().equals("aura")) {
            return new Aura();
        } else if (type.toLowerCase().equals("powerup")) {
            return new PowerUp();
        } else if (type.toLowerCase().equals("destroy")) {
            return new Destroy();
        } else {
            return null;
        }
    }
}