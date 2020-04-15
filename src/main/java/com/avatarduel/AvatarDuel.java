package com.avatarduel;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import com.avatarduel.model.player.Player;
import com.avatarduel.controller.PlayerController;
import com.avatarduel.gameplay.Gameplay;

public class AvatarDuel extends Application {
  private static final int WIDTH = 1280;
  private static final int HEIGHT = 720;
  private static final String[] playerNames = { "Qihla", "Hojun" };
  private static final int[] playerTotalDeck = { 40, 40 };
  public static final StackPane hoverSpace = new StackPane();
  public static final Text status = new Text();

  @Override
  public void start(Stage stage) {
    status.setText("Loading...");

    Line line = new Line();
    line.setStartX(100);
    line.setStartY(HEIGHT / 2);
    line.setEndX(940);
    line.setEndY(HEIGHT / 2);

    GridPane gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    gridPane.getChildren().add(status);

    Scene scene = new Scene(gridPane, WIDTH, HEIGHT, Color.WHITESMOKE);

    stage.setTitle("Avatar Duel");
    stage.setScene(scene);
    stage.setFullScreen(true);
    stage.show();

    Player[] players = new Player[2];
    PlayerController[] playerControllers = new PlayerController[2];
    for (int i = 0; i < 2; ++i) {
      players[i] = new Player(playerNames[i], playerTotalDeck[i]);
      playerControllers[i] = new PlayerController(players[i], i, ((i == 0) ? false : true));
    }
    Gameplay gameplay = new Gameplay(playerControllers, hoverSpace, status);

    try {
      gameplay.loadCards();
      status.setText("Avatar Duel!");
    } catch (Exception e) {
      status.setText("Failed to load cards: " + e);
      return;
    }

    gridPane.getChildren().remove(status);
    gridPane.add(playerControllers[0].getPlayerArena(), 1, 2, 2, 1); // bawah
    gridPane.add(playerControllers[1].getPlayerArena(), 1, 0, 2, 1); // atas
    gridPane.add(line, 1, 1);
    gridPane.add(status, 2, 1);

    hoverSpace.setBorder(
        new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    hoverSpace.setMinSize(250, 360);
    hoverSpace.setMaxSize(250, 360);

    gridPane.add(hoverSpace, 0, 0, 1, 3);
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