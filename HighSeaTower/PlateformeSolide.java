import javafx.scene.paint.Color;

public class PlatformeSolide extends Platform {

    public PlateformeSolide(double gameWidth) {
        super(gameWidth);
        this.defaultColor = Color.rgb(184, 15, 36);
        this.color = defaultColor;
    }
}
