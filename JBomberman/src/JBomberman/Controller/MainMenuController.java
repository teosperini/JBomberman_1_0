package JBomberman.Controller;

import JBomberman.JBomberMan;
import JBomberman.Model.Model;
import JBomberman.Utils.FontManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

    //utilizzo lo stesso model, per non creare problemi
    private static final Model model = JBomberMan.getModel();

    @FXML
    public Text titoloText;

    @FXML
    public Label toggleLabel;

    @FXML
    public Region semiTransparentPane;

    //per fare il label toggle, devo creare un label e impostare che sul click deve eseguire l'azione

    @FXML
    private ToggleButton exitButton;

    @FXML
    private ToggleButton gameButton;

    @FXML
    private void initialize() {
        // Carica il font personalizzato con stili specifici
        //Font customFont = FontManager.loadCustomFont(24);

        // Imposta il font personalizzato sul Label
        //toggleLabel.setFont(FontManager.loadCustomFont(24));
        titoloText.setFont(FontManager.loadCustomFont(54));
    }

    @FXML
    private void gameButtonPressed() throws IOException{
        model.changeSc("/Game.fxml");
    }

    @FXML
    private void exitButtonPressed() throws IOException {
            // get a handle to the stage
            Stage stage = (Stage) exitButton.getScene().getWindow();
            // close the stage
            stage.close();
    }

    public void handleSemiTransparentClick(MouseEvent mouseEvent) {

    }
}
