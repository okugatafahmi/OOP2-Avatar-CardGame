package com.avatarduel.model.card;

/**
 * Class that responsible to make sub-class of Card based on type
 */
public class CardFactory {
    /**
     * Retun card based on type and the attribute was set using cardBuilder
     * 
     * @param type        card type (character, land, or skill)
     * @param cardBuilder cardBuilder which attribut has been set
     * @return card that be produced, return null if type is null or not equal with
     *         card types or cardBuilder is null
     */
    public Card getCard(String type, CardBuilder cardBuilder) {
        if (type == null || cardBuilder == null) {
            return null;
        }
        if (type.toLowerCase().equals("character")) {
            return new Character(cardBuilder);
        } else if (type.toLowerCase().equals("land")) {
            return new Land(cardBuilder);
        } else if (type.toLowerCase().equals("skill")) {
            return new Skill(cardBuilder);
        } else {
            return null;
        }
    }
}