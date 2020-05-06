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
        this.width = imageSize;
        this.height = imageSize;
        this.x = x -this.imageSize/2;
        this.y = y -this.imageSize/2;

        imageTarget = new Image("images/cible.png");
    }

    public void move(double x, double y) {
        this.x = x-this.imageSize/2;
        this.y = y-this.imageSize/2;

    }

    @Override
    public void draw(GraphicsContext context) {
        context.drawImage(imageTarget, this.x, this.y, this.width, this.height);
    }
}

