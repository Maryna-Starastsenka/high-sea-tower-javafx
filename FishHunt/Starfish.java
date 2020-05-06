import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Starfish extends Fish {

    private Image ImageStarfish;
    private double initialPosY;

    public Starfish(double x) {
        super(x);
        initialPosY = y;
        this.ay = 0;
        this.vy = 300;
        this.ImageStarfish = new Image("/images/star.png");
    }

    @Override
    public void update (double dt) {
        super.update(dt);
        if (initialPosY - this.y > 50) {
            this.vy *= -1;
            this.y = initialPosY - 50;
        } else if (initialPosY - this.y < -50) {
            this.vy *= -1;
            this.y = initialPosY + 50;
        }
    }

    public void draw(GraphicsContext context) {
        context.drawImage(ImageStarfish, x, FishHunt.HEIGHT - y - height, width, height);
    }
}
