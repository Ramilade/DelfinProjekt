package logic;

import data.Database;
import logic.comparators.BirthDayComparator;
import logic.comparators.IDComparator;
import logic.comparators.NameComparator;
import logic.competitor.CompetitionMember;
import logic.competitor.Discipline;
import ui.ConsoleUI;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Controller {
    private ArrayList<Member> members;
    private ArrayList<CompetitionMember> Competition;
    private ArrayList<Discipline> disciplines;
    private boolean running;
    private final ConsoleUI UI;
    private final Database DB;
    private final Scanner input;
    private int currentHighestId;
    

    public Controller() {
        this.running = true;
        this.UI = new ConsoleUI();
        this.DB = new Database(); // god idé: interface impl så en stub kan bruges
        this.input = new Scanner(System.in);
    }

    public void run() {
        try {
            members = DB.loadMembers();
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

    public void viewSubscription(){
        System.out.println("");
    }


    private void select(String input) {
        switch (input) {
            case "1", "add member" -> inputAddMember();
            case "2", "edit member" -> inputEditMember();
            case "3", "show members" -> inputShowMember();
            case "4", "check rankings" -> inputCheckRankings();
            case "5", "check subscriptions" -> inputCheckSubscriptions();
            case "6", "delete member" -> deleteMember();
            case "7", "exit" -> running = false;
        }

    }
    private void inputEditMember() {
        UI.displayInputEditMemberChooseMember();
        int requestedID = Integer.parseInt(input.nextLine());
        for (Member member : members) {
            if (member.getUserID()==requestedID) {
                UI.nowEditing(member);
                System.out.println();
                editMember(member);
            }
        }

    }

    private void editMember(Member member) {
        UI.displayInputEditMember();
        String editOption = input.nextLine();
        switch (editOption) {
            case "1", "firstname" ->{
                UI.displayNowEditingChoiceDisplay(1);
                member.setFirstName(input.nextLine());
            }
            case "2", "lastname" -> {
                UI.displayNowEditingChoiceDisplay(2);
                member.setLastName(input.nextLine());
            }
            case "3", "birthday" -> {
                UI.displayNowEditingChoiceDisplay(3);
                member.setBirthday(input.nextLine());
            }
            case "4", "address" -> {
                UI.displayNowEditingChoiceDisplay(4);
                member.setAddress(input.nextLine());
            }
            case "5", "email" -> {
                UI.displayNowEditingChoiceDisplay(5);
                member.setEmail(input.nextLine());
            }
            case "6", "phone number" -> {
                UI.displayNowEditingChoiceDisplay(6);
                member.setPhoneNumber(input.nextLine());
            }
        }


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
                memberInformation.get("birthday"),
                memberInformation.get("address"),
                memberInformation.get("email"),
                memberInformation.get("phoneNumber"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        UI.printUserHasBeenCreated();
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
                default -> comparator = new IDComparator();
            }

            Collections.sort(members, comparator);

            for (Member member : members) {
                UI.printArray(member);
            }

        } else {
            UI.noMembersInList();
        }

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
        //Members skal have data for indmeldingsdato. Plus 1 år til hvert betaling.
        //Members skal også have passive

        String presentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        for (Member member : members) {

            UI.printSubscriptionDuePayment(member);

            String memberRest = member.getCreationDate().substring(0,6); //Erstat getBirthDay med getCreationDate
            String memberYear = member.getCreationDate().substring(6,10);//Erstat getBirthDay med getCreationDate

            int memberYearCal = Integer.parseInt(memberYear);
            memberYearCal++;
            memberYear = Integer.toString(memberYearCal);

            String[] presentTimeSplit = presentDate.split("/");

            String memberPaymentTime = memberRest + memberYear;

            String[] memberPaymentTimeArray = memberPaymentTime.split("/");

            int dayPresent = Integer.parseInt(presentTimeSplit[0]);
            int monthPresent = Integer.parseInt(presentTimeSplit[1]);
            int yearPresent = Integer.parseInt(presentTimeSplit[2]);

            int dayPayTime = Integer.parseInt(memberPaymentTimeArray[0]);
            int monthPayTime = Integer.parseInt(memberPaymentTimeArray[1]);
            int yearPayTime = Integer.parseInt(memberPaymentTimeArray[2]);

            if (yearPresent > yearPayTime){
                member.setHasPaid(false);
            } else if (monthPresent > monthPayTime){
                member.setHasPaid(false);
            } else if (dayPresent > dayPayTime){
                member.setHasPaid(false);
            }

            if (member.hasPaid()){
                UI.userPaidInTime(true);
            } else {
                UI.userPaidInTime(false);
            }
        }
    }

}
