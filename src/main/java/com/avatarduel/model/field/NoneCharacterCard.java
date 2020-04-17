package com.avatarduel.model.field;

public class NoneCharacterCard extends PlaceCardException {
    public NoneCharacterCard() {
        super("Can't use skill card to none character card");
    }

    /**
     *
     */
    private static final long serialVersionUID = -4889700282569068589L;
}