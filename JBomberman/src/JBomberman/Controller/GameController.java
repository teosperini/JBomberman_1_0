package JBomberman.Controller;

import JBomberman.JBomberMan;
import JBomberman.Model.Model;
import JBomberman.Utils.BackgroundMusic;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GameController {
    // model
    private static final Model MODEL = JBomberMan.getModel();
    // coordinates of the fixed blocks
    private static final ArrayList<coordinate> COORDINATES_FIXED_BLOCKS = new ArrayList<>();
    // how much the character can move every time a key is pressed
    private static final int MOVEMENT = 1;
    // how many random blocks are going to spawn
    private static final int NUM_RND_BLOCKS = 30;
    // the coordinates of the winning cell
    private static final coordinate EXIT = new coordinate(10,6);

    private static final coordinate MAX = new coordinate(14,10);

    private static final Map<coordinate, ImageView> COORDINATES_RANDOM_BLOCKS = new HashMap<>();


    private boolean isBombExploding = false;

    @FXML
    private AnchorPane gameBoard;

    @FXML
    private ImageView bomberman;

    @FXML
    private ImageView tnt;

    private static final ArrayList<coordinate> RANDOM_BLOCKS = new ArrayList<>();

    /**
     * initialize è un metodo che viene chiamato dopo il metodo start e dopo
     * essere stati inizializzati i campi, in modo tale da averli tutti disponibili
     */
    public void initialize() {

        // SOUNDTRACK
        BackgroundMusic bm = new BackgroundMusic();
        bm.initialize(JBomberMan.getStage());
        BackgroundMusic.playMusic();

        // FILLING THE FIXED BLOCK ARRAY
        for (int x = 1; x <14; x+=2) {
            for (int y = 1; y < 10; y+=2){
                COORDINATES_FIXED_BLOCKS.add(new coordinate(x,y));
            }
        }

        randomGen();

        // POSIZIONAMENTO DEL PERSONAGGIO
        bomberman.setLayoutX(0);        // x = 0
        bomberman.setLayoutY(0);        // y = 0
        gameBoard.requestFocus();    // richiede il focus
        gameBoard.setFocusTraversable(false);     // attiva la possibilitò per il nodo (pianoDiGioco) di
        // essere "navigabile" quando l'utente utilizza scorciatoie da tastiera per spostarsi tra i nodi

        // REGISTRAZIONE KEY EVENT PER MOVIMENTO PERSONAGGIO
        gameBoard.setOnKeyPressed(keyEvent -> {
            KeyCode keyCode = keyEvent.getCode();
            System.out.println("Key pressed: " + keyCode);

            switch (keyCode) {
                //case UP arrow key:
                case UP:
                    moveCharacter(0, -MOVEMENT);
                    break;
                //case DOWN arrow key:
                case DOWN:
                    moveCharacter(0, MOVEMENT);
                    break;
                //case LEFT arrow key:
                case LEFT:
                    moveCharacter(-MOVEMENT, 0);
                    break;
                //case RIGHT arrow key:
                case RIGHT:
                    moveCharacter(MOVEMENT, 0);
                    break;
                case SPACE:
                    bomb();
                    break;
            }
        });
    }

    private void bomb() {
        if (isBombExploding) {
            return;
        }
        isBombExploding = true;

        tnt = new ImageView();
        tnt.setImage(new Image(getClass().getResourceAsStream("/tnt.jpeg")));
        tnt.setLayoutX(bomberman.getLayoutX());
        tnt.setLayoutY(bomberman.getLayoutY());
        tnt.setFitHeight(40);
        tnt.setFitWidth(40);
        AudioClip boom = new AudioClip(getClass().getResource("/tnt_exp.mp3").toExternalForm());

        PauseTransition spawnTNT = new PauseTransition(Duration.millis(50));
        PauseTransition pauseTNT = new PauseTransition(Duration.millis(500));
        PauseTransition respawnTNT = new PauseTransition(Duration.millis(500));
        PauseTransition removeTNT = new PauseTransition(Duration.millis(650));

        spawnTNT.setOnFinished(event -> {
            gameBoard.getChildren().add(tnt);
            boom.play();
            pauseTNT.play();
        });

        pauseTNT.setOnFinished(event1 -> {
        gameBoard.getChildren().remove(tnt);
            respawnTNT.play();
        });

        respawnTNT.setOnFinished(event3 -> {
            gameBoard.getChildren().add(tnt);

            removeTNT.play();

        });

        removeTNT.setOnFinished(event4 -> {
            gameBoard.getChildren().remove(tnt);
            isBombExploding = false;
            try {
                explosion((int)tnt.getLayoutX(),(int)tnt.getLayoutY());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        spawnTNT.play();


    }
    //  <  >
    private void explosion(int x, int y) throws IOException {
        x = x/40;
        y = y/40;
        ArrayList<coordinate> rimossi = new ArrayList<>();
        System.out.println(x + " " + y);
        coordinate coordinate = new coordinate(x-1,y);
        if ((x > 0) && RANDOM_BLOCKS.contains(coordinate)){

            RANDOM_BLOCKS.remove(coordinate);
            rimossi.add(coordinate);
        }
        coordinate = new coordinate(x,y-1);
        if ((y > 0) && RANDOM_BLOCKS.contains(coordinate)){
            RANDOM_BLOCKS.remove(coordinate);
            rimossi.add(coordinate);
        }
        coordinate = new coordinate(x+1,y);
        if ((x < 14) && RANDOM_BLOCKS.contains(coordinate)){
            RANDOM_BLOCKS.remove(coordinate);
            rimossi.add(coordinate);
        }
        coordinate = new coordinate(x,y+1);
        if ((y < 10) && RANDOM_BLOCKS.contains(coordinate)){
            RANDOM_BLOCKS.remove(coordinate);
            rimossi.add(coordinate);
        }
        for (coordinate coord : rimossi) {
            if(coord.x() == bomberman.getLayoutX()/40 && coord.y() == bomberman.getLayoutY()/40){
                defeat();
            }
        }

        for (coordinate coord : rimossi) {
            ImageView imageView = COORDINATES_RANDOM_BLOCKS.get(coord);
            if (imageView != null) {
                gameBoard.getChildren().remove(imageView);
            }
        }
        gameBoard.layout();
    }


    private record coordinate(int x, int y) { }

    private void randomGen() {
        Random random = new Random();
        int i = 0;

        while (i < NUM_RND_BLOCKS) {
            coordinate coord = new coordinate(random.nextInt(MAX.x()), random.nextInt(MAX.y()));

            if (!isValidBlockPosition(coord)) {
                RANDOM_BLOCKS.add(coord);
                i++;
            }
        }

        RANDOM_BLOCKS.forEach(this::addBlocks);
    }

    private boolean isValidBlockPosition(coordinate xy) {
        coordinate a = new coordinate(0,0);
        coordinate b = new coordinate(1,0);
        coordinate c = new coordinate(0,1);
        return COORDINATES_FIXED_BLOCKS.contains(xy) || xy.equals(EXIT) || (xy.equals(a) || xy.equals(b) || xy.equals(c));
    }


    private void addBlocks(coordinate c) {
        ImageView blockView = new ImageView();
        blockView.setImage(new Image(getClass().getResourceAsStream("/stone.png")));
        blockView.setLayoutX(c.x()*40);
        blockView.setLayoutY(c.y()*40);
        blockView.setFitHeight(40);
        blockView.setFitWidth(40);

        gameBoard.getChildren().add(blockView);
        COORDINATES_RANDOM_BLOCKS.put(c, blockView);
    }


    /**
     * @param deltaX è il valore di quanto si deve spostare il personaggio sull'asse delle X
     * @param deltaY è il valore
     */
    private void moveCharacter(int deltaX, int deltaY) {
        // Calcola la nuova posizione del personaggio principale
        int newX = ((int)bomberman.getLayoutX()/40) + deltaX;
        int newY = ((int)bomberman.getLayoutY()/40) + deltaY;

        newX = clamp(newX, MAX.x());
        newY = clamp(newY, MAX.y());

        if (!collisione(newX, newY)) {
            bomberman.setLayoutX(newX * 40);
            bomberman.setLayoutY(newY * 40);
            if (newX == EXIT.x() && newY == EXIT.y()) {
                try {
                    victory();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private void defeat() throws IOException {
        BackgroundMusic.stopMusic();
        MODEL.changeSc("/MainMenu.fxml");
    }

    private void victory() throws IOException {
        BackgroundMusic.stopMusic();
        /*
        BackgorundMusic.setVolume();
        BackgorundMusic.playBomb();
         */
        MODEL.changeSc("/MainMenu.fxml");
    }

    /**
     * funzione di clamping, che limita il valore ad un massimo/minimo dato
     * il nuovo valore e il valore minimo (0 in questo caso)
     *
     * @param value
     * @param max
     * @return
     */
    private int clamp(int value, int max) {
        return Math.max(0, Math.min(max, value));
    }

    private boolean collisione(int x, int y) {
        coordinate cord = new coordinate(x,y);
        return (COORDINATES_FIXED_BLOCKS.contains(cord) || RANDOM_BLOCKS.contains(cord));
    }
}
