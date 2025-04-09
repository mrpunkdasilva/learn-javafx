# Primeiro Model

Models são classes que representam os dados e a lógica de negócios da sua aplicação JavaFX. Eles são a camada de dados no padrão MVC (Model-View-Controller).

## Estrutura Básica de um Model

Vamos analisar nosso primeiro model, a classe `Student`:

```java
public class Student {
    private long id;
    private String name;
    private int age;
    private char sex;

    // Getters e Setters
}
```

### Anatomia do Model

1. **Atributos Privados**
   - Use `private` para encapsulamento
   - Escolha tipos de dados apropriados
   - Nomeie de forma clara e descritiva

2. **Getters e Setters**
   - Fornecem acesso controlado aos atributos
   - Permitem validação de dados
   - Mantêm o encapsulamento

## Boas Práticas

### 1. Validação de Dados

```java
public void setAge(int age) {
    if (age < 0) {
        throw new IllegalArgumentException("Idade não pode ser negativa");
    }
    this.age = age;
}

public void setSex(char sex) {
    if (sex != 'M' && sex != 'F') {
        throw new IllegalArgumentException("Sexo deve ser 'M' ou 'F'");
    }
    this.sex = sex;
}
```

### 2. Construtores

```java
public class Student {
    // Construtor padrão
    public Student() {
    }

    // Construtor com parâmetros
    public Student(String name, int age, char sex) {
        this.name = name;
        setAge(age);
        setSex(sex);
    }
}
```

### 3. Métodos de Utilidade

```java
public boolean isAdult() {
    return age >= 18;
}

@Override
public String toString() {
    return "Student{" +
           "id=" + id +
           ", name='" + name + '\'' +
           ", age=" + age +
           ", sex=" + sex +
           '}';
}
```

## Integração com JavaFX

### 1. Observable Properties
Para melhor integração com JavaFX, podemos usar Properties:

```java
public class Student {
    private final StringProperty name = new SimpleStringProperty();
    private final IntegerProperty age = new SimpleIntegerProperty();
    
    // Getters e Setters para Properties
    public StringProperty nameProperty() {
        return name;
    }
    
    public String getName() {
        return name.get();
    }
    
    public void setName(String value) {
        name.set(value);
    }
}
```

### 2. Binding com a Interface

No controller:
```java
@FXML
private TextField tfNome;

public void initialize() {
    Student student = new Student();
    tfNome.textProperty().bindBidirectional(student.nameProperty());
}
```

## Persistência de Dados

### 1. Exemplo de Lista em Memória

```java
public class StudentRepository {
    private static final List<Student> students = new ArrayList<>();
    
    public static void add(Student student) {
        students.add(student);
    }
    
    public static List<Student> getAll() {
        return new ArrayList<>(students);
    }
}
```

### 2. Observable Collections

```java
public class StudentRepository {
    private static final ObservableList<Student> students = 
        FXCollections.observableArrayList();
    
    public static ObservableList<Student> getStudents() {
        return students;
    }
}
```

## Boas Práticas de Modelagem

1. **Imutabilidade Quando Possível**
   - Use `final` para atributos que não devem mudar
   - Considere criar versões imutáveis dos objetos

2. **Validação Robusta**
   - Valide dados em setters
   - Lance exceções apropriadas
   - Documente restrições

3. **Documentação**
   - Use JavaDoc para documentar a classe e métodos importantes
   - Explique regras de negócio complexas
   - Documente exceções

4. **Testes**
   - Crie testes unitários
   - Teste casos de borda
   - Verifique validações

## Próximos Passos

1. Implementar mais validações no model `Student`
2. Adicionar suporte a persistência em banco de dados
3. Criar models relacionados (ex: `Course`, `Grade`)
4. Implementar padrões de projeto relevantes

## Exercícios Práticos

1. Adicione mais campos ao model `Student` (ex: email, matrícula)
2. Implemente validações para os novos campos
3. Crie um método para calcular a idade baseado na data de nascimento
4. Implemente `equals()` e `hashCode()`
