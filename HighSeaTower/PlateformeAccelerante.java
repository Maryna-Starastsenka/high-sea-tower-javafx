import javafx.scene.paint.Color;

/**
 * Classe Plateforme Accélérante hérite de plateforme
 * Lorsque la méduse se pose sur une plateforme accélérante,
 * la vitesse de l’écran est multipliée par 3
 */
public class PlateformeAccelerante extends Platform {

    private boolean platformTouched = false;

    /**
     * Constructeur de la plateforme accélérante
     * @param game
     */
    public PlateformeAccelerante(Game game) {
        super(game);
        this.defaultColor = Color.rgb(230, 221, 58);
        this.color = defaultColor;
    }

    /**
     * Multiplie la vitesse de la fenêtre vers haut en 3 si la méduse est placée
     * sur la plateforme
     * @param jellyfish
     * @param deltaYAbove distance en y entre le haut de la plateforme et le bas de la méduse
     */
    @Override
    public void jellyfishPushUp(Jellyfish jellyfish, double deltaYAbove) {
        jellyfish.vy = 0;
        jellyfish.y += deltaYAbove;
        debugYellow(deltaYAbove);

        if (platformTouched == false) {
            game.fenetreVY *= 3;
        }
        platformTouched = true;
    }

    /**
     * Relantit la vitesse de la fenêtre en 3 fois lorse que la méduse n'est plus
     * placée dessus
     * @param dt temps écoulé depuis le dernier update en secondes
     */
    @Override
    public void update (double dt) {
        super.update(dt);
        if (platformTouched == true && (this.game.jellyfish.y - this.y - this.hauteur) > 5) {
            game.fenetreVY /= 3;
            platformTouched = false;
        }
    }
}

