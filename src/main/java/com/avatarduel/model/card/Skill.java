package com.avatarduel.model.card;

/**
 * Class turunan Card yang bertanggungjawab dengan kartu Skill.
 */
public class Skill extends Card implements Summonedable {
    private int power;

    public Skill() {
        this.power = 0;
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