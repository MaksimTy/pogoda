package parser;

import model.Forecast;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Parser {

    private final static String[] URLS = {
            "http://www.pogoda.spb.ru/",
            "http://www.pogoda.spb.ru/10days/"
    };

    public final static String SEP = ":";             //разделитель для элементов в строке с прогнозом

    private List<Forecast> forecasts;
    private TreeSet<Date> dates;

    public Parser() {
        try {
            this.setForecasts();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.dates = new TreeSet<>();
        for (Forecast inst : this.forecasts
        ) {
            this.dates.add(inst.getDate());
        }
    }

    public static void main(String[] args) throws Exception {
        Parser parser = new Parser();
        // css query language
        List<Forecast> forecasts = parser.getForecastToDate(Calendar.getInstance().getTime());

        System.out.println(Calendar.getInstance().getTime());
        System.out.println(forecasts.get(0).getDate());

        for (Forecast inst : forecasts
        ) {
            System.out.println(inst);
        }
    }

    /**
     * Метод извлекает из page данные о прогнозе погоды в виде массива строк.
     * Каждая строка содержит информацию о прогнозе погоды на дату и время суток.
     * Параметры прогноза разделены разделителем SEP
     *
     * @param page веб-страница
     * @return список строк с прогнозами на дату и время суток
     * @throws Exception
     */
    private List<String> getLines(Document page) throws Exception {
        List<String> lines = new ArrayList<>(); // список для строк с итоговыми данными прогноза
        Element list = page.select("table[class=wt]").first();
        String date = "";

        for (Element node : list.children().first().children()) {
            StringBuilder stringBuilder = new StringBuilder();
            if (!node.select("tr[class=wth]").isEmpty()) {
                date = getDateFromString(node.select("th[id=dt]").text()) + "." +
                        Calendar.getInstance().get(Calendar.YEAR);
            }
            if (!node.select("tr[valign=top]").isEmpty()) {
                stringBuilder.append(date).append(SEP);
                Elements elements = node.select("tr[valign=top]");
                for (Element elem : elements.first().children()
                ) {
                    stringBuilder.append(elem.text()).append(SEP);
                }
                lines.add(stringBuilder.substring(0, stringBuilder.length() - 1));
            }
        }
        return lines;
    }

    private static String getDateFromString(String string) throws Exception {
        Pattern pattern = Pattern.compile("^(\\d{2}\\.\\d{2})");
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            return matcher.group();
        }
        throw new Exception("Can't extract date from string!");
    }

    public Document getPage(String url) throws IOException {
        Document page = Jsoup.parse(new URL(url), 5000);
        return page;
    }

    private void setForecasts() throws Exception {
        this.forecasts = new ArrayList<>();
        List<String> list = new ArrayList<>();
        for (String str : URLS
        ) {
            list.addAll(this.getLines(this.getPage(str)));
        }
        for (String str : list
        ) {
            forecasts.add(new Forecast(str, SEP));
        }
    }

    public TreeSet<Date> getDates() {
        return this.dates;
    }

    public List<Forecast> getForecastToDate(Date date) throws Exception {
        DateFormat df = DateFormat.getDateInstance();
        List<Forecast> forecasts = this.forecasts.
                stream().
                filter(x -> df.format(x.getDate()).equals(df.format(date))).
                collect(Collectors.toList());
        return forecasts;
    }

    public Date getFirstDate(){
        return this.dates.first();
    }
}
