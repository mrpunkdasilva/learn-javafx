# Criando Classe para Connection MySQL

A conexão com banco de dados é uma parte fundamental de aplicações que necessitam persistir dados. Vamos criar uma classe responsável por gerenciar conexões com MySQL.

## Estrutura Básica

Nossa classe `ConnectionDB` utiliza o padrão Singleton para gerenciar conexões com o banco de dados:

```java
public class ConnectionDB {
    private static final String url = "jdbc:mysql://172.18.0.2:3306/crud_student_javafx";
    private static final String user = "root";
    private static final String password = "rootpassword";

    public static Connection getInstance() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
```

## Dependências Necessárias

Para usar o MySQL com Java, adicione a dependência no `pom.xml`:

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.28</version>
</dependency>
```

## Configuração da Conexão

### 1. String de Conexão
A string de conexão contém informações importantes:
- `jdbc:mysql://` - Protocolo e tipo de banco
- `172.18.0.2` - Endereço do servidor
- `3306` - Porta (padrão MySQL)
- `crud_student_javafx` - Nome do banco de dados

```java
private static final String url = "jdbc:mysql://172.18.0.2:3306/crud_student_javafx";
```

### 2. Credenciais
Armazene as credenciais de forma segura:
```java
private static final String user = "root";
private static final String password = "rootpassword";
```

> **Nota de Segurança**: Em ambiente de produção, considere usar variáveis de ambiente ou arquivos de configuração externos para armazenar credenciais.

## Métodos Principais

### 1. Obter Conexão

```java
public static Connection getInstance() {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, password);
    } catch (SQLException e) {
        throw new RuntimeException(e.getMessage());
    } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
    }
}
```

### 2. Fechar Conexão

```java
public static void close(Connection connection) {
    try {
        if (connection != null) {
            connection.close();
        }
    } catch (SQLException e) {
        throw new RuntimeException(e.getMessage());
    }
}
```

## Boas Práticas

1. **Tratamento de Exceções**
   - Sempre feche conexões em blocos `finally`
   - Use try-with-resources quando possível
   - Trate exceções específicas do SQL

2. **Gerenciamento de Recursos**
   - Feche conexões após o uso
   - Utilize pool de conexões em aplicações maiores
   - Monitore o número de conexões ativas

3. **Segurança**
   - Não exponha credenciais no código
   - Use prepared statements para evitar SQL injection
   - Implemente timeout de conexão

## Exemplo de Uso

```java
public class ExemploUso {
    public void executarOperacao() {
        try (Connection conn = ConnectionDB.getInstance()) {
            // Usar a conexão para operações no banco
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM students");
            ResultSet rs = stmt.executeQuery();
            // Processar resultados
        } catch (SQLException e) {
            // Tratar exceção
        }
    }
}
```

## Configuração do Banco de Dados

### 1. Script SQL para Criar o Banco

```sql
CREATE DATABASE crud_student_javafx;

USE crud_student_javafx;

CREATE TABLE students (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    sex CHAR(1) NOT NULL
);
```

### 2. Permissões Necessárias
```sql
GRANT ALL PRIVILEGES ON crud_student_javafx.* TO 'root'@'%';
FLUSH PRIVILEGES;
```

## Troubleshooting

1. **Conexão Recusada**
   - Verifique se o servidor MySQL está rodando
   - Confirme a porta e endereço IP
   - Verifique as permissões do usuário

2. **Driver não Encontrado**
   - Confirme se a dependência está no `pom.xml`
   - Verifique se o driver está no classpath
   - Confirme a versão correta do driver

3. **Problemas de Autenticação**
   - Verifique as credenciais
   - Confirme as permissões do usuário
   - Verifique o método de autenticação do MySQL

## Próximos Passos

1. Implementar pool de conexões
2. Adicionar logging de operações
3. Criar classes DAO para cada entidade
4. Implementar transações
5. Adicionar testes de integração

## Exercícios Práticos

1. Implemente um método para testar a conexão
2. Crie um arquivo de configuração para as credenciais
3. Adicione suporte a múltiplos bancos de dados
4. Implemente métricas de conexão
