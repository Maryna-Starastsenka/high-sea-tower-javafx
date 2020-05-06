public abstract class Fish extends Entity {

    private double minPosY = 1.0/5 * FishHunt.HEIGHT;
    private double maxPosY = 4.0/5 * FishHunt.HEIGHT;
    private double minImageSize = 80;
    private double maxImageSize = 120;
    private double imageSize;

    private int level = 2;

    public Fish (double x) {
        this.x = x;
        this.y = minPosY + Math.random()*(maxPosY - minPosY + 1);
        imageSize = minImageSize + Math.random()*(maxImageSize - minImageSize + 1);
        this.width = imageSize;
        this.height = imageSize;
        vx = 100.0 * Math.pow(level, 1.0/3) + 200;
        if (x != 0) {
            //Si vient de la droite:
            this.vx = -vx;
        }
    }
}


