import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Jellyfish extends Entity {

    private Image[] framesRight, framesLeft;
    private Image image;
    private double frameRate = 8; // 8 frame par sec
    private double tempsTotal = 0;

    protected boolean onGround = false;

    public Jellyfish(double x, double y) {
        this.x = x;
        this.y = y;
        this.largeur = 50;
        this.hauteur = 50;
        this.ay = -1200;
        this.vx = 0;



        // Chargement des images
        framesRight = new Image[]{
                new Image("images/jellyfish1.png"),
                new Image("images/jellyfish2.png"),
                new Image("images/jellyfish3.png"),
                new Image("images/jellyfish4.png"),
                new Image("images/jellyfish5.png"),
                new Image("images/jellyfish6.png")
        };

        framesLeft = new Image[]{
                new Image("images/jellyfish1g.png"),
                new Image("images/jellyfish2g.png"),
                new Image("images/jellyfish3g.png"),
                new Image("images/jellyfish4g.png"),
                new Image("images/jellyfish5g.png"),
                new Image("images/jellyfish6g.png")
        };
        image = framesRight[0];
    }

        @Override
        public void update ( double dt){
            // Physique du personnage
            super.update(dt);

            // Force à rester dans les bornes de l'écran
            if (this.x + largeur > HighSeaTower.WIDTH || this.x < 0) {
                this.vx *= -1;
            }

            this.x = Math.min(this.x, HighSeaTower.WIDTH - largeur);
            this.x = Math.max(this.x, 0);
            this.y = Math.max(this.y, 0);

            // Mise à jour de l'image affichée
            tempsTotal += dt;
            int frame = (int) (tempsTotal * frameRate);
            if (this.vx >= 0) {
                image = framesRight[frame % framesRight.length];
            } else {
                image = framesLeft[frame % framesLeft.length];
            }
        }

        public void testCollision (Platform other){
            /**
             * La collision avec une plateforme a lieu seulement si :
             *
             * - Il y a une intersection entre la plateforme et le personnage
             *
             * - La collision a lieu entre la plateforme et le *bas du personnage*
             * seulement
             *
             * - La vitesse va vers le bas (le personnage est en train de tomber,
             * pas en train de sauter)
             */
            if (intersects(other) && (other.y+other.hauteur-this.y) < 15
                    && vy < 0) {
                pushOut(other);
                this.vy = 0;

                if (Math.abs(other.y+other.hauteur-this.y) < 5) {
                    this.onGround = true;
                } else {
                    this.onGround = false;
                }

            }


        }

        public boolean intersects (Platform other){
            return !( // Un des carrés est à gauche de l’autre
                    this.x + this.largeur <= other.x
                            || other.x + other.largeur <= this.x
                            // Un des carrés est en haut de l’autre
                            || this.y + this.hauteur <= other.y
                            || other.y + other.hauteur <= this.y);
        }

        /**
         * Repousse le personnage vers le haut (sans déplacer la
         * plateforme)
         */
        public void pushOut (Platform other){
            double deltaY = other.y+other.hauteur-this.y;
            this.y += deltaY;
        }

        public void setOnGround(boolean onGround){
            this.onGround = onGround;
        }

        /**
         * Le personnage peut seulement sauter s'il se trouve sur une
         * plateforme
         */
        public void jump() {
            if (this.y == 0 || onGround == true) {
                this.vy = 600;
            }


        }

        public void moveLeft() { this.ax = -1200; }

        public void moveRight() { this.ax = 1200; }

        public void resetAccelerator() {
            this.ax = 0;
            this.vx = 0;
        }

        @Override
        public void draw (GraphicsContext context, double fenetreY){
            context.drawImage(image, x, Game.HEIGHT - (y-fenetreY) - hauteur, largeur, hauteur);
        }
    }

