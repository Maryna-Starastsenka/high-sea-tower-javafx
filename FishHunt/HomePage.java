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

/**
 * Classe Page d'Accueil de la vue
 */
public class HomePage extends Page {

    /**
     * Constructeur de la page d'accueil
     *
     * @param controller contrôleur du jeu
     */
    public HomePage(Controller controller) {

        VBox homepageRoot = new VBox();
        this.scene = new Scene(homepageRoot, FishHunt.WIDTH, FishHunt.HEIGHT);

        homepageRoot.setBackground(
            new Background(
                new BackgroundFill(
                        Color.DARKBLUE,
                        CornerRadii.EMPTY,
                        Insets.EMPTY)
            )
        );

        homepageRoot.setAlignment(Pos.CENTER);

        Button buttonNewGame = new Button("Nouvelle partie!");
        Button buttonBestScore = new Button("Meilleurs scores");

        Image logoImg = new Image("images/logo.png");
        ImageView logoView = new ImageView(logoImg);

        logoView.setFitWidth(FishHunt.WIDTH/2);
        logoView.setFitHeight(FishHunt.HEIGHT/2);

        homepageRoot.getChildren().add(logoView);
        homepageRoot.getChildren().add(buttonNewGame);
        homepageRoot.getChildren().add(buttonBestScore);
        homepageRoot.setSpacing(10);

        // Demande au contrôleur d'afficher la page de jeu
        buttonNewGame.setOnAction((event) -> controller.gamePage());

        // Demande au contrôleur d'afficher la page des meilleurs scores
        buttonBestScore.setOnAction((event) -> {
            controller.setScoreInputVisible(false);
            controller.scorePage();
        });
    }
}