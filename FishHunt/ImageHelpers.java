import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * Classe fournissant des méthodes statiques pour manipuler des Images avec JavaFX
 */
public class ImageHelpers {

    /**
     * Constructeur d'ImageHelpers
     */
    private ImageHelpers() {
    }

    /**
     * Inversion horizontale d'une image
     *
     * @param img Image à inverser
     * @return Nouvelle image contenant une inversion horizontale des pixels
     * de l'image originale
     */
    public static Image flop(Image img) {
        int w = (int) img.getWidth();
        WritableImage output = new WritableImage(w, (int) img.getHeight());

        PixelReader reader = img.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color color = reader.getColor(x, y);
                writer.setColor(w - 1 - x, y, color);
            }
        }
        return output;
    }

    /**
     * Recolorie une image avec une couleur donnée. Tous les pixels
     * non-transparents de l'image img se font attribuer la couleur color passée
     * en paramètre
     *
     * @param img Image originale
     * @param color Nouvelle couleur à utiliser
     * @return Une nouvelle image contenant une version re-coloriée de l'image
     * originale
     */
    public static Image colorize(Image img, Color color) {
        WritableImage output = new WritableImage((int) img.getWidth(), (int) img.getHeight());

        PixelReader reader = img.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                if (reader.getColor(x, y).getOpacity() > 0) {
                    writer.setColor(x, y, color);
                }
            }
        }
        return output;
    }
}
