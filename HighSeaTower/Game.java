import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import java.util.ArrayList;

/**
 * Classe du modèle qui contient la logique de jeu
 * et gère les entités dans l'espace en fonction du temps écoulé
 */
public class Game {

    private static final int NB_PLATFORMS = 5;
    private static boolean debugMode = false;
    private static int width, height;

    /**
     * Liste des plateformes en mémoire
     */
    private ArrayList<Platform> platforms = new ArrayList<>();

    /**
     * Liste des bulles en mémoire
     */
    private ArrayList<Bubble> bubbles = new ArrayList<>();

    private Jellyfish jellyfish;

    private double bubbleTimer = 0;
    private boolean gameStarted = false;

    /**
     * Différence verticale entre la méduse et la hauteur de l'écran
     */
    private double differenceY;

    /**
     * Paramètres verticaux de la fenêtre : postion depuis le fond de l'océan, vitesse, accélération
     */
    private double windowY = 0;
    private double windowVY = 50;
    private double windowAY = 2;

    /**
     * Définit si le jeu est commencé ou non
     *
     * @param started indicateur du début de jeu
     */
    public void setGameStarted(boolean started) {
        this.gameStarted = started;
    }

    /**
     * Constructeur de jeu qui instancie la méduse au fond de l'océan et génère les plateformes
     *
     * @param width largeur de la fenêtre
     * @param height hauteur de la fenêtre
     */
    public Game(int width, int height) {
        this.width = width;
        this.height = height;
        this.jellyfish = new Jellyfish(width / 2, 0);
        Platform.setPlatformSpacing(100);
        for (int i = 0; i < NB_PLATFORMS; i++) {
            generatePlatform();
        }
    }

