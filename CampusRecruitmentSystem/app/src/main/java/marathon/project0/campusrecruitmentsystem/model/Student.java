package marathon.project0.campusrecruitmentsystem.model;

/**
 * Created by Ishaq Hassan on 1/26/2017.
 */

public class Student {

    private String name;
    private String studentId;
    private String email;
    private String dob;
    private int gender;
    private double marks;
    private String uid;

    public Student(){

    }

    public Student(String name, String studentId, String email, String dob, int gender, double marks, String uid) {
        this.name = name;
        this.studentId = studentId;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
        this.marks = marks;
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}


