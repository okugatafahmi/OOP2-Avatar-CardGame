package com.avatarduel.model.card;

import com.avatarduel.model.enums.Element;

/**
 * Class yang bertanggung jawab untuk meng-construct suatu Card.
 */
public class CardBuilder {
    private Card card;

    public CardBuilder(){
        card = new Card();
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
