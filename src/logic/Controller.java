package logic;

import data.Database;
import ui.ConsoleUI;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.*;

public class Controller {
    private ArrayList<Member> members;
    private boolean running;
    private final ConsoleUI UI;
    private final Database DB;
    private final Scanner input;

    public Controller() {
        this.running = true;
        this.UI = new ConsoleUI();
        this.DB = new Database(); // god idé: interface impl så en stub kan bruges
        this.input = new Scanner(System.in);
    }

    public void run() {
        try {
            members = DB.loadMemberList();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (running) {
            UI.displayInputOptions();
            select(input.nextLine().toLowerCase());
        }
        try {
            DB.saveMembers(members);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void select(String input) {
        switch (input) {
            case "1", "add member" -> inputAddMember();
          //  case "6", "edit member" -> inputEditMember();
            case "2", "show members" -> inputShowMember();
            case "3", "check rankings" -> inputCheckRankings();
            case "4", "check subscriptions" -> inputCheckSubscriptions();
            case "5", "exit" -> running = false;
        }

    }
/*
    private void inputEditMember() {
        String requestedID = input.nextLine();
        for (Member member : members) {
            if (member.getUserID().equals(requestedID)) {
                editMember(member);
            }
        }

    }
* */

    private void editMember(Member member) {
        /*
        String editOption = input.nextLine();
        switch (editOption) {
            case "firstname" -> member.setFirstName();
            case "lastname" -> return;
            case "birthday" -> return;
            case "address" -> return;
            case "email" -> return;
            case "phonenumber" -> return;
        }
        * */


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
