import javafx.scene.paint.Color;

public class PlateformeRebondissante extends Platform {

    public PlateformeRebondissante(Game game) {
        super(game);
        this.defaultColor = Color.LIGHTGREEN;
        this.color = defaultColor;
    }

    @Override
    public void jellyfishPushUp(Jellyfish jellyfish, double deltaYAbove) {
        jellyfish.vy = Math.max(jellyfish.vy*-1.5, 100);
        jellyfish.y += deltaYAbove;
        debugYellow(deltaYAbove);
    }
}
