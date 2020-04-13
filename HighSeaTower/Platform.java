import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.Random;

public abstract class Platform extends Entity {
    private static double platformHeight = 100;
    protected Color defaultColor = Color.DARKORCHID;;
    protected Game game;

    public Platform(Game game) {
        this.game = game;
        this.largeur = (double) new Random().nextInt(96)+80; //entre 80 et 175 px;
        this.hauteur = 10;
        this.x = (double) new Random().nextInt((int) (game.WIDTH-this.largeur+1));
        this.y = platformHeight;
        platformHeight += 100;
    }

    public void jellyfishCollision() {
        //intersection déjà vérifiée dans Jellyfish
        double deltaYAbove = this.y + this.hauteur - this.game.jellyfish.y;
        double deltaYBelow = this.game.jellyfish.y + this.game.jellyfish.hauteur - this.y;

        //Si arrive d'en haut:
        if (deltaYAbove < 15 && this.game.jellyfish.vy < 0) {
            jellyfishPushUp(this.game.jellyfish, deltaYAbove);

            this.game.jellyfish.setOnGround(true);
        }

        //Si arrive d'en bas:
        if (deltaYBelow < 15 && this.game.jellyfish.vy > 0) {
            jellyfishPushDown(this.game.jellyfish, deltaYBelow);
        }
    }

    public void jellyfishPushUp(Jellyfish jellyfish, double deltaYAbove) {
        jellyfish.vy=0;
        jellyfish.y += deltaYAbove;
        debugYellow(deltaYAbove);

    }

    public void debugYellow(double deltaYAbove) {
        if (game.debugMode && Math.abs(deltaYAbove) < 5) {
            this.color = Color.YELLOW;
        }
    }

    //Sera overridée par PlateformeSolide:
    public void jellyfishPushDown(Jellyfish jellyfish, double deltaYBelow) {
    }

    @Override
    public void draw(GraphicsContext context, double fenetreY) {
        context.setFill(color);
        context.fillRect(x, Game.HEIGHT-(y-fenetreY)-hauteur, largeur, hauteur);
    }

    public static void setPlatformHeight (double platformHeight) {
        Platform.platformHeight = platformHeight;
    }

    @Override
    public void update (double dt) {
        super.update(dt);
    }
}