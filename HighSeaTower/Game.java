
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;

public class Game {

    public static int WIDTH, HEIGHT;

    protected ArrayList<Platform> platforms = new ArrayList<Platform>();
    private ArrayList<Bubble> bubbles = new ArrayList<>();
    private double baseX;
    private double counter = 0;
    protected Jellyfish jellyfish;
    protected static boolean debugMode = false;
    private Platform lastPlatform = null;
    private boolean gameStarted = false;
    private double differenceY;

    //Fenêtre:
    protected double fenetreAY = 2;
    protected double fenetreVY = 50;
    protected double fenetreY = 0;

    public void setGameStarted(boolean started) {
        this.gameStarted = started;
    }

    public Game(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        this.jellyfish = new Jellyfish(WIDTH / 2 - 50 / 2, 0);
        for (int i = 0; i < 5; i++) {
            generatePlatform();
        }


    }

    public void generatePlatform() {

        double probabilite = Math.random();

        Platform platform;

        if (probabilite < 0.05) {
           platform = new PlateformeSimple(this);
        } else if (probabilite < 0.85) {
            platform = new PlateformeRebondissante(this);
        } else if (probabilite < 0.95) {
           platform = new PlateformeAccelerante(this);
        } else if (lastPlatform != null && lastPlatform instanceof PlateformeSolide) {
            //relance la generation de plateforme si la derniere etait rouge
            generatePlatform();
           return;
       } else {
           platform = new PlateformeSolide(this);
       }
        platforms.add(platform);
        lastPlatform = platform;
    }

    public void generateBubbles() {
        for (int i = 0; i < 3; i++) {
            baseX = Math.random() * WIDTH;
            for (int j = 0; j < 5; j++) {
                bubbles.add(new Bubble((baseX - 20) + Math.random() * 41, 0));
            }
        }
    }

    public void jump() {
        jellyfish.jump();
    }

    public void moveLeft() {
        jellyfish.moveLeft();
    }

    public void moveRight() {
        jellyfish.moveRight();
    }

    public void resetAccelerator() {
        jellyfish.resetAccelerator();
    }

    public void setDebug() {
        debugMode = !debugMode;
        if (debugMode) {
            fenetreVY = 0;
        } else {
            fenetreVY = 50;
        }
    }

    //vérifie si la méduse tombe plus bas que l'écran
    public boolean gameIsOver() {
        return jellyfish.y + jellyfish.hauteur < fenetreY;
    }

    //vérifie si la meduse dépasse 75% de la hauteur de l'écran
    public boolean goAbove() {
        differenceY = jellyfish.y + jellyfish.hauteur - (HEIGHT * 0.75 + fenetreY);
        return differenceY > 0;
    }

    public void update(double dt) {
        //vérifie si le jeu a commencé
        if (!gameStarted) {
            return;
        }

        //monte l'écrant si la méduse dépasse 75%
        if (goAbove()) {
            fenetreY += differenceY;
        }

        if (!debugMode) {
            //Updater la fenêtre + accélération
            fenetreVY += fenetreAY * dt;
            fenetreY += fenetreVY * dt;
        }
        
        /**
         * À chaque tour, on recalcule si le personnage se trouve parterre ou
         * non
         */
        jellyfish.setOnGround(false);

        for (Platform p : platforms) {
            p.update(dt);
            jellyfish.testCollision(p);

        }
        jellyfish.update(dt);

        //création des bulles toutes les 3 secondes
        counter += dt;
        if (counter >= 3) {
            generateBubbles();
            counter = 0;
        }

        //mise à jour des bulles
        for (Bubble bubble : bubbles) {
            bubble.update(dt);
        }
    }

    public void draw(GraphicsContext context) {
        context.setFill(Color.DARKBLUE);
        context.fillRect(0, 0, WIDTH, HEIGHT);

        //TO DO : supprimer les bulles d'ArrayList
        for (Bubble bubble : bubbles) {
            bubble.draw(context, fenetreY);
        }

        //dessine un carré rouge derrière la meduse si le mode Debug est actif
        if (debugMode) {
            context.setFill(Color.rgb(255, 0, 0, 0.4));
            context.fillRect(jellyfish.x, HEIGHT - (jellyfish.y-fenetreY) - jellyfish.hauteur,
                    jellyfish.largeur, jellyfish.hauteur);
        }

        jellyfish.draw(context, fenetreY);


        //platforms.removeIf(p -> p.y + p.hauteur < fenetreY);

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

        context.setFill(Color.WHITE);

        if (debugMode) {
            context.setTextAlign(TextAlignment.LEFT);
            context.setFont(Font.font(13));
            context.fillText("Position = (" + Math.round(jellyfish.x) + ", " + Math.round(jellyfish.y) + ")", 10, 10);
            context.fillText("v = (" + Math.round(jellyfish.vx) + ", " + Math.round(jellyfish.vy) + ")", 10, 23);
            context.fillText("a = (" + Math.round(jellyfish.ax) + ", " + Math.round(jellyfish.ay) + ")", 10, 36);
            context.fillText("Touche le sol : " + (Jellyfish.getOnGround() ? "Oui" : "Non") , 10, 49);
            //context.fillText("Position fenetre: "+fenetreY, 10, 62);
        }

        //affichage de score actuel
        context.setTextAlign(TextAlignment.CENTER);
        context.setFont(Font.font(20));
        context.fillText((int)fenetreY + "m", WIDTH / 2, 0.08 * HEIGHT);

    }
}

