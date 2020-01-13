package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 * Класс описывает объект "ветер"
 */
public class Wind {

    private Direction direction;        //направление ветра
    private int[] speed;                //скорость
    private String unit;                //единица измерения
    private ImageView imageView;        //иконка

    // конструкотор передаётся строка, содержащая сырые данные из парсера
    //пример: "[Ю-З] 7-9 м/с"

    public Wind(String string) {
        String[] elems = string.split("\\s");
        this.direction = Direction.getDirection(elems[0].substring(1, elems[0].length() - 1));
        String[] speed = elems[1].split("-");
        this.speed = new int[]{
                Integer.parseInt(speed[0].trim()),
                Integer.parseInt(speed[1].trim())};
        this.unit = elems[2];

        String fileName = "src/main/resources/icons/" + this.direction.name() + ".png";
        File file = new File(fileName);
        try {
            this.imageView = new ImageView(new Image(new FileInputStream(file)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Wind{" +
                "direction=" + direction.getName() +
                ", speed=" + Arrays.toString(speed).substring(1, Arrays.toString(speed).length() - 1) +
                ", unit='" + unit + '\'' +
                '}';
    }

    public enum Direction {
        N("С"),
        NE("С-В"),
        E("В"),
        SE("Ю-В"),
        S("Ю"),
        SW("Ю-З"),
        W("З"),
        NW("С-З");

        private String name;

        Direction(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static Direction getDirection(String name) {
            if (name.equals("С")) return N;     //кириллица
            if (name.equals("C")) return N;     //латинница
            if (name.equals("С-В")) return NE;
            if (name.equals("В")) return E;
            if (name.equals("Ю-В")) return SE;
            if (name.equals("Ю")) return S;
            if (name.equals("Ю-З")) return SW;
            if (name.equals("З")) return W;
            if (name.equals("С-З")) return NW;
            else return null;
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public int[] getSpeed() {
        return speed;
    }

    public String getUnit() {
        return unit;
    }

    public ImageView getImageView() {
        return imageView;
    }
}
