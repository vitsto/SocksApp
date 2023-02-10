package pro.sky.socksapp.model;

public enum Color {
    YELLOW("желтый"), BLACK("черный"), WHITE("белый"), GREEN("зеленый"), BROWN("коричневый");
    private final String text;

    Color(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
