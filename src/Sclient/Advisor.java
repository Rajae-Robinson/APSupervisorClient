package Sclient;



public class Advisor {
    private String advisorID;
    private String firstName;
    private String lastName;

    public Advisor(String advisorID, String firstName, String lastName) {
        this.advisorID = advisorID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getAdvisorID() {
        return advisorID;
    }

    public void setAdvisorID(String advisorID) {
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

