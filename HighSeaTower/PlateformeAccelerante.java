import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlatformeAccelerante extends Platform {
    public PlateformeAccelerante(double gameWidth) {
        super(gameWidth);
        this.defaultColor = Color.rgb(230, 221, 58);
        this.color = defaultColor;
    }
}

