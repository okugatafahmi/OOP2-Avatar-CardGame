package com.avatarduel.exceptions;

import com.avatarduel.model.enums.Element;

public class PowerElementNotEnough extends Exception{

    public PowerElementNotEnough(Element element){
        super("Element " + element.toString() + " power not enough");
    }
}