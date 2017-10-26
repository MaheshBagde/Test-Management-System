package tms;

/**
 * This enumeration represents the types of tests that will be conducted
 *
 * @author Mahesh Bagde
 */
public enum TestType {
    Q1("Q1"), Q2("Q2"), Q3("Q3"), Q4("Q4"), Q5("Q5"), Q6("Q6"), Q7("Q7"), Q8("Q8"), Q9("Q9"), Q10("Q10"),
    A1("A1"), A2("A2"), A3("A3"), A4("A4"), A5("A5"), A6("A6"), A7("A7"), A8("A8"), A9("A9"), A10("A10"),
    T1("T1"), T2("T2"), T3("T3"),
    E1("E1"), E2("E2");

    /**
     * Stores a String representation of enum
     */
    private String typeName;

    /**
     * A private constructor to initialize the String representation of enum
     *
     * @param typeName
     */
    private TestType(String typeName) {
        this.typeName = typeName;
    } // end of constructor

    /**
     * Gets an ENUM value representing the test type
     *
     * @param typeName the type of test
     * @return the TestType
     */
    public static TestType getTestType(String typeName) {
        for (TestType testType : TestType.values()) {
            if (testType.typeName.equalsIgnoreCase(typeName)) {
                return testType;
            } // end if statement
        } // end for loop
        return null;
    } // end of method getTestType

    /**
     * Returns a string representation of the typeName field for display purpose
     *
     * @return String representation of enum in proper case
     */
    @Override
    public String toString() {
        return typeName;
    } // end of method toString

} // end of enumeration TestType
