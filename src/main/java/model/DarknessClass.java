package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Класс объекта "Darkness"
 */
public class DarknessClass {

    private Darkness darkness;    //наименование времени суток
    private ImageView imageView;  //иконка

    // в конструкотор передаётся строка, содержащая сырые данные из парсера
    // пример: "Ночь"
    public DarknessClass(String string) {
        this.darkness = Darkness.getDarkness(string);
        String fileName = "src/main/resources/icons/" + this.darkness.name() + ".png";
        try {
            this.imageView = new ImageView(new Image(new FileInputStream(new File(fileName))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Darkness getDarkness() {
        return darkness;
    }

    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Метод возвращает имя времени суток
     * @return имя времени суток
     */
    public String getName(){
        return this.darkness.getName();
    }

    /**
     * Метод возвращает порядковый номер времени суток.
     * @return порядвковый номер времени суток.
     */
    public int getGrade(){
        return this.darkness.ordinal();
    }
}
