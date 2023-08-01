module MyModule {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.desktop;
    requires javafx.media;

    opens JBomberman to javafx.fxml;

    exports JBomberman;
    exports JBomberman.Model;
    opens JBomberman.Model to javafx.fxml;
    exports JBomberman.Controller;
    opens JBomberman.Controller to javafx.fxml;
    exports JBomberman.Utils to javafx.graphics;
}