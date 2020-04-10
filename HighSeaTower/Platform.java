import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class Platform extends Entity {

    private String type;
    private static double platformHeight = 100;
    private Color defaultColor;

    public Platform(double gameWidth, String type) {

        this.largeur = (double) new Random().nextInt(96)+80; //entre 80 et 175 px;
        this.hauteur = 10;
        this.x = (double) new Random().nextInt((int) (gameWidth-this.largeur+1));
        this.y = platformHeight;
        platformHeight += 100;
        switch(type) {
            case "simple":
                this.defaultColor = Color.rgb(230,134,58);
                break;
            case "rebondissante":
                this.defaultColor = Color.LIGHTGREEN;
                break;
            case "accelerante":
                this.defaultColor = Color.rgb(230, 221, 58);
                break;
            case "solide":
                this.defaultColor = Color.rgb(184, 15, 36);
                break;
        }
        this.color = defaultColor;
    }

    @Override
    public void draw(GraphicsContext context, double fenetreY) {
        context.setFill(color);
        context.fillRect(x, Game.HEIGHT-(y-fenetreY)-hauteur, largeur, hauteur);
    }
}
