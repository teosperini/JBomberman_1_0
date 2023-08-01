package JBomberman.Controller;

import JBomberman.JBomberMan;
import JBomberman.Model.Model;
import JBomberman.Utils.BackgorundMusic;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameController {

    private static final Model model = JBomberMan.getModel();
    @FXML
    private AnchorPane pianoDiGioco;

    @FXML
    private ImageView bomberman;

    private final ArrayList<Double> blocchi = new ArrayList<>();

    private static final double SPOSTAMENTO = 40.0;
    private static final double NUM_BLOCCHI_CASUALI = 30    ;

    //creo questo array con tutte le coordinate di spawn possibili per i blocchi random
    //con una funzione randomica vado poi a pescare delle coordinate casuali che metterò negli array di X e Y sottostanti
    //l'importante è che non compaiano entro le coordinate 80 x 80
    private final ArrayList<Double> coordinate = new ArrayList<>();
    //in questi due array metterò le coordinate X e Y rispettivamente, grazie alle quali potrò controllare se il personaggio collide con esse
    private final ArrayList<coordinata> randomBlocks = new ArrayList<>();
    /**
     * initialize è un metodo che viene chiamato dopo il metodo start e dopo
     * essere stati inizializzati i campi, in modo tale da averli tutti disponibili
     */
    public void initialize() {

        // SOUNDTRACK
        BackgorundMusic bm = new BackgorundMusic();
        bm.initialize(JBomberMan.getStage());
        BackgorundMusic.playMusic();

        //blocchiRandomX.add(new coordinate(100d,24d));
        //System.out.println(blocchiRandomX.get(0).x);
        // GENERAZIONE BLOCCHI
        blocchi.addAll(List.of(40d, 120d, 200d, 280d, 360d, 440d, 520d));
        coordinate.addAll(List.of(80d,120d, 160d,200d,240d,280d,320d,360d,400d,440d,480d,520d,560d));
        randomGen();

        // POSIZIONAMENTO DEL PERSONAGGIO
        bomberman.setLayoutX(0);        // x = 0
        bomberman.setLayoutY(0);        // y = 0
        pianoDiGioco.requestFocus();    // richiede il focus
        pianoDiGioco.setFocusTraversable(false);     // attiva la possibilitò per il nodo (pianoDiGioco) di
        // essere "navigabile" quando l'utente utilizza scorciatoie da tastiera per spostarsi tra i nodi

        // REGISTRAZIONE KEY EVENT PER MOVIMENTO PERSONAGGIO
        pianoDiGioco.setOnKeyPressed(keyEvent -> {
            KeyCode keyCode = keyEvent.getCode();
            System.out.println("Key pressed: " + keyCode);

            switch (keyCode) {
                //case W:
                case UP:
                    moveCharacter(0, -SPOSTAMENTO);
                    break;
                //case S:
                case DOWN:
                    moveCharacter(0, SPOSTAMENTO);
                    break;
                //case A:
                case LEFT:
                    moveCharacter(-SPOSTAMENTO, 0);
                    break;
                //case D:
                case RIGHT:
                    moveCharacter(SPOSTAMENTO, 0);
                    break;
                case SPACE:
                    break;
            }
        });
    }

    private record coordinata(double x, double y) { }

    /**
     * questo metodo riempie le due liste contenenti rispettivamente le coordinate X e Y
     * dei blocchi casuali con valori randomici presi tra i valori possibili delle coordinate
     */
    private void randomGen() {
        int i = 0;
        while(i<NUM_BLOCCHI_CASUALI){
            Random random = new Random();
            Double coord = coordinate.get(random.nextInt(coordinate.size()));
            if(coord <= 560d && coord >= 80d){
                randomBlocks.add(new coordinata(coord,0d));
                i++;
            }
        }
        int j = 0;
        while(j < NUM_BLOCCHI_CASUALI){
            Random random = new Random();
            Double coord = coordinate.get(random.nextInt(coordinate.size()));
            if(coord <= 400d && coord >= 80d){
                randomBlocks.add(new coordinata(randomBlocks.get(j).x,coord));
                j++;
            }
        }
        randomBlocks.forEach(x -> aggiungiBlocchi(x.x(),x.y()));
    }

    private void aggiungiBlocchi(double x, double y) {
        ImageView blockView = new ImageView("C:\\Users\\matte\\Documents\\università\\Porgrammazione\\JBomberman_1_0\\JBomberman\\src\\resources\\637590568153490001.png");
        blockView.setLayoutX(x);
        blockView.setLayoutY(y);
        blockView.setFitHeight(40);
        blockView.setFitWidth(40);

        pianoDiGioco.getChildren().add(blockView);

        }

    /**
     * @param deltaX è il valore di quanto si deve spostare il personaggio sull'asse delle X
     * @param deltaY è il valore
     */
    private void moveCharacter(double deltaX, double deltaY) {
        // Calcola la nuova posizione del personaggio principale
        double newX = bomberman.getLayoutX() + deltaX;
        double newY = bomberman.getLayoutY() + deltaY;

        // Imposta i limiti della scena
        double sceneWidth = pianoDiGioco.getWidth();
        double sceneHeight = pianoDiGioco.getHeight();
        double characterWidth = bomberman.getFitWidth();
        double characterHeight = bomberman.getFitHeight();

        newX = clamp(newX, sceneWidth - characterWidth);
        newY = clamp(newY, sceneHeight - characterHeight);

        if (!collisione(newX, newY)) {
            bomberman.setLayoutX(newX);
            bomberman.setLayoutY(newY);
            if (newX == 400 && newY == 240) {
                try {
                    vittoria();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    private void vittoria() throws IOException {
        BackgorundMusic.stopMusic();
        /*
        BackgorundMusic.setVolume();
        BackgorundMusic.playBomb();
         */
        model.changeSc("/JBomberman/View/MainMenu.fxml");
    }

    /**
     * funzione di clamping, che limita il valore ad un massimo/minimo dato
     * il nuovo valore e il valore minimo (0 in questo caso)
     *
     * @param value
     * @param max
     * @return
     */

    private double clamp(double value, double max) {
        return Math.max(0, Math.min(max, value));
    }

    private boolean collisione(double x, double y) {
        coordinata cord = new coordinata(x,y);
        return (blocchi.contains(x) && blocchi.contains(y) || randomBlocks.contains(cord));
    }
}
