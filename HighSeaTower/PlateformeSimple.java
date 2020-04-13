import javafx.scene.paint.Color;

/**
 * Classe Plateforme Simple hérite de Plateforme
 * Peut être traverser depuis le bas mais qui sert de plancher lorsqu’on tombe dessus
 */
public class PlateformeSimple extends Platform {

    /**
     * Constructeur de la plateforme simple
     * @param game
     */
    public PlateformeSimple(Game game) {
        super(game);
        this.defaultColor = Color.rgb(230,134,58);
        this.color = defaultColor;
    }
}
