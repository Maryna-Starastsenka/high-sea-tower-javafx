import javafx.scene.paint.Color;

/**
 * Classe Plateforme Rebondissante hérite de Plateforme
 * Fait un rebond à la méduse lorsqu’elle tombe dessus
 */
public class PlateformeRebondissante extends Platform {

    /** Contructeur de la plateforme rebondissante
     * @param game
     */
    public PlateformeRebondissante(Game game) {
        super(game);
        this.defaultColor = Color.LIGHTGREEN;
        this.color = defaultColor;
    }

    /**
     * Accélère la vitesse en haut et rebondit la méduse
     * @param jellyfish
     * @param deltaYAbove distance en y entre le haut de la plateforme et le bas de la méduse
     */
    @Override
    public void jellyfishPushUp(Jellyfish jellyfish, double deltaYAbove) {
        // Vitesse après rebond est au moins de 100px/s vers le haut
        jellyfish.vy = Math.max(jellyfish.vy * -1.5, 100);
        jellyfish.y += deltaYAbove;
        debugYellow(deltaYAbove);
    }
}
