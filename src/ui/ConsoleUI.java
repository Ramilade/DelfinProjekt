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

        System.out.println("Name of the member");
        listOfMemberInformation.add(sc.nextLine());
        System.out.println("age of the member");
        listOfMemberInformation.add(sc.nextLine());

        return listOfMemberInformation;
    }



}
