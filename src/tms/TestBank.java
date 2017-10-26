package tms;

import java.util.ArrayList;

/**
 * This is the driver class that instantiates and calls methods on Course object
 *
 * @author Mahesh Bagde
 */
public class TestBank {

    /**
     * Stores a static reference to an object of this class
     */
    private static final TestBank testBank = new TestBank();

    /**
     * Represents a collection of Course objects
     */
    private ArrayList<Course> courses;

    /**
     * A private constructor to ensure that only one object of this class exits in the program
     */
    private TestBank() {
        courses = new ArrayList<>();
    } // end of constructor

    /**
     * This method returns a static instance of this class to ensure a singleton design pattern
     *
     * @return a TestBank object
     */
    public static TestBank getInstance() {
        return testBank;
    } // end of method getInstance

    /**
     * Gets a collection of Course objects
     *
     * @return a collection of courses
     */
    public ArrayList<Course> getCourses() {
        return courses;
    } // end of method getCourses

    /**
     * Sets a collection of Course objects in the field courses
     *
     * @param courses an ArrayList representing a collection of Course objects
     */
    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    } // end of method setCourses

    /**
     * Adds a new Course to the collection of courses
     *
     * @param courseID a Primary Key assigned by the database
     * @param courseName a String value representing the name of a Course
     */
    public void addCourse(int courseID, String courseName) {
        courses.add(new Course(courseID, courseName));
    } // end of method addCourse

} // end of class TestBank
