package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Класс описывает объект прогноза
 */
public class Forecast implements Comparable<Forecast> {

    private Date date;
    private DarknessClass darkness;                     // время суток, одно из четырёх
    private String events;                              // погодные явления
    private int[] temperature;                          // диапазон температур в градусах Цельсия
    private int pressure;                               // давление в мм. ртутного столба
    private String wetness;                             // относительная влажность в %
    private Wind wind;                                  // ветер, направление и скорость

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.mm.yyyy");

    //в конструкотор передаётся строка, содержащая сырые данные из парсера
    //пример: "13.01.2020:Ночь:Пасмурно. (99%) Небольшой дождь, местами умеренный. (2.8 мм.):0..+2:742:96%:[Ю-З] 7-9 м/с"

    public Forecast(String string, String sep) {
        String[] elems = string.split(sep);

        try {
            this.date = dateFormat.parse(elems[0]);
        } catch (ParseException e) {
            this.date = null;
        }
        this.darkness = new DarknessClass(elems[1]);

        int border = elems[2].indexOf("%) ") + 3;  //делит погодные явления на две строки
        this.events = elems[2].substring(0, border) + "\n" + elems[2].substring(border);

        String[] temper = elems[3].split("\\.\\.");
        this.temperature = new int[]{
                Integer.parseInt(temper[0].trim()),
                Integer.parseInt(temper[1].trim())
        };
        this.pressure = Integer.parseInt(elems[4]);
        this.wetness = elems[5];
        this.wind = new Wind(elems[6]);
    }

    @Override
    public String toString() {
        return "Forecast{" +
                "date=" + dateFormat.format(date) +
                ", darkness=" + darkness.getName() +
                ", events='" + events + '\'' +
                ", temperature='" + Arrays.toString(temperature) + '\'' +
                ", pressure=" + pressure +
                ", wetness='" + wetness + '\'' +
                ", wind='" + wind + '\'' +
                '}';
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure
     * {@code sgn(x.compareTo(y)) == -sgn(y.compareTo(x))}
     * for all {@code x} and {@code y}.  (This
     * implies that {@code x.compareTo(y)} must throw an exception iff
     * {@code y.compareTo(x)} throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * {@code (x.compareTo(y) > 0 && y.compareTo(z) > 0)} implies
     * {@code x.compareTo(z) > 0}.
     *
     * <p>Finally, the implementor must ensure that {@code x.compareTo(y)==0}
     * implies that {@code sgn(x.compareTo(z)) == sgn(y.compareTo(z))}, for
     * all {@code z}.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * {@code (x.compareTo(y)==0) == (x.equals(y))}.  Generally speaking, any
     * class that implements the {@code Comparable} interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * {@code sgn(}<i>expression</i>{@code )} designates the mathematical
     * <i>signum</i> function, which is defined to return one of {@code -1},
     * {@code 0}, or {@code 1} according to whether the value of
     * <i>expression</i> is negative, zero, or positive, respectively.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(Forecast o) {
        if (this.date.compareTo(o.date) != 0) {
            return this.date.compareTo(o.date);
        } else {
            return this.darkness.getGrade() - o.darkness.getGrade();
        }
    }

    public Date getDate() {
        return this.date;
    }

    public DarknessClass getDarkness() {
        return darkness;
    }

    public String getEvents() {
        return events;
    }

    public int[] getTemperature() {
        return temperature;
    }

    public int getPressure() {
        return pressure;
    }

    public String getWetness() {
        return wetness;
    }

    public Wind getWind() {
        return wind;
    }

}
