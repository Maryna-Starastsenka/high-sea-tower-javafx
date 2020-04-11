import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlateformeAccelerante extends Platform {
    public PlateformeAccelerante(Game game) {
        super(game);
        this.defaultColor = Color.rgb(230, 221, 58);
        this.color = defaultColor;
    }

    @Override
    public void jellyfishPushUp(Jellyfish jellyfish, double deltaYAbove) {
        jellyfish.vy=0;
        jellyfish.y += deltaYAbove;
        game.fenetreVY *= 3;
    }
}

