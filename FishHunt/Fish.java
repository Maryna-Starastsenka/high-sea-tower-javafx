import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Fish extends Entity {

    private double minPosY = 1.0/5 * FishHunt.HEIGHT;
    private double maxPosY = 4.0/5 * FishHunt.HEIGHT;
    private int level = 1;

    public Fish (double x) {
        this.x = x;
        this.y = minPosY + Math.random()*(maxPosY - minPosY + 1);
        vx = 100 * Math.pow(level, 1/3) + 200;
        if (x != 0) {
            this.vx = -vx;
        }
    }
}


