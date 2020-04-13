import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Classe abstraite Entité (modèle) qui représente un élément du jeu
 * et a une certaine position, vitesse et accélération
 */
public abstract class Entity {

    protected double largeur, hauteur;

    /**
     * attributs de position, vitesse et accélération
     */
    protected double x, y;
    protected double vx, vy;
    protected double ax, ay;

    protected Color color;

    /**
     * Met à jour la position et la vitesse de l'entité
     * @param dt temps écoulé depuis le dernier update en secondes
     */
    public void update(double dt) {
        vx += dt * ax;
        vy += dt * ay;
        x += dt * vx;
        y += dt * vy;
    }

    /**
     * Dessine l'entité en tenant compte de la fenêtre
     * @param context contexte sur lequel dessiner
     * @param fenetreY coordonnée y depuis le fond d'océan
     */
    public abstract void draw(GraphicsContext context, double fenetreY);
}