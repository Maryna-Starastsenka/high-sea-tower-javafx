import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlatformeRebondissante extends Platform {

    public PlateformeRebondissante(double gameWidth) {
        super(gameWidth);
        this.defaultColor = Color.LIGHTGREEN;
        this.color = defaultColor;
    }
}
