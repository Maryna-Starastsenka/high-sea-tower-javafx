import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.util.ArrayList;

public class ScorePage extends Page {

    private int newScore;
    private String playerName;
    private HBox scoreInputBox = new HBox();
    private Label scoreLabel = new Label();
    private TextField nameTextField = new TextField();
    private ListView<String> list = new ListView<>();

    ArrayList<String> scores = new ArrayList<>();

    public ScorePage(Controller controller) {


        VBox scorePageRoot = new VBox();
        this.scene = new Scene (scorePageRoot, FishHunt.WIDTH, FishHunt.HEIGHT);
        Text title = new Text("Meilleurs scores");


        scorePageRoot.setAlignment(Pos.CENTER);
        title.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
        scorePageRoot.getChildren().add(title);
        scorePageRoot.getChildren().add(list);

        //Input Field
        scoreInputBox.setAlignment(Pos.CENTER);
        Label nameLabel = new Label("Votre nom: ");

        Button buttonSubmit = new Button("Ajouter!");
        scoreInputBox.getChildren().add(nameLabel);
        scoreInputBox.getChildren().add(nameTextField);
        scoreInputBox.getChildren().add(scoreLabel);
        scoreInputBox.getChildren().add(buttonSubmit);
        scoreInputBox.setSpacing(10);
        scorePageRoot.getChildren().add(scoreInputBox);


        Button buttonMenu = new Button("Menu");
        scorePageRoot.getChildren().add(buttonMenu);
        scorePageRoot.setSpacing(10);
        scorePageRoot.setPadding(new Insets(10));

        buttonSubmit.setOnAction((event) -> submitScore(controller));
        buttonMenu.setOnAction((event) -> controller.homePage());

    }

    public void setNewScore(int score) {
        this.newScore = score;
        scoreLabel.setText(" a fait "+score+" points!");
        nameTextField.setText("");
    }

    public void setBestScores(ArrayList<Pair<String,Integer>> bestScores) {

        for (int i=0; i < bestScores.size(); i++) {
            scores.add("#"+(i+1)+" - "+bestScores.get(i).getKey()+" - "+bestScores.get(i).getValue());
        }
        list.getItems().setAll(scores);
    }

    //Active le champ pour rentrer son score
    public void setScoreInputVisible(boolean visible) {
        scoreInputBox.setVisible(visible);
    }

    private void submitScore(Controller controller) {
        this.playerName = nameTextField.getText();
        controller.addNewScore(playerName, this.newScore);
        setScoreInputVisible(false);
        controller.homePage();
    }

    public void clearScores() {
        scores.clear();
    }

}
