import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Platform extends Entity {

    public Platform(double x, double y) {
        this.x = x;
        this.y = y;
        this.largeur = 100;
        this.hauteur = 10;

        this.color = Color.DARKORCHID;
    }

    @Override
    public void draw(GraphicsContext context) {
        context.setFill(color);
        context.fillRect(x, y, largeur, hauteur);
    }
}
