/**
 * Author: Odane Walters
 */
package model;


import java.io.Serializable;
import java.util.Date;

public class Complaint implements Serializable {
	private static final long serialVersionUID = -8928497947145342486L;
    private int complaintID;
    private int studentID;
    private String details;
    private String category;
    private Date responseDate;
    private Integer responderID;
    private String response;


    public Complaint(int complaintID, int studentID, String details) {
        this.complaintID = complaintID;
        this.studentID = studentID;
        this.details = details;
    }

    public Complaint(String category) {
        this.category= category;
    }

    public Complaint(int complaintID, int studentID, String details,Date responseDate, int responderID,String response ){
        this.complaintID= complaintID;
        this.studentID = studentID;
        this.details = details;
        this.responseDate= responseDate;
        this.responderID = responderID;
        this.response = response;
    }


    public int getComplaintID() {
        return complaintID;
    }

    public int getStudentID() {
        return studentID;
    }

    public String getDetails() {
        return details;
    }

    public String getCategory() {
        return category;
    }

    public Date getResponseDate() {
        return responseDate;
    }

    public int getResponderID(){return responderID;}

    public String getResponse(){return response;}
}


