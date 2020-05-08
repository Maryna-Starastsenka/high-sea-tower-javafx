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


    /**
     * Attributs de jeu
     */
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
    private boolean generateFishes = true;
    private double specialFishTimer = 0;
    private double normalFishTimer = 0;
    private double bubbleTimer = 1.5;
    private double gameOverTimer = 0;

    private Target target;

    /**
     * Liste des poissons en mémoire
     */
    private ArrayList<Fish> fishes = new ArrayList<>();

    /**
     * Liste des balles en mémoire
     */
    private ArrayList<Bullet> bullets = new ArrayList<>();

    /**
     * Liste des bulles en mémoire
     */
    private ArrayList<Bubble> bubbles = new ArrayList<>();

    /**
     * Constructeur de jeu qui instancie la cible au milieu de l'écran
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


    public double getGameOverTimer() {
        return gameOverTimer;
    }

    public static int getLevel() {
        return level;
    }

    public int getScore() {
        return score;
    }

    /**
     * Instancie les poissons normaux
     */
    public void generateNormalFishes() {
        fishes.add(new NormalFish(fishPosX()));
    }

    /**
     * Instancie les crabs et les étoiles avec la probabilité de 50%
     */
    public void generateSpecialFish() {
        double probability = Math.random();
        if (probability < 0.5) {
            fishes.add(new Crab(fishPosX()));
        } else {
            fishes.add(new Starfish(fishPosX()));
        }
    }

    /**
     * Choisit aléqtoirement l'abscisse du poisson entre 0 et largeur de l'écran
     *
     * @return position horizontale initilale du poisson
     */
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

    /**
     * Déplace la cible sur l'écran
     *
     * @param x abscisse
     * @param y ordonnée
     */
    public void move(double x, double y) {
        target.move(x, y);
    }

    /**
     * Instancie la balle lorqu'on la lance
     *
     * @param x abscisse
     * @param y ordonnée
     */
    public void shoot(double x, double y) {
        if (gameOver || nextLevel) return;
        bullets.add(new Bullet(x, y));
    }

    /**
     * Met à jour des entités du jeu
     * Retire de la mémoire les poissons, les balles et les bulles qui sont sorties de l'écran
     * ou qui ont resolu le test de collision
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

        // Augmente le niveau de jeu et lance le compteur de temps pour afficher le niveau pendant 3 secondes
        // si 5 poissons sont caprturés
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

        // Génère des poissons normaux toutes les 3 secondes
        normalFishTimer += dt;
        if (normalFishTimer >= 3 && !nextLevel && !gameOver) {
            generateNormalFishes();
            normalFishTimer = 0;
        }

        // Génère des poissons spéciaux toutes les 5 secondes
        specialFishTimer += dt;
        if (specialFishTimer >= 5 && level >= 2 && !nextLevel && !gameOver) {
           generateSpecialFish();
            specialFishTimer = 0;
        }

        // Vérifie les poissions qui sont sortis de l'écran
        for (Fish fish : fishes) {
            if ((fish.vx > 0 && fish.x > width) ||
                    (fish.vx < 0 && (fish.x + fish.width) < 0) ||
                    (fish.y < 0) || (fish.y > height)) {
                fish.setHasEscaped(true);

                // Diminue le nombre des vies si le poissont sort de l'écran
                if (!gameOver && !nextLevel) {
                    lives--;
                }
            }
        }

        // Supprime les poissins qui sont sortie de l'écran
        fishes.removeIf(fish -> fish.getHasEscaped());

        // Supprime les bulles de la mémoire si elles dépassent le haut de l'écran
        bubbles.removeIf(bubble -> bubble.y - bubble.getRadius() > height);

        // Parcourt la liste de balles et de poissons
        for (Iterator<Bullet> iterator1 = bullets.iterator(); iterator1.hasNext();) {
            Bullet bullet = iterator1.next();
            if (bullet.getExploded()) {
                for (Iterator<Fish> iterator2 = fishes.iterator(); iterator2.hasNext(); ) {
                    Fish fish = iterator2.next();
                    if (bullet.testCollision(fish)) {
                        score++;
                        numberOfKilledFishesInLevel++;
                        iterator2.remove();
                    }
                }
                // Supprime le poission et la balle de la mémoire lorsequ'il y a une collision
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

        // Demande aux poissons de mettre à jour leur modèle
        for (Fish fish : fishes) {
            fish.update(dt);
        }

        // Demande aux balles de mettre à jour leur modèle
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

        // Itère sur la liste de poissons pour leur demander de se dessiner
        for (Fish fish : fishes) {
            fish.draw(context);
        }

        // Itère sur la liste de balles pour leur demander de se dessiner
        for (Bullet bullet : bullets) {
            bullet.draw(context);
        }

        // Demande à la cible de se dessiner
        target.draw(context);

        // Affiche le niveau de jeu
        if (nextLevel) {
            context.setFill(Color.WHITE);
            context.setTextAlign(TextAlignment.CENTER);
            context.setFont(Font.font(60));
            context.fillText("Level " + level, width / 2, height / 2);
        }

        // Affiche "game over" si la partie est perdue
        if (gameOver) {
            context.setFill(Color.RED);
            context.setTextAlign(TextAlignment.CENTER);
            context.setFont(Font.font(60));
            context.fillText("GAME OVER", width / 2, height / 2);
        }

        // Affiche le score actuel et les poissons représentants le nombre de vies restantes
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

    /**
     * Augmente le score dans le mode debug
     */
    public void increaseScore() {
        score++;
        numberOfKilledFishesInLevel++;
    }

    /**
     * Augmente le nombre de vies dans mode debug (maximum de 3 poissons)
     */
    public void increaseLife() {
        if (lives < 3) {
            lives++;
        }
    }

    /**
     * Partie est perdue dans le mode debug
     */
    public void gameOver() {
        gameOver = true;
    }
}
