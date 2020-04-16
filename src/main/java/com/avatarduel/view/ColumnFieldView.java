package com.avatarduel.view;

import com.avatarduel.model.card.Character;
import com.avatarduel.model.card.Skill;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class ColumnFieldView extends GridPane {
    private CharacterFieldView characterFieldView;
    private SkillFieldView skillFieldView;
    private int column;

    public ColumnFieldView(int column, boolean isMirror) {
        this.characterFieldView = new CharacterFieldView(column);
        this.skillFieldView = new SkillFieldView(column);

        int row = ((isMirror) ? 1:0);
        this.addRow(row, characterFieldView);
        this.addRow((row+1)%2, skillFieldView);
        this.setAlignment(Pos.CENTER);
        this.setVgap(10);
        this.column = column;
    }

    /**
     * @return the column number
     */
    public int getColumn() {
        return column;
    }

    /**
     * Set card view on field
     * @param cardView card view will be set on field. The card must instance of character or skill
     */
    public void setCardView(CardView cardView) {
        if (cardView.getCard() instanceof Character) {
            this.characterFieldView.setCardView(cardView);
        }
        else if (cardView.getCard() instanceof Skill) {
            this.skillFieldView.setCardView(cardView);
        }
    }

    public void changeStance() {
        this.characterFieldView.changeStance();
    }

    /**
     * Set field click hander
     * 
     * @param characterFieldEventHandler event handler for character field
     * @param skillFieldEventHandler     event handler for skill field
     */
    public void setClickHandler(EventHandler<MouseEvent> characterFieldEventHandler,
            EventHandler<MouseEvent> skillFieldEventHandler) {
            characterFieldView.addEventHandler(MouseEvent.MOUSE_CLICKED, characterFieldEventHandler);
            skillFieldView.addEventHandler(MouseEvent.MOUSE_CLICKED, skillFieldEventHandler);
    }
}