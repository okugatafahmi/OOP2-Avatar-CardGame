package com.avatarduel.view;

import com.avatarduel.model.card.Card;
import com.avatarduel.model.card.Character;
import com.avatarduel.model.card.Land;
import com.avatarduel.model.card.Skill;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class CardView extends GridPane {
    private Card card;
    private GridPane containerCardView;

    public CardView(Card card) {
        this.card = card;
        this.containerCardView = new GridPane();

        this.containerCardView.getColumnConstraints().add(new ColumnConstraints(25));
        this.containerCardView.getColumnConstraints().add(new ColumnConstraints(25));
        this.containerCardView.getRowConstraints().add((new RowConstraints(15)));

        // Setting names
        GridPane header = new GridPane();
        Text nama = new Text(this.card.getName());
        Text element = new Text(this.card.getElement().toString());
        Text tipe = new Text("[" + this.card.getClass().getSimpleName() + "]");

        nama.setStyle("-fx-font: 4 arial");
        element.setStyle("-fx-font: 4 arial");
        tipe.setStyle("-fx-font: 4 arial");

        TextFlow namaFlow = new TextFlow(nama);
        TextFlow elementFlow = new TextFlow(element);
        TextFlow tipeFlow = new TextFlow(tipe);

        namaFlow.setTextAlignment(TextAlignment.LEFT);
        elementFlow.setTextAlignment(TextAlignment.RIGHT);
        tipeFlow.setTextAlignment(TextAlignment.RIGHT);

        header.add(namaFlow, 0, 0);
        header.add(elementFlow, 1, 0);
        header.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        header.setAlignment(Pos.CENTER);
        header.getColumnConstraints().add(new ColumnConstraints(35));
        header.getColumnConstraints().add(new ColumnConstraints(15));
        this.containerCardView.add(header, 0, 0, 2, 1);
        if (card instanceof Character || card instanceof Land){
            this.containerCardView.add(tipeFlow, 0, 1, 2, 1);
        }
        else {
            Text effect = new Text(((Skill) this.card).getEffect());
            effect.setStyle("-fx-font: 4 arial");
            TextFlow effectFlow = new TextFlow(effect);
            effectFlow.setTextAlignment(TextAlignment.LEFT);
            this.containerCardView.add(effect, 0, 1);
            this.containerCardView.add(tipeFlow, 1, 1);
        }

        Image image = new Image(this.card.getImagePath());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(37);
        imageView.setFitWidth(37);
        BorderPane bPane = new BorderPane();
        bPane.setCenter(imageView);
        this.containerCardView.add(bPane, 0, 2, 2, 2);
        super.add(containerCardView, 0, 0);
        super.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        super.setMinSize(65, 90);
        super.setMaxSize(65, 90);
        super.setPadding(new Insets(3, 3, 3, 3));
        super.setHgap(2);
        super.setAlignment(Pos.TOP_CENTER);
    }

}