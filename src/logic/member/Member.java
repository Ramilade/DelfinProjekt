package logic.member;

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
    private LocalDate datePaid;
    private boolean paid;

    private String address;
    private String email;
    private String phoneNumber;

    private boolean active;
    private double subscription;


    public Member(int userID, String firstName, String lastName,
                  String birthday, String address, String email,
                  String phoneNumber,String status, String creationDate, String datePaid, String paid) {
        this(userID,firstName,lastName,birthday,address,email,phoneNumber,status);
        setCreationDate(creationDate);

        this.datePaid = LocalDate.parse(datePaid,dtf);
        this.paid = Boolean.parseBoolean(paid);
    }

    public Member(int userID, String firstName, String lastName,
                  String birthday, String address, String email,
                  String phoneNumber, String status) {

        this.userID = userID;
        this.creationDate = LocalDate.now();
        this.firstName = firstName;
        this.lastName = lastName;
        setBirthday(birthday);
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        setStatus(status);
        this.subscription = 0;
        this.paid = true;
        this.datePaid = LocalDate.now();

    }

    public void setStatus(String status) {
        if (status.equals("true") || status.equals("aktiv")){
            active = true;
        }
    }


    public String getDatePaid() {
        return datePaid.format(dtf);
    }

    public void setDatePaid(String datePaid) {
        this.datePaid = LocalDate.parse(datePaid,dtf);
    }

    public String getCreationDate() {
        return creationDate.format(dtf);
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = LocalDate.parse(creationDate, dtf);
    }

    public int getUserID() {
        return userID;
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

    public String getActive () {
        return String.valueOf(isActive());
    }

    public boolean isActive() {
        return active;
    }

    public boolean getHasPaidNextYear() {
        return paid;
    }

    public void setHasPaidNextYear(boolean hasPaid) {
        this.paid = hasPaid;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return "MemberID: " + userID + ";"
            + this.getClass().getSimpleName() + ";"
            + "First Name: " + firstName + ";"
            + "Last Name: " + lastName + ";"
            + "Birthday: " + getBirthday() + ";"
            + "E-mail: " + email + ";"
            + "Address: " + address + ";"
            + "Phone: " + phoneNumber;
    }


}
