package tms;

/**
 * This class represents a Test Label
 *
 * @author Mahesh Bagde
 */
public class TestLabel {

    /**
     * Represents the Primary Key assigned by the database
     */
    private int testLabelID;

    /**
     * Represents the year
     */
    private int year;

    /**
     * Represents the Term such as Fall, Winter, Spring
     */
    private Term term;

    /**
     * Represents the TestType such as Q4, A9, T2, E1
     */
    private TestType testType;

    /**
     * Constructor that initializes the instance variables
     *
     * @param testLabelID a Primary Key assigned by the database
     * @param year an int value representing the year
     * @param term an Enum of the Term such as Fall, Winter, Spring
     * @param testType an Enum of the TestType such as Q4, A9, T2, E1
     */
    public TestLabel(int testLabelID, int year, Term term, TestType testType) {
        this.testLabelID = testLabelID;
        this.year = year;
        this.term = term;
        this.testType = testType;
    } // end of constructor

    /**
     * Returns the test label ID which is the primary key assigned by the database
     *
     * @return an int value representing the test label ID
     */
    public int getTestLabelID() {
        return testLabelID;
    } // end of method getTestLabelID

    /**
     * Gets an int value representing the year
     *
     * @return the year
     */
    public int getYear() {
        return year;
    } // end of method getYear

    /**
     * Sets the value of field year
     *
     * @param year the year
     */
    public void setYear(int year) {
        this.year = year;
    } // end of method setYear

    /**
     * Gets an ENUM value representing the term
     *
     * @return the term
     */
    public Term getTerm() {
        return term;
    } // end of method getTerm

    /**
     * Sets the value of field term
     *
     * @param term the term
     */
    public void setTerm(Term term) {
        this.term = term;
    } // end of method setTerm

    /**
     * Gets an ENUM value representing the test type
     *
     * @return the test type
     */
    public TestType getTestType() {
        return testType;
    } // end of method getTestType

    /**
     * Sets the value of field testType
     *
     * @param testType the test type
     */
    public void setTestType(TestType testType) {
        this.testType = testType;
    } // end of method setTestType

    /**
     * Returns a string representation of the TestType object
     *
     * @return String value showing the year, term and type of test
     */
    @Override
    public String toString() {
        return year + " " + term + " " + testType;
    } // end of method toString

} // end of class TestLabel
