package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import model.DarknessClass;

/**
 * Класс для отображения времени суток.
 */
public class GUIDarkness extends HBox {

    private DarknessClass darkness;
    private Label name;
    private final int insets = 10;           //стандартные отступы

    public GUIDarkness(DarknessClass darkness) {
        this.darkness = darkness;
        this.name = new Label(darkness.getName());

        this.getChildren().add(this.darkness.getImageView());
        this.getChildren().add(this.name);
        //иконка выравнивается по 1/2 высоты строки.
        double iconHeight = this.darkness.getImageView().getImage().getHeight();
        HBox.setMargin(this.darkness.getImageView(),
                new Insets((GUIForecast.getRowHeight() - iconHeight) / 2, insets, 0, 0));

        this.name.setPrefHeight(Double.MAX_VALUE);
        this.name.setAlignment(Pos.CENTER);

    }
}
