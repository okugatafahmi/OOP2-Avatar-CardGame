package com.avatarduel.view.player;

import javafx.geometry.Pos;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class StatusPlayer extends GridPane {
    private Text nameText;
    private Text hpText;

    public StatusPlayer() {
        this.setMinSize(180, 50);
        this.setMaxSize(180, 50);
        this.setAlignment(Pos.CENTER);
        this.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        nameText = new Text("XXX");
        hpText = new Text("HP : " + 80 + " / 80");
        TextFlow nameFlow = new TextFlow(nameText);
        TextFlow hpFlow = new TextFlow(hpText);
        this.add(nameFlow, 0, 0);
        this.add(hpFlow, 0, 1);
    }

    /**
     * Update player's name
     * 
     * @param name player's name
     */
    public void updatePlayerName(String name) {
        nameText.setText(name.toUpperCase());
    }

    /**
     * Update player's hp
     * 
     * @param hp player's hp
     */
    public void updatePlayerHp(int hp) {
        hpText.setText("HP : " + hp + " / 80");
    }
}