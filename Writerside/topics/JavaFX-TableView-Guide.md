# Trabalhando com TableView em JavaFX

## Configuração Básica da TableView

A TableView é um componente poderoso do JavaFX para exibir dados em formato tabular. Aqui está um guia completo de como configurá-la corretamente.

### 1. Definição no FXML

```xml
<TableView fx:id="tableView" layoutX="14.0" layoutY="388.0" prefHeight="200.0" prefWidth="200.0">
    <columns>
        <TableColumn fx:id="tc_id" text="ID" />
        <TableColumn fx:id="tc_nome" text="Nome" />
        <TableColumn fx:id="tc_sexo" text="Sexo" />
        <TableColumn fx:id="tc_idade" text="Idade" />
    </columns>
</TableView>
```

### 2. Declaração no Controller

```java
@FXML private TableView<Student> tableView;
@FXML private TableColumn<Student, Long> tc_id;
@FXML private TableColumn<Student, String> tc_nome;
@FXML private TableColumn<Student, Character> tc_sexo;
@FXML private TableColumn<Student, Integer> tc_idade;

private List<Student> students;
private ObservableList<Student> observableStudents;
```

### 3. Configuração das Colunas

```java
public void prepareTableList() {
    // Configurando as colunas
    tc_id.setCellValueFactory(new PropertyValueFactory<>("id"));
    tc_nome.setCellValueFactory(new PropertyValueFactory<>("name"));
    tc_sexo.setCellValueFactory(new PropertyValueFactory<>("sex"));
    tc_idade.setCellValueFactory(new PropertyValueFactory<>("age"));

    // Carregando dados
    students = studentDAO.getAll();
    observableStudents = FXCollections.observableArrayList(students);
    tableView.setItems(observableStudents);
}
```

## Pontos Importantes

### PropertyValueFactory

- Use `setCellValueFactory()` (não `setCellFactory()`)
- Os nomes das propriedades devem corresponder aos getters do modelo
- Exemplo: "name" corresponde a `getName()` no modelo

### Tipos de Dados

- Especifique os tipos genéricos corretos para as colunas
- Exemplo: `TableColumn<Student, Integer>` para idade
- Os tipos devem corresponder aos tipos dos atributos do modelo

### Atualização de Dados

```java
public void refreshTable() {
    observableStudents.clear();
    observableStudents.addAll(studentDAO.getAll());
}
```

### Formatação Personalizada

```java
tc_sexo.setCellFactory(column -> new TableCell<Student, Character>() {
    @Override
    protected void updateItem(Character item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
        } else {
            setText(item == 'M' ? "Masculino" : "Feminino");
        }
    }
});
```

## Entendendo PropertyValueFactory

O `PropertyValueFactory` é uma implementação de `Callback` que cria uma propriedade de valor para uma célula da TableView. Vamos entender em detalhes:

### Sintaxe Básica

```java
tc_nome.setCellValueFactory(new PropertyValueFactory<>("name"));
```

### Como Funciona

1. **Mapeamento de Propriedades**
   - O parâmetro passado para `PropertyValueFactory` ("name") deve corresponder ao nome do getter no modelo
   - Exemplo: "name" procura por `getName()` no modelo Student
   - Exemplo: "age" procura por `getAge()` no modelo Student

2. **Tipos Genéricos**
   ```java
   TableColumn<Student, String> tc_nome;
   //           ^       ^
   //           |       |
   //     Tipo da    Tipo do
   //     Entidade   Atributo
   ```
   - Primeiro tipo (Student): classe do modelo
   - Segundo tipo (String): tipo do atributo

### Exemplos Práticos

```java
// Para um atributo String
tc_nome.setCellValueFactory(new PropertyValueFactory<>("name"));
// Procura por getName() no modelo

// Para um atributo Integer
tc_idade.setCellValueFactory(new PropertyValueFactory<>("age"));
// Procura por getAge() no modelo

// Para um atributo Boolean
tc_ativo.setCellValueFactory(new PropertyValueFactory<>("active"));
// Procura por isActive() ou getActive() no modelo
```

### Alternativas ao PropertyValueFactory

Você também pode usar lambdas para mais flexibilidade:

