# Criar primeiro Controller

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

# Criando a Lógica do Controller

Após configurar a estrutura básica do controller e conectá-lo ao FXML, vamos implementar a lógica de negócios. Usaremos como exemplo o `StudentController`, que gerencia um formulário de cadastro de estudantes.

## 1. Estrutura do Controller

O controller deve implementar `Initializable` e conter as referências aos elementos FXML:

```java
public class StudentController implements Initializable {
    @FXML
    private TextField tf_nome;
    @FXML
    private TextField tf_idade;
    @FXML
    private RadioButton rb_m;
    @FXML
    private RadioButton rb_f;
    @FXML
    private Button btn_salvar;
    
    // Objetos de negócio
    private Student student = new Student();
    private StudentDAO studentDAO = new StudentDAO();
}
```

## 2. Implementando Validações

É importante validar os dados antes de processá-los. Crie um método de validação reutilizável:

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

    // Exibição de erros se houver
    if (!message.isEmpty()) {
        showErrorAlert("Erro de Validação", message.toString());
        return false;
    }
    
    return true;
}
```

## 3. Handlers de Eventos

Os métodos que respondem a ações do usuário devem ser anotados com `@FXML`:

```java
@FXML
public void save(ActionEvent actionEvent) {
    if (validator()) {
        // Preenche o objeto com dados do formulário
        student.setName(tf_nome.getText());
        student.setAge(Integer.parseInt(tf_idade.getText()));
        student.setSex(rb_m.isSelected() ? 'M' : 'F');

        // Persiste os dados
        studentDAO.add(student);
        
        // Limpa o formulário
        clearFields();
        
        // Feedback ao usuário
        showSuccessMessage();
    }
}
```

## 4. Métodos Auxiliares

Crie métodos auxiliares para manter o código organizado:

```java
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
```

## 5. Boas Práticas

1. **Separação de Responsabilidades**
   - O controller deve focar na interação entre UI e modelo
   - Lógica de negócios complexa deve ficar em classes separadas
   - Acesso a dados deve ser delegado aos DAOs

2. **Tratamento de Erros**
   - Sempre valide dados de entrada
   - Forneça feedback claro ao usuário
   - Use try-catch para operações que podem falhar

3. **Organização do Código**
   - Agrupe campos FXML relacionados
   - Mantenha métodos pequenos e focados
   - Use nomes descritivos para métodos e variáveis

## 6. Inicialização

O método `initialize` é chamado após o FXML ser carregado:

```java
@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
    // Configurações iniciais
    // Exemplo: configurar listeners, carregar dados iniciais
}
```

## Conclusão

Um bom controller JavaFX deve:
- Validar dados de entrada
- Fornecer feedback ao usuário
- Manter o código organizado e legível
- Seguir o princípio de responsabilidade única
- Delegar operações complexas para classes apropriadas

Este padrão de desenvolvimento ajuda a manter o código manutenível e escalável à medida que a aplicação cresce.

