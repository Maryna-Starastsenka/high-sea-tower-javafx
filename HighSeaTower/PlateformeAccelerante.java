import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlateformeAccelerante<update> extends Platform {

    private boolean platformTouched = false;
    public PlateformeAccelerante(Game game) {
        super(game);
        this.defaultColor = Color.rgb(230, 221, 58);
        this.color = defaultColor;
    }

    @Override
    public void jellyfishPushUp(Jellyfish jellyfish, double deltaYAbove) {
        jellyfish.vy=0;
        jellyfish.y += deltaYAbove;
        debugYellow(deltaYAbove);

            if (platformTouched == false) {
                game.fenetreVY *= 3;
            }
        platformTouched = true;
    }

    @Override
    public void update (double dt) {
        super.update(dt);
        if (platformTouched == true && (this.game.jellyfish.y - this.y - this.hauteur) > 5) {
            game.fenetreVY /= 3;
            platformTouched = false;
        }
    }
}

