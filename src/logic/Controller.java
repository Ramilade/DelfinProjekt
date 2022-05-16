package logic;

import data.Database;
import ui.ConsoleUI;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Controller {
    private boolean running;
    private final ConsoleUI UI = new ConsoleUI();
    private final Database db = new Database(); // god idé: interface impl så vi kan lave en stub
    private final Scanner sc = new Scanner(System.in);

    public Controller() {
        this.running = true;
    }

    public void run() {
        while (running) {
            UI.displayInputOptions();
            select(sc.nextLine().toLowerCase());
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

        ArrayList<String> memberInformation = UI.askForMemberInformation();
        Member member = null;
        try {
            member = new Member(
                memberInformation.get(0),
                memberInformation.get(1),
                Integer.parseInt(memberInformation.get(2)),
                memberInformation.get(3),
                memberInformation.get(4),
                memberInformation.get(5));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // forkert indtastning - formodentlig ved 'age'
        } catch (IndexOutOfBoundsException e ) {
            // blev ikke givet nok information
            e.printStackTrace();
        }

        try {
            db.createMember(member);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    private void inputShowMember() {

        try {
            for (Member member : db.memberList()) {
                System.out.println(member);
            }

           // db.displayDatabase();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void inputCheckRankings() {
        System.out.println("TODO");
    }

    private void inputCheckSubscriptions() {
        System.out.println("TODO");
    }


}
