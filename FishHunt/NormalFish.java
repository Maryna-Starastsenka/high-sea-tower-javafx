import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.util.Random;

/**
 * Classe Poisson Normal hérite de Poisson
 * Les poissons normaux apparaissent tous les 3 secondes à partir du niveau 1
 */
public class NormalFish extends Fish {

    private Image currentNormalFish;

    /**
     * Constructeur du poisson normal
     *
     * @param x position horizontale initiale
     */
    public NormalFish(double x) {
        super(x);

        Random rand = new Random();

        this.ay = -100;

        // Vitesse verticale aléatoire dans la plage définie
        double minVerticalSpeed = 100;
        double maxVerticalSpeed = 200;
        this.vy = minVerticalSpeed + Math.random() * (maxVerticalSpeed - minVerticalSpeed + 1);

        // Choisit aléatoirement une image sur 8
        int randomInt = rand.nextInt(8);
        currentNormalFish = new Image("/images/fish/0"+ randomInt +".png");

        // Inverse horizontalement l'image si le poisson bouge vers la gauche
        if (x != 0) {
            currentNormalFish = ImageHelpers.flop(currentNormalFish);
        }

        // Choisit aléatoirement la couleur du poisson
        int redValue = rand.nextInt(255);
        int greenValue = rand.nextInt(255);
        int blueValue = rand.nextInt(255);

        // Colorie le poisson avec le couleur aléatoire
        Color color = Color.rgb(redValue, greenValue, blueValue);
        currentNormalFish = ImageHelpers.colorize(currentNormalFish, color);
    }

    /**
     * Met à jour les attributs du poisson normal
     *
     * @param dt temps écoulé depuis le dernier update (en secondes)
     */
    @Override
    public void update (double dt) { super.update(dt); }

    /**
     * Dessine le poisson normal sur l'écran
     *
     * @param context contexte sur lequel dessiner
     */
    public void draw(GraphicsContext context) {
        context.drawImage(currentNormalFish, x, FishHunt.HEIGHT - y - height, width, height);
    }
}
