package logic;

import data.Database;
import ui.ConsoleUI;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.*;

public class Controller {
    private ArrayList<Member> members;
    private boolean running;
    private final ConsoleUI UI = new ConsoleUI();
    private final Database db = new Database(); // god idé: interface impl så vi kan lave en stub
    private final Scanner sc = new Scanner(System.in);

    public Controller() {
        this.running = true;
    }

    public void run() {
        try {
            members = db.loadMemberList();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (running) {
            UI.displayInputOptions();
            select(sc.nextLine().toLowerCase());
        }
        try {
            db.saveMembers(members);
        } catch (FileNotFoundException e) {
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

        HashMap<String, String> memberInformation = UI.askForMemberInformation();
        Member member = null;
        try {
            member = new Member(
                memberInformation.get("firstName"),
                memberInformation.get("lastName"),
                new Date(new java.text.SimpleDateFormat("dd/MM/yyyy").parse(memberInformation.get("birthday")).getTime()),
                memberInformation.get("address"),
                memberInformation.get("email"),
                memberInformation.get("phoneNumber"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        members.add(member);
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
            System.out.println(member.toString());
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
