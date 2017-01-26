package marathon.project0.campusrecruitmentsystem.model;

/**
 * Created by Ishaq Hassan on 1/26/2017.
 */

public class Company {

    private String name;
    private String email;
    private String uid;

    public Company() {

    }

    public Company(String name, String email, String uid) {
        this.name = name;
        this.email = email;
        this.uid = uid;
    }

    public Company(String name, String email) {
        this.name = name;
        this.email = email;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
