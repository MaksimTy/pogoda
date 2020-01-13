package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import parser.Parser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeSet;

public class GUIButtons extends VBox {

    private TreeSet<Date> dates;
/*    private Border border = new Border(
            new BorderStroke(
                    Color.GREEN,
                    BorderStrokeStyle.DASHED,
                    CornerRadii.EMPTY,
                    BorderWidths.DEFAULT));*/
    private Date selected;              //выбранная дата для отображения прогноза.
    private GUIForecast guiForecast;
    private Parser parser;

    private final int insets = 1;
    private final int buttonsWidth = 80;
    private final int dateFontSize = 10;


    public GUIButtons(Parser parser) {

        this.parser = parser;
        this.dates = this.parser.getDates();

        SimpleDateFormat sd = new SimpleDateFormat("dd.MM.yyyy");

        for (Date date : this.dates
        ) {
            String title = sd.format(date);

            Button button = new Button();
            button.setText(title);
            button.setFont(new Font(this.dateFontSize));
            button.setPrefWidth(this.buttonsWidth);
            button.setMaxWidth(this.buttonsWidth);
            button.setMinWidth(this.buttonsWidth);
            /*button.setBackground(new Background(
                    new BackgroundFill(Color.SNOW,
                            CornerRadii.EMPTY,
                            Insets.EMPTY)));
            button.setBorder(new Border(
                    new BorderStroke(Color.BLACK,
                            BorderStrokeStyle.SOLID,
                            new CornerRadii(15),
                            BorderWidths.DEFAULT)));*/

            this.getChildren().add(button);

            VBox.setMargin(button, new Insets(this.insets));
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        DateFormat ddf = DateFormat.getDateInstance(0);
                        selected = sd.parse(button.getText());
                        guiForecast.getTitle().setText(ddf.format(selected));
                        guiForecast.setForecastList(parser.getForecastToDate(selected));
                        guiForecast.setVisible(true);
                        guiForecast.makeData(guiForecast.getForecastList());

                    } catch (ParseException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
       // this.setBorder(this.border);
    }

    public void setGuiForecast(GUIForecast guiForecast) {
        this.guiForecast = guiForecast;
    }
}
