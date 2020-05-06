import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.util.ArrayList;

public class ScorePage extends Page {

    ArrayList<String> scores = new ArrayList<>();

    public ScorePage(Controller controller) {

        scores.add("Test 1");
        scores.add("Test 2");
        VBox scorePageRoot = new VBox();
        this.scene = new Scene (scorePageRoot, FishHunt.WIDTH, FishHunt.HEIGHT);
        Text title = new Text("Meilleurs scores");
        ListView<String> list = new ListView<>();
        list.getItems().setAll(scores);
        Button buttonMenu = new Button("Menu");

        scorePageRoot.setAlignment(Pos.CENTER);
        title.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
        scorePageRoot.getChildren().add(title);
        scorePageRoot.getChildren().add(list);
        scorePageRoot.getChildren().add(buttonMenu);
        scorePageRoot.setSpacing(10);
        scorePageRoot.setPadding(new Insets(10));

        buttonMenu.setOnAction((event) -> controller.homePage());
    }
}
