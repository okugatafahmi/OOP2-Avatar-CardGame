package com.avatarduel.model.card;

/**
 * Class turunan Card yang bertanggungjawab dengan kartu Skill.
 */
public class Skill extends Card {
    private int attack;
    private int defense;
    private int power;
    private String effect;

    public Skill(CardBuilder cardBuilder) {
        super(cardBuilder);
        this.attack = cardBuilder.getAttack();
        this.defense = cardBuilder.getDefense();
        this.power = cardBuilder.getPower();
        this.effect = cardBuilder.getEffect();
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
}