import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Classe Etoile hérite de Poisson
 * Les étoiles apparaissent toutes les 5 secondes à partir du niveau 2
 */
public class Starfish extends Fish {

    private Image ImageStarfish;
    private double initialPosY;

    /**
     * Constructeur de l'étoile
     *
     * @param x position horizontale initiale
     */
    public Starfish(double x) {
        super(x);
        initialPosY = y;
        this.ay = 0;
        this.vy = 300;
        this.ImageStarfish = new Image("/images/star.png");
    }

    /**
     * Met à jour les attributs de l'étoile
     *
     * @param dt temps écoulé depuis le dernier update (en secondes)
     */
    @Override
    public void update (double dt) {
        super.update(dt);

        // Définit la direction de la vitesse horizontale et la position verticale
        // afin d'assurer l'oscillation de l'étoile
        if (initialPosY - this.y > 50) {
            this.vy *= -1;
            this.y = initialPosY - 50;
        } else if (initialPosY - this.y < -50) {
            this.vy *= -1;
            this.y = initialPosY + 50;
        }
    }

    /**
     * Dessine l'étoile sur l'écran
     *
     * @param context contexte sur lequel dessiner
     */
    public void draw(GraphicsContext context) {
        context.drawImage(ImageStarfish, x, FishHunt.HEIGHT - y - height, width, height);
    }
}
