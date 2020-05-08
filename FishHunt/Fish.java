/**
 * Classe abstraite Poisson du modèle
 * */

public abstract class Fish extends Entity {

    /**
     * Attributs de position horizontale et de taille d'image de poisson affiché
     */
    private double minPosY = 1.0/5 * FishHunt.HEIGHT;
    private double maxPosY = 4.0/5 * FishHunt.HEIGHT;
    private double minImageSize = 80;
    private double maxImageSize = 120;
    private double imageSize;

    /**
     * Montre si le poisson est sorti de la fenêtre
     */
    private boolean hasEscaped;

    /**
     * Constructeur du poisson
     *
     * @param x position horizontale initiale
     */
    public Fish (double x) {
        // Taille aléatoire dans la plage définie
        imageSize = minImageSize + Math.random()*(maxImageSize - minImageSize + 1);
        this.x = x;
        // Position verticale aléatoire dans la plage définie
        this.y = minPosY + Math.random()*(maxPosY - imageSize - minPosY + 1);
        this.width = imageSize;
        this.height = imageSize;
        // Vitesse horizontale dépendamment du niveau de jeu
        vx = 100.0 * Math.pow(Game.getLevel(), 1.0/3) + 200;
        if (x != 0) {
            // Vitesse horizontale négative si le poisson vient de la droite
            this.vx = -vx;
        }
        this.hasEscaped = false;
    }

    public boolean getHasEscaped() {
        return hasEscaped;
    }

    public void setHasEscaped (boolean hasEscaped) {
        this.hasEscaped = hasEscaped;
    }
}


