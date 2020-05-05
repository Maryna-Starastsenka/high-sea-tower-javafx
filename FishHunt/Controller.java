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
        homePage = new HomePage(this, FishHunt.WIDTH, FishHunt.HEIGHT);
        updateView(homePage);
    }

    void gamePage() {
        game = new Game(FishHunt.WIDTH, FishHunt.HEIGHT);

        gamePage = new GamePage(this, FishHunt.WIDTH, FishHunt.HEIGHT);
        updateView(gamePage);
    }

    void scorePage() {
        scorePage = new ScorePage(this, FishHunt.WIDTH, FishHunt.HEIGHT);
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

    /**
     * Demande au modèle de sauter
     */
    void jump() {
        game.setGameStarted(true);
        game.jump();
    }

    /**
     * Demande au modèle d'aller à gauche et lui indique que le jeu peut commencer
     */
    void moveLeft() {
        game.setGameStarted(true);
        game.moveLeft();
    }

    /**
     * Demande au modèle d'aller à droite et lui indique que le jeu peut commencer
     */
    void moveRight() {
        game.setGameStarted(true);
        game.moveRight();
    }

    /**
     * Demande au modèle de remettre la vitesse et l'accélération horizontale de la méduse à 0
     *
     */
    void stopMoving() { game.stopMoving(); }

    /**
     * Demande au modèle de gérer l'activation/désactivation du mode debug
     */
    void switchDebug() {
        game.switchDebug();
    }


}