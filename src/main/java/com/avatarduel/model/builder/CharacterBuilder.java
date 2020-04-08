package com.avatarduel.model.builder;

import com.avatarduel.model.card.Card;
import com.avatarduel.model.card.Character;
import com.avatarduel.model.enums.Element;

public class CharacterBuilder {
    private Character character;

    public CharacterBuilder(){
        this.character = new Character();
    }

    public CharacterBuilder name(String name){
        this.character.setName(name);
        return this;
    }

    public CharacterBuilder description(String description){
        this.character.setDescription(description);
        return this;
    }

    public CharacterBuilder element(Element element){
        this.character.setElement(element);
        return this;
    }

    public CharacterBuilder image(String path){
        this.character.setImagePath(path);
        return this;
    }

    public CharacterBuilder attack(int attack){
        this.character.setAttack(attack);
        return this;
    }

    public CharacterBuilder defense(int defense){
        this.character.setDefense(defense);
        return this;
    }

    public CharacterBuilder power(int power){
        this.character.setPower(power);
        return this;
    }


    public Card build(){
        return this.character;
    }
}