package logic;

import data.Database;
import ui.ConsoleUI;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Controller {
    private boolean running;
    private final ConsoleUI UI = new ConsoleUI();
    private final Database db = new Database();
    private final Scanner sc = new Scanner(System.in);

    public Controller() {
        this.running = true;
    }

    public void run() {
        UI.displayInputOptions();
        while (running) {
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
        ArrayList<String> test = UI.askForMemberInformation();

        Member member = new Member(test.get(0),Integer.parseInt(test.get(1)));
        try {
            db.createMember(member);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        //System.out.println("TODO");
    }

    private void inputShowMember() {

        try {
            db.displayDatabase();
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
