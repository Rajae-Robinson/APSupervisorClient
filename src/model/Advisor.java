/**
 * Author: Odane Walters
 */
package model;

import java.io.Serializable;

public class Advisor implements Serializable {
	private static final long serialVersionUID = 3755315976869196098L;
	
    private int advisorID;
    private String firstName;
    private String lastName;

    public Advisor(int advisorID, String firstName, String lastName) {
        this.advisorID = advisorID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getAdvisorID() {
        return advisorID;
    }

    public void setAdvisorID(int advisorID) {
        this.advisorID = advisorID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

