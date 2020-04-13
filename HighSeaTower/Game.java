import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import java.util.ArrayList;

/**
 * Classe Jeu (modèle) qui contient la logique de jeu
 * Met à jour les entités et le temps écoulé dt
 */
public class Game {

    public static int WIDTH, HEIGHT;
    /**
     * Liste des plateforme en mémoire
     */
    protected ArrayList<Platform> platforms = new ArrayList<>();
    /**
     * Liste des bulles en mémoire
     */
    private ArrayList<Bubble> bubbles = new ArrayList<>();
    private double baseX;
    private double counter = 0;
    protected Jellyfish jellyfish;
    protected static boolean debugMode = false;
    private Platform lastPlatform = null;
    private boolean gameStarted = false;
    /**
     * Différence en Y entre la méduse et la hauteur de l'écrane
     */
    private double differenceY;
    /**
     * Paramètres de la fenêtre : accélération en Y, vitesse en Y, coord Y en bas
     */
    protected double fenetreAY = 2;
    protected double fenetreVY = 50;
    protected double fenetreY = 0;

    /**
     * @param started indicateur du début de jeu
     */
    public void setGameStarted(boolean started) {
        this.gameStarted = started;
    }

    /**
     * Constructeur de jeu
     * @param width langeur de la fenêtre
     * @param height hauteur de la fenêtre
     */
    public Game(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        this.jellyfish = new Jellyfish(WIDTH / 2 - 50 / 2, 0);
        for (int i = 0; i < 5; i++) {
            generatePlatform();
        }
    }

    /**
     * Choisie aléatoirement un type de plateforme selon la probsbilité et l'instancie
     * */
    public void generatePlatform() {

        double probabilite = Math.random();

        Platform platform;

        if (probabilite < 0.65) {
           platform = new PlateformeSimple(this);
        } else if (probabilite < 0.85) {
            platform = new PlateformeRebondissante(this);
        } else if (probabilite < 0.95) {
           platform = new PlateformeAccelerante(this);
        } else if (lastPlatform != null && lastPlatform instanceof PlateformeSolide) {
            // Relance la génération de plateforme si la dernière plateforme était Solide
            generatePlatform();
           return;
       } else {
           platform = new PlateformeSolide(this);
       }
        platforms.add(platform);
        lastPlatform = platform;
    }

    /**
     * Instancie 3 groupes de 5 bulles 
     */
    public void generateBubbles() {
        for (int i = 0; i < 3; i++) {
            baseX = Math.random() * WIDTH;
            for (int j = 0; j < 5; j++) {
                bubbles.add(new Bubble((baseX - 20) + Math.random() * 41, 0));
            }
        }
    }

    /**
     * Demande à la méduse de sauter
     */
    public void jump() {
        jellyfish.jump();
    }

    /**
     * Demande à la méduse d'aller à gauche
     */
    public void moveLeft() {
        jellyfish.moveLeft();
    }

    /**
     * Demande au modèle d'aller à droite
     */
    public void moveRight() {
        jellyfish.moveRight();
    }

    /**
     * Demende à la méduse d'arrêter de bouger
     */
    public void stopMoving() {
        jellyfish.stopMoving();
    }

    /**
     * Met la vitesse de la fenêtre en 0, si le mode debug est activé
     * Remet la vitesse à 50, si le mode debug est désactivé
     */
    public void setDebug() {
        debugMode = !debugMode;
        if (debugMode) {
            fenetreVY = 0;
        } else {
            fenetreVY = 50;
        }
    }

    /**
     * Vérifie la position de la méduse par rapport au bas de l'écran
     * @return vrai si le haut de la méduse est plus bas que le bas de l'écran, faux sinon
     */
    public boolean gameIsOver() {
        return jellyfish.y + jellyfish.hauteur < fenetreY;
    }

    /**
     * Vérifie la position de la méduse par rapport à la hauteur de l'écran
     * @return vrai si le haut de la méduse dépasse 75% de la hauteur de l'écran, faux sinon
     */
    public boolean goAbove() {
        differenceY = jellyfish.y + jellyfish.hauteur - (HEIGHT * 0.75 + fenetreY);
        return differenceY > 0;
    }

