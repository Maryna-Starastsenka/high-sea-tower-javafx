import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlateformeRebondissante extends Platform {

    public PlateformeRebondissante(Game game) {
        super(game);
        this.defaultColor = Color.LIGHTGREEN;
        this.color = defaultColor;
    }
}
