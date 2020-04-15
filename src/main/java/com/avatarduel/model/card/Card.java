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

    public Card() {
        this.name = "";
        this.description = "";
        this.element = null;
        this.imagePath = "";
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

    public String getElementImagePath() {
        if(this.element == Element.AIR){
            return "/com/avatarduel/element/Air.png";
        } else if(this.element == Element.EARTH){
            return "/com/avatarduel/element/Earth.png";
        } else if(this.element == Element.FIRE){
            return "/com/avatarduel/element/Fire.png";
        } else if(this.element == Element.WATER){
            return "/com/avatarduel/element/Water.png";
        } else if(this.element == Element.ENERGY){
            return "/com/avatarduel/element/Energy.png";
        } else {
            return "";
        }
    }
}
