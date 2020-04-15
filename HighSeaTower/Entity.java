import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Classe abstraite du modèle qui représente un élément du jeu
 * disposant d'une certaine position, vitesse et accélération
 */
public abstract class Entity {

    //Tous les attributs sont censés pouvoir être modifiés de l'extérieur, donc pas besoin d'accesseurs ni mutateurs
    protected double width, height;

    /**
     * attributs horizontaux et verticaus de position, vitesse et accélération
     */
    protected double x, y;
    protected double vx, vy;
    protected double ax, ay;

    protected Color color;

    /**
     * Met à jour la position et la vitesse de l'entité
     *
     * @param dt temps écoulé depuis le dernier update (en secondes)
     */
    public void update(double dt) {
        vx += dt * ax;
        vy += dt * ay;
        x += dt * vx;
        y += dt * vy;
    }

    /**
     * Dessine l'entité en tenant compte des coordonnées de la fenêtre
     *
     * @param context contexte sur lequel dessiner
     * @param windowY ordonnée depuis le fond de l'océan
     */
    public abstract void draw(GraphicsContext context, double windowY, int gameHeight);

}