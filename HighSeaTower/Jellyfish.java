import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Classe Méduse (modèle) qui représente un objet méduse
 */
public class Jellyfish extends Entity {

    private Image[] framesRight, framesLeft;
    private Image image;

    /**
     * Framerate de 8 images par seconde
     */
    private double frameRate = 8;

    private double tempsTotal = 0;
    private static boolean onGround = false;
    private boolean turnLeft = false;
    public static int IMAGESIZE = 50;

    /**
     * Constructeur de la méduse
     * @param x position de la méduse
     * @param y position de la méduse
     */
    public Jellyfish(double x, double y) {
        this.x = x;
        this.y = y;
        this.largeur = IMAGESIZE;
        this.hauteur = IMAGESIZE;
        // Accélération en y
        this.ay = -1200;
        // Vitesse en x
        this.vx = 0;

        // Chargement des images des la méduse qui regarde à droite
        framesRight = new Image[]{
                new Image("images/jellyfish1.png"),
                new Image("images/jellyfish2.png"),
                new Image("images/jellyfish3.png"),
                new Image("images/jellyfish4.png"),
                new Image("images/jellyfish5.png"),
                new Image("images/jellyfish6.png")
        };

        // Chargement des images des la méduse qui regarde à gauche
        framesLeft = new Image[]{
                new Image("images/jellyfish1g.png"),
                new Image("images/jellyfish2g.png"),
                new Image("images/jellyfish3g.png"),
                new Image("images/jellyfish4g.png"),
                new Image("images/jellyfish5g.png"),
                new Image("images/jellyfish6g.png")
        };
        // Image affichée au debut de la patrie
        image = framesRight[0];
    }

    public void setOnGround(boolean onGround) { this.onGround = onGround; }

    public static boolean getOnGround() { return onGround; }

    /**
     * Met à jour les ratamètres de la méduse et les images affichées
     * @param dt temps écoulé depuis le dernier update en secondes
     */
    @Override
    public void update(double dt) {

        super.update(dt);

        // Force à rester la méduse dans les bornes de l'écran
        if (this.x + largeur > HighSeaTower.WIDTH || this.x < 0) {
            this.vx *= -1;
        }

        this.x = Math.min(this.x, HighSeaTower.WIDTH - largeur);
        this.x = Math.max(this.x, 0);
        this.y = Math.max(this.y, 0);

        // Mise à jour de l'image affichée
        tempsTotal += dt;
        int frame = (int) (tempsTotal * frameRate);
        if (this.vx > 0) {
            image = framesRight[frame % framesRight.length];
            turnLeft = false;
        } else if (this.vx < 0) {
            image = framesLeft[frame % framesLeft.length];
            turnLeft = true;
        } else {
            // Affiche la méduse tournée à gauche si turnLeft est vrai
            image = turnLeft
                    ? framesLeft[frame % framesLeft.length]
                    : framesRight[frame % framesRight.length];
        }
    }

    /**
     * Met l'accélération si la méduse bouge vers la gauche
     */
    public void moveLeft() { this.ax = -1200; }

    /**
     * Met l'accélération si la méduse bouge vers le droite
     */
    public void moveRight() { this.ax = 1200; }

    /**
     * Teste la collision entre la méduse et la plateforme et
     * et demande à la plateforme de soudre la collision
     * @param platform à tester la collision avec la méduse
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
     * @param platform à vérifier l'intersection avec la méduse
     * @return vrai s’il y a intersection
     */
    public boolean intersects(Platform platform) {
        // Un des carrés est à gauche de l’autre
        return !(
                // La méduse est à gauche de la plateforme
                this.x + this.largeur < platform.x
                        // La méduse est à droite de la plateforme
                        || platform.x + platform.largeur < this.x
                        // La méduse est en bas de la plateforme
                        || this.y + this.hauteur < platform.y
                        // La méduse est en haut de la plateforme
                        || platform.y + platform.hauteur < this.y);
    }

    /**
     * Met une vitesse de vers la haut si la méduse saute
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
     * @param context  contexte sur lequel dessiner
     * @param fenetreY coordonnée y depuis le fond d'océan
     */
    @Override
    public void draw(GraphicsContext context, double fenetreY) {
        context.drawImage(image, x, Game.HEIGHT - (y-fenetreY) - hauteur, largeur, hauteur);
    }
}

