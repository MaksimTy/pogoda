package model;

/**
 * Время суток.
 */
public enum Darkness {

    MORNING("Утро"),
    MIDDAY("День"),
    EVENING("Вечер"),
    NIGHT("Ночь");

    private String name;

    Darkness(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Darkness getDarkness(String name) {
        if (name.equals(MORNING.name)) return MORNING;
        if (name.equals(MIDDAY.name)) return MIDDAY;
        if (name.equals(EVENING.name)) return EVENING;
        if (name.equals(NIGHT.name)) return NIGHT;
        else return null;
    }
}
