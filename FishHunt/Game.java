import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Classe du modèle qui contient la logique de jeu
 * et gère les entités dans l'espace en fonction du temps écoulé
 */
public class Game {

    private static int width, height;
    private int level = 1;
    private int score = 0;
    private int lives = 3;
    private double lifeFishWidth = 30;
    private double lifeFishSpacing = 10;
    private double posXLives;

//    private int missedFishes = 0;

    private Target target;
    private ArrayList<Fish> fishes = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();

    /**
     * Liste des bulles en mémoire
     */
    private ArrayList<Bubble> bubbles = new ArrayList<>();

    private double specialFishTimer = 0;
    private double normalFishTimer = 0;
    private double bubbleTimer = 1.5;

//    private boolean gameStarted = false;


//    /**
//     * Définit si le jeu est commencé ou non
//     *
//     * @param started indicateur du début de jeu
//     */
//    public void setGameStarted(boolean started) {
//        this.gameStarted = started;
//    }

    /**
     * Constructeur de jeu qui instancie la méduse au fond de l'océan et génère les plateformes
     *
     * @param width largeur de la fenêtre
     * @param height hauteur de la fenêtre
     */
    public Game(int width, int height) {
        this.width = width;
        this.height = height;
        this.target = new Target(this.width/2, this.height/2);
    }


    public void generateNormalFishes() {
        fishes.add(new NormalFish(fishPosX()));
    }

    public void generateSpecialFish() {
        double probability = Math.random();
        if (probability < 0.5) {
            fishes.add(new Crab(fishPosX()));
        } else {
            fishes.add(new Starfish(fishPosX()));
        }
    }

    public double fishPosX() {
        double probability = Math.random();
        if (probability < 0.5) {
            return 0;
        } else {
            return width;
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
    public void shoot(double x, double y) {
        bullets.add(new Bullet(x, y));
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

    public boolean gameIsOver() {

        return false;
    }

    /**
     * Met à jour la position de la fenêtre et des entités
     * Retire de la mémoire les plateformes et les bulles qui sont sorties de l'écran
     *
     * @param dt temps écoulé depuis le dernier update (en secondes)
     */
    public void update(double dt) {

//        // Pas d'update si le jeu n'est pas commencé
//        if (!gameStarted) {
//            return;
//        }

        if (lives <= 0) {
            gameOver();
        }

//        if (!debugMode) {
//
//        }

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

        specialFishTimer += dt;
        if (specialFishTimer >= 5) {
           generateSpecialFish();
            specialFishTimer = 0;
        }

        // Vérifie les poissions qui sont sortis de l'écran
        for (Fish fish : fishes) {
            if ((fish.vx > 0 && fish.x > width) ||
                    (fish.vx < 0 && (fish.x + fish.width) < 0) ||
                    (fish.y < 0) || (fish.y > height)) {
                fish.setHasEscaped(true);
                lives --;
            }
        }

        // Supprime les poissins qui sont sortie de l'écran
        fishes.removeIf(fish -> fish.getHasEscaped());

        // Supprime les bulles de la mémoire si elles dépassent le haut de l'écran
        bubbles.removeIf(bubble -> bubble.y - bubble.getRadius() > height);

        //Façon safe d'enlever des éléments d'une liste qu'on itère, apparemment
        for (Iterator<Bullet> iterator1 = bullets.iterator(); iterator1.hasNext();) {
            Bullet bullet = iterator1.next();
            if (bullet.getExploded()) {
                for (Iterator<Fish> iterator2 = fishes.iterator(); iterator2.hasNext(); ) {
                    Fish fish = iterator2.next();
                    if (bullet.testCollision(fish)) {
                        //Tue tous les poissons qui entrent en contact avec la balle
                        score++;
                        iterator2.remove();
                    }
                }
                //Enlève la balle une fois qu'elle a tué les poissons ou pas
                iterator1.remove();
            }
        }

        // *** N'efface pas les poissons qui sortent de l'écran ***
//        //Efface les poissons qui sortent de l'écran
//        for (Iterator<Fish> iterator = fishes.iterator(); iterator.hasNext(); ) {
//            Fish fish = iterator.next();
//            if (fish.y > FishHunt.HEIGHT || fish.y+fish.height < 0 || fish.x+fish.width < 0 || fish.x > FishHunt.WIDTH) {
//                lives--;
//                iterator.remove();
//            }
//        }


        // Demande aux bulles de mettre à jour leur modèle
        for (Bubble bubble : bubbles) {
            bubble.update(dt);
        }

        for (Fish f : fishes) {
            f.update(dt);
        }

        for (Bullet bullet : bullets) {
            bullet.update(dt);
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
            bubble.draw(context);
        }

        for (Fish fish : fishes) {
            fish.draw(context);
        }

        for (Bullet bullet : bullets) {
            bullet.draw(context);
        }

        target.draw(context);

        // Dessine un carré rouge derrière la meduse et affiche des informations contextuelles
        // lorsque le mode debug est activé
//        if (debugMode) {
//            context.setFill(Color.rgb(255, 0, 0, 0.4));
//
//            context.setFill(Color.WHITE);
//            context.setTextAlign(TextAlignment.LEFT);
//            context.setFont(Font.font(13));
////            context.fillText("Position = (" + Math.round(jellyfish.x) + ", "
////                    + Math.round(jellyfish.y) + ")", 0.03 * width, 0.03 * height);
//        }

        // Affiche le score actuel
        context.setFill(Color.WHITE);
        context.setTextAlign(TextAlignment.CENTER);
        context.setFont(Font.font(20));
        context.fillText(score +"", width / 2, 0.09 * height);
        Image imageLives = new Image("images/fish/00.png");
        posXLives = width / 2.0 - 1.5 * lifeFishWidth - lifeFishSpacing;
        for (int i = 0; i < lives ; i++) {
            context.drawImage(imageLives, posXLives, 0.14 * height, lifeFishWidth, lifeFishWidth);
            posXLives += lifeFishWidth + lifeFishSpacing ;
        }
    }

    public void nextLevel() {
        // todo timer à 0, level++
    }

    public void increaseScore() {
        score++;
    }

    public void increaseLife() {
        if (lives < 3) {
            lives++;
        }
    }

    public void gameOver() {
        // todo game over
    }
}
