package gui;


import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import parser.Parser;

import java.util.concurrent.*;


public class GUIWindow extends HBox {

    private GUIButtons guiButtons;
    private GUIForecast guiForecast;
    private Parser parser;

    public GUIWindow() throws ExecutionException, InterruptedException {
        this.parser = new Parser();
        this.guiButtons = new GUIButtons(this.parser);
        try {
            this.guiForecast = new GUIForecast(this.parser.getForecastToDate(this.parser.getFirstDate()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.guiButtons.setGuiForecast(this.guiForecast);

        this.getChildren().add(0, this.guiButtons);
        this.getChildren().add(1, this.guiForecast);

        //выравнивает блок кнопок по уровню с блоком таблицы.
        HBox.setMargin(this.guiButtons, new Insets(this.guiForecast.getTopIndent(), 0, 0, 0));
        // обновлять Parser через каждые 30 мин.
        this.refreshParser();
    }

    /**
     * Метод обновляет Parser каждые 30 минут.
     */
    private void refreshParser() {
        System.out.println("getParser");
        Runnable task = new Runnable() {
            @Override
            public void run() {
                parser = new Parser();
                System.out.println(parser.getFirstDate());
                System.out.println("delay");
            }
        };
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleWithFixedDelay(task, 30, 30, TimeUnit.MINUTES);
        System.out.println("getParser");
    }
}
