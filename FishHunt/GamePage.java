import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;

/**
 * Classe Page de Jeu de la vue
 */
public class GamePage extends Page {

    /**
     * Constructeur de la page de jeu
     *
     * @param controller contrôleur du jeu
     */
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

    /**
     * Traitement des événements de la souris
     * Mapping de la vue avec le contrôleur
     *
     * @param scene scène du jeu
     * @param controller contrôleur du jeu
     */
    private void setupMouseEvents(Scene scene, Controller controller) {
        scene.setOnMouseMoved((event) -> {
          controller.move(event.getX(), FishHunt.HEIGHT - event.getY());
        });

        scene.setOnMouseClicked((event) -> {
            controller.shoot(event.getX(), FishHunt.HEIGHT - event.getY());
        });
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
                case H:
                    controller.nextLevel();
                    break;
                case J:
                    controller.increaseScore();
                    break;
                case K:
                    controller.increaseLife();
                    break;
                case L:
                    controller.gameOver();
                    break;
                case ESCAPE:
                    Platform.exit();
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

                // Force les updates à se faire avec un max de maxDt secondes
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