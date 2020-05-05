import javafx.scene.paint.Color;

/**
 * Classe Plateforme Solide hérite de Plateforme
 * Ne peut pas être traversée depuis le bas
 */
public class PlateformeSolide extends Platform {

    /**
     * Constructeur de la plateforme solide
     *
     * @param game jeu
     */
    public PlateformeSolide(Game game) {
        super(game);
        this.defaultColor = Color.rgb(184, 15, 36);
        this.color = defaultColor;
    }

    /**
     * Fait rebondir la méduse et ne permet pas à la méduse de sauter sur la plateforme
     *
     * @param jellyfish méduse
     * @param deltaYBelow distance en y entre le haut de la meduse et le bas de la plateforme
     */
    @Override
    public void jellyfishPushDown(Jellyfish jellyfish, double deltaYBelow) {
        jellyfish.vy = 0;
        jellyfish.y -= deltaYBelow;
    }
}