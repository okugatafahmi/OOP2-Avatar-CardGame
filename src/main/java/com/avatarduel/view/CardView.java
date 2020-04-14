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
    private final ImageView backCard = new ImageView(
            new Image(getClass().getResource("/com/avatarduel/card/image/Back.jpg").toString()));
    private boolean isFaceUp;

    public CardView(Card card) {
        this.card = card;
        this.containerCardView = new GridPane();
        this.isFaceUp = true;

        this.containerCardView.getColumnConstraints().add(new ColumnConstraints(25));
        this.containerCardView.getColumnConstraints().add(new ColumnConstraints(25));
        this.containerCardView.getRowConstraints().add((new RowConstraints(15)));
        this.containerCardView.setStyle("-fx-background-color: #f7da00");

        // Setting names
        GridPane header = new GridPane();
        GridPane deskripsi_box = new GridPane();
        GridPane white_space = new GridPane();
        Text empty = new Text(" ");
        Text nama = new Text(this.card.getName());
        Text element = new Text(this.card.getElement().toString());
        Text tipe = new Text("[" + this.card.getClass().getSimpleName() + "]");
        Text deskripsi = new Text(this.card.getDescription());

        empty.setStyle("-fx-font: 4 helvetica");
        nama.setStyle("-fx-font: bold 4 helvetica");
        nama.setFill(Color.WHITE);
        element.setStyle("-fx-font: 4 helvetica");
        element.setFill(Color.WHITE);
        tipe.setStyle("-fx-font: 4 helvetica");
        deskripsi.setStyle("-fx-font: 3 helvetica");

        TextFlow emptyFlow = new TextFlow(empty);
        TextFlow namaFlow = new TextFlow(nama);
        TextFlow elementFlow = new TextFlow(element);
        TextFlow tipeFlow = new TextFlow(tipe);
        TextFlow deskripsiFlow = new TextFlow(deskripsi);

        namaFlow.setTextAlignment(TextAlignment.LEFT);
        elementFlow.setTextAlignment(TextAlignment.RIGHT);
        tipeFlow.setTextAlignment(TextAlignment.RIGHT);
        deskripsiFlow.setTextAlignment(TextAlignment.LEFT);

        header.add(namaFlow, 0, 0);
        header.add(elementFlow, 1, 0);
        header.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        header.setAlignment(Pos.CENTER);
        header.setStyle("-fx-background-color: #000000");
        header.getColumnConstraints().add(new ColumnConstraints(35));
        header.getColumnConstraints().add(new ColumnConstraints(15));
        header.getRowConstraints().add(new RowConstraints(1));
        this.containerCardView.add(header, 0, 0, 2, 1);
        if (card instanceof Character || card instanceof Land) {
            this.containerCardView.add(tipeFlow, 0, 1, 2, 1);
            if(card instanceof Character){
                Text info_char = new Text("ATK/"+Integer.toString(((Character) this.card).getAttack()) + " | DEF/"+Integer.toString(((Character) this.card).getDefense()) + " | POW/"+Integer.toString(((Character) this.card).getPower()));
                info_char.setStyle("-fx-font: 3 helvetica");
                info_char.setFill(Color.WHITE);
    
                TextFlow info_char_flow = new TextFlow(info_char);
                info_char_flow.setTextAlignment(TextAlignment.RIGHT);
    
                GridPane info_box = new GridPane();
                info_box.add(info_char_flow,0,0);
                info_box.setBorder(new Border(
                    new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID, CornerRadii.EMPTY,BorderWidths.DEFAULT)));
                info_box.setAlignment(Pos.CENTER);
                info_box.setStyle("-fx-background-color: #000000");
                info_box.getColumnConstraints().add(new ColumnConstraints(50));
                
                this.containerCardView.add(info_box,0,6,2,1);
    
            }
        }
        
        else {
            Text effect = new Text(((Skill) this.card).getEffect());
            effect.setStyle("-fx-font: 4 helvetica");
            TextFlow effectFlow = new TextFlow(effect);
            effectFlow.setTextAlignment(TextAlignment.LEFT);
            this.containerCardView.add(effect, 0, 1);
            this.containerCardView.add(tipeFlow, 1, 1);

            Text info_skill = new Text("+"+Integer.toString(((Skill) this.card).getAttack()) + "ATK +"+Integer.toString(((Skill) this.card).getDefense()) + "DEF");
            info_skill.setStyle("-fx-font: 3 helvetica");
            info_skill.setFill(Color.LIGHTBLUE);

            Text info_pow = new Text("POW/"+Integer.toString(((Skill) this.card).getPower()));
            info_pow.setStyle("-fx-font: 3 helvetica");
            info_pow.setFill(Color.YELLOW);

            TextFlow info_skill_flow = new TextFlow(info_skill);
            info_skill_flow.setTextAlignment(TextAlignment.LEFT);

            TextFlow info_pow_flow = new TextFlow(info_pow);
            info_pow_flow.setTextAlignment(TextAlignment.RIGHT);

            GridPane info_skill_box = new GridPane();
            info_skill_box.add(info_skill_flow,0,0);
            info_skill_box.add(info_pow_flow,1,0);
            
            info_skill_box.setBorder(new Border(
                new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID, CornerRadii.EMPTY,BorderWidths.DEFAULT)));
            info_skill_box.setAlignment(Pos.CENTER);
            info_skill_box.setStyle("-fx-background-color: #000000");
            info_skill_box.getColumnConstraints().add(new ColumnConstraints(35));
            info_skill_box.getColumnConstraints().add(new ColumnConstraints(15));
            
            this.containerCardView.add(info_skill_box,0,6,2,1);

        }

        Image image = new Image(this.card.getImagePath());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        BorderPane bPane = new BorderPane();
        bPane.setCenter(imageView);
        this.containerCardView.add(bPane, 0, 2, 2, 2);

        this.containerCardView.add(emptyFlow,0,4,1,1);

        deskripsi_box.add(deskripsiFlow,0,0);
        deskripsi_box.setBorder(new Border(
                        new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID, CornerRadii.EMPTY,BorderWidths.DEFAULT)));
        deskripsi_box.setAlignment(Pos.CENTER);
        deskripsi_box.setStyle("-fx-background-color: #fffeb3");
        deskripsi_box.getColumnConstraints().add(new ColumnConstraints(50));
        deskripsi_box.getRowConstraints().add(new RowConstraints(20));
        this.containerCardView.add(deskripsi_box,0,5,1,1);
        
        super.add(containerCardView, 0, 0);
        super.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        super.setMinSize(63, 90);
        super.setMaxSize(63, 90);
        super.setPadding(new Insets(3, 3, 3, 3));
        super.setHgap(2);
        super.setAlignment(Pos.TOP_CENTER);

        backCard.setFitHeight(90);
        backCard.setPreserveRatio(true);
    }

    /**
     * Procedure that make the card in view will be face down
     */
    public void faceDown() {
        if (isFaceUp){
            this.getChildren().remove(containerCardView);
            super.setPadding(new Insets(0, 0, 0, 0));
            this.getChildren().add(backCard);
            isFaceUp = false;
        }
    }

    /**
     * Procedure that make the card in view will be face up
     */
    public void faceUp() {
        if (!isFaceUp){
            this.getChildren().remove(backCard);
            super.setPadding(new Insets(3, 3, 3, 3));
            this.getChildren().add(containerCardView);
            isFaceUp = true;
        }
    }
}