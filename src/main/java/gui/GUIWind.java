package gui;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import model.Wind;

import java.util.Arrays;

/**
 * Класс для визуаизации объекта "Wind".
 */
public class GUIWind extends HBox {

    private Wind wind;
    private Label direction;                            //направление ветра
    private Label speed;                                //скорость ветра
    private final int dateFontSize = 10;                //шрифт для вывода данных
    private final int labelDirectionlWidth = 25;         //стандартная ширина текстового элемента
    private final int labelSpeedlWidth = 45;             //стандартная ширина текстового элемента
    private final int insets = 3;                       //стандартные отступы

    public GUIWind(Wind wind) {
        this.wind = wind;
        this.direction = new Label(this.wind.getDirection().getName());
        this.direction.setFont(new Font(dateFontSize));
        String lspeed = Arrays.toString(this.wind.getSpeed());
        this.speed = new Label(
                lspeed.substring(1, lspeed.length() - 1).
                        replace(",", " -").concat(" м/с"));
        this.speed.setFont(new Font(dateFontSize));

        this.getChildren().add(this.wind.getImageView());
        this.getChildren().add(this.direction);
        this.getChildren().add(this.speed);

        //иконка выравнивается по 1/2 высоты строки.
        double iconHeight = this.wind.getImageView().getImage().getHeight();
        HBox.setMargin(this.wind.getImageView(),
                new Insets((GUIForecast.getRowHeight() - iconHeight) / 2, 0, 0, 0));
        HBox.setHgrow(this.wind.getImageView(), Priority.ALWAYS);

        this.direction.setPrefWidth(labelDirectionlWidth);
        this.speed.setPrefWidth(labelSpeedlWidth);

        this.direction.setPrefHeight(Double.MAX_VALUE);
        this.speed.setPrefHeight(Double.MAX_VALUE);

        this.speed.setAlignment(Pos.CENTER);
        this.direction.setAlignment(Pos.CENTER);
    }
}
