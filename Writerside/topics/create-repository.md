# Repository Pattern em JavaFX

O Repository Pattern é um padrão de projeto que abstrai a lógica de acesso a dados. Em nosso projeto, implementamos uma interface `StudentRepository` que define as operações fundamentais para manipulação de dados de estudantes.

## Nossa Implementação

### Interface StudentRepository

```java
public interface StudentRepository {
    public Student byID(long id);
    public List<Student> getAll();
    public void add(Student student);
    public void update(Student student, long id);
    public void delete(long id);
}
```

### Análise das Operações

1. **byID(long id)**
   - **Retorno**: `Student` - Retorna um objeto estudante ou null se não encontrado
   - **Parâmetro**: `id` - ID do estudante a ser buscado
   - **Propósito**: Recuperar um estudante específico do banco de dados

2. **getAll()**
   - **Retorno**: `List<Student>` - Lista contendo todos os estudantes
   - **Propósito**: Recuperar todos os estudantes cadastrados
   - **Comportamento**: Retorna uma lista vazia se não houver registros

3. **add(Student student)**
   - **Parâmetro**: `student` - Objeto estudante a ser persistido
   - **Propósito**: Inserir um novo estudante no banco de dados
   - **Observação**: O ID é gerado automaticamente pelo banco

4. **update(Student student, long id)**
   - **Parâmetros**: 
     - `student` - Objeto com os dados atualizados
     - `id` - Identificador do estudante a ser atualizado
   - **Propósito**: Atualizar os dados de um estudante existente

5. **delete(long id)**
   - **Parâmetro**: `id` - Identificador do estudante a ser removido
   - **Propósito**: Remover um estudante do banco de dados

