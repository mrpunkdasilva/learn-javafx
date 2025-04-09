package prime.punkdomus.primebank.model;

/**
 * Represents a student with an id, name, age, and sex.
 */
public class Student {

    private long id;
    private String name;
    private int age;
    private char sex;

    /**
     * Gets the student's ID.
     *
     * @return the student's ID.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the student's ID.
     *
     * @param id the student's ID.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the student's name.
     *
     * @return the student's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the student's name.
     *
     * @param name the student's name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the student's age.
     *
     * @return the student's age.
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets the student's age.
     *
     * @param age the student's age.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets the student's sex.
     *
     * @return the student's sex.
     */
    public char getSex() {
        return sex;
    }

    /**
     * Sets the student's sex.
     *
     * @param sex the student's sex.
     */
    public void setSex(char sex) {
        this.sex = sex;
    }
}