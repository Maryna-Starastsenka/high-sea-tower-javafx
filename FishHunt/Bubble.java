import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Classe bulle du modèle
 * Sert à décorer l'arrière-plan du jeu dans l'océan
 */
public class Bubble extends Entity {

    private final double radius;

    /**
     * Plages de rayon et vitesse verticale des bulles
     */
    private final double minRadius = 10;
    private final double maxRadius = 40;
    private final double minVY = 350;
    private final double maxVY = 450;

    /**
     * Constructeur de la bulle qui prend en paramètres les coordonnées
     * depuis le coin inférieur gauche du niveau (au fond de l'océan)
     *
     * @param x abscisse
     * @param y ordonnée
     */
    public Bubble(double x, double y) {
        this.x = x;
        this.y = y;
        // Vitesse aléatoire dans la plage définie
        this.vy = minVY + Math.random()*(maxVY - minVY + 1);
        // Rayon aléatoire dans la plage définie
        this.radius = minRadius + Math.random() * (maxRadius - minRadius + 1);
        this.color = Color.rgb(0, 0, 255, 0.4);
    }

    /**
     * Dessine la bulle sur le contexte du jeu en fonction de la position
     * de la fenêtre
     *
     * @param context  contexte sur lequel dessiner
     */
    @Override
    public void draw(GraphicsContext context) {
        context.setFill(color);
        context.fillOval(x, FishHunt.HEIGHT - y, radius * 2, radius * 2);
    }

    public double getRadius() {
        return this.radius;
    }
}