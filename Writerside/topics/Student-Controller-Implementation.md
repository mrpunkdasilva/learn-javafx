# Implementação do Controller StudentController

O `StudentController` é responsável por gerenciar a interface do usuário para o cadastro, edição, exclusão e visualização de estudantes. Vamos analisar sua implementação em detalhes.

## Estrutura Básica

### Componentes FXML Injetados

```java
@FXML
private Button btn_deletar;
@FXML
private Button btn_editar;
@FXML
private Button btn_salvar;
@FXML
private RadioButton rb_f, rb_m;
@FXML
private TextField tf_nome, tf_idade;
@FXML
private TableView tv_estudante;
```

Estes componentes são injetados automaticamente pelo JavaFX quando o arquivo FXML é carregado.

### Atributos de Estado

```java
Student student = new Student();
StudentDAO studentDAO = new StudentDAO();
private List<Student> students;
private ObservableList<Student> observableStudents;
```

- `student`: Mantém o estado do estudante atual sendo editado/criado
- `studentDAO`: Responsável pela persistência dos dados
- `students`: Lista de todos os estudantes
- `observableStudents`: Lista observável usada pelo TableView

## Inicialização

```java
@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
    prepareTableList();
    toggleVisibilityButtons();
}
```

O método `initialize` é chamado automaticamente após o FXML ser carregado e configura:
- A TableView com suas colunas
- A visibilidade inicial dos botões

## Gerenciamento da TableView

### Preparação da Tabela

```java
void prepareTableList() {
    tc_id.setCellValueFactory(new PropertyValueFactory<>("id"));
    tc_nome.setCellValueFactory(new PropertyValueFactory<>("name"));
    tc_sexo.setCellValueFactory(new PropertyValueFactory<>("sex"));
    tc_idade.setCellValueFactory(new PropertyValueFactory<>("age"));

    students = studentDAO.getAll();
    observableStudents = FXCollections.observableArrayList(students);
    tv_estudante.setItems(observableStudents);
}
```

Este método:
1. Configura o mapeamento das colunas com as propriedades do modelo
2. Carrega os dados do banco
3. Cria uma lista observável
4. Associa a lista à TableView

### Atualização da Tabela

```java
private void reloadTable() {
    students = studentDAO.getAll();
    observableStudents = FXCollections.observableArrayList(students);
    tv_estudante.setItems(observableStudents);
}
```

Método utilitário para recarregar os dados da tabela após operações de CRUD.

## Operações CRUD

### Create (Criar)

```java
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
```

### Read (Ler)

```java
@FXML
public void fillFields(MouseEvent event) {
    student = (Student) tv_estudante.getSelectionModel().getSelectedItem();
    if (student != null) {
        // Preenche os campos com dados do estudante selecionado
    }
}
```

### Update (Atualizar)

```java
@FXML
public void edit(MouseEvent event) {
    if (validator()) {
        // Atualiza o objeto student com dados dos campos
        studentDAO.update(student, student.getId());
        // Limpa campos e atualiza interface
    }
}
```

### Delete (Excluir)

```java
@FXML
void delete() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    // Confirmação de exclusão
    if (result.get() == ButtonType.OK) {
        studentDAO.delete(student.getId());
        clearFields();
        reloadTable();
    }
}
```

## Validação de Dados

```java
public boolean validator() {
    StringBuffer message = new StringBuffer();
    
    // Validação do nome
    if (tf_nome.getText().isEmpty()) {
        message.append("Nome não pode ser vazio\n");
    }

    // Validação da idade
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

    // Exibe mensagens de erro se houver
    if (!message.isEmpty()) {
        showErrorAlert(message.toString());
        return false;
    }
    
    return true;
}
```

## Utilitários da Interface

### Limpeza de Campos

```java
private void clearFields() {
    tf_nome.clear();
    tf_idade.clear();
    rb_m.setSelected(true);
}
```

### Mensagens ao Usuário

```java
private void showSuccessMessage() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Sucesso");
    alert.setHeaderText(null);
    alert.setContentText("Estudante salvo com sucesso!");
    alert.showAndWait();
}
```

## Boas Práticas Implementadas

1. **Validação de Dados**
   - Validação completa antes de operações CRUD
   - Feedback claro ao usuário

2. **Gerenciamento de Estado**
   - Uso de objeto `student` para manter estado atual
   - Atualização consistente da interface

3. **Feedback ao Usuário**
   - Mensagens de sucesso e erro
   - Confirmação antes de exclusão

4. **Organização do Código**
   - Métodos bem definidos e com responsabilidade única
   - Separação clara entre lógica de negócio e interface

## Pontos de Atenção

1. **Tratamento de Erros**
   - Implementar try-catch para operações de banco
   - Feedback adequado em caso de falhas

2. **Atualização da Interface**
   - Sempre atualizar a tabela após operações CRUD
   - Manter consistência entre modelo e view

3. **Validação**
   - Validar todos os campos antes de operações
   - Fornecer feedback claro sobre erros