public abstract class Fish extends Entity {

    private double minPosY = 1.0/5 * FishHunt.HEIGHT;
    private double maxPosY = 4.0/5 * FishHunt.HEIGHT;
    private double minImageSize = 80;
    private double maxImageSize = 120;
    private double imageSize;

    private boolean hasEscaped;

    public Fish (double x) {
        imageSize = minImageSize + Math.random()*(maxImageSize - minImageSize + 1);
        this.x = x;
        this.y = minPosY + Math.random()*(maxPosY - imageSize - minPosY + 1);
        this.width = imageSize;
        this.height = imageSize;
        vx = 100.0 * Math.pow(Game.getLevel(), 1.0/3) + 200;
        if (x != 0) {
            //Si vient de la droite:
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


