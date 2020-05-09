/**
 * Classe abstraite Poisson du modèle qui hérite d'Entité
 */
public abstract class Fish extends Entity {

    /**
     * Attributs de position horizontale et de taille d'image du poisson affiché
     */
    private final double minPosY = 1.0/5 * FishHunt.HEIGHT;
    private final double maxPosY = 4.0/5 * FishHunt.HEIGHT;
    private final double minImageSize = 80;
    private final double maxImageSize = 120;

    /**
     * Indique si le poisson est sorti de la fenêtre
     */
    private boolean hasEscaped;

    public boolean getHasEscaped() {
        return hasEscaped;
    }

    public void setHasEscaped (boolean hasEscaped) {
        this.hasEscaped = hasEscaped;
    }

    /**
     * Constructeur du poisson
     *
     * @param x position horizontale initiale
     */
    public Fish (double x) {
        // Taille aléatoire dans la plage définie
        double imageSize = minImageSize + Math.random() * (maxImageSize - minImageSize + 1);
        this.x = x;

        // Position verticale aléatoire dans la plage définie
        this.y = minPosY + Math.random() * (maxPosY - imageSize - minPosY + 1);
        this.width = imageSize;
        this.height = imageSize;

        // Vitesse horizontale dépendamment du niveau de jeu
        vx = 100.0 * Math.pow(Game.getLEVEL(), 1.0/3) + 200;

        if (x != 0) {
            // Vitesse horisontale négative si le poissin vient de la droite
            this.vx = -vx;
        }
        this.hasEscaped = false;
    }
}


