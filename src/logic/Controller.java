package logic;

import data.Database;
import ui.ConsoleUI;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class Controller {
    private ArrayList<Member> members = new ArrayList<>();
    private boolean running;
    private final ConsoleUI UI = new ConsoleUI();
    private final Database db = new Database(); // god idé: interface impl så vi kan lave en stub
    private final Scanner sc = new Scanner(System.in);

    public Controller() {
        this.running = true;
    }

    public void run() {
        try {

            db.memberList(members);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        UI.displayInputOptions();
        while (running) {
            select(sc.nextLine().toLowerCase());
        }
        try {
            db.saveMembers(members);

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private void select(String input) {
        switch (input) {
            case "1", "add member" -> inputAddMember();
            case "2", "show members" -> inputShowMember();
            case "3", "check rankings" -> inputCheckRankings();
            case "4", "check subscriptions" -> inputCheckSubscriptions();
            case "5", "exit" -> running = false;
        }

    }

    private void inputAddMember() {

        HashMap<String,String> memberInformation = UI.askForMemberInformation();
        Member member = null;
        try {
            member = new Member(
                memberInformation.get("firstname"),
                memberInformation.get("lastname"),
                new Date(),
                memberInformation.get(3),
                memberInformation.get(4),
                memberInformation.get(5));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // forkert indtastning - formodentlig 'age'
        } catch (IndexOutOfBoundsException e ) {
            // blev ikke givet nok information
            e.printStackTrace();
        }
            members.add(member);
        System.out.println("Input New Command");

    }

    private void inputShowMember() {
        /*
        String choice = sc.nextLine();
        switch (switch){
            case "1","Sort by name" ->
            case "2","Sort by date" ->
            case "3","Sort by ID" ->
        }
        */
            for (Member member : members) {
                System.out.println(member);
            }

           // db.displayDatabase();
    }

    private void inputCheckRankings() {
        System.out.println("TODO");
    }

    private void inputCheckSubscriptions() {
        System.out.println("TODO");
    }


}