    /**
     * Choisit aléatoirement un type de plateforme selon une probabilité et l'instancie
     *
     * Probabilités à condition de ne pas avoir 2 plateformes solides de suite :
     * -Plateforme simple : 65%
     * -Plateforme rebondissante : 20%
     * -Plateforme accélérante : 10%
     * -Plateforme solide : 5%
     *
     * Si 2 plateformes solides de suite, appel récursif de la méthode
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
        } else if (platforms.size() != 0 && platforms.get(platforms.size() - 1) instanceof PlateformeSolide) {
            // Relance la génération de plateforme si la dernière plateforme était Solide
            generatePlatform();
           return;
       } else {
           platform = new PlateformeSolide(this);
       }
        platforms.add(platform);
    }

    /**
     * Instancie 3 groupes de 5 bulles 
     */
    public void generateBubbles() {
        for (int i = 0; i < 3; i++) {
            double baseX = Math.random() * width;
            for (int j = 0; j < 5; j++) {
                // La zone en x de chaque bulle est dans la plage [-20;+20] px autour de la base du groupe
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
     * Demande à la méduse d'arrêter de bouger horizontalement
     */
    public void stopMoving() {
        jellyfish.stopMoving();
    }

    /**
     * Active/désactive le mode debug comme un interrupteur
     * Si le mode debug est activé, la fenêtre ne bouge plus automatiquement
     */
    public void switchDebug() {
        debugMode = !debugMode;
        if (debugMode) {
            windowVY = 0;
        } else {
            windowVY = 50;
        }
    }

    /**
     * Vérifie la position de la méduse par rapport au bas de l'écran afin de déterminer si
     * la partie est perdue ou non
     *
     * @return vrai si le haut de la méduse est plus bas que le bas de l'écran, faux sinon
     */
    public boolean gameIsOver() {
        return jellyfish.y + jellyfish.height < windowY;
    }

    /**
     * Vérifie si la méduse dépasse 75% de la hauteur de l'écran
     *
     * @return vrai si le haut de la méduse dépasse 75% de la hauteur de l'écran, faux sinon
     */
    public boolean goAbove() {
        differenceY = jellyfish.y + jellyfish.height - (height * 0.75 + windowY);
        return differenceY > 0;
    }

    /**
     * Met à jour la position de la fenêtre et des entités
     * Retire de la mémoire les plateformes et les bulles qui sont sorties de l'écran
     *
     * @param dt temps écoulé depuis le dernier update (en secondes)
     */
    public void update(double dt) {
        // Pas d'update si le jeu n'est pas commencé
        if (!gameStarted) {
            return;
        }

        // Monte l'écran si la méduse dépasse 75%
        if (goAbove()) {
            windowY += differenceY;
        }

        // Fait monter la fenêtre automatiquement si le mode debug est désactivé
        if (!debugMode) {
            windowVY += windowAY * dt;
            windowY += windowVY * dt;
        }

        // Recalcule si la méduse se trouve par terre ou non
        jellyfish.setOnGround(false);

        // Retire les plateformes de la mémoire lorsqu'elles ne sont plus affichables
        platforms.removeIf(p -> p.y + p.height < windowY);
        for (int i = 0; i < NB_PLATFORMS - platforms.size(); i++) {
                generatePlatform();
        }

        // Demande à la plateforme de mettre à jour son modèle
        // et demande à la meduse de faire un test de collision avec chaque plateforme
        for (Platform p : platforms) {
            p.update(dt);
            jellyfish.testCollision(p);
        }

        // Demande à la méduse de mettre à jour son modèle
        jellyfish.update(dt);

        // Génère de nouveaux groupes de bulles toutes les 3 secondes
        bubbleTimer += dt;
        if (bubbleTimer >= 3) {
            generateBubbles();
            bubbleTimer = 0;
        }

        // Supprime les bulles de la mémoire si elles dépassent le haut de l'écran
        bubbles.removeIf(bubble -> bubble.y - bubble.getRadius() > windowY + height);

        // Demande aux bulles de mettre à jour leur modèle
        for (Bubble bubble : bubbles) {
            bubble.update(dt);
        }
    }

    /**
     * Dessine les éléments graphiques du jeu
     *
     * @param context contexte sur lequel dessiner
     */
    public void draw(GraphicsContext context) {
        // Arrière-plan
        context.setFill(Color.DARKBLUE);
        context.fillRect(0, 0, width, height);

        // Itère sur la liste de bulles pour leur demander de se dessiner
        for (Bubble bubble : bubbles) {
            bubble.draw(context, windowY, this.height);
        }

        // Itère sur la liste de plateformes pour leur demander de se dessiner
        for (Platform p : platforms) {
            p.draw(context, windowY, this.height);
        }

        // Demande à la méduse de se dessiner
        jellyfish.draw(context, windowY, this.height);

        // Dessine un carré rouge derrière la meduse et affiche des informations contextuelles
        // lorsque le mode debug est activé
        if (debugMode) {
            context.setFill(Color.rgb(255, 0, 0, 0.4));
            context.fillRect(jellyfish.x, height - (jellyfish.y- windowY) - jellyfish.height,
                    jellyfish.width, jellyfish.height);

            context.setFill(Color.WHITE);
            context.setTextAlign(TextAlignment.LEFT);
            context.setFont(Font.font(13));
            context.fillText("Position = (" + Math.round(jellyfish.x) + ", "
                    + Math.round(jellyfish.y) + ")", 0.03 * width, 0.03 * height);
            context.fillText("v = (" + Math.round(jellyfish.vx) + ", "
                    + Math.round(jellyfish.vy) + ")", 0.03 * width, 0.06 * height);
            context.fillText("a = (" + Math.round(jellyfish.ax) + ", "
                    + Math.round(jellyfish.ay) + ")", 0.03 * width, 0.09 * height);
            context.fillText("Touche le sol : "
                    + (Jellyfish.getOnGround() ? "oui" : "non") , 0.03 * width, 0.12 * height);
        }

        // Affiche le score actuel
        context.setFill(Color.WHITE);
        context.setTextAlign(TextAlignment.CENTER);
        context.setFont(Font.font(20));
        context.fillText((int) windowY + "m", width / 2, 0.08 * height);
    }

    public boolean getDebugMode() {
        return debugMode;
    }

    public Jellyfish getJellyfish() {
        return this.jellyfish;
    }

    public double getWindowVY() {
        return windowVY;
    }

    public void setWindowVY(double newSpeed) {
        windowVY = newSpeed;
    }

    public int getWidth() {
        return this.width;
    }
}