package prime.punkdomus.primebank.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import prime.punkdomus.primebank.dao.StudentDAO;
import prime.punkdomus.primebank.model.Student;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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


    Student student = new Student();
    StudentDAO studentDAO = new StudentDAO();
    private List<Student> students;
    private ObservableList<Student> observableStudents;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prepareTableList();
    }

    @FXML
    public void save(ActionEvent actionEvent) {
        if (validator()) {
            student.setName(tf_nome.getText().toString());
            student.setAge(Integer.parseInt(tf_idade.getText()));
            student.setSex(rb_m.isSelected() ? 'M' : 'F');

            studentDAO.add(student);

            clearFields();
            showSuccessMessage();
        }
    }

    void prepareTableList() {
        tc_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tc_nome.setCellValueFactory(new PropertyValueFactory<>("name"));
        tc_sexo.setCellValueFactory(new PropertyValueFactory<>("sex"));
        tc_idade.setCellValueFactory(new PropertyValueFactory<>("age"));

        students = studentDAO.getAll();
        observableStudents = FXCollections.observableArrayList(students);
        tv_estudante.setItems(observableStudents);
    }

    public boolean validator() {
        StringBuffer message = new StringBuffer();

        if (tf_nome.getText().isEmpty()) {
            message.append("Nome não pode ser vazio\n");
        }

        if (tf_idade.getText().isEmpty()) {
            message.append("Idade não pode ser vazia\n");
        } else {
            try {
                int age = Integer.parseInt(tf_idade.getText());
                if (age <= 0) {
                    message.append("Idade deve ser maior que zero\n");
                }
            } catch (NumberFormatException e) {
                message.append("Idade deve ser um número válido\n");
            }
        }

        if (!message.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro de Validação");
            alert.setHeaderText(null);
            alert.setContentText(message.toString());
            alert.showAndWait();
            return false;
        }
        
        return true;
    }

    private void clearFields() {
        tf_nome.clear();
        tf_idade.clear();
        rb_m.setSelected(true);
    }

    private void showSuccessMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText(null);
        alert.setContentText("Estudante salvo com sucesso!");
        alert.showAndWait();
    }

}
