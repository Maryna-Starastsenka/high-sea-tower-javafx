import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.util.ArrayList;

/**
 * Classe Contrôleur qui gère le flux de données du modèle et
 * met à jour la vue lorsque les données changent
 */
public class Controller {
    /**
     * Modèles
     */
    private Game game;
    private Score scoreModel;

    /**
     * Vues
     */
    private Stage primaryStage;
    private HomePage homePage;
    private GamePage gamePage;
    private ScorePage scorePage;

    /**
     * Constructeur qui instancie les modèles de base
     * et affiche la page d'accueil
     *
     * @param primaryStage stage (fenêtre) principale du jeu
     */
    public Controller(Stage primaryStage) {
        setPrimaryStage(primaryStage);
        //Crée le modèle du score, la vue du score et du menu principal
        initScoreModel();
        initScorePage();
        homePage();
    }

    void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Instanciation et affichage de la page d'accueil
     */
    void homePage() {
        if (homePage == null) {
            homePage = new HomePage(this);
        }
        updateView(homePage);
    }

    /**
     * Instanciation et affichage de la page du jeu
     * Instanciation du modèle associé
     */
    void gamePage() {
        game = new Game();

        // Fait le binding du timeur d'update avec le contrôleur
        if (gamePage == null) {
            gamePage = new GamePage(this);
        }
        updateView(gamePage);
    }

    /**
     * Instanciation de la page des meilleurs scores
     * Pas d'affichage par défaut du champ d'input permettant
     * l'ajout d'un score
     */
    void initScorePage() {
        scorePage = new ScorePage(this);
        scorePage.setScoreInputVisible(false);
    }

    /**
     * Instanciation du modèle de score
     */
    void initScoreModel() {
        scoreModel = new Score();
    }

    /**
     * Affichage des 10 meilleurs scores sur la page des scores
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
     * @param page page affichée à l'écran
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
     * Fait la mise à jour de la fenêtre de jeu
     * Gère l'interaction entre la page de jeu et de score
     *
     * @param deltaTime temps écoulé depuis le dernier appel en secondes
     */
    void update(double deltaTime) {
        if (game == null) return;

        if (game.hasGameOverTimerEnded()) {
            int score = game.getScore();
            // Vérifie si le score de la partie est dans le top 10
            if(scoreModel.compareNewScore(score)) {
                scorePage.setNewScore(score);
                setScoreInputVisible(true);
            } else {
                setScoreInputVisible(false);
            }
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
    void nextLevel() {
        game.nextLevel();
    }

    /**
     * Demande au modèle de faire monter le score de +1
     */
    void increaseScore() {
        game.increaseScore();
    }

    /**
     * Demande au modèle de faire monter le nombre de vies restantes
     * dans le mode debug de +1 (maximum de 3 poissons)
     */
    void increaseLife() {
        game.increaseLife();
    }

    /**
     * Demande au modèle de faire perdre la partie dans le mode debug
     */
    void gameOver() {
        game.gameOver();
    }
}