    /**
     * Met à jour la position de la fenêtre et des entités
     * Retire de la mémoire les plateformes et les bulles qui sont sortis de l'écran
     * @param dt temps écoulé depuis le dernier update en secondes
     */
    public void update(double dt) {
        // Vérifie si le jeu a commencé
        if (!gameStarted) {
            return;
        }

        // Monte l'écrant si la méduse dépasse 75%
        if (goAbove()) {
            fenetreY += differenceY;
        }

        // Met à jour la position y et la vitesse Y de la fenêtre si le mode debug est désactivé
        if (!debugMode)
            fenetreVY += fenetreAY * dt;
            fenetreY += fenetreVY * dt;

        // Recalcule si la méduse se trouve par terre ou non
        jellyfish.setOnGround(false);

        // Enlève les plateformes de la liste si la position y de haut de une pateforme
        // est plus basse que la position y de la fenêtre
        for (int i=0; i<platforms.size();i++) {
            if (platforms.get(i).y + platforms.get(i).hauteur < fenetreY) {
                platforms.remove(i);
                generatePlatform();
            }
        }

        // Parcourt la liste des plateformes en mémoire, demande de la misa à jour de leurs coord (x,y)
        // Demande à la meduse de faire le test collision avec chaque paleteforme
        for (Platform p : platforms) {
            p.update(dt);
            jellyfish.testCollision(p);
        }

        // Demande à la méduse de mettre à jours les coord (x,y) et l'image affichée
        jellyfish.update(dt);

        // Fait le démande au constructeur de bulle toutes les 3 secondes
        counter += dt;
        if (counter >= 3) {
            generateBubbles();
            counter = 0;
        }

        // Supprime des bulles de la liste si sa position y de bas est plus grande que le haut de l'écran
        bubbles.removeIf(bubble -> bubble.y - bubble.radius > fenetreY + HEIGHT);

        // Demande la mise à jour des coord (x,y) et de VY de bulles en mémoire
        for (Bubble bubble : bubbles) {
            bubble.update(dt);
        }
    }

    /**
     * Dessine les éléments graphiques du jeu
     * @param context contexte sur lequel dessiner
     */
    public void draw(GraphicsContext context) {
        context.setFill(Color.DARKBLUE);
        context.fillRect(0, 0, WIDTH, HEIGHT);

        // Demande à Bulle de dessiner des bulles de la liste
        for (Bubble bubble : bubbles) {
            bubble.draw(context, fenetreY);
        }

        // Demande à Plateform de dessiner des plateformes de la liste
        for (Platform p : platforms) {
            p.draw(context, fenetreY);
        }

        jellyfish.draw(context, fenetreY);

        // Dessine un carré rouge derrière la meduse et affiche des informations
        // si le mode debug est activé
        if (debugMode) {
            context.setFill(Color.rgb(255, 0, 0, 0.4));
            context.fillRect(jellyfish.x, HEIGHT - (jellyfish.y-fenetreY) - jellyfish.hauteur,
                    jellyfish.largeur, jellyfish.hauteur);

            context.setFill(Color.WHITE);
            context.setTextAlign(TextAlignment.LEFT);
            context.setFont(Font.font(13));
            context.fillText("Position = (" + Math.round(jellyfish.x) + ", " + Math.round(jellyfish.y) + ")", 10, 10);
            context.fillText("v = (" + Math.round(jellyfish.vx) + ", " + Math.round(jellyfish.vy) + ")", 10, 23);
            context.fillText("a = (" + Math.round(jellyfish.ax) + ", " + Math.round(jellyfish.ay) + ")", 10, 36);
            context.fillText("Touche le sol : " + (Jellyfish.getOnGround() ? "Oui" : "Non") , 10, 49);
            //context.fillText("Position fenetre: "+fenetreY, 10, 62);
        }

        // Affiche le score actuel
        context.setFill(Color.WHITE);
        context.setTextAlign(TextAlignment.CENTER);
        context.setFont(Font.font(20));
        context.fillText((int)fenetreY + "m", WIDTH / 2, 0.08 * HEIGHT);

    }
}

