import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Classe Cible du modèle
 */
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

    /**
     * Déplace la cible centrée sur le curseur
     *
     * @param x abscisse
     * @param y ordonnée
     */
    public void move(double x, double y) {
        this.x = x-this.imageSize/2;
        this.y = y+this.imageSize/2;

    }

    /**
     * Dessine l'étoile sur l'écran
     *
     * @param context contexte sur lequel dessiner
     */
    @Override
    public void draw(GraphicsContext context) {
        context.drawImage(imageTarget, this.x, FishHunt.HEIGHT - this.y, this.width, this.height);
    }
}

