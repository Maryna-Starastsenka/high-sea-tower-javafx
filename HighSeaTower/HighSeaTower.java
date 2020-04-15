/**
 * @author Michel Adant, matricule C1176, code permanent p0681884
 * @author Maryna Starastsenka, matricule 20166402, code permanent p1240201
 *
 * Le programme est un jeu avec GUI où une méduse tente de remonter
 * le plus haut possible dans l’océan en sautant de plateforme en plateforme. 
 */

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Classe de la Vue qui avertit le contrôleur lorsqu’il y a des événements
 */
public class HighSeaTower extends Application {

    /**
     * Largeur et hauteur de la fenêtre
     */
    public static final int WIDTH = 350, HEIGHT = 480;

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

        Pane root = new Pane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        Canvas canvas = new Canvas(WIDTH, HEIGHT);

        root.getChildren().add(canvas);
        GraphicsContext context = canvas.getGraphicsContext2D();

        Controller controller = new Controller(WIDTH, HEIGHT);

        setupKeyEvents(scene, controller);

        startTimer(context, controller);

        primaryStage.setResizable(false);
        primaryStage.setTitle("High Sea Tower");
        primaryStage.getIcons().add(new Image("images/jellyfish1.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Traitement des touches entrées
     * Mapping de la vue avec le contrôleur
     *
     * @param scene scène du jeu
     * @param controller contrôleur du jeu
     */
    private void setupKeyEvents(Scene scene, Controller controller) {

        scene.setOnKeyPressed((value) -> {
            switch (value.getCode()) {
                case SPACE:
                case UP:
                    controller.jump();
                    break;
                case ESCAPE:
                    Platform.exit();
                    break;
                case LEFT:
                    controller.moveLeft();
                    break;
                case RIGHT:
                    controller.moveRight();
                    break;
                case T:
                    controller.switchDebug();
                    break;
            }
        });

        // Appuyer sur une touche gauche/droite doit appeler les actions une seule fois
        // tant que l'usager n'a pas relevé la touche
        scene.setOnKeyReleased((value) -> {
            switch (value.getCode()) {
                case LEFT:
                    controller.stopMoving();
                    break;
                case RIGHT:
                    controller.stopMoving();
                    break;
            }
        });
    }

    /**
     * Création et démarrage du timer de jeu
     *
     * @param context contexte du jeu
     * @param controller contrôleur du jeu
     */
    private void startTimer(GraphicsContext context, Controller controller) {
        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = 0;
            private final double maxDt = 0.01;

            /**
             * Est appelé automatiquement à chaque frame quand AnimationTimer est actif
             *
             * @param now temps actuel (en nanosecondes)
             */
            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }
                // Temps écoulé depuis le dernier appel en secondes
                double deltaTime = (now - lastTime) * 1e-9;

                //Force les updates à se faire avec un max de maxDt secondes
                while (deltaTime > maxDt) {
                    controller.update(maxDt);
                    deltaTime -= maxDt;
                }

                controller.update(deltaTime);
                controller.draw(context);

                lastTime = now;
            }
        };
        timer.start();
    }
}