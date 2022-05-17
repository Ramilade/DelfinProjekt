package logic;

import data.Database;
import logic.comparators.BirthDayComparator;
//import logic.comparators.IDComparator;
import logic.comparators.IDComparator;
import logic.comparators.NameComparator;
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
    private static int currentHighestId;
    

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
            members = new ArrayList<Member>();
            UI.fileNotFoundErrorMessage();
        }
        
        determineID();
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

    private void determineID() {
        currentHighestId = 1000;
        for (Member member : members) {
            if (currentHighestId < member.getUserID()) {
                currentHighestId = member.getUserID();
            }
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
            case "7", "delete member" -> deleteMember();
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
        currentHighestId++;
        try {
            member = new Member(
                currentHighestId,
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

        if (members.size() > 0) {

            UI.displayInputSortingMember();

            String choice = input.nextLine();
            Comparator comparator = null;
            switch (choice) {
                case "1", "Sort by name" -> comparator = new NameComparator();
                case "2", "Sort by date" -> comparator = new BirthDayComparator();
                case "3", "Sort by ID" -> comparator = new IDComparator();
            }

            Collections.sort(members, comparator);

            for (Member member : members) {
                UI.printArray(member);
            }
        } else {
            UI.noMembersInList();
        }

        // db.displayDatabase();
    }
    public void deleteMember(){
        int data = input.nextInt();
        for (Member member : members ) {
            if (data == member.getUserID()){
                members.remove(member);
            }
        }
    }

    private void inputCheckRankings() {
        System.out.println("TODO");
    }

    private void inputCheckSubscriptions() {
        System.out.println("TODO");
    }


}
