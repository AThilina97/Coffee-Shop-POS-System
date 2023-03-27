package lk.ijse.dep10.app.model;

import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.ArrayList;

public class Employee implements Serializable {
    private  String id;
    private String name;
    private String address;
    private Gender gender;
    private LocalDate dob;
    private ArrayList<String> contactList =new ArrayList<>();
    private String designation;
    private String salary;
    private ImageView image;
    private Blob picture;

    public Employee() {
    }

    public Employee(String id, String name, String address, Gender gender,
                    LocalDate dob, ArrayList<String> contactList,
                    String designation, String salary, ImageView image, Blob picture) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.gender = gender;
        this.dob = dob;
        this.contactList = contactList;
        this.designation = designation;
        this.salary = salary;
        this.image = image;
        this.picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public ArrayList<String> getContactList() {
        return contactList;
    }

    public void setContactList(ArrayList<String> contactList) {
        this.contactList = contactList;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public Blob getPicture() {
        return picture;
    }

    public void setPicture(Blob picture) {
        this.picture = picture;
    }
}
