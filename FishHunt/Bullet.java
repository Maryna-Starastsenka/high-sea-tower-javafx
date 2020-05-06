import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet extends Entity {

    private double radius = 50;
    private double vx = 300;
    private boolean exploded = false;

    public Bullet(double x, double y) {
        this.color = Color.BLACK;
        this.x = x;
        this.y = y;
    }

    @Override
    public void update(double dt) {
        if (!this.exploded) {
            this.radius -= vx*dt;
            if (radius <= 0) {
                checkCollisions();
                this.exploded = true;
            }
        }
    }

    private void checkCollisions () {

            System.out.println("La balle a explosé!");

    }

    @Override
    public void draw(GraphicsContext context) {
        context.setFill(color);
        context.fillOval(this.x- this.radius, this.y-this.radius, this.radius*2, this.radius*2);
    }

}
