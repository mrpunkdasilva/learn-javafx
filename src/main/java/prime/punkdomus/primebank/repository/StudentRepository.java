package prime.punkdomus.primebank.repository;

import prime.punkdomus.primebank.model.Student;
import java.util.List;

public interface StudentRepository {
    public Student byID(long id);
    public List<Student> getAll();
    public void add(Student student);
    public void update(Student student, long id);
    public void delete(long id);
}
