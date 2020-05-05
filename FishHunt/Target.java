import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Target extends Entity {

    private int imageSize = 50;
    private Image imageTarget;

    /**
     * Constructeur de la cible
     *
     * @param x abscisse
     * @param y ordonnée
     */
    public Target(double x, double y) {
        this.x = x - imageSize / 2;
        this.y = y - imageSize / 2;
        this.width = imageSize;
        this.height = imageSize;
        imageTarget = new Image("/images/cible.png");
    }

    public void move(double x, double y) {

    }

    @Override
    public void draw(GraphicsContext context) {

    }
}

