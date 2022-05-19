package logic;

import data.Database;
import logic.comparators.BirthDayComparator;
import logic.comparators.IDComparator;
import logic.comparators.NameComparator;
import ui.ConsoleUI;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Controller {
    private ArrayList<Member> members;
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
        int requestedID = input.nextInt();
        for (Member member : members) {
            if (member.getUserID()==requestedID) {
                editMember(member);
            }
        }

    }

    private void editMember(Member member) {
        String editOption = input.nextLine();
        UI.nowEditing(editOption);
        switch (editOption) {
            case "firstname" -> member.setFirstName(input.nextLine());
            case "lastname" -> member.setLastName(input.nextLine());
            case "birthday" -> member.setBirthday(input.nextLine());
            case "address" -> member.setAddress(input.nextLine());
            case "email" -> member.setEmail(input.nextLine());
            case "phonenumber" -> member.setPhoneNumber(input.nextLine());
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
        //Skal bruge data fra Member (Senior el. Junior, Aktive el passive.) Løst, har calculateSub();

        String presentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        for (Member member : members) {

            //Member subscription tidspunkt til betaling skal minuses med nutiden, så differencen kan beregnes.

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
            } else if (dayPresent < dayPayTime){
                member.setHasPaid(false);
            }

            if (member.hasPaid()){
                System.out.print(memberPaymentTime);
                System.out.print(" User has paid in time.");
                System.out.println();
            } else {
                System.out.print(memberPaymentTime);
                System.out.print(" User has not paid in time.");
                System.out.println();
            }

        }
    }


}
