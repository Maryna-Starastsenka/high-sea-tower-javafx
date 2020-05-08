import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Classe Balle du modèle
 */
public class Bullet extends Entity {

    private double radius = 50;
    private double vx = 300;
    private boolean exploded = false;

    public boolean getExploded() {
        return this.exploded;
    }

    /**
     * Constructeur de la balle
     *
     * @param x abscisse
     * @param y ordonnée
     */
    public Bullet(double x, double y) {
        this.color = Color.BLACK;
        this.x = x;
        this.y = y;
    }

    /**
     * Met à jour les attributs de la balle
     *
     * @param dt temps écoulé depuis le dernier update (en secondes)
     */
    @Override
    public void update(double dt) {
        if (!this.exploded) {
            this.radius -= vx * dt;
            if (radius <= 0) {
                this.exploded = true;
            }
        }
    }

    /**
     * Fait le test de collision pour vérifier si le poisson est capturé
     *
     * @param fish poisson qu'on veut capturer
     * @return vrai si la balle atteint le poisson
     */
    public boolean testCollision(Fish fish) {
        if (this.x >= fish.x && this.x <= fish.x + fish.width) {
            return this.y >= fish.y && this.y <= fish.y + fish.width;
        }
        return false;
    }

    /**
     * Dessine la balle sur l'écran
     *
     * @param context contexte sur lequel dessiner
     */
    @Override
    public void draw(GraphicsContext context) {
        context.setFill(color);
        context.fillOval(this.x - this.radius, FishHunt.HEIGHT - (this.y + this.radius),
                this.radius * 2, this.radius * 2);
    }
}
