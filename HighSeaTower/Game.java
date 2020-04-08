
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Game {

    public static int WIDTH, HEIGHT;

    private Platform[] platforms = new Platform[5];
    private Jellyfish jellyfish;

    public Game(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        for (int i = 0; i < platforms.length; i++) {
            platforms[i] = new Platform((double) i / platforms.length * WIDTH, Math.random() * HEIGHT);
        }

        jellyfish = new Jellyfish(WIDTH/2 - 50/2, 0);
    }

    public void jump() {
        jellyfish.jump();
    }

    public void moveLeft() { jellyfish.moveLeft(); }

    public void moveRight() {
        jellyfish.moveRight();
    }

    public void resetAccelerator() { jellyfish.resetAccelerator(); }

    public void update(double dt) {
        /**
         * À chaque tour, on recalcule si le personnage se trouve parterre ou
         * non
         */
        jellyfish.setOnGround(false);

        for (Platform p : platforms) {
            p.update(dt);
            // Si le personnage se trouve sur une plateforme, ça sera défini ici
            jellyfish.testCollision(p);
        }
        jellyfish.update(dt);
    }

    public void draw(GraphicsContext context) {
        context.setFill(Color.DARKBLUE);
        context.fillRect(0, 0, WIDTH, HEIGHT);

        jellyfish.draw(context);
        for (Platform p : platforms) {
            p.draw(context);
        }
    }
}
