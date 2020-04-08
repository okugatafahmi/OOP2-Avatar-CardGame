package com.avatarduel.model.card;

import com.avatarduel.model.enums.Element;

public class CardBuilder {
    private Card card;

    public CardBuilder(String type){
        if (type.toLowerCase().equals("character")){
            this.card = new Character();
        }
        else if (type.toLowerCase().equals("land")){
            this.card = new Land();
        }
        else if (type.toLowerCase().equals("skill")){
            this.card = new Skill();
        }
    }

    public CardBuilder name(String name){
        this.card.setName(name);
        return this;
    }

    public CardBuilder description(String description){
        this.card.setDescription(description);
        return this;
    }

    public CardBuilder element(Element element){
        this.card.setElement(element);
        return this;
    }

    public CardBuilder image(String path){
        this.card.setImagePath(path);
        return this;
    }

    public CardBuilder attack(int attack){
        if (this.card instanceof Character){
            ((Character) this.card).setAttack(attack);
        }
        else if (this.card instanceof Skill){
            ((Skill) this.card).setAttack(attack);
        }
        return this;
    }

    public CardBuilder defense(int defense){
        if (this.card instanceof Character){
            ((Character) this.card).setDefense(defense);
        }
        else if (this.card instanceof Skill){
            ((Skill) this.card).setDefense(defense);
        }
        return this;
    }

    public CardBuilder power(int power){
        if (this.card instanceof Character){
            ((Character) this.card).setPower(power);
        }
        else if (this.card instanceof Skill){
            ((Skill) this.card).setPower(power);
        }
        return this;
    }

    public CardBuilder effect(String effect){
        if (this.card instanceof Skill){
            ((Skill) this.card).setEffect(effect);
        }
        return this;
    }

    public Card build(){
        return this.card;
    }
}