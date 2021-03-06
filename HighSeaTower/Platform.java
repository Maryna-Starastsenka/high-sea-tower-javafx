import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Random;

/**
 * Classe abstraite Plateforme du modèle
 */
public abstract class Platform extends Entity {

    private static double platformSpacing = 100;
    protected Color defaultColor = Color.DARKORCHID;;
    protected Game game;

    /**
     * Constructeur de la plateforme
     *
     * @param game
     */
    public Platform(Game game) {
        this.game = game;
        // Largeur aléatoire entre 80 et 175px
        this.width = (double) new Random().nextInt(96) + 80;
        this.height = 10;
        // Position en x choisie aléatoirement dans les bornes de l'écran
        this.x = new Random().nextInt((int)(game.getWidth() - this.width + 1));
        this.y = platformSpacing;
        // Écart entre chaque plateforme
        platformSpacing += 100;
    }

    public static void setPlatformSpacing(double platformSpacing) {
        Platform.platformSpacing = platformSpacing;
    }

    /**
     * Résout la collision s'il y a une intersection entre la méduse et la plateforme
     */
    public void jellyfishCollision() {
        // Distance en y entre le haut de la plateforme et le bas de la méduse
        double deltaYAbove = this.y + this.height - this.game.getJellyfish().y;
        // Distance en y entre le haut de la meduse et le bas de la plateforme
        double deltaYBelow = this.game.getJellyfish().y + this.game.getJellyfish().height - this.y;

        // Si la méduse arrive d'en haut et la vitesse est negative,
        // la méduse est en train de tomber sur la plateforme
        if (deltaYAbove < 10 && this.game.getJellyfish().vy < 0) {
            jellyfishPushUp(this.game.getJellyfish(), deltaYAbove);

            this.game.getJellyfish().setOnGround(true);
        }

        // Si la méduse arrive d'en bas et la vitesse est positive,
        // la tête de la méduse cogne sous la plateforme
        if (deltaYBelow < 10 && this.game.getJellyfish().vy > 0) {
            jellyfishPushDown(this.game.getJellyfish(), deltaYBelow);
        }
    }

    /**
     * Place la méduse sur la plateforme et remet sa vitesse verticale à 0
     *
     * @param jellyfish méduse
     * @param deltaYAbove distance en y entre le haut de la plateforme et le bas de la méduse
     */
    public void jellyfishPushUp(Jellyfish jellyfish, double deltaYAbove) {
        jellyfish.vy = 0;
        jellyfish.y += deltaYAbove;
        debugYellow(deltaYAbove);
    }

    /**
     * Change la couleur de la plateforme à jaune si le mode debug est activé et
     * la distance entre la plateforme et la méduse en haut est moins de 5px
     *
     * @param deltaYAbove distance en y entre le haut de la plateforme et le bas de la méduse
     */
    public void debugYellow(double deltaYAbove) {
        if (game.getDebugMode() && Math.abs(deltaYAbove) < 5) {
            this.color = Color.YELLOW;
        }
    }

    /**
     * Action de rejet de la méduse vers le bas
     *
     * @param jellyfish méduse
     * @param deltaYBelow distance en y entre le haut de la meduse et le bas de la plateforme
     */
    public void jellyfishPushDown(Jellyfish jellyfish, double deltaYBelow) {
    }

    /**
     * Met à jour la position de la plateforme
     *
     * @param dt temps écoulé depuis le dernier update (en secondes)
     */
    @Override
    public void update (double dt) { super.update(dt); }

    /**
     * Dessine la plateforme sur l'écran
     *
     * @param context contexte sur lequel dessiner
     * @param windowY coordonnée y depuis le fond de l'océan
     */
    @Override
    public void draw(GraphicsContext context, double windowY, int gameHeight) {
        context.setFill(color);
        context.fillRect(x, gameHeight - (y - windowY) - height, width, height);
    }
}