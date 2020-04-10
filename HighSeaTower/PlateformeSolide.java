import javafx.scene.paint.Color;

public class PlateformeSolide extends Platform {

    public PlateformeSolide(Game game) {
        super(game);
        this.defaultColor = Color.rgb(184, 15, 36);
        this.color = defaultColor;
    }

    @Override
    public void jellyfishPushDown(Jellyfish jellyfish, double deltaYBelow) {
        jellyfish.vy=0;
        jellyfish.y -= deltaYBelow;
    }
}
