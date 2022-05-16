package logic;

import java.util.Date;
import java.util.Random;

public class Member {

    private String userID;
    private String firstName;
    private String lastName;
    private int age;

    private Date birthday;

    private String address;
    private String email;
    private String mobile;


    private boolean active;
    private double subscription;

    public Member(String firstName, String lastName, int age, String address, String email, String mobile) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.address = address;
        this.email = email;
        this.mobile = mobile;
        calculateSub();
        generateID();
    }

    public Member(String firstName, String lastName, Date birthday, String address, String email, String mobile) {

        this.birthday = birthday;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.mobile = mobile;
        calculateSub();
        generateID();
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setSubscription(double subscription) {
        this.subscription = subscription;
    }

    public String getUserID() {
        return userID;
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
        return "Member: " + firstName + " " + lastName + ", age is " + age + " \n\t contact information: Email - " + email + " | Phonenumber - " + mobile;
    }

    public void generateID(){
    String firstFour = "";
    if (getFirstName().length()>3) {
      firstFour = getFirstName().substring(0, 4);
    } else if (getFirstName().length()<3){
      firstFour = getFirstName().substring(0,2)+"00";
    } else if (getFirstName().length()<4){
      firstFour = getFirstName().substring(0,3)+"0";
    }
    Random roll = new Random();
    String ID = firstFour+roll.nextInt(1000,9999);
    userID = ID;
  }
}
