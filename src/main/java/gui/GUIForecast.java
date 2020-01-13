package gui;


import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Forecast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class GUIForecast extends AnchorPane {

    /**
     * массив с шириной столбцов.
     */
    private final int[] columnsWeights = {90, 300, 45, 45, 45, 110};

    /**
     * стандартный отступ
     */
    private final int titleFontSize = 15;
    private final int headFontSize = 13;
    private final int dateFontSize = 10;
    private final double indent = 5;
    private static final int rowHeight = 50;

    /**
     * массив с названиями столбцов.
     */
    private final Label[] labels = {

            new Label("Время суток"),
            new Label("Погодные явления"),
            new Label("t°C"),
            new Label("Давл."),
            new Label("Влаж."),
            new Label("Ветер")
    };

    /**
     * список прогнозов на выбранную дату.
     */
    private List<Forecast> forecastList;
    private Label title;
    private GridPane gridPane;
    private Border border = new Border(
            new BorderStroke(
                    Color.INDIGO,
                    BorderStrokeStyle.SOLID,
                    CornerRadii.EMPTY,
                    BorderWidths.DEFAULT));
    private List<Label> dataLabels;

    public GUIForecast(List<Forecast> forecasts) {
        this.forecastList = forecasts;
        DateFormat df = DateFormat.getDateInstance(0);
        this.title = new Label();
        this.title.setText(df.format(this.getDate()));
        this.title.setFont(new Font(titleFontSize));
        this.getChildren().add(this.title);

        AnchorPane.setTopAnchor(this.title, indent);
        AnchorPane.setLeftAnchor(this.title, indent);

        this.gridPane = new GridPane();
        this.getChildren().add(gridPane);
        this.gridPane.setGridLinesVisible(true);

        AnchorPane.setTopAnchor(this.gridPane, this.getTopIndent());
        AnchorPane.setLeftAnchor(this.gridPane, indent);
        AnchorPane.setRightAnchor(this.gridPane, indent);
        AnchorPane.setBottomAnchor(this.gridPane, indent);

        /** Заголовки таблицы*/
        this.makeHeader();
        /** Данные таблицы*/
        this.makeData(this.forecastList);
    }

    /**
     * Метод очищает данные таблицы
     */
    public void clearDate() {
        this.gridPane.getRowConstraints().clear();
        this.gridPane.getChildren().removeAll(this.dataLabels);
    }

    /**
     * Метод заполняет таблицу данными
     */
    public void makeData(List<Forecast> forecastList) {
        if (this.gridPane.getRowConstraints().size() > 0)
            clearDate();
        this.gridPane.getRowConstraints().add(new RowConstraints(this.rowHeight));

        this.dataLabels = new ArrayList<>();
        for (int i = 0; i < forecastList.size(); i++
        ) {
            this.gridPane.getRowConstraints().add(new RowConstraints(this.rowHeight));

            List<Label> rowLabels = new ArrayList<>();
            rowLabels.add(new Label("", new GUIDarkness(forecastList.get(i).getDarkness())));
            //погодные явления
            Label levents = new Label(String.valueOf(forecastList.get(i).getEvents()));
            levents.setPadding(new Insets(indent));
            rowLabels.add(levents);
            //вывод температуры:
            String temperature = Arrays.toString(forecastList.get(i).getTemperature())
                    .substring(1, Arrays.toString(forecastList.get(i).getTemperature()).length() - 1)
                    .replaceAll(" ", ",").replaceAll(",", ".");
            Label ltemper = new Label(temperature);
            ltemper.setPadding(new Insets(indent));
            rowLabels.add(ltemper);
            //давление
            Label lpress = new Label(String.valueOf(forecastList.get(i).getPressure()));
            lpress.setPadding(new Insets(indent));
            rowLabels.add(lpress);
            //влажность
            Label lwetness = new Label(forecastList.get(i).getWetness());
            lwetness.setPadding(new Insets(indent));
            rowLabels.add(lwetness);

            rowLabels.add(new Label("", new GUIWind(forecastList.get(i).getWind())));

            this.dataLabels.addAll(rowLabels);

            for (int j = 0; j < rowLabels.size(); j++
            ) {
                this.gridPane.add(rowLabels.get(j), j, i + 1);

                rowLabels.get(j).setFont(new Font(dateFontSize));
                rowLabels.get(j).setBorder(border);
                rowLabels.get(j).setPrefHeight(Double.MAX_VALUE);
                rowLabels.get(j).setPrefWidth(Double.MAX_VALUE);

            }
        }
    }

    /**
     * Метод формирует заголовок таблицы.
     */
    public void makeHeader() {
        for (int i = 0; i < this.labels.length; i++) {
            this.gridPane.getColumnConstraints().add(new ColumnConstraints(columnsWeights[i]));
            this.gridPane.setColumnIndex(this.labels[i], i);
            this.gridPane.getChildren().add(this.labels[i]);
            this.labels[i].setPadding(new Insets(indent));
            this.labels[i].setFont(new Font(headFontSize));
            this.labels[i].setBorder(border);
            this.labels[i].setPrefHeight(Double.MAX_VALUE);
            this.labels[i].setPrefWidth(Double.MAX_VALUE);
        }
    }

    public Label getTitle() {
        return title;
    }

    public void setForecastList(List<Forecast> forecastList) {
        this.forecastList = forecastList;
    }

    public List<Forecast> getForecastList() {
        return forecastList;
    }

    public double getTopIndent() {
        return (double) this.titleFontSize + this.indent * 2;
    }

    public Date getDate() {
        return this.forecastList.get(0).getDate();
    }

    public static int getRowHeight() {
        return rowHeight;
    }
}
