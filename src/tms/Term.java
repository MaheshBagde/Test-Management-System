package tms;

/**
 * This enumeration represents the Term in which the tests will be conducted
 * 
 * @author Mahesh Bagde
 */
public enum Term {
    FALL("Fall"),
    WINTER("Winter"),
    SPRING("Spring");

    /**
     * Stores a String representation of enum in proper case
     */
    private String termName;

    /**
     * A private constructor to initialize the String representation of enum in
     * proper case
     *
     * @param termName 
     */
    private Term(String termName) {
        this.termName = termName;
    } // end of constructor
    
    /**
     * Gets an ENUM value representing the term
     * @param termName the name of term
     * @return the Term
     */
    public static Term getTerm(String termName) {
        for (Term term : Term.values()) {
            if (term.termName.equalsIgnoreCase(termName)) {
                return term;
            } // end if statement
        } // end for loop
        return null;
    } // end of method getTerm

    /**
     * Returns a string representation of the termName field for display purpose
     *
     * @return String representation of enum in proper case
     */
    @Override
    public String toString() {
        return termName;
    } // end of method toString    

} // end of enumeration Term
