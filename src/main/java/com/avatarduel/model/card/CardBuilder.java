package com.avatarduel.model.card;

import com.avatarduel.model.enums.Element;

/**
 * Class yang bertanggung jawab untuk meng-construct suatu Card.
 */
public class CardBuilder {
    private Card card;
    private String type;

    public CardBuilder(String type){
        if (type.equals("Character")){
            card = new Character();
        }
        else if (type.equals("Land")){
            card = new Land();
        }
        else if (type.equals("Skill")){
            card = new Skill();
        }
        else{
            card = null;
        }
        this.type = type;
    }

    public CardBuilder name(String name){
        card.setName(name);
    }
    public CardBuilder description(String description){
        card.setDescription(description);
    }
    public CardBuilder element(Element element){
        card.setElement(element);
    }
    public Card build(){
        return this.card;
    }
}
