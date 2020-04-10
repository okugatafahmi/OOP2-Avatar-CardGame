package com.avatarduel.model.card;

/**
 * Class turunan Card yang bertanggungjawab dengan kartu Character.
 */
public class Character extends Card {
  private int attack;
  private int defense;
  private int power;

  public Character(CardBuilder cardBuilder) {
    super(cardBuilder);
    this.attack = cardBuilder.getAttack();
    this.defense = cardBuilder.getDefense();
    this.power = cardBuilder.getPower();
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
}