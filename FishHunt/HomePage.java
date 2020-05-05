import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class HomePage extends Page {
    private Button buttonNewGame;
    private Button buttonBestScore;

    public HomePage(Controller controller, int width, int height) {

        VBox homepageRoot = new VBox();
        this.scene = new Scene(homepageRoot, width, height);

        homepageRoot.setBackground(
            new Background(
                new BackgroundFill(
                        Color.DARKBLUE,
                        CornerRadii.EMPTY,
                        Insets.EMPTY)
            )
        );

        homepageRoot.setAlignment(Pos.CENTER);

        buttonNewGame = new Button("Nouvelle partie!");
        buttonBestScore = new Button("Meilleurs scores");

        Image logoImg = new Image("images/logo.png");
        ImageView logoView = new ImageView(logoImg);

        logoView.setFitWidth(width/2);
        logoView.setFitHeight(height/2);

        homepageRoot.getChildren().add(logoView);
        homepageRoot.getChildren().add(buttonNewGame);
        homepageRoot.getChildren().add(buttonBestScore);
        homepageRoot.setSpacing(10);

        buttonNewGame.setOnAction((event) -> controller.gamePage());

        buttonBestScore.setOnAction((event) -> controller.scorePage());
    }
}