package com.avatarduel.model.card;

/**
 * Class yang bertanggung jawab untuk sebuah kartu. Cara constructnya dapat
 * menggunakan CardBuilder.
 */
public abstract class Card {
    protected String name;
    protected String description;
    protected Element element;
    protected String imagePath;

    public Card(CardBuilder cardBuilder) {
        this.name = cardBuilder.getName();
        this.description = cardBuilder.getDescription();
        this.element = cardBuilder.getElement();
        this.imagePath = cardBuilder.getImagePath();
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Element getElement() {
        return this.element;
    }

    public String getImagePath() {
        return this.imagePath;
    }
}