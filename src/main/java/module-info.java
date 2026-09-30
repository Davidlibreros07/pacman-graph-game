module org.icesi.implementacionjuegopacman {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.icesi.implementacionjuegopacman.Controllers to javafx.fxml;

    exports org.icesi.implementacionjuegopacman;
    exports org.icesi.implementacionjuegopacman.Controllers;
}
