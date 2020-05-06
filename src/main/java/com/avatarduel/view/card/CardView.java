package com.avatarduel.view.card;

import com.avatarduel.model.card.Card;
import com.avatarduel.model.card.Character;
import com.avatarduel.model.card.Land;
import com.avatarduel.model.card.Skill;
import com.avatarduel.model.card.Aura;

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
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class CardView extends StackPane {
    private Card card;
    private GridPane containerCardView;
    private final ImageView backCard = new ImageView(
            new Image(getClass().getResource("/com/avatarduel/card/image/Back.jpg").toString()));
    private boolean isFaceUp;

    public CardView(Card card, int scale) {
        this.card = card;
        this.containerCardView = new GridPane();
        this.isFaceUp = true;

        this.containerCardView.getColumnConstraints().add(new ColumnConstraints(27 * scale));
        this.containerCardView.getColumnConstraints().add(new ColumnConstraints(27 * scale));
        this.containerCardView.getRowConstraints().add((new RowConstraints(15 * scale)));
        this.containerCardView.setStyle("-fx-background-color: #f7da00");

        // Setting names
        String string_tipe = "";
        if (this.card instanceof Skill) {
            string_tipe += ("[Skill]");
        } else {
            string_tipe += ("[" + this.card.getClass().getSimpleName() + "]");
        }

        GridPane header = new GridPane();
        GridPane deskripsiBox = new GridPane();
        Text empty = new Text(" ");
        Text nama = new Text(this.card.getName());
        Text tipe = new Text(string_tipe);
        Text deskripsi = new Text(this.card.getDescription());

        nama.setFill(Color.WHITE);
        empty.setFont(new Font("Helvetica", 4 * scale));
        nama.setFont(new Font("Helvetica", 4 * scale));
        nama.setFont(new Font("Helvetica", 4 * scale));
        tipe.setFont(new Font("Helvetica", 4 * scale));
        deskripsi.setFont(new Font("Helvetica", 3 * scale));

        TextFlow emptyFlow = new TextFlow(empty);
        TextFlow namaFlow = new TextFlow(nama);
        TextFlow tipeFlow = new TextFlow(tipe);
        TextFlow deskripsiFlow = new TextFlow(deskripsi);

        namaFlow.setTextAlignment(TextAlignment.LEFT);
        namaFlow.setPadding(new Insets(0 * scale, 0 * scale, 0 * scale, 2 * scale));
        tipeFlow.setTextAlignment(TextAlignment.RIGHT);
        tipeFlow.setPadding(new Insets(0 * scale, 4 * scale, 0 * scale, 0 * scale));
        deskripsiFlow.setTextAlignment(TextAlignment.LEFT);

        Image element_image = new Image(this.card.getElementImagePath());
        ImageView elementView = new ImageView(element_image);
        elementView.setFitHeight(10 * scale);
        elementView.setFitWidth(10 * scale);
        BorderPane element = new BorderPane();
        element.setCenter(elementView);

        header.add(namaFlow, 0, 0);
        header.add(element, 1, 0);
        header.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        header.setAlignment(Pos.CENTER);
        header.setStyle("-fx-background-color: #000000");
        header.getColumnConstraints().add(new ColumnConstraints(40 * scale));
        header.getColumnConstraints().add(new ColumnConstraints(12 * scale));
        header.getRowConstraints().add(new RowConstraints(1 * scale));
        this.containerCardView.add(header, 0, 0, 2, 1);
        if (card instanceof Character || card instanceof Land) {
            this.containerCardView.add(tipeFlow, 0, 1, 2, 1);
            if (card instanceof Character) {
                Text infoChar = new Text("ATK/" + Integer.toString(((Character) this.card).getAttack()) + " | DEF/"
                        + Integer.toString(((Character) this.card).getDefense()) + " | POW/"
                        + Integer.toString(((Character) this.card).getPower()));
                infoChar.setFont(new Font("Helvetica", 3 * scale));
                infoChar.setFill(Color.WHITE);

                TextFlow infoCharFlow = new TextFlow(infoChar);
                infoCharFlow.setTextAlignment(TextAlignment.RIGHT);

                GridPane infoBbox = new GridPane();
                infoBbox.add(infoCharFlow, 0, 0);
                infoBbox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
                        BorderWidths.DEFAULT)));
                infoBbox.setAlignment(Pos.CENTER);
                infoBbox.setStyle("-fx-background-color: #000000");
                infoBbox.getColumnConstraints().add(new ColumnConstraints(50 * scale));

                this.containerCardView.add(infoBbox, 0, 6, 2, 1);

            }
        }

        else {
            Text effect = new Text("★" + (this.card.getClass().getSimpleName()));
            effect.setFont((new Font("Helvetica", 4 * scale)));
            TextFlow effectFlow = new TextFlow(effect);
            effectFlow.setTextAlignment(TextAlignment.LEFT);
            effectFlow.setPadding(new Insets(0 * scale, 0 * scale, 0 * scale, 4 * scale));
            this.containerCardView.add(effectFlow, 0, 1);
            this.containerCardView.add(tipeFlow, 1, 1);

            String info = "";
            if (this.card.getClass().getSimpleName().equals("Aura")) {
                if (((Aura) this.card).getAttack() >= 0) {
                    info += "+";
                }
                info += (Integer.toString(((Aura) this.card).getAttack()) + " ATK   ");
                if (((Aura) this.card).getDefense() >= 0) {
                    info += "+";
                }
                info += (Integer.toString(((Aura) this.card).getDefense()) + " DEF");
            }

            Text infoSkill = new Text(info);
            infoSkill.setFont(new Font("Helvetica", 3 * scale));
            infoSkill.setFill(Color.LIGHTBLUE);

            Text infoPow = new Text("POW/" + Integer.toString(((Skill) this.card).getPower()));
            infoPow.setFont(new Font("Helvetica", 3 * scale));
            infoPow.setFill(Color.YELLOW);

            TextFlow infoSkillFlow = new TextFlow(infoSkill);
            infoSkillFlow.setTextAlignment(TextAlignment.LEFT);

            TextFlow infoPowFlow = new TextFlow(infoPow);
            infoPowFlow.setTextAlignment(TextAlignment.RIGHT);

            GridPane infoSkillBox = new GridPane();
            infoSkillBox.add(infoSkillFlow, 0, 0);
            infoSkillBox.add(infoPowFlow, 1, 0);

            infoSkillBox.setBorder(new Border(
                    new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            infoSkillBox.setAlignment(Pos.CENTER);
            infoSkillBox.setStyle("-fx-background-color: #000000");
            infoSkillBox.getColumnConstraints().add(new ColumnConstraints(35 * scale));
            infoSkillBox.getColumnConstraints().add(new ColumnConstraints(15 * scale));

            this.containerCardView.add(infoSkillBox, 0, 6, 2, 1);

        }

        Image image = new Image(this.card.getImagePath());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(30 * scale);
        imageView.setFitWidth(30 * scale);
        BorderPane bPane = new BorderPane();
        bPane.setCenter(imageView);
        this.containerCardView.add(bPane, 0, 2, 2, 2);

        this.containerCardView.add(emptyFlow, 0, 4, 1, 1);

        deskripsiBox.add(deskripsiFlow, 0, 0);
        deskripsiBox.setAlignment(Pos.CENTER);
        deskripsiBox.setStyle("-fx-background-color: #fffeb3");
        deskripsiBox.getColumnConstraints().add(new ColumnConstraints(50 * scale));
        deskripsiBox.getRowConstraints().add(new RowConstraints(21 * scale));
        this.containerCardView.add(deskripsiBox, 0, 5, 2, 1);

        this.containerCardView.setPadding(new Insets(3 * scale, 4 * scale, 3 * scale, 4 * scale));
        this.containerCardView.setMinSize(61 * scale, 88 * scale);
        this.containerCardView.setMaxSize(61 * scale, 88 * scale);
        this.getChildren().add(containerCardView);
        this.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.setMinSize(63 * scale, 90 * scale);
        this.setMaxSize(63 * scale, 90 * scale);
        this.setAlignment(Pos.TOP_CENTER);

        backCard.setFitHeight(90 * scale);
        backCard.setPreserveRatio(true);
    }

    /**
     * @return the card
     */
    public Card getCard() {
        return card;
    }

    /**
     * @return true if card is facing up
     */
    public boolean getIsFaceUp() {
        return isFaceUp;
    }

    /**
     * Procedure that make the card in view will be face down
     */
    public void faceDown() {
        if (isFaceUp) {
            this.getChildren().remove(containerCardView);
            this.getChildren().add(backCard);
            isFaceUp = false;
        }
    }

    /**
     * Procedure that make the card in view will be face up
     */
    public void faceUp() {
        if (!isFaceUp) {
            this.getChildren().remove(backCard);
            this.getChildren().add(containerCardView);
            isFaceUp = true;
        }
    }

    /**
     * Set card border color based on color
     * 
     * @param color border's color
     */
    public void setBorder(Color color) {
        this.setBorder(
                new Border(new BorderStroke(color, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }
}