```java
// Usando lambda
tc_nome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));

// Para cálculos ou formatações complexas
tc_nomeCompleto.setCellValueFactory(data -> {
    Student student = data.getValue();
    return new SimpleStringProperty(student.getFirstName() + " " + student.getLastName());
});
```

### Quando Usar Cada Abordagem

1. **PropertyValueFactory**
   - Mais simples e direto
   - Bom para mapeamentos simples de propriedades
   - Requer que o modelo siga o padrão JavaBean

2. **Lambda**
   - Mais flexível
   - Permite transformações de dados
   - Necessário para lógica personalizada
   - Melhor para debug

### Exemplo Completo com Diferentes Tipos

```java
public void configureColumns() {
    // String - nome do aluno
    tc_nome.setCellValueFactory(new PropertyValueFactory<>("name"));
    
    // Integer - idade
    tc_idade.setCellValueFactory(new PropertyValueFactory<>("age"));
    
    // Character com formatação personalizada
    tc_sexo.setCellValueFactory(new PropertyValueFactory<>("sex"));
    tc_sexo.setCellFactory(column -> new TableCell<Student, Character>() {
        @Override
        protected void updateItem(Character item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                setText(item == 'M' ? "Masculino" : "Feminino");
            }
        }
    });
    
    // Propriedade calculada usando lambda
    tc_maiorIdade.setCellValueFactory(data -> 
        new SimpleBooleanProperty(data.getValue().getAge() >= 18));
}
```

### Dicas e Boas Práticas

1. **Nomenclatura**
   - Use nomes consistentes entre o modelo e PropertyValueFactory
   - Mantenha o padrão JavaBean (get/set/is)

2. **Validação**
   - Verifique se os tipos correspondem corretamente
   - Trate valores nulos adequadamente

3. **Performance**
   - PropertyValueFactory usa reflexão
   - Para melhor performance em tabelas grandes, considere usar lambdas

4. **Debug**
   - Se a coluna não exibir dados, verifique:
     - Nome da propriedade no PropertyValueFactory
     - Tipos genéricos da coluna
     - Existência do getter correspondente no modelo

## Eventos e Seleção

### Seleção de Linha

```java
tableView.getSelectionModel().selectedItemProperty().addListener(
    (observable, oldValue, newValue) -> {
        if (newValue != null) {
            // Preencher formulário com dados selecionados
            tf_nome.setText(newValue.getName());
            tf_idade.setText(String.valueOf(newValue.getAge()));
            rb_m.setSelected(newValue.getSex() == 'M');
            rb_f.setSelected(newValue.getSex() == 'F');
        }
    }
);
```

### Duplo Clique

```java
tableView.setOnMouseClicked(event -> {
    if (event.getClickCount() == 2) {
        Student selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Ação para duplo clique
        }
    }
});
```

## Boas Práticas

1. **Inicialização**
   - Configure a tabela no método `initialize()`
   - Carregue os dados após a configuração das colunas

2. **Tratamento de Erros**
   - Verifique valores nulos
   - Implemente tratamento de exceções

3. **Desempenho**
   - Use `ObservableList` para atualizações dinâmicas
   - Considere paginação para grandes conjuntos de dados

4. **Responsividade**
   - Configure redimensionamento de colunas
   - Defina políticas de crescimento apropriadas

## Exemplo Completo

```java
public class StudentController implements Initializable {
    @FXML private TableView<Student> tableView;
    @FXML private TableColumn<Student, Long> tc_id;
    // ... outros campos

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prepareTableList();
        configureTableSelection();
    }

    private void prepareTableList() {
        // Configuração das colunas
        tc_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        // ... outras colunas

        // Carregamento inicial
        refreshTable();
    }

    private void configureTableSelection() {
        tableView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    populateForm(newValue);
                }
            }
        );
    }

    private void refreshTable() {
        try {
            List<Student> students = studentDAO.getAll();
            observableStudents = FXCollections.observableArrayList(students);
            tableView.setItems(observableStudents);
        } catch (Exception e) {
            showErrorAlert("Erro ao carregar dados: " + e.getMessage());
        }
    }
}
```

## Conclusão

Uma TableView bem configurada é essencial para uma boa experiência do usuário. Siga estas diretrizes para criar interfaces robustas e funcionais com JavaFX.