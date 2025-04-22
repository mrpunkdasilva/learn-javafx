package prime.punkdomus.primebank.util;

import prime.punkdomus.primebank.dao.StudentDAO;
import prime.punkdomus.primebank.model.Student;

import java.sql.SQLOutput;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        Student student = new Student();

        student.setName("1");
        student.setSex('M');
        student.setAge(99);

        StudentDAO studentDAO = new StudentDAO();
        studentDAO.update(student, 2);

        List<Student> students = studentDAO.getAll();

        studentDAO.delete(2);
        for (Student s : students) {
            System.out.println(s.getName());
        }

        System.out.println(studentDAO.byID(3).getName());
    }
}
