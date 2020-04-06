package com.avatarduel.model.card;

import com.avatarduel.model.enums.Element;

/**
 * Class yang bertanggung jawab untuk sebuah kartu. Cara constructnya
 * dapat menggunakan CardBuilder.
 */
public abstract class Card {
    private String name;
    private String description;
    private Element element;

    public Card(){
        this.name = "";
        this.description = "";
        this.element = null;
    }
}