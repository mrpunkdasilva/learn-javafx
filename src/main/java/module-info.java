module prime.punkdomus.primebank {
    requires javafx.controls;
    requires javafx.fxml;

    opens prime.punkdomus.primebank to javafx.fxml;
    opens prime.punkdomus.primebank.controller to javafx.fxml;
    exports prime.punkdomus.primebank;
}