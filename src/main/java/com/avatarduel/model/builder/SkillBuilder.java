package com.avatarduel.model.builder;

import com.avatarduel.model.card.Card;
import com.avatarduel.model.card.Skill;
import com.avatarduel.model.enums.Element;

public class SkillBuilder {
    private Skill skill;

    public SkillBuilder(){
        this.skill = new Skill();
    }

    public SkillBuilder name(String name){
        this.skill.setName(name);
        return this;
    }

    public SkillBuilder description(String description){
        this.skill.setDescription(description);
        return this;
    }

    public SkillBuilder element(Element element){
        this.skill.setElement(element);
        return this;
    }

    public SkillBuilder image(String path){
        this.skill.setImagePath(path);
        return this;
    }

    public SkillBuilder attack(int attack){
        this.skill.setAttack(attack);
        return this;
    }

    public SkillBuilder defense(int defense){
        this.skill.setDefense(defense);
        return this;
    }

    public SkillBuilder power(int power){
        this.skill.setPower(power);
        return this;
    }

    public SkillBuilder effect(String effect){
        this.skill.setEffect(effect);
        return this;
    }

    public Card build(){
        return this.skill;
    }
}