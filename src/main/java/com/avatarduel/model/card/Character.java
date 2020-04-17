package com.avatarduel.model.card;

/**
 * Class turunan Card yang bertanggungjawab dengan kartu Character.
 */
public class Character extends Card implements Summonedable {
  private int attack;
  private int defense;
  private int power;

  public Character() {
    this.attack = 0;
    this.defense = 0;
    this.power = 0;
  }

  /**
   * @param attack the attack to set
   */
  public void setAttack(int attack) {
    this.attack = attack;
  }
  
  /**
   * @return the attack
   */
  public int getAttack() {
    return attack;
  }

  public void setDefense(int defense) {
    this.defense = defense;
  }

  public int getDefense() {
    return this.defense;
  }

  @Override
  public void setPower(int power) {
    this.power = power;
  }

  @Override
  public int getPower() {
    return this.power;
  }
}