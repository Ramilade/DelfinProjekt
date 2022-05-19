package logic;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Member {

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private int userID;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private LocalDate creationDate;
    private boolean paid;

    private String address;
    private String email;
    private String phoneNumber;

    private boolean active;
    private double subscription;

    public Member(int userID, String firstName, String lastName,
                  String birthday, String address, String email,
                  String phoneNumber) {
        this.creationDate = LocalDate.now();
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        setBirthday(birthday);
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.subscription = calculateSub();
        this.paid = true;
    }

    public Member(int userID, String firstName, String lastName,
                  String birthday, String address, String email,
                  String phoneNumber, String creationDate) {
        new Member(userID,firstName,lastName,birthday,address,email,phoneNumber);
        setCreationDate(creationDate);
    }


    public String getCreationDate() {
        return creationDate.format(dtf);
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = LocalDate.parse(creationDate, dtf);
    }

    public String getAgeGroup() {
        if (getAge() < 18) {
            return "Junior";
        } else {
            return "Senior";
        }
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public String getBirthday() {
        return birthday.format(dtf);
    }

    public void setBirthday(String birthday) {
        this.birthday = LocalDate.parse(birthday,dtf);
    }

    public int getAge() {
        return Period.between(birthday,LocalDate.now()).getYears();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        calculateSub();
    }

    public double calculateSub() {
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
        return subCost;
    }

    public boolean hasPaid() {
        return paid;
    }

    public void setHasPaid(boolean hasPaid) {
        this.paid = hasPaid;
    }

    @Override
    public String toString() {
        return userID + ";"
            + firstName + ";"
            + lastName + ";"
            + getBirthday() + ";"
            + email + ";"
            + address + ";"
            + phoneNumber;
    }


}
