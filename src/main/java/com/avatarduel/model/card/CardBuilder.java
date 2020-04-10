package com.avatarduel.model.card;

/**
 * Class that responsible to build card
 */
public class CardBuilder {
    private String name;
    private String description;
    private Element element;
    private String imagePath;
    private int attack;
    private int defense;
    private int power;
    private String effect;

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

    public int getAttack() {
        return this.attack;
    }

    public int getDefense() {
        return this.defense;
    }

    public int getPower() {
        return this.power;
    }

    public String getEffect() {
        return this.effect;
    }

    public CardBuilder name(String name) {
        this.name = name;
        return this;
    }

    public CardBuilder description(String description) {
        this.description = description;
        return this;
    }

    public CardBuilder element(Element element) {
        this.element = element;
        return this;
    }

    public CardBuilder image(String path) {
        this.imagePath = path;
        return this;
    }

    public CardBuilder attack(int attack) {
        this.attack = attack;
        return this;
    }

    public CardBuilder defense(int defense) {
        this.defense = defense;
        return this;
    }

    public CardBuilder power(int power) {
        this.power = power;
        return this;
    }

    public CardBuilder effect(String effect) {
        this.effect = effect;
        return this;
    }

    public Card build(String typeCard) {
        return new CardFactory().getCard(typeCard, this);
    }
}