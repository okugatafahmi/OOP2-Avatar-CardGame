package com.avatarduel;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import com.avatarduel.model.player.Player;

import com.avatarduel.controller.PlayerController;
import com.avatarduel.gameplay.Gameplay;

public class AvatarDuel extends Application {
  private static final int WIDTH = 1280;
  private static final int HEIGHT = 720;
  private final String[] playerNames = { "Qihla", "Hojun" };
  private final int[] playerTotalDeck = { 40, 40 };

  @Override
  public void start(Stage stage) {
    Player[] players = new Player[2];
    PlayerController[] playerControllers = new PlayerController[2];
    for (int i = 0; i < 2; ++i) {
      players[i] = new Player(i);
      playerControllers[i] = new PlayerController(players[i], ((i == 0) ? false : true));
    }
    Gameplay gameplay = new Gameplay(playerControllers);

    Line line = new Line();
    line.setStartX(100);
    line.setStartY(HEIGHT / 2);
    line.setEndX(940);
    line.setEndY(HEIGHT / 2);

    GridPane gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    gridPane.getChildren().add(gameplay.getStatus());

    Scene scene = new Scene(gridPane, WIDTH, HEIGHT, Color.WHITESMOKE);

    stage.setTitle("Avatar Duel");
    stage.setScene(scene);
    stage.setFullScreen(true);
    stage.show();

    try {
      gameplay.loadCards();
      gameplay.getStatus().setText("Avatar Duel!");
    } catch (Exception e) {
      gameplay.getStatus().setText("Failed to load cards: " + e);
      return;
    }

    gridPane.getChildren().remove(gameplay.getStatus());
    
    // TODO bikin tampilan awal untuk memasukkan nama dan total deck
    for (int i=0; i<2; ++i) {
      players[i].setName(playerNames[i]);
      players[i].setTotalDeckCard(playerTotalDeck[i]);
    }
    
    gridPane.add(playerControllers[0].getPlayerArena(), 1, 2, 2, 1); // bawah
    gridPane.add(playerControllers[1].getPlayerArena(), 1, 0, 2, 1); // atas
    gridPane.add(line, 1, 1);
    gridPane.add(gameplay.getStatus(), 2, 1);

    

    gridPane.add(gameplay.getHoverSpace(), 0, 0, 1, 3);
    gridPane.setHgap(20);
    gridPane.getRowConstraints().add(new RowConstraints());
    gridPane.getRowConstraints().add(new RowConstraints(1));
    gridPane.getRowConstraints().add(new RowConstraints());
    gameplay.run();
  }

  public static void main(String[] args) {
    launch();
  }
}