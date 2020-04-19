package com.avatarduel.view.player;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import com.avatarduel.model.card.Element;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class LandStatus extends GridPane {
    private HashMap<Element, Integer> rowElement;
    private Text[] powerTotal;
    private Text[] powerCanUse;

    public LandStatus() {
        this.setMinSize(180, 120);
        this.setMaxSize(180, 120);
        this.setAlignment(Pos.CENTER);
        this.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        rowElement = new HashMap<>();
        try {
            File elementFolder = new File(getClass().getResource("/com/avatarduel/element").toURI());
            int row = 0;
            GridPane elementName = new GridPane();
            for (File file : elementFolder.listFiles()) {
                String filename = file.getName().split("[.]")[0];
                Image image = new Image(new FileInputStream(file));
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(15);
                imageView.setFitWidth(15);
                elementName.add(imageView, 0, row);
                elementName.add(new Text(file.getName().split("[.]")[0]), 1, row);
                rowElement.put(Element.valueOf(filename.toUpperCase()), row);
                ++row;
            }
            elementName.setVgap(5);
            elementName.setHgap(3);
            this.add(elementName, 0, 0);
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
        GridPane powerNumberGrid = new GridPane();
        powerTotal = new Text[5];
        powerCanUse = new Text[5];
        for (int i=0; i<5; ++i) {
            powerCanUse[i] = new Text("0");
            powerTotal[i] = new Text("0");
            powerNumberGrid.add(powerCanUse[i], 0, i);
            powerNumberGrid.add(new Text(" / "), 1, i);
            powerNumberGrid.add(powerTotal[i], 2, i);
        }
        powerCanUse[0].setText("12");
        powerNumberGrid.setVgap(5);
        this.add(powerNumberGrid, 1, 0);
        this.setHgap(30);
    }

    /**
     * Update power can use
     * @param element element to set
     * @param powerCanUse amount power can use to set
     */
    public void updatePowerCanUse(Element element, int powerCanUse) {
        int row = this.rowElement.get(element);
        this.powerCanUse[row].setText(Integer.toString(powerCanUse));
    }

    /**
     * Update power total
     * @param element element to set
     * @param powerTotal amount power total to set
     */
    public void updatePowerTotal(Element element,  int powerTotal) {
        int row = this.rowElement.get(element);
        this.powerTotal[row].setText(Integer.toString(powerTotal));
    }
}