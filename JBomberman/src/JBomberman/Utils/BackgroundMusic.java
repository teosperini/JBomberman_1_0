package JBomberman.Utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.nio.file.Paths;
public class BackgroundMusic {
    private static final String PATH = System.getProperty("user.dir");
    private static final String GAMESOUNDTRACK = PATH + "\\JBomberman\\src\\resources\\UndertaleOST.mp3";
    private static final String GAMEBOMB = "..\\JBomberman\\src\\resources\\Meme de bomba nuclear.mp3";

    private static MediaPlayer mediaPlayer;

    public void initialize(Stage primaryStage) {
    }

    public static void playMusic(){
        // Carica il file audio da riprodurre
        System.out.println();
        Media media = new Media(Paths.get(GAMESOUNDTRACK).toUri().toString());
        mediaPlayer =  new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Riproduce la musica in modo continuo
        mediaPlayer.play();
    }

    public static void stopMusic(){
        mediaPlayer.stop();
    }

    public static void playBomb(){
        Media bomb = new Media(Paths.get(GAMEBOMB).toUri().toString());
        mediaPlayer = new MediaPlayer(bomb);
        mediaPlayer.play();
    }

    //capire bene come funziona
    public static void setVolume(){
        mediaPlayer.setVolume(50);
    }
}