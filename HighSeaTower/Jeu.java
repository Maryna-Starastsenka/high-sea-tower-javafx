
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Jeu {

    public static final int WIDTH = 640, HEIGHT = 320;

    private Plateforme[] plateformes = new Plateforme[5];
    private Jumper jumper;

    public Jeu() {
        for (int i = 0; i < plateformes.length; i++) {
            plateformes[i] = new Plateforme((double) i / plateformes.length * WIDTH, Math.random() * HEIGHT);
        }

        jumper = new Jumper(10, 10);
    }

    public void jump() {
        jumper.jump();
    }

    public void update(double dt) {
        /**
         * À chaque tour, on recalcule si le personnage se trouve parterre ou
         * non
         */
        jumper.setParterre(false);

        for (Plateforme p : plateformes) {
            p.update(dt);
            // Si le personnage se trouve sur une plateforme, ça sera défini ici
            jumper.testCollision(p);
        }
        jumper.update(dt);
    }

    public void draw(GraphicsContext context) {
        context.setFill(Color.CORNFLOWERBLUE);
        context.fillRect(0, 0, WIDTH, HEIGHT);

        jumper.draw(context);
        for (Plateforme p : plateformes) {
            p.draw(context);
        }
    }
}
