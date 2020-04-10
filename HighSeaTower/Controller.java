
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
        game.update(deltaTime);
    }

    void jump() {
        game.jump();
    }

    void moveLeft() { game.moveLeft(); }

    void moveRight() { game.moveRight(); }

    void resetAccelerator() { game.resetAccelerator(); }

    void setDebug() {
        game.setDebug();
    }
}
