package com.avatarduel.model.card;

import com.avatarduel.model.enums.Element;

/**
 * Class turunan Card yang bertanggungjawab dengan kartu Skill.
 */
public class Skill extends Card {
    private int attack;
	private int defense;
	private int power;
	private String effect;

    public Skill(){
	    super();
		this.attack = 0;
		this.defense = 0;
		this.power = 0;
		this.effect = "";
    }

    public setAttack(int attack) {
    	this.attack = attack;
    }

    public getAttack() {
    	return this.attack;
    }

    public setDefense(int defense) {
    	this.defense = defense;
    }

    public getDefense() {
    	return this.defense;
    }

    public setPower(int power) {
    	this.power = power;
    }

    public getPower() {
    	return this.power;
    }

    public setEffect(String effect) {
    	this.effect = effect;
    }

    public getEffect() {
    	return this.effect;
    }
}