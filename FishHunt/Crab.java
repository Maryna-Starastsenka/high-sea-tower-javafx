import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Classe Crabe hérite de Poisson
 * Les crabes apparaissent toutes les 5 secondes à partir du niveau 2
 */
public class Crab extends Fish {

    /**
     * Le timer commence à 0.5 plutôt qu'à 0 de manière à pouvoir
     * gérer des valeurs strictement positives ou strictement négatives
     * avec la même variable
     */
    private double crabTimer = 0.5;
    private final Image imageCrab;

    /**
     * Constructeur du crabe
     * Sa vitesse horizontale est 1,3 fois celle des poissons ordinaires
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
        // nota : le timer commence à 0.5, d'où la comparaison avec 1.0
        if (crabTimer >= 1.0) {
            this.vx *= -1;
            crabTimer = 0;
        // Inverse de nouveau la vitesse après 0.25 seconde
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
