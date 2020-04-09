import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HighSeaTower extends Application {

    public static final int WIDTH = 350, HEIGHT = 480;


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

        //Texte?


        Controller controller = new Controller(WIDTH, HEIGHT);

        scene.setOnKeyPressed((value) -> {
            switch (value.getCode()) {
                case SPACE:
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
                    controller.setDebug();
                    break;
            }
        });

        scene.setOnKeyReleased((value) -> {
            switch (value.getCode()) {
                case LEFT:
                    controller.resetAccelerator();
                    break;
                case RIGHT:
                    controller.resetAccelerator();
                    break;
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

                controller.update(deltaTime);
                controller.draw(context);

                lastTime = now;
            }
        };
        timer.start();

        primaryStage.setResizable(false);
        primaryStage.setTitle("High Sea Tower");
        primaryStage.getIcons().add(new Image("images/jellyfish1.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
