package prime.punkdomus.primebank.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import prime.punkdomus.primebank.dao.StudentDAO;
import prime.punkdomus.primebank.model.Student;

import java.net.URL;
import java.util.List;
import java.util.Optional;
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
        toggleVisibilityButtons();
    }

    void toggleVisibilityButtons() {
        this.btn_editar.setVisible(!this.btn_editar.isVisible());
        this.btn_deletar.setVisible(!this.btn_deletar.isVisible());
    }

    @FXML
    public void fillFields(MouseEvent event) {
        student = (Student) tv_estudante.getSelectionModel().getSelectedItem();

        if (student != null) {
            btn_salvar.setVisible(false);
            btn_deletar.setVisible(true);
            btn_editar.setVisible(true);

            tf_nome.setText(student.getName());
            tf_idade.setText(String.valueOf(student.getAge()));
            rb_m.setSelected(student.getSex() == 'M');
            rb_f.setSelected(student.getSex() == 'F');
        }
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
            reloadTable();
        }
    }

    @FXML
    public void edit(MouseEvent event) {
        if (validator()) {
            student.setName(tf_nome.getText().toString());
            student.setAge(Integer.parseInt(tf_idade.getText()));
            student.setSex(rb_m.isSelected() ? 'M' : 'F');

            studentDAO.update(student, student.getId());

            clearFields();
            showSuccessMessage();

            btn_salvar.setVisible(true);
            btn_editar.setVisible(false);
            reloadTable();
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

    @FXML
void delete() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Confirmaçao da exclusao");
    alert.setHeaderText("Confirma a exclusao do estudante?");
    alert.setContentText("Deseja excluir o estudante?");
    alert.showAndWait();

    Optional<ButtonType> result = alert.showAndWait();

    if (result.get() == ButtonType.OK) {
        studentDAO.delete(student.getId());
        clearFields();
        reloadTable();
    }
}

    private void reloadTable() {
        students = studentDAO.getAll();
        observableStudents = FXCollections.observableArrayList(students);
        tv_estudante.setItems(observableStudents);
    }
}
