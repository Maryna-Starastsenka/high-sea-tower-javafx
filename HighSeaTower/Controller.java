import javafx.scene.canvas.GraphicsContext;

/**
 * Classe Contrôleur contrôle le flux de données dans l'objet modèle et
 * met à jour la vue lorsque les données changent.
 */
public class Controller {

    Game game;

    /**
     * Contructeur de Contrôleur
     * @param width largeur de la fenêtre
     * @param height hauteur de la fenêtre
     */
    public Controller(int width, int height) {
        game = new Game(width, height);
    }

    /**
     * Dessine tous les éléments graphiques du jeu
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
        //commence une nouvelle partie si la méduse est tombée
        if (game.gameIsOver()) {
            Platform.setPlatformHeight(100);
            game = new Game(Game.WIDTH, Game.HEIGHT);
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
     * Demande au modèle d'aller à gauche
     */
    void moveLeft() {
        game.setGameStarted(true);
        game.moveLeft();
    }

    /**
     * Demande au modèle d'aller à droite
     */
    void moveRight() {
        game.setGameStarted(true);
        game.moveRight();
    }

    /**
     * Demande au modèle de remettre la vitesse et l'accélération de la méduse à 0
     */
    void stopMoving() { game.stopMoving(); }

    /**
     * Active le mode debug
     */
    void setDebug() {
        game.setDebug();
    }
}
