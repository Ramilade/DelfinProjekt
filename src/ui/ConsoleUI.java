package ui;

import logic.Member;

import java.util.HashMap;
import java.util.Scanner;

public class ConsoleUI {

    public void displayMember(Member member) {
        System.out.println(member);
    }

    public void displayInputOptions() {
        System.out.println("""
            1. Add a member
            2. Edit a member
            3. Show members
            4. (TODO) View rankings
            5. View subscriptions
            6. Delete a member
            7. Save & exit
            """);
    }

    public void displayInputSortingMember(){
        System.out.println("""
                Choose sorting method.
                
                1. Sort by name
                2. Sort by date of birth
                3. Sort by ID
                """);
    }
    public void displayInputEditMemberChooseMember(){
        System.out.println("Please input member ID to edit.");
    }

    public void displayDeleteMember(){
        System.out.println("Enter memberID you wish to delete");
    }

    public void displayMemberDeleted(){
        System.out.println("MemberID has been deleted");
    }

    public void displayInvalidMemberID(){
        System.out.println("You have entered an invalid memberID");
    }


    public void displayInputEditMember(){
        System.out.println("""
                1. Change first name
                2. Change last name
                3. Change birthday (dd/mm/yyyy) format.
                4. Change address
                5. Change email
                6. Change phone number
                7. Change member status
                """);
    }
    public void nowEditing(Member member){
        System.out.println("Now editing: " + member.getUserID());
    }

    public void displayNowEditingChoiceDisplay(int option){
        switch (option) {
            case 1 -> System.out.println("Now editing First Name");
            case 2 -> System.out.println("Now editing Last Name");
            case 3 -> System.out.println("Now editing Birthday");
            case 4 -> System.out.println("Now editing Address");
            case 5 -> System.out.println("Now editing Email");
            case 6 -> System.out.println("Now editing Phone number");
            case 7 -> System.out.println("Now editing Member status(aktiv/passiv)");

        }
    }



    public HashMap<MemberInformation,String> askForMemberInformation() {
        Scanner userInput = new Scanner(System.in);
        HashMap<MemberInformation,String> listOfMemberInformation = new HashMap<>();

        MemberInformation[] memberInformation = MemberInformation.values();

        for (int i = 0; i < memberInformation.length; i++) {
            String name;
            if (memberInformation[i].name().toLowerCase().contains("_")) {
                String[] split = memberInformation[i].name().toLowerCase().split("_");
                name = split[0] + " " + split[1];
            } else {
                name = memberInformation[i].name().toLowerCase();
            }
            System.out.printf("(Step %s/%s) Enter '%s'%s : ",i+1,memberInformation.length, name, memberInformation[i].getSpecificInformation());
            listOfMemberInformation.put(memberInformation[i],userInput.nextLine());
        }

        return listOfMemberInformation;
    }

    public void fileNotFoundErrorMessage(){
        System.out.println("ERROR: File could not be found! Initializing new save file.");
    }

    public void printArray(Member member){
        String[] memberInformation = member.toString().split(";");
        for (String info : memberInformation) {
            System.out.println(info);
        }
        System.out.println("---------------------");


    }


    public void noMembersInList() {
        System.out.println("No concurrent members in list!");
    }

    public void printUserHasBeenCreated(){
        System.out.println("User has been created!");
    }

    public void printSubscriptionDuePayment(Member member) {
        System.out.print("Member first name: " + member.getFirstName() + ", ID: " + member.getUserID() + " Created: " + member.getCreationDate() + " Subscription amount: " + member.getSubscription() + "kr.- is due: " + member.getDatePaid() + " ");
    }

    public void userPaidInTime(boolean paid) {
        if (paid){
            System.out.print("User has paid in time");
        } else {
            System.out.print("User has yet to pay in time!");
        }
    }

    public void totalSubscriptionNumber(int subscriptions) {
        System.out.println("Total amount of payment expected: " + subscriptions);
    }

    public void printDateOfPay(int day, int month, int year) {
        System.out.print(day + "/" + month + "/" + year);
    }

    public void printInputCaseCheckSubscription(){
        System.out.println("""
                1. View subscriptions
                2. Change a member's subscription status
                """);
    }

    public void printSubscriptionCaseChangeSub(){
        System.out.println("""
                Input the member's ID you want to change. (NOTE: Have they paid their subscription within this year?)
                """);
    }

    public void printSubscriptionCaseChosenID(Member member){
        System.out.println("Changing user: " + member.getFirstName() + " with ID: " + member.getUserID() + " with subscription date: " + member.getDatePaid() + "to ? (inactive or active)");
    }

    public void printCantFindMember() {
        System.out.println("Cannot find member with requested ID!");
    }

    public void notValidChoice() {
        System.out.println("Not a valid choice!");
    }
}
