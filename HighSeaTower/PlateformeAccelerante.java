import javafx.scene.paint.Color;

/**
 * Classe de plateforme accélérante qui hérite de plateforme
 * Quand une entité la touche par le haut, cette entité
 * rebondit avec une vitesse multipliée par 1.5
 * avec une vitesse minimale de 100
 */
public class PlateformeAccelerante extends Platform {

    private boolean platformTouched = false;
    public PlateformeAccelerante(Game game) {
        super(game);
        this.defaultColor = Color.rgb(230, 221, 58);
        this.color = defaultColor;
    }

    /**
     *
     *
     * @param jellyfish méduse du jeu
     * @param deltaYAbove
     */
    @Override
    public void jellyfishPushUp(Jellyfish jellyfish, double deltaYAbove) {
        jellyfish.vy=0;
        jellyfish.y += deltaYAbove;
        debugYellow(deltaYAbove);

           if (platformTouched == false) {
                game.fenetreVY *= 3;
            }
        platformTouched = true;
    }

    @Override
    public void update (double dt) {
        super.update(dt);
        if (platformTouched == true && (this.game.jellyfish.y - this.y - this.hauteur) > 5) {
            game.fenetreVY /= 3;
            platformTouched = false;
        }
    }
}

