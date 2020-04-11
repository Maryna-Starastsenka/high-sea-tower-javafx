
import javafx.scene.canvas.GraphicsContext;

public class Controller {

    Game game;
    
    public Controller(int width, int height) {
        game = new Game(width, height);
    }

    void draw(GraphicsContext context) {
        game.draw(context);
    }

    void update(double deltaTime) {
        //commence une nouvelle partie si la méduse est tombée
        if (game.gameIsOver()) {
            Platform.setPlatformHeight(100);
            game = new Game(Game.WIDTH, Game.HEIGHT);
        }
        game.update(deltaTime);
    }

    void jump() {
        game.setGameStarted(true);
        game.jump();
    }

    void moveLeft() {
        game.setGameStarted(true);
        game.moveLeft();
    }

    void moveRight() {
        game.setGameStarted(true);
        game.moveRight();
    }

    void resetAccelerator() { game.resetAccelerator(); }

    void setDebug() {
        game.setDebug();
    }
}
