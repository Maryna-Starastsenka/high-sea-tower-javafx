import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Entity {

    protected double largeur, hauteur;
    protected double x, y;

    protected double vx, vy;
    protected double ax, ay;

    protected Color color;

    /**
     * Met à jour la position et la vitesse de la balle
     *
     * @param dt Temps écoulé depuis le dernier update() en secondes
     */
    public void update(double dt) {
        vx += dt * ax;
        vy += dt * ay;
        x += dt * vx;
        y += dt * vy;

        // Force à rester dans les bornes de l'écran
        if (x + largeur > HighSeaTower.WIDTH || x < 0) {
            vx *= -1;
        }
        if (y + hauteur > HighSeaTower.HEIGHT) {
            vy *= -1;
        }
        x = Math.min(x, HighSeaTower.WIDTH - largeur);
        x = Math.max(x, 0);
        y = Math.min(y, HighSeaTower.HEIGHT - hauteur);
        y = Math.max(y, 0);
    }

    public abstract void draw(GraphicsContext context);
}

