package com.avatarduel;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import com.avatarduel.model.enums.Element;
import com.avatarduel.gui.PlayerArena;
import com.avatarduel.model.card.Card;
import com.avatarduel.model.card.CardBuilder;
import com.avatarduel.util.CSVReader;

public class AvatarDuel extends Application {
  private static final String CARD_CSV_FOLDER_PATH = "card/data/";
  private static final int WIDTH = 1280;
  private static final int HEIGHT = 720;

  public LinkedList<Card> loadCards() throws IOException, URISyntaxException {
    File folder = new File(getClass().getResource(CARD_CSV_FOLDER_PATH).toURI());
    LinkedList<Card> loaded = new LinkedList<>();
    for (File file : folder.listFiles()) {
      String filename = file.getName().split("[.]")[0];
      CSVReader landReader = new CSVReader(file, "\t");
      landReader.setSkipHeader(true);
      List<String[]> landRows = landReader.read();
      for (String[] row : landRows) {
        CardBuilder builder = new CardBuilder(filename)
                                  .name(row[1])
                                  .element(Element.valueOf(row[2]))
                                  .description(row[3])
                                  .image(row[4]);

        if (filename.equals("character") || filename.equals("skill")) {
          builder = builder.power(Integer.parseInt(row[5]))
                           .attack(Integer.parseInt(row[6]))
                           .defense(Integer.parseInt(row[7]));
          if (filename.equals("skill")) {
            builder = builder.effect(row[8]);
          }
        }
        loaded.add(builder.build());
      }
    }
    return loaded;
  }

  @Override
  public void start(Stage stage) {
    Text text = new Text();
    text.setText("Loading...");

    Line line = new Line();
    line.setStartX(100);
    line.setStartY(HEIGHT / 2);
    line.setEndX(940);
    line.setEndY(HEIGHT / 2);

    GridPane gridPane = new GridPane();
    gridPane.setAlignment(Pos.CENTER);
    gridPane.getChildren().add(text);

    Scene scene = new Scene(gridPane, WIDTH, HEIGHT, Color.WHITESMOKE);

    stage.setTitle("Avatar Duel");
    stage.setScene(scene);
    stage.show();

    LinkedList<Card> listCard;
    try {
      listCard = this.loadCards();
      text.setText("Avatar Duel!");
    } catch (Exception e) {
      text.setText("Failed to load cards: " + e);
      return;
    }

    PlayerArena[] PlayerArena = new PlayerArena[2];
    for (int i = 0; i < 2; ++i) {
      PlayerArena[i] = new PlayerArena(((i == 0) ? true : false));
    }
    gridPane.getChildren().remove(text);
    text.setText("Buat status fase dan player");
    gridPane.add(PlayerArena[0], 1, 0, 2, 1);
    gridPane.add(PlayerArena[1], 1, 2, 2, 1);
    gridPane.add(line, 1, 1);
    gridPane.add(text, 2, 1);
    GridPane hover = new GridPane();
    hover.setBorder(
        new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    hover.getChildren().add(new Text("Ini buat hover"));
    hover.setMinSize(240, 320);
    hover.setMaxSize(240, 320);
    gridPane.add(hover, 0, 0, 1, 3);
    gridPane.setHgap(20);
  }

  public static void main(String[] args) {
    launch();
  }
}