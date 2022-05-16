package ui;

import logic.Member;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleUI {

    public void displayMember(Member member) {
        System.out.println(member);
    }

    public void displayInputOptions() {
        System.out.println("""
            1. add a member
            2. show members
            3. (TODO) check the rankings
            4. (TODO) check the subscriptions
            5. exit
            """);
    }



    public ArrayList<String> askForMemberInformation() {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> listOfMemberInformation = new ArrayList<>();

        System.out.println("(Step 1/6) \nPut in 'First name' of the member");
        listOfMemberInformation.add(sc.nextLine());

        System.out.println("(Step 2/6) \nPut in 'Last name' of the member");
        listOfMemberInformation.add(sc.nextLine());

        System.out.println("(Step 3/6) \nPut in 'Age' of the member");
        listOfMemberInformation.add(sc.nextLine());

        System.out.println("(Step 4/6) \nPut in 'Address' of the member");
        listOfMemberInformation.add(sc.nextLine());

        System.out.println("(Step 5/6) \nPut in 'Email' of the member");
        listOfMemberInformation.add(sc.nextLine());

        System.out.println("(Step 6/6) \nPut in 'Phonenumber' of the member");
        listOfMemberInformation.add(sc.nextLine());

        return listOfMemberInformation;
    }



}
