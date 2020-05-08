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

    /**
     * Constructeur de la page d'accueil
     */
    void homePage() {
        homePage = new HomePage(this);
        updateView(homePage);
    }

    /**
     * Constructeur de la page du jeu
     */
    void gamePage() {
        game = new Game(FishHunt.WIDTH, FishHunt.HEIGHT);

        // Fait le binding du timeur d'uptade avec le contrôleur
        if (gamePage == null) {
            gamePage = new GamePage(this);
        }
        updateView(gamePage);
    }

    /**
     * Constructeur de la page des meilleurs scores
     */
    void initScorePage() {
        scorePage = new ScorePage(this);
        scorePage.setScoreInputVisible(false);
    }

    /**
     * Constructeur du modèle de score
     */
    void initScoreModel() {
        scoreModel = new Score();
    }

    /**
     * Affiche les 10 meilleurs scores sur la page des scores
     */
    void scorePage() {
        ArrayList<Pair<String,Integer>> bestScores = scoreModel.readScoreFile();
        scorePage.clearScores();
        scorePage.setBestScores(bestScores);
        updateView(scorePage);
    }

    /**
     * Ajoute un nouveau score dans le modèle du score
     *
     * @param name nom de joueur
     * @param score score obtenu
     */
    void addNewScore(String name, int score) {
        scoreModel.addNewScore(name, score);
    }

    void setScoreInputVisible(boolean visible) {
        scorePage.setScoreInputVisible(visible);
    }

    /**
     * Fait la mise à jour de la vue d'une fenêtre
     *
     * @param page page affichée sur l'écran
     */
    void updateView(Page page) {
        primaryStage.setScene(page.getScene());
        primaryStage.show();
    }

    /**
     * Demande au modèle de déplacer la cible
     *
     * @param x abscisse
     * @param y ordonnée
     */
    void move(double x, double y) {
        game.move(x, y);
    }

    /**
     * Demande au modèle de lancer la balle
     *
     * @param x abscisse
     * @param y ordonnée
     */
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
     *
     * @param deltaTime temps écoulé depuis le dernier appel en secondes
     */
    void update(double deltaTime) {
        if (game == null) return;
        if (game.getGameOverTimer() >= 3) {
            int score = game.getScore();
            // Vérifie si le score de la partie est dans le top 10
            if(scoreModel.compareNewScore(score)) {
                scorePage.setNewScore(score);
                setScoreInputVisible(true);
            } else {
                setScoreInputVisible(false);
            };
            scorePage();
            game = null;
            scorePage();
        } else {
            game.update(deltaTime);
        }
    }

    /**
     * Demande au modèle de faire monter le niveau de +1
     */
    public void nextLevel() {
        game.nextLevel();
    }

    /**
     * Demande au modèle de faire monter le score de +1
     */
    public void increaseScore() {
        game.increaseScore();
    }

    /**
     * Demande au modèle de faire monter le nombre de vies restantes
     * dans le mode debug (maximum de 3 poissons)
     */
    public void increaseLife() {
        game.increaseLife();
    }

    /**
     * Demande au modèle de faire perdre la partie dans le mode debug
     */
    public void gameOver() {
        game.gameOver();
    }
}