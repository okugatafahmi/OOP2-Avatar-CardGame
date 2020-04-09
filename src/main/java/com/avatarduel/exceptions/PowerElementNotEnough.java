package com.avatarduel.exceptions;

import com.avatarduel.model.card.Element;

public class PowerElementNotEnough extends Exception{

    public PowerElementNotEnough(Element element){
        super("Element " + element.toString() + " power not enough");
    }
}