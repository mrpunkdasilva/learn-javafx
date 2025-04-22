# Implementação do StudentDAO com JPA

Esta implementação utiliza JPA (Java Persistence API) com EclipseLink como provider padrão, oferecendo uma abordagem padronizada para ORM em Java.

## Configuração do JPA

### 1. Dependências Maven

```xml
<dependencies>
    <!-- JPA API -->
    <dependency>
        <groupId>jakarta.persistence</groupId>
        <artifactId>jakarta.persistence-api</artifactId>
        <version>3.1.0</version>
    </dependency>
    
    <!-- EclipseLink Implementation -->
    <dependency>
        <groupId>org.eclipse.persistence</groupId>
        <artifactId>eclipselink</artifactId>
        <version>4.0.1</version>
    </dependency>
    
    <!-- MySQL Connector -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.33</version>
    </dependency>
</dependencies>
```

### 2. Configuração JPA (persistence.xml)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence version="3.0" xmlns="https://jakarta.ee/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="https://jakarta.ee/xml/ns/persistence 
             https://jakarta.ee/xml/ns/persistence/persistence_3_0.xsd">
    
    <persistence-unit name="PrimeBankPU" transaction-type="RESOURCE_LOCAL">
        <provider>org.eclipse.persistence.jpa.PersistenceProvider</provider>
        <class>prime.punkdomus.primebank.model.Student</class>
        
        <properties>
            <property name="jakarta.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>
            <property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/primebank"/>
            <property name="jakarta.persistence.jdbc.user" value="root"/>
            <property name="jakarta.persistence.jdbc.password" value="root"/>
            
            <property name="eclipselink.logging.level" value="FINE"/>
            <property name="eclipselink.ddl-generation" value="create-or-extend-tables"/>
            <property name="eclipselink.ddl-generation.output-mode" value="database"/>
        </properties>
    </persistence-unit>
</persistence>
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
    
    // Construtores, getters e setters
    
    // Métodos equals e hashCode baseados no ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Objects.equals(getId(), student.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
```

## Implementação do StudentDAO com JPA

```java
public class StudentDAO implements StudentRepository, AutoCloseable {
    private final EntityManagerFactory emf;
    private final EntityManager em;
    
    public StudentDAO() {
        this.emf = Persistence.createEntityManagerFactory("PrimeBankPU");
        this.em = emf.createEntityManager();
    }
    
    @Override
    public Student byID(long id) {
        return em.find(Student.class, id);
    }
    
    @Override
    public List<Student> getAll() {
        try {
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s", Student.class);
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao listar estudantes: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    @Override
    public void add(Student student) {
        try {
            em.getTransaction().begin();
            em.persist(student);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Erro ao adicionar estudante: " + e.getMessage());
            throw new RuntimeException("Erro ao adicionar estudante", e);
        }
    }
    
    @Override
    public void update(Student student, long id) {
        try {
            em.getTransaction().begin();
            Student existingStudent = em.find(Student.class, id);
            if (existingStudent != null) {
                student.setId(id);
                em.merge(student);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Erro ao atualizar estudante: " + e.getMessage());
            throw new RuntimeException("Erro ao atualizar estudante", e);
        }
    }
    
    @Override
    public void delete(long id) {
        try {
            em.getTransaction().begin();
            Student student = em.find(Student.class, id);
            if (student != null) {
                em.remove(student);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Erro ao deletar estudante: " + e.getMessage());
            throw new RuntimeException("Erro ao deletar estudante", e);
        }
    }
    
    @Override
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
```

## Exemplos de Uso Avançado

### 1. Consultas Dinâmicas com Criteria API

```java
public List<Student> findByAgeRange(int minAge, int maxAge) {
    CriteriaBuilder cb = em.getCriteriaBuilder();
    CriteriaQuery<Student> cq = cb.createQuery(Student.class);
    Root<Student> student = cq.from(Student.class);
    
    cq.where(
        cb.and(
            cb.greaterThanOrEqualTo(student.get("age"), minAge),
            cb.lessThanOrEqualTo(student.get("age"), maxAge)
        )
    );
    
    return em.createQuery(cq).getResultList();
}
```

### 2. Consultas Nomeadas

```java
@NamedQueries({
    @NamedQuery(
        name = "Student.findByName",
        query = "SELECT s FROM Student s WHERE LOWER(s.name) LIKE LOWER(:name)"
    )
})
public class Student {
    // ... resto da classe
}

// Uso:
public List<Student> findByName(String name) {
    return em.createNamedQuery("Student.findByName", Student.class)
        .setParameter("name", "%" + name + "%")
        .getResultList();
}
```

### 3. Paginação

```java
public List<Student> getPagedStudents(int page, int pageSize) {
    return em.createQuery("SELECT s FROM Student s", Student.class)
        .setFirstResult((page - 1) * pageSize)
        .setMaxResults(pageSize)
        .getResultList();
}
```

## Vantagens do JPA

1. **Padronização**
   - API padrão Java para ORM
   - Independência de implementação
   - Portabilidade entre providers

2. **Simplicidade**
   - API mais limpa que Hibernate puro
   - Configuração mais simples
   - Menos código boilerplate

3. **Flexibilidade**
   - Suporte a múltiplos providers
   - Fácil troca entre implementações
   - Integração com diferentes frameworks

## Boas Práticas

1. **Gerenciamento de Recursos**
   - Implementar AutoCloseable
   - Fechar EntityManager e Factory
   - Gerenciar transações adequadamente

2. **Tratamento de Exceções**
   - Rollback em caso de erro
   - Logging apropriado
   - Exceções específicas

3. **Performance**
   - Usar consultas tipadas
   - Implementar paginação
   - Otimizar consultas JPQL

## Próximos Passos

1. Implementar cache de segundo nível
2. Adicionar validações com Bean Validation
3. Configurar pool de conexões
4. Implementar auditoria com `@EntityListeners`
5. Adicionar suporte a operações em lote
6. Implementar testes de integração

## Comparação com Outras Abordagens

| Aspecto | JPA | Hibernate | JDBC |
|---------|-----|-----------|------|
| Padronização | Alta | Média | Baixa |
| Simplicidade | Alta | Média | Baixa |
| Performance | Boa | Boa | Excelente |
| Manutenção | Fácil | Média | Difícil |
| Portabilidade | Alta | Média | Baixa |