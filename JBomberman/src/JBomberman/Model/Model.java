package JBomberman.Model;

import JBomberman.JBomberMan;
import JBomberman.Utils.SceneManager;
import JBomberman.View.View;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;

public class Model extends Observable {
    // Aggiungi qui i dati e i metodi specifici del modello

    public void doSomething() {
        // Esempio di un'azione che modifica il modello
        setChanged();
        notifyObservers("Qualcosa è cambiato nel modello!");
    }
    public void changeSc(String fxml) throws IOException {
        View.changeScene(fxml);
    }
}

/*
Assolutamente sì! Quando il tuo progetto crescerà e avrai più scene o
schermate diverse, il ruolo della classe View e del Model diventerà più
importante e utile.

Con l'aggiunta di nuovi file FXML per altre scene, la classe View potrà
essere utilizzata per gestire la logica di ogni schermata. Ad esempio,
potresti avere metodi nella classe View per caricare le diverse scene dai
file FXML, impostare i controller corrispondenti, gestire il passaggio tra
le diverse schermate, ecc.

Il Model, d'altra parte, può essere utilizzato per gestire i dati e la
logica di business dell'applicazione in modo indipendente dalla vista.
Potresti avere metodi nel Model per salvare o caricare i dati, eseguire
operazioni di calcolo o gestire lo stato dell'applicazione.

Quando hai più scene diverse, il Model potrebbe essere condiviso tra le
diverse viste per mantenere i dati coerenti in tutta l'applicazione. La
classe View potrebbe osservare il Model per essere notificata dei cambiamenti
e aggiornare di conseguenza le viste.

Inoltre, potresti avere più classi controller, ognuna associata a una
specifica schermata. Queste classi controller potrebbero collaborare con
il Model per ottenere o aggiornare i dati, e con la classe View per
aggiornare l'interfaccia grafica in base alle informazioni ricevute dal modello.

In definitiva, con l'aggiunta di altre scene, la classe View e il Model
diventeranno sempre più importanti per organizzare e gestire in modo
strutturato l'applicazione, garantendo una separazione chiara tra la logica
di business e l'interfaccia grafica.
 */