package logic;

import java.util.Date;
import java.util.Random;

public class Member {

    private int userID;
    private String firstName;
    private String lastName;
    private int age;

    private Date birthday;

    private String address;
    private String email;
    private String mobile;

    private boolean active;
    private double subscription;

    public Member(String firstName, String lastName, Date birthday, String address, String email, String mobile) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.address = address;
        this.email = email;
        this.mobile = mobile;
        calculateSub();
    }
    public Member(int userID, String firstName, String lastName, Date birthday, String address, String email, String mobile) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.address = address;
        this.email = email;
        this.mobile = mobile;
        calculateSub();
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setSubscription(double subscription) {
        this.subscription = subscription;
    }

    public double getSubscription() {
        return subscription;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Member(String name, int age, boolean active) {
        this.age = age;
        this.active = active;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        calculateSub();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        calculateSub();
    }

    public void calculateSub() {
        double subCost = 500;
        if (active) {
            subCost = 1000;
            if (getAge() > 17) {
                subCost = 1600;
            }
            if (getAge() > 60) {
                subCost = subCost * 0.75;
            }
        }
        subscription = subCost;
    }


    @Override
    public String toString() {
        return userID + ": " + firstName + " " + lastName + ", birthday is " + birthday + " \n\t contact information: Email - " + email + " | Phonenumber - " + mobile;
    }


}
