package com.avatarduel.model.card;

import com.avatarduel.model.enums.Element;

public abstract class Card {
    private String name;
    private String description;
    private Element element;

    public Card(String name, String description, Element element){
        this.name = name;
        this.description = description;
        this.element = element;
    }
}