import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bubble extends Entity {

    protected double radius;
    private double minRadius = 10;
    private double maxRadius = 40;
    private double minVY = 350;
    private double maxVY = 450;

    public Bubble(double x, double y) {
        this.x = x;
        this.y = y;
        this.vy = minVY + Math.random()*(maxVY - minVY + 1);
        this.radius = minRadius + Math.random()*(maxRadius - minRadius + 1);
        this.color = Color.rgb(0, 0, 255, 0.4);

    }

    @Override
    public void draw(GraphicsContext context, double fenetreY) {
        context.setFill(color);
        context.fillOval(x, Game.HEIGHT-(y-fenetreY)-hauteur, radius * 2, radius * 2);
    }
}