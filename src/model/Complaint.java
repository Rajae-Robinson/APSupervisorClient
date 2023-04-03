package model;


import java.util.Date;

public class Complaint {
    private int complaintID;
    private String studentID;
    private String details;
    private String category;
    private Date responseDate;
    private int responderID;
    private String response;


    public Complaint(int complaintID, String studentID, String details) {
        this.complaintID = complaintID;
        this.studentID = studentID;
        this.details = details;
    }

    public Complaint(String category) {
        this.category= category;
    }

    public Complaint(int complaintID,String studentID, String details,Date responseDate, int responderID,String response ){
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

    public String getStudentID() {
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


