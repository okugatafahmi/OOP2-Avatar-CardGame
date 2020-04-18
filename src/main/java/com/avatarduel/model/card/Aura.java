package com.avatarduel.model.card;

public class Aura extends Skill{

    private int attack;
    private int defense;

    public Aura(){
        this.attack = 0;
        this.defense = 0;
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

}