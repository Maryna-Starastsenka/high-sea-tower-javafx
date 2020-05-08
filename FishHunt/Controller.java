import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Classe Contrôleur qui gère le flux de données du modèle et
 * met à jour la vue lorsque les données changent
 */
public class Controller {

    private Game game;

    Stage primaryStage;
    private HomePage homePage;
    private GamePage gamePage;
    private ScorePage scorePage;
    private Score scoreModel;

    /**
     * Constructeur du Contrôleur
     */
    public Controller() {
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    void homePage() {
        homePage = new HomePage(this);
        updateView(homePage);

    }

    void gamePage() {
        game = new Game(FishHunt.WIDTH, FishHunt.HEIGHT);

        // ce if est important car sinon le timer d'update sera créé plusieurs fois
        if (gamePage == null) {
            gamePage = new GamePage(this);
        }
        updateView(gamePage);


    }

    void initScorePage() {
        scorePage = new ScorePage(this);
        scorePage.setScoreInputVisible(false);
    }

    void initScoreModel() {
        scoreModel = new Score(this);
    }

    //Affiche les scores
    void scorePage() {
        ArrayList<Pair<String,Integer>>  bestScores = scoreModel.readScoreFile();
        scorePage.clearScores();
        scorePage.setBestScores(bestScores);
        updateView(scorePage);
    }

    void addNewScore(String name, int score) {
        scoreModel.addNewScore(name, score);
    }

    void updateView(Page page) {
        primaryStage.setScene(page.getScene());
        primaryStage.show();

    }

    //deplacer la cible
    void move(double x, double y) {
        game.move(x, y);
    }

    void shoot(double x, double y) {
        game.shoot(x, y);
    }
    /**
     * Dessine tous les éléments graphiques du jeu
     *
     * @param context contexte sur lequel dessiner
     */
    void draw(GraphicsContext context) {
        if (game == null) return;
        game.draw(context);
    }

    /**
     * Fait la mise à jour de la fenêtre
     * @param deltaTime temps écoulé depuis le dernier appel en seconde
     */
    void update(double deltaTime) {
        if (game == null) return;
        if (game.gameOverTimer >= 3) {
            int score = game.getScore();
            // vérifie si le score de la partie est dans le top 10
            if(scoreModel.compareNewScore(score)) {
                scorePage.setNewScore(score);
                scorePage.setScoreInputVisible(true);
            } else {
                scorePage.setScoreInputVisible(false);
            };
            scorePage();
            game = null;
            scorePage();
        } else {
            game.update(deltaTime);
        }
    }

    public void nextLevel() {
        game.nextLevel();
    }

    public void increaseScore() {
        game.increaseScore();
    }

    public void increaseLife() {
        game.increaseLife();
    }

    public void gameOver() {
        game.gameOver();
    }
}