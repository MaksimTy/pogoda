package gui;


import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import parser.Parser;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class GUIWindow extends HBox {

    private GUIButtons guiButtons;
    private GUIForecast guiForecast;
    private Parser parser;

    public GUIWindow() {
        this.parser = new Parser();
        this.guiButtons = new GUIButtons(this.parser);
        try {
            this.guiForecast = new GUIForecast(this.parser.getForecastToDate(this.parser.getFirstDate()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.guiButtons.setGuiForecast(this.guiForecast);

        this.getChildren().add(0,this.guiButtons);
        this.getChildren().add(1,this.guiForecast);

        //выравнивает блок кнопок по уровню с блоком таблицы.
        HBox.setMargin(this.guiButtons, new Insets(this.guiForecast.getTopIndent(), 0,0,0));

        Executor executor = Executors.newSingleThreadExecutor();


    }
}
