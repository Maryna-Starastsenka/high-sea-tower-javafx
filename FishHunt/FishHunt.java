/**
 * @author Michel Adant, matricule C1176, code permanent p0681884
 * @author Maryna Starastsenka, matricule 20166402, code permanent p1240201
 *
 * Le programme est un jeu avec GUI où une méduse tente de remonter
 * le plus haut possible dans l’océan en sautant de plateforme en plateforme. 
 */

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Classe de la Vue qui avertit le contrôleur lorsqu’il y a des événements
 */
public class FishHunt extends Application {

    /**
     * Largeur et hauteur de la fenêtre
     */
    public static final int WIDTH = 640, HEIGHT = 480;

    /**
     * Point d'entrée du programme qui démarre la vue
     *
     * @param args arguments passés au programme en ligne de commande
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Définit les composants de la vue et crée le contrôleur
     *
     * @param primaryStage stage (fenêtre) principale du jeu
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setResizable(false);
        primaryStage.setTitle("Fish Hunt");


        Controller controller = new Controller();
        controller.setPrimaryStage(primaryStage);
        controller.homePage();
    }
}