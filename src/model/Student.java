/**
 * Author: Rajae Robinson
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import factories.SessionFactoryBuilder;

@Entity
@Table(name = "students")
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studentID")
    private int studentID;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "contactNumber")
    private String contactNumber;

    public Student() {
        this.studentID = 2006677;
        this.firstName = "John";
        this.lastName = "Brown";
        this.email = "jbrown@email.com";
        this.contactNumber = "8765555555";
    }

    public Student(int studentID, String firstName, String lastName, String email, String contactNumber) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contactNumber = contactNumber;
    }

    public List<Student> readAll() throws HibernateException {
        List<Student> students = new ArrayList<>();
        Session session = null;
        try {
            session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            students = session.createQuery("from Student", Student.class).getResultList();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null && session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            throw e;
        }
        return students;
    }

    public Student findStudent(int studentID) {
        Session session = null;
        Student student = null;

        try {
            session = SessionFactoryBuilder.getSessionFactory().getCurrentSession();
            session.beginTransaction();

            student = session.get(Student.class, studentID);

            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return student;
    }

    public int getStudentID() {
        return studentID;
    }


    public void setStudentID(int studentID) {
        this.studentID = studentID;
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


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getContactNumber() {
        return contactNumber;
    }


    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }


    @Override
    public String toString() {
        return "Student [studentID=" + studentID + ", firstName=" + firstName + ", lastName=" + lastName + ", email="
                + email + ", contactNumber=" + contactNumber + "]";
    }
}









/*package model;

import java.io.Serializable;
import java.util.Date;

public class Student implements Serializable {
    private int studentID;
    private String firstName;
    private String lastName ;
    private String email;
    private String contactNumber;

    public Student(int studentID, String firstName, String lastName,String email,String contactNumber) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contactNumber = contactNumber;

    }

    public int getStudentID() {
        return studentID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNumber() {
        return contactNumber;
    }



}

*/

