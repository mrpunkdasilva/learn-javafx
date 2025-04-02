package prime.punkdomus.primebank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    
    Stage window;
    
    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        // Chamar a tela
        Parent screen = FXMLLoader.load(getClass().getResource("screens/student.fxml"));

        // Definir a janela
        Scene scene = new Scene(screen);

        window.setScene(scene);
        window.show();
        window.setTitle("Prime Bank");
    }
}
