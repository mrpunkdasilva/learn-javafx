package prime.punkdomus.primebank.dao;

import prime.punkdomus.primebank.model.Student;
import prime.punkdomus.primebank.repository.StudentRepository;
import prime.punkdomus.primebank.util.ConnectionDB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO implements StudentRepository {

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

    @Override
    public List<Student> getAll() {
        List<Student> students = new ArrayList<>();

        try {
            ResultSet rs;
            String sql = "SELECT * FROM student";
            PreparedStatement ps = ConnectionDB.getInstance().prepareStatement(sql);

            rs = ps.executeQuery(sql);

            while (rs.next())  {
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
            System.out.println("ERRO "+e.getMessage());
        }
    }

    @Override
    public void delete(long id) {
        try {
            String sql = "DELETE FROM student WHERE id = ?";
            PreparedStatement ps = ConnectionDB.getInstance().prepareStatement(sql);

            ps.setLong(1, id);

            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR DELETING STUDENT" + e.getMessage());
        }
    }
}
