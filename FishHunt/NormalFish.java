import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.util.Random;

public class NormalFish extends Fish {

    private double minVerticalSpeed = 100;
    private double maxVerticalSpeed = 200;

    private Image currentNormalFish;
    Random rand = new Random();
    private int randomInt = rand.nextInt(8);
    private int redValue = rand.nextInt(255);
    private int greenValue = rand.nextInt(255);
    private int blueValue = rand.nextInt(255);
    private Color color = Color.rgb(redValue, greenValue, blueValue);


    public NormalFish(double x) {
        super(x);
        this.ay = 100;
        this.vy = minVerticalSpeed + Math.random()*(maxVerticalSpeed - minVerticalSpeed + 1);

        currentNormalFish = new Image("/images/fish/0"+randomInt+".png");

        if (x != 0) {
            currentNormalFish = ImageHelpers.flop(currentNormalFish);
        }
        currentNormalFish = ImageHelpers.colorize(currentNormalFish, color);
    }

    @Override
    public void update (double dt) { super.update(dt); }

    public void draw(GraphicsContext context) {
        context.drawImage(currentNormalFish, x, FishHunt.HEIGHT - y, width, height);
    }
}
