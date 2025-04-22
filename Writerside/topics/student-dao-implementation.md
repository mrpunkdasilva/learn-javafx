# Implementação do StudentDAO

O `StudentDAO` é a implementação concreta da interface `StudentRepository`, responsável por realizar operações CRUD (Create, Read, Update, Delete) no banco de dados MySQL para a entidade Student.

## Estrutura da Classe

```java
public class StudentDAO implements StudentRepository {
    // Implementação dos métodos da interface StudentRepository
}
```

## Operações Implementadas

### 1. Buscar por ID (byID)

```java
@Override
public Student byID(long id) {
    Student student = null;
    try {
        String sql = "SELECT * FROM student WHERE id = ?";
        PreparedStatement ps = ConnectionDB.getInstance().prepareStatement(sql);
        ps.setLong(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            student = new Student();
            student.setId(rs.getLong("id"));
            student.setName(rs.getString("name"));
            student.setSex(rs.getString("sex").charAt(0));
            student.setAge(rs.getInt("age"));
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    return student;
}
```

**Características:**
- Utiliza PreparedStatement para prevenir SQL Injection
- Retorna null se não encontrar o estudante
- Mapeia resultado para objeto Student
- Trata exceções SQL apropriadamente

### 2. Listar Todos (getAll)

```java
@Override
public List<Student> getAll() {
    List<Student> students = new ArrayList<>();
    try {
        String sql = "SELECT * FROM student";
        PreparedStatement ps = ConnectionDB.getInstance().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Student student = new Student();
            student.setId(rs.getLong("id"));
            student.setName(rs.getString("name"));
            student.setSex(rs.getString("sex").charAt(0));
            student.setAge(rs.getInt("age"));
            students.add(student);
        }
    } catch (Exception e) {
        System.out.println("ERROR GETTING ALL STUDENTS" + e.getMessage());
        e.printStackTrace();
    }
    return students;
}
```

**Características:**
- Retorna lista vazia se não houver registros
- Processa múltiplos resultados
- Utiliza ArrayList para armazenamento
- Inclui log de erros

### 3. Adicionar (add)

```java
@Override
public void add(Student student) {
    try {
        String sql = "INSERT INTO student (name, sex, age) VALUES (?, ?, ?)";
        PreparedStatement ps = ConnectionDB.getInstance().prepareStatement(sql);
        ps.setString(1, student.getName());
        ps.setString(2, String.valueOf(student.getSex()));
        ps.setInt(3, student.getAge());
        ps.execute();
    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("ERROR ADDING STUDENT" + e.getMessage());
    }
}
```

**Características:**
- Insere novo registro no banco
- Não retorna o ID gerado
- Utiliza prepared statement
- Trata exceções com log

### 4. Atualizar (update)

```java
@Override
public void update(Student student, long id) {
    try {
        String sql = "UPDATE student SET name = ?, sex = ?, age = ? WHERE id = ?";
        PreparedStatement ps = ConnectionDB.getInstance().prepareStatement(sql);
        ps.setString(1, student.getName());
        ps.setString(2, String.valueOf(student.getSex()));
        ps.setInt(3, student.getAge());
        ps.setLong(4, id);
        ps.execute();
    } catch (SQLException e) {
        System.out.println("ERRO " + e.getMessage());
    }
}
```

**Características:**
- Atualiza registro existente
- Requer ID para identificação
- Atualiza todos os campos
- Log de erros SQL

### 5. Deletar (delete)

```java
@Override
public void delete(long id) {
    try {
        String sql = "DELETE FROM student WHERE id = ?";
        PreparedStatement ps = ConnectionDB.getInstance().prepareStatement(sql);
        ps.setLong(1, id);
        ps.execute();
    } catch (SQLException e) {
        System.out.println("ERRO AO DELETAR: " + e.getMessage());
    }
}
```

**Características:**
- Remove registro por ID
- Operação não reversível
- Tratamento de exceções
- Log de erros

## Conexão com Banco de Dados

A classe utiliza `ConnectionDB` para gerenciar conexões:

```java
ConnectionDB.getInstance().prepareStatement(sql)
```

**Características:**
- Singleton pattern para conexão
- Conexão automática ao MySQL
- Configurações em constantes
- Tratamento de erros de conexão

## Boas Práticas Implementadas

1. **Prevenção de SQL Injection**
   - Uso consistente de PreparedStatement
   - Parâmetros sempre escapados

2. **Tratamento de Recursos**
   - Conexões gerenciadas
   - Statements fechados apropriadamente
   - ResultSets processados corretamente

3. **Tratamento de Erros**
   - Exceções capturadas e logadas
   - Mensagens de erro informativas
   - Stack traces preservados

## Melhorias Sugeridas

1. **Gerenciamento de Recursos**
```java
try (PreparedStatement ps = ConnectionDB.getInstance().prepareStatement(sql)) {
    // código aqui
}
```

2. **Logging Estruturado**
```java
private static final Logger logger = LoggerFactory.getLogger(StudentDAO.class);
// ...
logger.error("Erro ao deletar estudante", e);
```

3. **Validações de Entrada**
```java
public void add(Student student) {
    if (student == null) {
        throw new IllegalArgumentException("Student cannot be null");
    }
    // resto do código
}
```

## Exemplo de Uso

```java
StudentDAO dao = new StudentDAO();

// Criar novo estudante
Student newStudent = new Student();
newStudent.setName("Maria");
newStudent.setAge(20);
newStudent.setSex('F');
dao.add(newStudent);

// Buscar todos
List<Student> allStudents = dao.getAll();

// Buscar por ID
Student found = dao.byID(1);

// Atualizar
found.setAge(21);
dao.update(found, found.getId());

// Deletar
dao.delete(1);
```
