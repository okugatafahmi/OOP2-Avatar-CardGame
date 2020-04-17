package com.avatarduel.model.field;

public class CardInFieldExist extends Exception {

    public CardInFieldExist() {
        super("The selected field has had card");
    }

    /**
     *
     */
    private static final long serialVersionUID = 8502871531773066094L;

}