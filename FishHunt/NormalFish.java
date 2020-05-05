import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class NormalFish extends Fish {

    private double minVerticalSpeed = 100;
    private double maxVerticalSpeed = 200;

    private Image currentNormalFish;
    private int imageSize = 50;

    public NormalFish(double x) {
        super(x);
        this.ay = 100;
        this.vy = minVerticalSpeed + Math.random()*(maxVerticalSpeed - minVerticalSpeed + 1);
        this.width = imageSize;
        this.height = imageSize;
        currentNormalFish = new Image("/images/fish/00.png");
    }

    @Override
    public void update (double dt) { super.update(dt); }

    public void draw(GraphicsContext context) {
        context.drawImage(currentNormalFish, x, FishHunt.HEIGHT - y, width, height);
    }
}
