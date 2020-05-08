import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


//Logique du score

public class Score {


    private ArrayList<Pair<String,Integer>> bestScores = new ArrayList<>();

    public Score() {
       readScoreFile();
    }

    public boolean compareNewScore(int newScore) {
        readScoreFile();
        if (bestScores.size() < 10 || newScore > bestScores.get(9).getValue()) {
            return true;
        }
        return false;
    }

    public void addNewScore(String name, int score) {
        bestScores.add(new Pair(name, score));
        bestScores = sortScores(bestScores);
        writeScoreFile();
    }

    private ArrayList<Pair<String,Integer>> sortScores(ArrayList<Pair<String,Integer>> scores) {
        //Ordre décroissant
        scores.sort(Comparator.comparing(x -> x.getValue()));
        Collections.reverse(scores);

        //Efface tous les éléments au delà de la 10e position:
        if (scores.size() > 10)
        scores.subList(10, scores.size()).clear();
        return scores;
    }


    public ArrayList<Pair<String,Integer>> readScoreFile() {
        try {
            FileInputStream scoreFileInputStream = new FileInputStream("scores.dat");
            ObjectInputStream  scoreObjectInputStream = new ObjectInputStream(scoreFileInputStream);
            bestScores = (ArrayList<Pair<String,Integer>>) scoreObjectInputStream.readObject();
            scoreObjectInputStream.close();
            scoreFileInputStream.close();

        } catch (IOException | ClassNotFoundException ioe) {
            ioe.printStackTrace();
            System.out.println("Erreur de lecture du fichier scores.dat");
         }
        return bestScores;
    }


    private void writeScoreFile() {
        try {
            FileOutputStream scoreFileOutputStream = new FileOutputStream("scores.dat");
            ObjectOutputStream scoreObjectOutputStream = new ObjectOutputStream(scoreFileOutputStream);
            scoreObjectOutputStream.writeObject(bestScores);
            scoreObjectOutputStream.close();
            scoreFileOutputStream.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("Erreur d'écriture du fichier");
        }
    }


}
