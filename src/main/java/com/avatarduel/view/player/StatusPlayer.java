package com.avatarduel.view.player;

import com.avatarduel.model.player.Player;
import java.util.HashMap;

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
    private String hp;
    private String name;
    private Player player;

    public StatusPlayer() {
        this.setMinSize(180, 50);
        this.setMaxSize(180, 50);
        this.setAlignment(Pos.CENTER);
        this.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        try{
            updatePlayer(player);
        } catch (Exception e){
            name = "xxx";
            hp = ("80");    
        }
        Text nameText = new Text(name);
        Text hpText = new Text("HP : " + hp + " / 80");

        TextFlow nameFlow = new TextFlow(nameText);
        TextFlow hpFlow = new TextFlow(hpText);

        this.add(nameFlow,1,0);
        this.add(hpFlow,1,1);
    }

    public void updatePlayer(Player player){
        this.player = player;
        this.name = player.getName();
        this.hp = Integer.toString(player.getHp());
    }
}