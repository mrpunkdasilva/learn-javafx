# Create first Controller

Controllers são componentes fundamentais em aplicações JavaFX, responsáveis por gerenciar a lógica de interação entre a interface do usuário (FXML) e o modelo de dados.

## Estrutura Básica de um Controller

Um controller JavaFX típico segue esta estrutura:

```java
public class StudentController implements Initializable {
    // Injeção de componentes FXML
    @FXML
    private Button btnSalvar;
    
    @FXML
    private TextField tfNome;
    
    // Método de inicialização
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Código de inicialização
    }
    
    // Handlers de eventos
    @FXML
    public void handleSalvar(ActionEvent event) {
        // Lógica do botão salvar
    }
}
```

## Componentes Principais

### 1. Anotações FXML

- `@FXML`: Usada para injetar elementos definidos no FXML no controller
- Deve ser aplicada em:
  - Campos que representam elementos da UI
  - Métodos que tratam eventos
  - Método initialize (opcional)

### 2. Implementação do Initializable

```java
public class SeuController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Código executado quando a tela é carregada
    }
}
```

### 3. Exemplo Prático: StudentController

Nosso `StudentController` atual demonstra vários conceitos importantes:

```java
public class StudentController implements Initializable {
    // Campos de UI
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
    private TextField tf_nome;
    @FXML
    private TextField tf_idade;
    @FXML
    private TableView tv_estudante;

    // Método de inicialização
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inicialização dos componentes
    }

    // Exemplo de handler de evento
    @FXML
    public void showName(ActionEvent actionEvent) {
        String name = tf_nome.getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hello World");
        alert.setHeaderText("Hello World");
        alert.setContentText(name);
        alert.showAndWait();
    }
}
```

## Conectando Controller ao FXML

Para conectar um controller ao arquivo FXML, você precisa:

1. Definir o controller no FXML:
```xml
<AnchorPane xmlns="http://javafx.com/javafx"
            xmlns:fx="http://javafx.com/fxml"
            fx:controller="prime.punkdomus.primebank.controller.StudentController">
```

2. Referenciar os elementos no controller usando `@FXML`
3. Definir os handlers de eventos no FXML:
```xml
<Button onAction="#showName" text="Mostrar Nome"/>
```

## Boas Práticas

1. **Nomenclatura**
   - Use nomes descritivos para métodos handlers
   - Prefixe campos de UI com identificadores (ex: btn_, tf_, lbl_)

2. **Organização**
   - Agrupe campos relacionados
   - Mantenha handlers próximos aos campos que eles manipulam
   - Separe lógica de negócios da lógica de UI

3. **Inicialização**
   - Use o método `initialize()` para configuração inicial
   - Configure listeners e bindings neste método
   - Inicialize coleções e outros dados necessários

4. **Tratamento de Eventos**
   - Mantenha handlers concisos
   - Delegue lógica complexa para classes de serviço
   - Trate exceções adequadamente

## Dicas de Desenvolvimento

1. **Debug**
   - Use `System.out.println()` ou logging para debug
   - Verifique se os elementos FXML estão sendo injetados corretamente
   - Confirme se os handlers estão sendo chamados

2. **Testes**
   - Teste diferentes cenários de interação
   - Verifique estados inválidos
   - Teste navegação entre telas

3. **Manutenção**
   - Mantenha o controller focado em uma responsabilidade
   - Documente comportamentos complexos
   - Refatore quando necessário

## Próximos Passos

- Implementar validação de campos
- Adicionar persistência de dados
- Criar telas adicionais
- Implementar navegação entre telas

