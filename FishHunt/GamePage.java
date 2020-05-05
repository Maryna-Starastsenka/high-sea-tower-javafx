import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class GamePage extends Page {

    public GamePage(Controller controller) {

        Pane root = new Pane();

        this.scene = new Scene(root, FishHunt.WIDTH, FishHunt.HEIGHT);
        setupKeyEvents(this.scene, controller);
        setupMouseEvents(this.scene, controller);

        Canvas canvas = new Canvas(FishHunt.WIDTH, FishHunt.HEIGHT);
        root.getChildren().add(canvas);
        GraphicsContext context = canvas.getGraphicsContext2D();

        startTimer(context, controller);
    }

    private void setupMouseEvents(Scene scene, Controller controller) {
//        scene.setOnMouseMoved((event) -> controller.move(event.getX(), event.getY()));
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
//                    controller.stopMoving();
                    break;
                case RIGHT:
//                    controller.stopMoving();
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