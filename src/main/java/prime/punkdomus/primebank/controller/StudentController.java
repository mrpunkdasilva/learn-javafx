package prime.punkdomus.primebank.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    @FXML
    private Button btn_deletar;

    @FXML
    private Button btn_editar;

    @FXML
    private Button btn_salvar;

    @FXML
    private RadioButton rb_f;

    @FXML
    private RadioButton rb_m;

    @FXML
    private ToggleGroup sexo;

    @FXML
    private TableColumn tc_id;

    @FXML
    private TableColumn tc_idade;

    @FXML
    private TableColumn tc_nome;

    @FXML
    private TableColumn tc_sexo;

    @FXML
    private TextField tf_idade;

    @FXML
    private TextField tf_nome;

    @FXML
    private TableView tv_estudante;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @FXML
    public void showName(ActionEvent actionEvent) {
        String name = tf_nome.getText().toString();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Hello World");
        alert.setHeaderText("Hello World");
        alert.setContentText(name);
        alert.showAndWait();
    }
}
