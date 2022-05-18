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
            2. Show members
            3. (TODO) View rankings
            4. (TODO) View subscriptions
            5. Save & exit
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
        System.out.println("Please choose a member to edit.");
    }


    public void displayInputEditMember(){
        System.out.println("""
                1. Change first name
                2. Change last name
                3. Change birthday (dd/mm/yyyy) format.
                4. Change address
                5. Change email
                6. Change phonenumber
                """);
    }
    public void nowEditing(String s){
        System.out.println("now editing" + s );
    }



    public HashMap<String,String> askForMemberInformation() {
        Scanner userInput = new Scanner(System.in);
        HashMap<String,String> listOfMemberInformation2 = new HashMap<>();

        System.out.println("(Step 1/6) \nPut in 'First name' of the member");
        listOfMemberInformation2.put("firstName",userInput.nextLine());

        System.out.println("(Step 2/6) \nPut in 'Last name' of the member");
        listOfMemberInformation2.put("lastName",userInput.nextLine());

        System.out.println("(Step 3/6) \nPut in 'Birthday'(dd/mm/yyyy) of the member");
        listOfMemberInformation2.put("birthday",userInput.nextLine());

        System.out.println("(Step 4/6) \nPut in 'Address' of the member");
        listOfMemberInformation2.put("address",userInput.nextLine());

        System.out.println("(Step 5/6) \nPut in 'Email' of the member");
        listOfMemberInformation2.put("email",userInput.nextLine());

        System.out.println("(Step 6/6) \nPut in 'Phonenumber' of the member");
        listOfMemberInformation2.put("phoneNumber",userInput.nextLine());

        return listOfMemberInformation2;
    }

    public void fileNotFoundErrorMessage(){
        System.out.println("ERROR: File could not be found! Initializing new save file.");
    }

    public void printArray(Member member){

        System.out.println(member);
    }


    public void noMembersInList() {
        System.out.println("No concurrent members in list!");
    }

    public void printUserHasBeenCreated(){
        System.out.println("User has been created!");
    }

}
