import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Classe Crabe hérite de Poisson
 * Les crabes apparaissent tous les 5 secondes à partir du niveau 2
 */
public class Crab extends Fish {

    private double crabTimer = 0.5;
    private Image imageCrab;

    /**
     * Constructeur du crabe
     *
     * @param x position horizontale initiale
     */
    public Crab(double x) {
        super(x);
        this.ay = 0;
        this.vy = 0;
        this.vx *= 1.3;
        imageCrab = new Image("/images/crabe.png");
    }

    /**
     * Met à jour les attributs du crabe
     *
     * @param dt temps écoulé depuis le dernier update (en secondes)
     */
    @Override
    public void update(double dt) {
        super.update(dt);
        if (crabTimer > 0) {
            crabTimer += dt;
        } else {
            crabTimer -= dt;
        }
        // Inverse la vitesse après 0.5 seconde
        if (crabTimer >= 1.0) {
            this.vx *= -1;
            crabTimer = 0;
        // Remet la vitesse après 0.25 seconde
        } else if (crabTimer < -0.25) {
            this.vx *= -1;
            crabTimer = 0.5;
        }
    }

    /**
     * Dessine le crabe sur l'écran
     *
     * @param context contexte sur lequel dessiner
     */
    @Override
    public void draw(GraphicsContext context) {
        context.drawImage(imageCrab, x, FishHunt.HEIGHT - y - height, width, height);
    }
}
