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

    private boolean gameOver = false;
    private double levelTimer = 0;
    private boolean nextLevel = true;
    private static int width, height;
    private static int level;
    private int score = 0;
    private int lives = 3;
    private double lifeFishWidth = 30;
    private double lifeFishSpacing = 10;
    private double posXLives;
    private int numberOfKilledFishesInLevel = 0;

    private Target target;
    private ArrayList<Fish> fishes = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();

    /**
     * Liste des bulles en mémoire
     */
    private ArrayList<Bubble> bubbles = new ArrayList<>();

    private boolean generateFishes = true;
    private double specialFishTimer = 0;
    private double normalFishTimer = 0;
    private double bubbleTimer = 1.5;

    public static int getLevel() {
        return level;
    }

    /**
     * Constructeur de jeu qui instancie la méduse au fond de l'océan et génère les plateformes
     *
     * @param width largeur de la fenêtre
     * @param height hauteur de la fenêtre
     */
    public Game(int width, int height) {
        level = 1;
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
        if (gameOver || nextLevel) return;
        bullets.add(new Bullet(x, y));
    }

    public double gameOverTimer = 0;

    /**
     * Met à jour la position de la fenêtre et des entités
     * Retire de la mémoire les plateformes et les bulles qui sont sorties de l'écran
     *
     * @param dt temps écoulé depuis le dernier update (en secondes)
     */
    public void update(double dt) {
        if (lives <= 0) {
            gameOver = true;
        }

        if (gameOver) {
            gameOverTimer += dt;
        }

        // Affiche le niveau suivant pendant 3 sec
        levelTimer += dt;
        if (levelTimer >= 3) {
            nextLevel = false;

            if (numberOfKilledFishesInLevel >= 5 || (score >= level * 5)) {
                level++;
                levelTimer = 0;
                nextLevel = true;
                numberOfKilledFishesInLevel = 0;
            }
        }

        // Génère de nouveaux groupes de bulles toutes les 3 secondes
        bubbleTimer += dt;
        if (bubbleTimer >= 3) {
            generateBubbles();
            bubbleTimer = 0;
        }

        normalFishTimer += dt;
        if (normalFishTimer >= 3 && !nextLevel && !gameOver) {
            generateNormalFishes();
            normalFishTimer = 0;
        }

        specialFishTimer += dt;
        if (specialFishTimer >= 5 && level >= 2 && !nextLevel && !gameOver) {
           generateSpecialFish();
            specialFishTimer = 0;
        }

        // Vérifie les poissons qui sont sortis de l'écran
        for (Fish fish : fishes) {
            if ((fish.vx > 0 && fish.x > width) ||
                    (fish.vx < 0 && (fish.x + fish.width) < 0) ||
                    (fish.y < 0) || (fish.y > height)) {
                fish.setHasEscaped(true);

                if (!gameOver && !nextLevel) {
                    lives--;
                }
            }
        }

        // Supprime les poissons qui sont sortis de l'écran
        fishes.removeIf(fish -> fish.getHasEscaped());

        // Supprime les bulles de la mémoire si elles dépassent le haut de l'écran
        bubbles.removeIf(bubble -> bubble.y - bubble.getRadius() > height);

        //Façon safe d'enlever des éléments d'une liste qu'on itère
        for (Iterator<Bullet> iterator1 = bullets.iterator(); iterator1.hasNext();) {
            Bullet bullet = iterator1.next();
            if (bullet.getExploded()) {
                for (Iterator<Fish> iterator2 = fishes.iterator(); iterator2.hasNext(); ) {
                    Fish fish = iterator2.next();
                    if (bullet.testCollision(fish)) {
                        //Tue tous les poissons qui entrent en contact avec la balle
                        score++;
                        numberOfKilledFishesInLevel++;
                        iterator2.remove();
                    }
                }
                //Enlève la balle une fois qu'elle a tué les poissons ou pas
                iterator1.remove();
            }
        }

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

        if (nextLevel) {
            context.setFill(Color.WHITE);
            context.setTextAlign(TextAlignment.CENTER);
            context.setFont(Font.font(60));
            context.fillText("Level " + level, width / 2, height / 2);
        }

        if (gameOver) {
            context.setFill(Color.RED);
            context.setTextAlign(TextAlignment.CENTER);
            context.setFont(Font.font(60));
            context.fillText("GAME OVER", width / 2, height / 2);
        }
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
        numberOfKilledFishesInLevel = 5;
    }

    public void increaseScore() {
        score++;
        numberOfKilledFishesInLevel++;
    }

    public void increaseLife() {
        if (lives < 3) {
            lives++;
        }
    }

    public void gameOver() {
        gameOver = true;
    }

    public int getScore() {
        return score;
    }
}
