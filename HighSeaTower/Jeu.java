
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Jeu {

    public static int WIDTH, HEIGHT;

    private Plateforme[] plateformes = new Plateforme[5];
    private Meduse meduse;

    public Jeu(int width, int height) {
        WIDTH = width;
        HEIGHT = height;
        for (int i = 0; i < plateformes.length; i++) {
            plateformes[i] = new Plateforme((double) i / plateformes.length * WIDTH, Math.random() * HEIGHT);
        }

        meduse = new Meduse(10, 10);
    }

    public void jump() {
        meduse.jump();
    }

    public void update(double dt) {
        /**
         * À chaque tour, on recalcule si le personnage se trouve parterre ou
         * non
         */
        meduse.setParterre(false);

        for (Plateforme p : plateformes) {
            p.update(dt);
            // Si le personnage se trouve sur une plateforme, ça sera défini ici
            meduse.testCollision(p);
        }
        meduse.update(dt);
    }

    public void draw(GraphicsContext context) {
        context.setFill(Color.CORNFLOWERBLUE);
        context.fillRect(0, 0, WIDTH, HEIGHT);

        meduse.draw(context);
        for (Plateforme p : plateformes) {
            p.draw(context);
        }
    }
}
