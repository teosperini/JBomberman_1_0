package JBomberman.View;


import JBomberman.JBomberMan;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {

    private static final GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    private static final int width = gd.getDisplayMode().getWidth();
    private static final int height = gd.getDisplayMode().getHeight();

    public static void changeScene(String fxml) throws IOException {
        Parent root = FXMLLoader.load(View.class.getResource(fxml));
        //Scene scene = new Scene(root, width, height);            //creo una nuova scena con il file fxml che ho preso
        //Scene scene = new Scene(root, 600, 440);
        Scene scene = new Scene(root);
        scene.getRoot().requestFocus();
        Stage stage = JBomberMan.getStage();                       //utilizzo lo stesso stage
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void update(Observable o, Object arg) {
        //dato lo stato del model, aggiorno la view
    }
}


/*
Sì, puoi modificare il file FXML che stai utilizzando per la schermata di gioco durante le
modifiche temporanee, ad esempio per aggiungere una schermata di pausa.

Modificare il file FXML può essere una soluzione semplice e veloce se hai
bisogno di apportare modifiche temporanee all'interfaccia grafica senza
dover creare un file FXML separato per la schermata di pausa.

Ecco un esempio di come puoi gestire la schermata di pausa utilizzando lo stesso
file FXML della schermata di gioco:

1. Nel tuo file FXML principale (che rappresenta la schermata di gioco), aggiungi gli
elementi grafici per la schermata di pausa, come ad esempio una finestra di dialogo o
un pannello che si sovrappone alla schermata di gioco.

2. Puoi rendere visibili o invisibili gli elementi della schermata di pausa in base
alle esigenze. Ad esempio, quando vuoi mostrare la schermata di pausa, imposti la
visibilità degli elementi corrispondenti a `true`, e quando vuoi nascondere la schermata
di pausa, imposti la visibilità a `false`.

3. Gestisci gli eventi e le azioni necessarie per la schermata di pausa all'interno
della classe View o del Controller. Ad esempio, puoi gestire il pulsante "Pausa" per
attivare la schermata di pausa e il pulsante "Continua" per tornare alla schermata di gioco.

4. Se necessario, puoi impostare uno stato specifico nel Model per indicare se il
gioco è in pausa o in esecuzione, in modo da gestire la logica del gioco di conseguenza.

Un approccio alternativo potrebbe essere quello di creare un file FXML separato
specifico per la schermata di pausa se hai bisogno di una struttura più complessa o se
la schermata di pausa ha un aspetto grafico completamente diverso da quello della schermata di gioco.

In definitiva, puoi scegliere l'approccio che meglio si adatta alle esigenze specifiche
del tuo gioco. Modificare temporaneamente il file FXML può essere una soluzione veloce
e conveniente per aggiungere funzionalità temporanee come la schermata di pausa.
 */