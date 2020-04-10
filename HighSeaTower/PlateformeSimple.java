import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlateformeSimple extends Platform {

    public PlateformeSimple(Game game) {
        super(game);
        this.defaultColor = Color.rgb(230,134,58);
        this.color = defaultColor;
    }
}
