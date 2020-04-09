
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Game {

    public static int WIDTH, HEIGHT;

    private Platform[] platforms = new Platform[5];
    private Jellyfish jellyfish;
    private boolean debugMode = false;
    //Fenêtre:
    private double fenetreAY;
    private double fenetreVY = 5;
    private double fenetreY = 0;

    public Game(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        for (int i = 0; i < platforms.length; i++) {
            platforms[i] = new Platform((double) i / platforms.length * WIDTH, Math.random() * HEIGHT);
        }

        jellyfish = new Jellyfish(WIDTH/2 - 50/2, 0);
    }

    public void jump() {
        jellyfish.jump();
    }

    public void moveLeft() { jellyfish.moveLeft(); }

    public void moveRight() {
        jellyfish.moveRight();
    }

    public void resetAccelerator() { jellyfish.resetAccelerator(); }

    public void setDebug() {
        debugMode = !debugMode;
        if (debugMode) {
            fenetreVY = 0;
        } else {
            fenetreVY = 3;
        }
    }

    public void update(double dt) {
        //Updater la fenêtre:
        fenetreY += fenetreVY*dt;

        /**
         * À chaque tour, on recalcule si le personnage se trouve parterre ou
         * non
         */
        jellyfish.setOnGround(false);

        for (Platform p : platforms) {
            p.update(dt);
            // Si le personnage se trouve sur une plateforme, ça sera défini ici
            jellyfish.testCollision(p);
        }
        jellyfish.update(dt);
    }

    public void draw(GraphicsContext context) {
        context.setFill(Color.DARKBLUE);
        context.fillRect(0, 0, WIDTH, HEIGHT);

        jellyfish.draw(context, fenetreY);
        for (Platform p : platforms) {
            p.draw(context, fenetreY);
        }

        if (debugMode) {
            context.setFill(Color.WHITE);
            context.setFont(Font.font("serif",13));
            context.fillText("Position = (" + Math.round(jellyfish.x) + ", " + Math.round(jellyfish.y) + ")", 10, 10);
            context.fillText("v = (" + Math.round(jellyfish.vx) + ", " + Math.round(jellyfish.vy) + ")", 10, 23);
            context.fillText("a = (" + Math.round(jellyfish.ax) + ", " + Math.round(jellyfish.ay) + ")", 10, 36);
            context.fillText("Touche le sol:"+jellyfish.onGround, 10, 49);
            context.fillText("Position fenetre: "+fenetreY, 10, 62);
        }

    }
}
