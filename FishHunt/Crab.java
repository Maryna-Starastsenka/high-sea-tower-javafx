import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Crab extends Fish {

    private double crabTimer = 0.5;
    private Image imageCrab;

    public Crab(double x) {
        super(x);
        this.ay = 0;
        this.vy = 0;
        this.vx *= 1.3;
        imageCrab = new Image("/images/crabe.png");
    }

    @Override
    public void update(double dt) {
        super.update(dt);
        if (crabTimer > 0) {
            crabTimer += dt;
        } else {
            crabTimer -= dt;
        }
        if (crabTimer >= 1.0) {
            this.vx *= -1;
            crabTimer = 0;
        } else if (crabTimer < -0.25) {
            this.vx *= -1;
            crabTimer = 0.5;
        }
    }

    @Override
    public void draw(GraphicsContext context) {
        context.drawImage(imageCrab, x, FishHunt.HEIGHT - y - height, width, height);
    }
}
