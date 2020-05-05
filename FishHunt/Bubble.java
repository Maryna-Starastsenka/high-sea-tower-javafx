import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Classe du modèle qui représente un objet bulle
 * qui décore l'arrière-plan
 */
public class Bubble extends Entity {

    private double radius;

    /**
     * Plages de rayon et vitesse verticale des bulles
     */
    private double minRadius = 10;
    private double maxRadius = 40;
    private double minVY = 350;
    private double maxVY = 450;

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
        this.radius = minRadius + Math.random()*(maxRadius - minRadius + 1);
        this.color = Color.rgb(0, 0, 255, 0.4);
    }

    /**
     * Dessine la bulle sur le contexte du jeu en fonction de la position
     * de la fenêtre
     *
     * @param context  contexte sur lequel dessiner
     * @param windowY ordonnée de la fenêtre depuis le fond de l'océan
     */
    @Override
    public void draw(GraphicsContext context, double windowY, int gameHeight) {
        context.setFill(color);
        context.fillOval(x, gameHeight -(y-windowY)- height, radius * 2, radius * 2);
    }

    public double getRadius() {
        return this.radius;
    }
}