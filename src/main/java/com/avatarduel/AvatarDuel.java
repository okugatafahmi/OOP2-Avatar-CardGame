package com.avatarduel;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import com.avatarduel.model.enums.Element;
import com.avatarduel.model.card.Card;
import com.avatarduel.model.builder.*;
import com.avatarduel.util.CSVReader;

public class AvatarDuel extends Application {
  private static final String CARD_CSV_FOLDER_PATH = "card/data/";

  public LinkedList<Card> loadCards() throws IOException, URISyntaxException {
    File folder = new File(getClass().getResource(CARD_CSV_FOLDER_PATH).toURI());
    LinkedList<Card> loaded = new LinkedList<>();
    for (File file: folder.listFiles()){
      String filename = file.getName();
      CSVReader landReader = new CSVReader(file, "\t");
      landReader.setSkipHeader(true);
      List<String[]> landRows = landReader.read();
      for (String[] row : landRows) {
        if (filename.equals("character.csv")){
          loaded.add(new CharacterBuilder()
                         .name(row[1])
                         .element(Element.valueOf(row[2]))
                         .description(row[3])
                         .image(row[4])
                         .attack(Integer.parseInt(row[5]))
                         .defense(Integer.parseInt(row[6]))
                         .power(Integer.parseInt(row[7]))
                         .build());
        }
        else if (filename.equals("land.csv")){
          loaded.add(new LandBuilder()
                         .name(row[1])
                         .element(Element.valueOf(row[2]))
                         .description(row[3])
                         .image(row[4])
                         .build());
        }
        else if (filename.equals("skill.csv")){
          loaded.add(new SkillBuilder()
                         .name(row[1])
                         .element(Element.valueOf(row[2]))
                         .description(row[3])
                         .image(row[4])
                         .attack(Integer.parseInt(row[5]))
                         .defense(Integer.parseInt(row[6]))
                         .power(Integer.parseInt(row[7]))
                         .effect(row[8])
                         .build());
        }
      }
    }
    return loaded;
  }

  @Override
  public void start(Stage stage) {
    Text text = new Text();
    text.setText("Loading...");
    text.setX(50);
    text.setY(50);

    Group root = new Group();
    root.getChildren().add(text);

    Scene scene = new Scene(root, 1280, 720);

    stage.setTitle("Avatar Duel");
    stage.setScene(scene);
    stage.show();

    try {
      LinkedList<Card> listCard = this.loadCards();
      text.setText("Avatar Duel!");
    } catch (Exception e) {
      text.setText("Failed to load cards: " + e);
    }
  }

  public static void main(String[] args) {
    launch();
  }
}