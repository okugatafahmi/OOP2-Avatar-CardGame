package com.avatarduel.model.player;

import com.avatarduel.model.card.Element;

public class PowerElementNotEnough extends Exception {

    public PowerElementNotEnough(Element element) {
        super("Element " + element.toString() + " power not enough");
    }

    /**
     *
     */
    private static final long serialVersionUID = 7682005013008200493L;
}