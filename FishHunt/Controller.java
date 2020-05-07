import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

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

    /**
     * Contructeur du Contrôleur
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

        gamePage = new GamePage(this);
        updateView(gamePage);
    }

    void scorePage() {
        scorePage = new ScorePage(this);
        updateView(scorePage);
    }

    void updateView(Page page) {
        primaryStage.setScene(page.getScene());
        primaryStage.show();

    }

    //deplacer la cible
    void move(double x, double y) {
        game.setGameStarted(true);
        game.move(x, y);
    }

    void shoot(double x, double y) {
        game.setGameStarted(true);
        game.shoot(x, y);
    }
    /**
     * Dessine tous les éléments graphiques du jeu
     *
     * @param context contexte sur lequel dessiner
     */
    void draw(GraphicsContext context) {
        game.draw(context);
    }

    /**
     * Fait la mise à jour de la fenêtre
     * @param deltaTime temps écoulé depuis le dernier appel en seconde
     */
    void update(double deltaTime) {
        // Commence une nouvelle partie si la méduse est tombée
        if (game.gameIsOver()) {
            game = new Game(FishHunt.WIDTH, FishHunt.HEIGHT);
        }
        game.update(deltaTime);
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