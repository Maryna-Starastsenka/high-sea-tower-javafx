import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.util.Random;

/**
 * Classe Poisson Normal hérite de Poisson
 * Les poissons normaux apparaissent tous les 3 secondes à partir du niveau 1
 */
public class NormalFish extends Fish {

    private double minVerticalSpeed = 100;
    private double maxVerticalSpeed = 200;

    private Image currentNormalFish;

    /**
     * Choisit aléatoirement la couleur du poisson
     */
    Random rand = new Random();
    private int randomInt = rand.nextInt(8);
    private int redValue = rand.nextInt(255);
    private int greenValue = rand.nextInt(255);
    private int blueValue = rand.nextInt(255);
    private Color color = Color.rgb(redValue, greenValue, blueValue);


    /**
     * Constructeur du poisson normal
     *
     * @param x position horizontale initiale
     */
    public NormalFish(double x) {
        super(x);
        this.ay = -100;
        // Vitesse vertical aléatoire dans la plage définie
        this.vy = minVerticalSpeed + Math.random()*(maxVerticalSpeed - minVerticalSpeed + 1);

        // Choisit aléatoirement une image sur 8
        currentNormalFish = new Image("/images/fish/0"+randomInt+".png");

        // Inverse horizontalement l'image si le poisson bouge vers la gauche
        if (x != 0) {
            currentNormalFish = ImageHelpers.flop(currentNormalFish);
        }
        // Colorie le poisson avec le couleur aléatoire
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
