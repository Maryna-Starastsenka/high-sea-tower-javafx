import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlatformeSimple extends Platform {

    public PlatformeSimple(double gameWidth) {
        super(gameWidth);
        this.defaultColor = Color.rgb(230,134,58);
        this.color = defaultColor;
    }
}
