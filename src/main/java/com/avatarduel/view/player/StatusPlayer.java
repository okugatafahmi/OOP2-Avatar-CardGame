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
    private Text nameText;
    private Text hpText;
    private TextFlow nameFlow;
    private TextFlow hpFlow;

    public StatusPlayer() {
        this.setMinSize(180, 50);
        this.setMaxSize(180, 50);
        this.setAlignment(Pos.CENTER);
        this.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        name = "xxx";
        hp = ("80");

        nameText = new Text(name);
        hpText = new Text("HP : " + hp + " / 80");
        nameFlow = new TextFlow(nameText);
        hpFlow = new TextFlow(hpText);
    }

    public void updatePlayer(Player player){
        this.player = player;
        this.name = player.getName();
        this.hp = Integer.toString(player.getHp());

        nameFlow.getChildren().clear();
        hpFlow.getChildren().clear();

        nameText = new Text(name);
        hpText = new Text("HP : " + hp + " / 80");
        System.out.println("updated5!");

        nameFlow = new TextFlow(nameText);
        hpFlow = new TextFlow(hpText);

        this.add(nameFlow,1,0);
        this.add(hpFlow,1,1);

        System.out.println("updated!");
    }
}