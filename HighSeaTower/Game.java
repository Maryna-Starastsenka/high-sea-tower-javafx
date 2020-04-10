import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.Random;

public class Game {

    public static int WIDTH, HEIGHT;

    protected ArrayList<Platform> platforms = new ArrayList<>();
    private Jellyfish jellyfish;
    private boolean debugMode = false;
    private Platform lastPlatform = null;

    //Fenêtre:
    private double fenetreAY;
    private double fenetreVY = 20;
    private double fenetreY = 0;
    private int platformHeight = 100; //hauteur

    public Game(int width, int height) {
        WIDTH = width;
        HEIGHT = height;

        for (int i = 0; i < 5; i++) {
            generatePlatform();
        }
        jellyfish = new Jellyfish(WIDTH/2 - 50/2, 0);
    }

    public void generatePlatform() {
//        int randomLength = new Random().nextInt(96)+80; //entre 80 et 175 px
//        int randomX = new Random().nextInt(WIDTH-randomLength+1);
//        Platform platform = new Platform((double)randomX, (double)platformHeight, (double)randomLength);

        double probabilite = Math.random();
        Platform platform;
        Platform lastPlatform = platforms.get(platforms.size() - 1);
        if (probabilite < 0.1) {
            platform = new PlateformeSimple(this);
        } else if (probabilite < 0.2) {
            platform = new PlateformeRebondissante(this);
        } else if (probabilite < 0.3) {
            platform = new PlateformeAccelerante(this);
        } else if (lastPlatform != null && lastPlatform instanceof PlateformeSolide) {
            //relance la generation de plateforme si la derniere etait rouge
            generatePlatform();
            return;
        } else {
            platform = new PlateformeSolide(this);
        }
        platforms.add(platform);
        platformHeight += 100;
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
            fenetreVY = 20;
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

        //Enlève plateformes disparues et en rajoute une
        for (int i=0; i<platforms.size();i++) {
            if (platforms.get(i).y+platforms.get(i).hauteur < fenetreY) {
                platforms.remove(i);
                generatePlatform();
            }
        }

        //Dessine les plateformes
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
