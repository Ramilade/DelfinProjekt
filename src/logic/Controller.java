package logic;

import data.Database;
import logic.comparators.BirthDayComparator;
import logic.comparators.IDComparator;
import logic.comparators.NameComparator;
import logic.competitor.CompetitionMember;
import logic.competitor.Discipline;
import ui.ConsoleUI;
import ui.MemberInformation;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Controller {
    private ArrayList<Member> members;
    private ArrayList<CompetitionMember> competitionMembers;
    private ArrayList<Discipline> disciplines;
    private boolean running;
    private final ConsoleUI UI;
    private final Database DB;
    private final Scanner input;
    private int currentHighestId;
    

    public Controller() {
        this.running = true;
        this.competitionMembers = new ArrayList<>();
        this.UI = new ConsoleUI();
        this.DB = new Database(); // god idé: interface impl så en stub kan bruges
        this.input = new Scanner(System.in);
    }

    public void run() {
        try {
            members = DB.loadMembers();
            //competitionMembers = DB.loadCompetitionMembers(); < skal laves *ser på Bjørn*
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
        UI.displayInputEditMemberChooseMember();
        int requestedID = Integer.parseInt(input.nextLine());
        for (Member member : members) {
            if (member.getUserID() == requestedID) {
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
            case "1", "firstname" -> {
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
            case "7", "member status" -> {
                UI.displayNowEditingChoiceDisplay(7);
                member.setStatus(input.nextLine());
            }
        }


    }

    private void inputAddMember() {
        HashMap<MemberInformation, String> memberInformationMap = UI.askForMemberInformation();
        currentHighestId++;
        if (memberInformationMap.containsValue("konkurrent")) {
            addCompetitionMember(memberInformationMap);
        } else {
            addMember(memberInformationMap);
        }
        UI.printUserHasBeenCreated();
    }



    public void addMember(HashMap<MemberInformation,String> information) {
        Member member = new Member(
            currentHighestId,
            information.get(MemberInformation.FIRST_NAME),
            information.get(MemberInformation.LAST_NAME),
            information.get(MemberInformation.BIRTHDAY),
            information.get(MemberInformation.ADDRESS),
            information.get(MemberInformation.EMAIL),
            information.get(MemberInformation.PHONE_NUMBER),
            information.get(MemberInformation.STATUS));
        members.add(member);
    }
    public void addCompetitionMember(HashMap<MemberInformation,String> information) {
        CompetitionMember member = new CompetitionMember(
            currentHighestId,
            information.get(MemberInformation.FIRST_NAME),
            information.get(MemberInformation.LAST_NAME),
            information.get(MemberInformation.BIRTHDAY),
            information.get(MemberInformation.ADDRESS),
            information.get(MemberInformation.EMAIL),
            information.get(MemberInformation.PHONE_NUMBER),
            information.get(MemberInformation.STATUS));
        competitionMembers.add(member);
    }
    private void inputShowMember() {
        ArrayList<Member> members = new ArrayList<>();
        members.addAll(this.members);
        members.addAll(competitionMembers);

        if (members.size() > 0) {

            UI.displayInputSortingMember();

            String choice = input.nextLine();
            Comparator comparator;
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
        Member found = null;
        UI.displayDeleteMember();
        int data = input.nextInt();
        for (Member member : members) {
            if (data == member.getUserID()) {
                found = member;
                UI.displayMemberDeleted();
            }
        }

        if (found == null){
            UI.displayInvalidMemberID();
        } else {
            members.remove(found);
        }


    }

    private void inputCheckRankings() {
        for (CompetitionMember competitionMember : competitionMembers) {

        }


    }

    private void inputCheckSubscriptions() {
        //Members skal have data for indmeldingsdato. Plus 1 år til hvert betaling.
        //Members skal også have passive

        String presentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        int subscriptions = 0;

        for (Member member : members) {

            //Skal bruge paidInTime true/false til at bestemme om at member har betalt gennem alle årene.
            //En For loop skal tjekke om at creationDate og currentDate har overskrævet hinanden, hvis ***nok ikke rigtig***
            //currentDate overskriver creationDate, og at paidInTime er True, skal næste paymentTime være ét år over currentDate sat sammen med CreationDate.

            subscriptions += member.getSubscription();

            String memberCreationDate = member.getCreationDate();

            UI.printSubscriptionDuePayment(member);

            String[] presentDateArray = presentDate.split("/");

            String[] memberCreationDateArray = memberCreationDate.split("/");


            int dayCreation = Integer.parseInt(memberCreationDateArray[0]);
            int monthCreation = Integer.parseInt(memberCreationDateArray[1]);
            int yearCreation = Integer.parseInt(memberCreationDateArray[2]);

            int dayPresent = Integer.parseInt(presentDateArray[0]);
            int monthPresent = Integer.parseInt(presentDateArray[1]);
            int yearPresent = Integer.parseInt(presentDateArray[2]);

            if (yearCreation < yearPresent) {

            } else if (monthCreation < monthPresent) {

            } else if (dayCreation < dayPresent) {

            } else {

            }
/*
            int presentYear = Integer.parseInt(presentDateArray[2]); //Plus 1 siden den springer et år over.
            int paymentYear = presentYear++;



            String paymentDate = paymentDateArray[0] + "-" + paymentDateArray[1] + "-" + paymentDateArray[2];


            int dayPayTime = Integer.parseInt(paymentDateArray[0]);
            int monthPayTime = Integer.parseInt(paymentDateArray[1]);
            int yearPayTime = Integer.parseInt(paymentDateArray[2]);

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
        */

            UI.totalSubscriptionNumber(subscriptions);
        }

    }
}
