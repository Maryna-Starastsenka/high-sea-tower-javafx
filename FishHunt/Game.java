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

    private static boolean debugMode = false;
    private static int width, height;

    private Target target = new Target(0,0);
    private ArrayList<Fish> fishes = new ArrayList<>();

    /**
     * Liste des bulles en mémoire
     */
    private ArrayList<Bubble> bubbles = new ArrayList<>();

    private Jellyfish jellyfish;

    private double normalFishTimer = 0;
    private double specialFishTimer = 0;
    private double bubbleTimer = 0;

    private boolean gameStarted = false;


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
//        this.jellyfish = new Jellyfish(width / 2, 0);
    }

    public void generateNormalFishes() {
        double probability = Math.random();
        if (probability < 0.5) {
            fishes.add(new NormalFish(0));
        } else {
            fishes.add(new NormalFish(width));
        }
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

    public void move(double x, double y) {
        target.move(x, y);
    }

    /**
     * Demande à la méduse d'aller à gauche
     */
    public void moveLeft() {
//        jellyfish.moveLeft();
    }

    /**
     * Demande au modèle d'aller à droite
     */
    public void moveRight() {
//        jellyfish.moveRight();
    }

    /**
     * Demande à la méduse d'arrêter de bouger horizontalement
     */
    public void stopMoving() {
//        jellyfish.stopMoving();
    }

    /**
     * Active/désactive le mode debug comme un interrupteur
     * Si le mode debug est activé, la fenêtre ne bouge plus automatiquement
     */
    public void switchDebug() {
        debugMode = !debugMode;
        if (debugMode) {
        } else {
        }
    }

    /**
     * Vérifie la position de la méduse par rapport au bas de l'écran afin de déterminer si
     * la partie est perdue ou non
     *
     * @return vrai si le haut de la méduse est plus bas que le bas de l'écran, faux sinon
     */
    public boolean gameIsOver() {
        return false;
//        return jellyfish.y + jellyfish.height < height;
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

        if (!debugMode) {

        }

        // Génère de nouveaux groupes de bulles toutes les 3 secondes
        bubbleTimer += dt;
        if (bubbleTimer >= 3) {
            generateBubbles();
            bubbleTimer = 0;
        }

        normalFishTimer += dt;
        if (normalFishTimer >= 3) {
            generateNormalFishes();
            normalFishTimer = 0;
        }

        // Supprime les bulles de la mémoire si elles dépassent le haut de l'écran
        bubbles.removeIf(bubble -> bubble.y - bubble.getRadius() > height);

        // Demande aux bulles de mettre à jour leur modèle
        for (Bubble bubble : bubbles) {
            bubble.update(dt);
        }

        for (Fish f : fishes) {
            f.update(dt);
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



        for (Fish f : fishes) {
            f.draw(context);
        }


        // Itère sur la liste de bulles pour leur demander de se dessiner
        for (Bubble bubble : bubbles) {
            bubble.draw(context);
        }

        target.draw(context);
        // Dessine un carré rouge derrière la meduse et affiche des informations contextuelles
        // lorsque le mode debug est activé
        if (debugMode) {
            context.setFill(Color.rgb(255, 0, 0, 0.4));

            context.setFill(Color.WHITE);
            context.setTextAlign(TextAlignment.LEFT);
            context.setFont(Font.font(13));
//            context.fillText("Position = (" + Math.round(jellyfish.x) + ", "
//                    + Math.round(jellyfish.y) + ")", 0.03 * width, 0.03 * height);
        }

        // Affiche le score actuel
        context.setFill(Color.WHITE);
        context.setTextAlign(TextAlignment.CENTER);
        context.setFont(Font.font(20));
        context.fillText((int) height + "m", width / 2, 0.08 * height);
    }

    public boolean getDebugMode() {
        return debugMode;
    }
}