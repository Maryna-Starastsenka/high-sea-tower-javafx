import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Platform extends Entity {

    public Platform(double x, double y, double largeur) {
        this.x = x;
        this.y = y;
        this.largeur = largeur;
        this.hauteur = 10;

        this.color = Color.DARKORCHID;
    }

    @Override
    public void draw(GraphicsContext context, double fenetreY) {
        context.setFill(color);
        context.fillRect(x, Game.HEIGHT-(y-fenetreY)-hauteur, largeur, hauteur);
    }
}
