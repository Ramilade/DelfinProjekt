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
            3. check the rankings
            4. check the subscriptions
            5. exit
            """);
    }



    public ArrayList<String> askForMemberInformation() {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> listOfMemberInformation = new ArrayList<>();

        System.out.println("Put in 'First name' of the member 1/6");
        listOfMemberInformation.add(sc.nextLine());

        System.out.println("Put in 'Last name' of the member 2/6");
        listOfMemberInformation.add(sc.nextLine());

        System.out.println("Put in 'Age' of the member 3/6");
        listOfMemberInformation.add(sc.nextLine());

        System.out.println("Put in 'Address' of the member 4/6");
        listOfMemberInformation.add(sc.nextLine());

        System.out.println("Put in 'Email' of the member 5/6");
        listOfMemberInformation.add(sc.nextLine());

        System.out.println("Put in 'Phonenumber' of the member 6/6");
        listOfMemberInformation.add(sc.nextLine());

        return listOfMemberInformation;
    }



}
