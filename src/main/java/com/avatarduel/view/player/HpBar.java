package com.avatarduel.view.player;

import javafx.scene.control.ProgressBar;

import static com.avatarduel.model.player.Player.HP;

public class HpBar extends ProgressBar {
    public HpBar(){
    }

    public HpBar(double progress) {
        super(progress);
    }

    public void setHp(double hp) {
        this.setProgress(hp/(double) HP);
    }
}