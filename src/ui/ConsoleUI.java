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

        System.out.println("Step 1/6 - Put in 'First name' of the member 1/6");
        listOfMemberInformation.add(sc.nextLine());

        System.out.println("Step 2/6 - Put in 'Last name' of the member");
        listOfMemberInformation.add(sc.nextLine());

        System.out.println("Step 3/6 - Put in 'Age' of the member");
        listOfMemberInformation.add(sc.nextLine());

        System.out.println("Step 4/6 - Put in 'Address' of the member");
        listOfMemberInformation.add(sc.nextLine());

        System.out.println("Step 5/6 - Put in 'Email' of the member");
        listOfMemberInformation.add(sc.nextLine());

        System.out.println("Step 6/6 - Put in 'Phonenumber' of the member");
        listOfMemberInformation.add(sc.nextLine());

        return listOfMemberInformation;
    }



}
