module prime.punkdomus.primebank {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens prime.punkdomus.primebank to javafx.fxml;
    opens prime.punkdomus.primebank.controller to javafx.fxml;
    opens prime.punkdomus.primebank.model to javafx.base;

    exports prime.punkdomus.primebank;
}