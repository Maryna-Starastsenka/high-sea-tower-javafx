
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SuperJumper extends Application {

    public static final int WIDTH = 640, HEIGHT = 320;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane root = new Pane();
        Scene scene = new Scene(root, WIDTH, HEIGHT);

        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);

        GraphicsContext context = canvas.getGraphicsContext2D();

        Controleur controleur = new Controleur();

        scene.setOnKeyPressed((value) -> {
            if (value.getCode() == KeyCode.SPACE) {
                controleur.jump();
            }
        });

        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                double deltaTime = (now - lastTime) * 1e-9;

                controleur.update(deltaTime);
                controleur.draw(context);

                lastTime = now;
            }
        };
        timer.start();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
