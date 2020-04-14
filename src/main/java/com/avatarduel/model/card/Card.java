package com.avatarduel.model.card;

/**
 * Class yang bertanggung jawab untuk sebuah kartu. Cara constructnya dapat
 * menggunakan CardBuilder.
 */
public abstract class Card {
    private String name;
    private String description;
    private Element element;
    private String imagePath;
    private String elementImagePath;

    public Card() {
        this.name = "";
        this.description = "";
        this.element = null;
        this.imagePath = "";
        this.elementImagePath = "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public Element getElement() {
        return this.element;
    }

    public void setImagePath(String path) {
        this.imagePath = path;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setElementImagePath() {
        if(this.element == Element.AIR){
            this.elementImagePath = "/com/avatarduel/element/Air.png";
        } else if(this.element == Element.EARTH){
            this.elementImagePath = "/com/avatarduel/element/Earth.png";
        } else if(this.element == Element.FIRE){
            this.elementImagePath = "/com/avatarduel/element/Fire.png";
        } else if(this.element == Element.WATER){
            this.elementImagePath = "/com/avatarduel/element/Water.png";
        }
    }

    public String getElementImagePath() {
        return this.elementImagePath;
    }
}
