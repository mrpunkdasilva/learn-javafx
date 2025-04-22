# Implementação do StudentDAO com Hibernate

Esta é uma implementação alternativa do `StudentDAO` utilizando Hibernate como framework ORM (Object-Relational Mapping).

## Configuração do Hibernate

### 1. Dependências Maven

```xml
<dependencies>
    <dependency>
        <groupId>org.hibernate</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>5.6.15.Final</version>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
    </dependency>
</dependencies>
```

### 2. Configuração do Hibernate (hibernate.cfg.xml)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <session-factory>
        <property name="hibernate.connection.driver_class">com.mysql.cj.jdbc.Driver</property>
        <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/primebank</property>
        <property name="hibernate.connection.username">root</property>
        <property name="hibernate.connection.password">root</property>
        <property name="hibernate.dialect">org.hibernate.dialect.MySQL8Dialect</property>
        <property name="show_sql">true</property>
        <property name="format_sql">true</property>
        <property name="hbm2ddl.auto">update</property>
        
        <mapping class="prime.punkdomus.primebank.model.Student"/>
    </session-factory>
</hibernate-configuration>
```

## Modelo Student com Anotações JPA

```java
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private Integer age;
    
    @Column(length = 1, nullable = false)
    private Character sex;
    
    // getters e setters
}
```

## Implementação do StudentDAO com Hibernate

```java
public class StudentDAO implements StudentRepository {
    private final SessionFactory sessionFactory;
    
    public StudentDAO() {
        this.sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .buildSessionFactory();
    }
    
    @Override
    public Student byID(long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Student.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar estudante por ID: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Student> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Student", Student.class).list();
        } catch (Exception e) {
            System.err.println("Erro ao listar estudantes: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    @Override
    public void add(Student student) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.save(student);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        } catch (Exception e) {
            System.err.println("Erro ao adicionar estudante: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    public void update(Student student, long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                student.setId(id);
                session.update(student);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        } catch (Exception e) {
            System.err.println("Erro ao atualizar estudante: " + e.getMessage());
        }
    }
    
    @Override
    public void delete(long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Student student = session.get(Student.class, id);
                if (student != null) {
                    session.delete(student);
                }
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw e;
            }
        } catch (Exception e) {
            System.err.println("Erro ao deletar estudante: " + e.getMessage());
        }
    }
}
```

## Vantagens do Hibernate

1. **Mapeamento Objeto-Relacional**
   - Elimina necessidade de SQL manual
   - Mapeamento automático entre objetos e tabelas
   - Reduz erros de SQL

2. **Gerenciamento de Transações**
   - Controle transacional automático
   - Suporte a rollback
   - Consistência de dados

3. **Cache**
   - Cache de primeiro nível automático
   - Possibilidade de cache de segundo nível
   - Melhor performance em consultas repetidas

4. **Portabilidade**
   - Independência de banco de dados
   - Mudança fácil entre diferentes SGBDs
   - SQL gerado automaticamente

## Exemplo de Uso

```java
StudentDAO dao = new StudentDAO();

// Criar novo estudante
Student newStudent = new Student();
newStudent.setName("Maria");
newStudent.setAge(20);
newStudent.setSex('F');
dao.add(newStudent);

// Buscar todos com paginação
Session session = sessionFactory.openSession();
List<Student> students = session.createQuery("from Student", Student.class)
    .setFirstResult(0)
    .setMaxResults(10)
    .list();

// Buscar por critérios
List<Student> adultStudents = session.createQuery(
    "from Student where age >= :minAge", Student.class)
    .setParameter("minAge", 18)
    .list();
```

## Considerações de Performance

1. **Sessões e Conexões**
   - Pool de conexões configurável
   - Gerenciamento automático de sessões
   - Otimização de recursos

2. **Lazy Loading**
   - Carregamento sob demanda
   - Evita sobrecarga de memória
   - Configurável por relacionamento

3. **Consultas Otimizadas**
   - HQL para consultas complexas
   - Criteria API para consultas dinâmicas
   - Cache de consultas

## Próximos Passos

1. Implementar cache de segundo nível
2. Configurar pool de conexões
3. Adicionar validações com Bean Validation
4. Implementar consultas específicas com Criteria API
5. Configurar logging do Hibernate
6. Adicionar testes de integração

## Comparação com JDBC

| Aspecto | Hibernate | JDBC Puro |
|---------|-----------|-----------|
| SQL | Gerado automaticamente | Manual |
| Mapeamento | Automático | Manual |
| Transações | Gerenciamento automático | Manual |
| Performance | Overhead inicial | Mais leve |
| Produtividade | Alta | Média |
| Curva de Aprendizado | Maior | Menor |