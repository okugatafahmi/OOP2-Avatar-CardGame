package com.avatarduel.model.builder;

import com.avatarduel.model.card.Card;
import com.avatarduel.model.card.Land;
import com.avatarduel.model.enums.Element;

public class LandBuilder {
    private Land land;
    
    public LandBuilder(){
        this.land = new Land();
    }

    public LandBuilder name(String name){
        this.land.setName(name);
        return this;
    }

    public LandBuilder description(String description){
        this.land.setDescription(description);
        return this;
    }

    public LandBuilder element(Element element){
        this.land.setElement(element);
        return this;
    }

    public LandBuilder image(String path){
        this.land.setImagePath(path);
        return this;
    }

    public Card build(){
        return this.land;
    }
}