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

    public void seteElement(Element element) {
    	this.element = element;
    }

    public Element geteElement() {
    	return this.element;
    }
}