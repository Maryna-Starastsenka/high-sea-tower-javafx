import javafx.util.Pair;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Classe Score du modèle qui contient la logique des scores du jeu
 */
public class Score {

    /**
     * Liste contenant les 10 meilleurs scores et les noms des joueurs
     */
    private ArrayList<Pair<String,Integer>> bestScores = new ArrayList<>();

    /**
     * Constructeur de Score qui initialise le modèle avec un fichier .dat
     */
    public Score() {
       readScoreFile();
    }

    /**
     * Vérifie si le nouveau score doit être affiché dans la liste des scores
     *
     * @param newScore score de la partie qui vient d'être jouée
     * @return vrai si la taille de la liste des scores est inférieure à 10 ou
     * le nouveau score est supérieur au 10ème score
     */
    public boolean compareNewScore(int newScore) {
        readScoreFile();
        return bestScores.size() < 10 || newScore > bestScores.get(9).getValue();
    }

    /**
     * Ajoute le score actuel dans la liste de meilleurs scores et
     * reçoit la liste triée des 10 meilleurs scores
     * Procède à la sérialisation du modèle dans un fichier .dat
     *
     * @param name nom du joueur
     * @param score score de la partie
     */
    public void addNewScore(String name, int score) {
        bestScores.add(new Pair<>(name, score));
        bestScores = sortScores(bestScores);
        writeScoreFile();
    }

    /**
     * Trie la liste de meilleurs scores et garde les 10 premiers scores
     *
     * @param scores liste des meilleurs scores
     * @return liste triée des meilleurs scores
     */
    private ArrayList<Pair<String,Integer>> sortScores(ArrayList<Pair<String,Integer>> scores) {
        // Trie la liste de meilleurs scores en ordre décroissant
        scores.sort(Comparator.comparing(x -> x.getValue()));
        Collections.reverse(scores);

        // Efface tous les éléments au-delà de la 10ème position
        if (scores.size() > 10)
            scores.subList(10, scores.size()).clear();
        return scores;
    }

    /**
     * Lit le fichier "scores.dat" avec les meilleurs scores avant la partie actuelle
     *
     * @return liste de paires avec les meilleurs scores avant la partie actuelle
     */
    public ArrayList<Pair<String,Integer>> readScoreFile() {
        try {
            FileInputStream scoreFileInputStream = new FileInputStream("scores.dat");
            ObjectInputStream scoreObjectInputStream = new ObjectInputStream(scoreFileInputStream);
            bestScores = (ArrayList<Pair<String,Integer>>) scoreObjectInputStream.readObject();
            scoreObjectInputStream.close();
            scoreFileInputStream.close();

        } catch (IOException | ClassNotFoundException ioe) {
            ioe.printStackTrace();
            System.out.println("Erreur de lecture du fichier scores.dat");
         }
        return bestScores;
    }

    /**
     * Fait l'écriture des 10 meilleurs scores dans le fichier "scores.dat"
     */
    private void writeScoreFile() {
        try {
            FileOutputStream scoreFileOutputStream = new FileOutputStream("scores.dat");
            ObjectOutputStream scoreObjectOutputStream = new ObjectOutputStream(scoreFileOutputStream);
            scoreObjectOutputStream.writeObject(bestScores);
            scoreObjectOutputStream.close();
            scoreFileOutputStream.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
            System.out.println("Erreur d'écriture dans le fichier");
        }
    }
}
