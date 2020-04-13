import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Classe Méduse du modèle
 */
public class Jellyfish extends Entity {

    public static int IMAGESIZE = 50;

    private final Image[] framesRight;
    private final Image[] framesLeft;
    private Image currentImage;

    /**
     * Framerate de 8 images par seconde
     */
    private final double frameRate = 8;
    private double totalTime = 0;
    private static boolean onGround = false;
    private boolean turnLeft = false;

    /**
     * Constructeur de la méduse
     *
     * @param x abscisse
     * @param y ordonnée
     */
    public Jellyfish(double x, double y) {
        this.x = x;
        this.y = y;
        this.width = IMAGESIZE;
        this.height = IMAGESIZE;
        // Gravité
        this.ay = -1200;
        // Vitesse horizontale
        this.vx = 0;

        // Chargement des images de la méduse lorsqu'elle regarde à droite
        framesRight = new Image[]{
                new Image("images/jellyfish1.png"),
                new Image("images/jellyfish2.png"),
                new Image("images/jellyfish3.png"),
                new Image("images/jellyfish4.png"),
                new Image("images/jellyfish5.png"),
                new Image("images/jellyfish6.png")
        };

        // Chargement des images de la méduse lorsqu'elle regarde à gauche
        framesLeft = new Image[]{
                new Image("images/jellyfish1g.png"),
                new Image("images/jellyfish2g.png"),
                new Image("images/jellyfish3g.png"),
                new Image("images/jellyfish4g.png"),
                new Image("images/jellyfish5g.png"),
                new Image("images/jellyfish6g.png")
        };
        // Image affichée au debut de la patrie
        currentImage = framesRight[0];
    }

    public void setOnGround(boolean onGround) { Jellyfish.onGround = onGround; }

    public static boolean getOnGround() { return onGround; }

    /**
     * Met à jour les attributs de la méduse et l'image qui la représente
     *
     * @param dt temps écoulé depuis le dernier update (en secondes)
     */
    @Override
    public void update(double dt) {

        super.update(dt);

        // Force la méduse à rester dans les bornes de l'écran gauche/droite
        if (this.x + width > HighSeaTower.WIDTH || this.x < 0) {
            this.vx *= -1;
        }

        this.x = Math.min(this.x, HighSeaTower.WIDTH - width);
        this.x = Math.max(this.x, 0);
        this.y = Math.max(this.y, 0);

        // Mise à jour de l'image affichée
        totalTime += dt;
        int frame = (int) (totalTime * frameRate);
        if (this.vx > 0) {
            currentImage = framesRight[frame % framesRight.length];
            turnLeft = false;
        } else if (this.vx < 0) {
            currentImage = framesLeft[frame % framesLeft.length];
            turnLeft = true;
        } else {
            // Affiche la méduse tournée à gauche si turnLeft est vrai
            currentImage = turnLeft
                    ? framesLeft[frame % framesLeft.length]
                    : framesRight[frame % framesRight.length];
        }
    }

    /**
     * Change l'accélération de la méduse si elle va à gauche
     */
    public void moveLeft() { this.ax = -1200; }

    /**
     * Change l'accélération de la méduse si elle va à droite
     */
    public void moveRight() { this.ax = 1200; }

    /**
     * Teste la collision entre la méduse et une plateforme et
     * demande à la plateforme de résoudre la collision
     *
     * @param platform plateforme dont on veut évaluer la collision avec la méduse
     */
    public void testCollision(Platform platform) {
        if (intersects(platform)) {
            platform.jellyfishCollision();
        } else {
            platform.color = platform.defaultColor;
        }
    }

    /**
     * Indique s’il y a intersection entre la méduse et la plateforme
     *
     * @param platform plateforme dont on veut vérifier l'intersection avec la méduse
     * @return vrai s’il y a intersection
     */
    public boolean intersects(Platform platform) {
        return !(
                // La méduse est à gauche de la plateforme
                this.x + this.width < platform.x
                        // La méduse est à droite de la plateforme
                        || platform.x + platform.width < this.x
                        // La méduse est en bas de la plateforme
                        || this.y + this.height < platform.y
                        // La méduse est en haut de la plateforme
                        || platform.y + platform.height < this.y);
    }

    /**
     * Change la vitesse verticale de la méduse dans le cas d'un saut
     */
    public void jump() {
        if (this.y == 0 || onGround == true) {
            this.vy = 600;
        }
    }

    /**
     * Met la vitesse et l'accéleération de la méduse à 0
     */
    public void stopMoving() {
        this.ax = 0;
        this.vx = 0;
    }

    /**
     * Dessine la méduse sur l'écran
     *
     * @param context  contexte sur lequel dessiner
     * @param windowY coordonnée y depuis le fond de l'océan
     */
    @Override
    public void draw(GraphicsContext context, double windowY) {
        context.drawImage(currentImage, x, Game.HEIGHT - (y- windowY) - height, width, height);
    }
}