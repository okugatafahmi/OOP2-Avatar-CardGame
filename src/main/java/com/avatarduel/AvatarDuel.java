package com.avatarduel;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import com.avatarduel.view.player.PlayerArena;
import com.avatarduel.controller.PlayerController;
import com.avatarduel.gameplay.Gameplay;

public class AvatarDuel extends Application implements AlertShower {
  private static final int WIDTH = 1280;
  private static final int HEIGHT = 720;
  private static final String LOGO_PATH = "Logo.png";
  private static final String BACKGROUND_PATH1 = "Background1.png";
  private static final String BACKGROUND_PATH2 = "Background2.png";

  @Override
  public void showAlert(AlertType alertType, String msg) {
    Alert alert = new Alert(alertType, msg, ButtonType.OK);
    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    alert.showAndWait();
  }

  @Override
  public void start(Stage stage) {
    PlayerArena[] playerArenas = new PlayerArena[2];
    PlayerController[] playerControllers = new PlayerController[2];
    for (int i = 0; i < 2; ++i) {
      playerArenas[i] = new PlayerArena(i==1);
      playerControllers[i] = new PlayerController(i);
      playerControllers[i].setPlayerArena(playerArenas[i]);
    }
    Gameplay gameplay = new Gameplay(playerControllers);

    Line line = new Line();
    line.setStartX(100);
    line.setStartY(HEIGHT / 2);
    line.setEndX(940);
    line.setEndY(HEIGHT / 2);

    GridPane gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER_RIGHT);
    gridPane.getChildren().add(gameplay.getStatus());

    Scene scene = new Scene(gridPane, WIDTH, HEIGHT);

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

    gameplay.setAlertShower(this);
    gridPane.getChildren().remove(gameplay.getStatus());
    Image back1 = new Image(getClass().getResource(BACKGROUND_PATH1).toString());
    BackgroundImage backgroundImage1 = new BackgroundImage(back1,
                                      BackgroundRepeat.NO_REPEAT,
                                      BackgroundRepeat.NO_REPEAT,
                                      BackgroundPosition.CENTER,  
                                      BackgroundSize.DEFAULT);
    Background background1 = new Background(backgroundImage1);
    gridPane.setBackground(background1);

    Image logo = new Image(getClass().getResource(LOGO_PATH).toString());
    ImageView logoView = new ImageView(logo);
    GridPane inputPane = new GridPane();
    TextField[] playerNames = new TextField[2];
    Spinner<?>[] playerTotalDeck = new Spinner<?>[2];
    Button playButton = new Button("Play!");
    gridPane.add(logoView, 0, 0);
    for (int i = 0; i < 2; ++i) {
      playerNames[i] = new TextField(){
        @Override
        public void replaceText(int start, int end, String text) {
          super.replaceText(start, end, text.toUpperCase());
        }
      };
      playerTotalDeck[i] = new Spinner<Integer>(40, 60, 40, 5);
      playerNames[i].setPromptText("Player " + (i+1) + " name");
      inputPane.add(new Label("Player " + (i + 1)), 0, i);
      inputPane.add(playerNames[i], 1, i);
      inputPane.add(new Label("Total Deck " + (i + 1)), 2, i);
      inputPane.add(playerTotalDeck[i], 3, i);
    }
    inputPane.setHgap(20);
    inputPane.setVgap(10);
    gridPane.add(inputPane, 0, 1);
    playButton.setPadding(new Insets(10, 20, 10, 20));
    gridPane.add(playButton, 0, 2);
    GridPane.setHalignment(playButton, HPos.CENTER);
    gridPane.setVgap(40);
    gridPane.requestFocus();
    playButton.setOnAction(e -> {
      if (playerNames[0].getText().isEmpty() || playerNames[1].getText().isEmpty()) {
        new Alert(AlertType.ERROR, "Player names can't be empty", ButtonType.OK).showAndWait();
      }
      else {
        gridPane.getChildren().clear();
        gridPane.setVgap(0);
        gridPane.setAlignment(Pos.CENTER);

      // TODO bikin tampilan awal untuk memasukkan nama dan total deck
      for (int i = 0; i < 2; ++i) {
        playerControllers[i].setName(playerNames[i].getText());
        playerControllers[i].setTotalDeckCard((Integer) playerTotalDeck[i].getValue());
      }

      gridPane.add(playerArenas[0], 1, 2, 2, 1); // bawah
      gridPane.add(playerArenas[1], 1, 0, 2, 1); // atas
      gridPane.add(line, 1, 1);
      gridPane.add(gameplay.getStatus(), 2, 1);
      gridPane.add(gameplay.getHoverSpace(), 0, 0, 1, 3);
      gridPane.setHgap(20);
      gridPane.getRowConstraints().add(new RowConstraints());
      gridPane.getRowConstraints().add(new RowConstraints(1));
      gridPane.getRowConstraints().add(new RowConstraints());
      Image back2 = new Image(getClass().getResource(BACKGROUND_PATH2).toString());
      BackgroundImage backgroundImage2 = new BackgroundImage(back2,
                                        BackgroundRepeat.NO_REPEAT,
                                        BackgroundRepeat.NO_REPEAT,
                                        BackgroundPosition.CENTER,  
                                        BackgroundSize.DEFAULT);
      Background background2 = new Background(backgroundImage2);
      gridPane.setBackground(background2);
      gameplay.run();
      }
    });
  }

  public static void main(String[] args) {
    launch();
  }
}