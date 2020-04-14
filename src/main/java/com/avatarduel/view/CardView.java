package com.avatarduel.view;

import com.avatarduel.model.card.Card;
import com.avatarduel.model.card.Character;
import com.avatarduel.model.card.Land;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
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

        this.containerCardView.getColumnConstraints().add(new ColumnConstraints(35));
        this.containerCardView.getColumnConstraints().add(new ColumnConstraints(35));

        // Setting names
        GridPane header = new GridPane();
        Text nama = new Text(this.card.getName());
        Text element = new Text(this.card.getElement().toString());
        Text tipe = new Text("[" + this.card.getClass().getSimpleName() + "]");

        nama.setStyle("-fx-font: 5 arial");
        element.setStyle("-fx-font: 5 arial");
        tipe.setStyle("-fx-font: 5 arial");

        TextFlow namaFlow = new TextFlow(nama);
        TextFlow elementFlow = new TextFlow(element);
        TextFlow tipeFlow = new TextFlow(tipe);

        namaFlow.setTextAlignment(TextAlignment.RIGHT);
        elementFlow.setTextAlignment(TextAlignment.RIGHT);
        tipeFlow.setTextAlignment(TextAlignment.RIGHT);

        header.add(namaFlow, 0, 0);
        header.add(elementFlow, 1, 0);
        this.containerCardView.add(header, 0, 0, 2, 1);
        if (card instanceof Character || card instanceof Land){
            this.containerCardView.add(tipeFlow, 0, 1, 2, 1);
        }

        Image image = new Image(this.card.getImagePath());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        this.containerCardView.add(imageView, 0, 2, 2, 2);
        super.add(containerCardView, 0, 0);
        super.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        super.setMinSize(70, 90);
        super.setMaxSize(70, 90);
    }

}