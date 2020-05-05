import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Fish extends Entity {

    private double minVerticalSpeed = 100;
    private double maxVerticalSpeed = 200;
    private double minPosY = 1/5 * FishHunt.HEIGHT;
    private double maxPosY = 4/5 * FishHunt.HEIGHT;
    private double ay = 100;
    private int level = 1;

    public Fish (double x) {
        this.x = x;
        this.y = minPosY + Math.random()*(maxPosY - minPosY + 1);
        vx = 100 * Math.pow(level, 1/3) + 200;
        if (x == 0) {
            this.vx = -vx;
        } else {
            this.vx = vx;
        }
        this.vy = minVerticalSpeed + Math.random()*(maxVerticalSpeed - minVerticalSpeed + 1);
    }
}

