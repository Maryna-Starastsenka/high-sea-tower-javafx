
import javafx.scene.canvas.GraphicsContext;


public class Controleur {

    Jeu jeu;
    
    public Controleur(int width, int height) {
        jeu = new Jeu(width, height);
    }

    void draw(GraphicsContext context) {
        jeu.draw(context);
    }

    void update(double deltaTime) {
        jeu.update(deltaTime);
    }

    void jump() {
        jeu.jump();
    }


    void setDebug() {
        //TO DO
    }
}
