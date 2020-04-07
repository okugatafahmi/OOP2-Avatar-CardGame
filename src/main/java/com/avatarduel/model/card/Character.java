package com.avatarduel.model.card;

import com.avatarduel.model.enums.Element;

/**
 * Class turunan Card yang bertanggungjawab dengan kartu Character.
 */
public class Character extends Card{
    private int attack;
	private int defense;
	private int power;

    public Character(){
    	super();
		this.attack = 0;
		this.defense = 0;
		this.power = 0;
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
}