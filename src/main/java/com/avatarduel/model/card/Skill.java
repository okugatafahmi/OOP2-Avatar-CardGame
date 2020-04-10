package com.avatarduel.model.card;

/**
 * Class turunan Card yang bertanggungjawab dengan kartu Skill.
 */
public class Skill extends Card {
    private int attack;
    private int defense;
    private int power;
    private String effect;

    public Skill() {
        this.attack = 0;
        this.defense = 0;
        this.power = 0;
        this.effect = "";
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getAttack() {
        return this.attack;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getDefense() {
        return this.defense;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getPower() {
        return this.power;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getEffect() {
        return this.effect;
    }
}