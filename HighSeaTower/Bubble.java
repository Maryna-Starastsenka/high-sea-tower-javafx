import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Classe Bille (modèle) qui représente un objet bulle
 */
public class Bubble extends Entity {

    protected double radius;

    /**
     * Les limites du rayon de la vatesse vers haut des bulles
     */
    private double minRadius = 10;
    private double maxRadius = 40;
    private double minVY = 350;
    private double maxVY = 450;

    /**
     * Constructeur de la bulle
     * @param x position de la bulle
     * @param y position de la bulle
     */
    public Bubble(double x, double y) {
        this.x = x;
        this.y = y;
        // Vitesse aléatoire entre 350 et 450 px/s vers le haut
        this.vy = minVY + Math.random()*(maxVY - minVY + 1);
        // Rayon aléatoire entre 10 et 40 px
        this.radius = minRadius + Math.random()*(maxRadius - minRadius + 1);
        this.color = Color.rgb(0, 0, 255, 0.4);
    }

    /**
     * Dessine la bulle sur l'écran
     * @param context  contexte sur lequel dessiner
     * @param fenetreY coordonnée y depuis le fond d'océan
     */
    @Override
    public void draw(GraphicsContext context, double fenetreY) {
        context.setFill(color);
        context.fillOval(x, Game.HEIGHT-(y-fenetreY)-hauteur, radius * 2, radius * 2);
    }
}