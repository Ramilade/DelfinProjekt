package logic;

import data.Database;
import logic.comparators.BirthDayComparator;
import logic.comparators.IDComparator;
import logic.comparators.NameComparator;
import logic.competitor.Competition;
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
    private ArrayList<Competition> competitions;
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
            competitionMembers = DB.loadCompetetiveMembers();
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
        try {
            DB.saveCompetetiveMembers(competitionMembers);
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
        System.out.println("enter competitive/member");
        String option = input.nextLine();
        switch (option){
            case "1","competitive" -> {
                UI.displayInputEditMemberChooseMember();
                int requestedID = Integer.parseInt(input.nextLine());
                for (CompetitionMember competitionMember : competitionMembers) {
                    if (competitionMember.getUserID() == requestedID) {
                        UI.nowEditing(competitionMember);
                        System.out.println();
                        editMember(competitionMember,competitionMember);
                    }
                }

            }
            case "2","member" -> {
                UI.displayInputEditMemberChooseMember();
                int requestedID = Integer.parseInt(input.nextLine());
                for (Member member : members) {
                    if (member.getUserID() == requestedID) {
                        UI.nowEditing(member);
                        System.out.println();
                        editMember(member, null);
                    }
                }
            }

    }

    }

    private void editMember(Member member, CompetitionMember competitionMember) {
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
            case "8", "add competition" -> {
                UI.displayNowEditingChoiceDisplay(8);
                competitionMember.addNewComp();
            }case "9", "edit competitions" -> {
                UI.displayNowEditingChoiceDisplay(9);
                competitionMember.addNewComp();
            }case "10", "add discipline" -> {
                UI.displayNowEditingChoiceDisplay(10);
                competitionMember.addNewDisci();
            }case "11", "edit discipline" -> {
                UI.displayNowEditingChoiceDisplay(11);
                competitionMember.addNewComp();
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


    public void addMember(HashMap<MemberInformation, String> information) {
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

    public void addCompetitionMember(HashMap<MemberInformation, String> information) {
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
        int data = Integer.parseInt(input.nextLine());
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

    public void inputCheckSubscriptions() {
        UI.printInputCaseCheckSubscription();
        int editOption = input.nextInt();
        switch (editOption) {
            case 1 -> inputCheckSubscriptionsView();
            case 2 -> inputCheckSubscriptionsChangeMember();
        }
    }

    public void inputCheckSubscriptionsChangeMember() {
        UI.printSubscriptionCaseChangeSub();
        int requestedID = input.nextInt();
        boolean memberFound = false;
        for (Member member : members) {
            if (member.getUserID() == requestedID) {
                memberFound = true;
                UI.printSubscriptionCaseChosenID(member);
                int newStatus = input.nextInt();
                switch (newStatus) {
                    case 1 -> member.setHasPaid(false);
                    case 2 -> member.setHasPaid(true);
                    default -> UI.notValidChoice();
                }
            }
        }
        if (!memberFound) {
            UI.printCantFindMember();
    }
        input.nextLine(); //Fixes scannerBug
}


    public void inputCheckSubscriptionsView() {

        //Members skal have data for indmeldingsdato. Plus 1 år til hvert betaling.
        //Members skal også have passive

        String presentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        int subscriptions = 0;

        for (Member member : members) {

            subscriptions += member.getSubscription();

            String memberPayDate = member.getDatePaid();

            UI.printSubscriptionDuePayment(member);

            String[] presentDateArray = presentDate.split("/");

            String[] memberPayDateArray = memberPayDate.split("/");

            int dayPay = Integer.parseInt(memberPayDateArray[0]);
            int monthPay = Integer.parseInt(memberPayDateArray[1]);
            int yearPay = Integer.parseInt(memberPayDateArray[2]);

            int dayPresent = Integer.parseInt(presentDateArray[0]);
            int monthPresent = Integer.parseInt(presentDateArray[1]);
            int yearPresent = Integer.parseInt(presentDateArray[2]);


            if (member.hasPaid()) {
                if (yearPay < yearPresent || (yearPay == yearPresent && monthPay < monthPresent) || (yearPay == yearPresent && monthPay == monthPresent && dayPay < dayPresent) || (yearPay == yearPresent && monthPay == monthPresent && dayPay == dayPresent)) {
                    yearPay++;
                    String newDatePaid = memberPayDateArray[0] + "/" + memberPayDateArray[1] + "/" + yearPay;
                    member.setDatePaid(newDatePaid);
                }
                UI.printDateOfPay(yearPay, memberPayDateArray[1], memberPayDateArray[0]);
                UI.userPaidInTime(true);
            } else {
                UI.printDateOfPay(yearPay, memberPayDateArray[1], memberPayDateArray[0]);
                if (yearPay < yearPresent || (yearPay == yearPresent && monthPay < monthPresent) || (yearPay == yearPresent && monthPay == monthPresent && dayPay < dayPresent) || (yearPay == yearPresent && monthPay == monthPresent && dayPay == dayPresent)){
                    UI.userPaidInTime(false);
                } else {
                    UI.userPaidInTime(true);
                }
            }
        }
        UI.totalSubscriptionNumber(subscriptions);
        input.nextLine(); //Fixes scannerBug
    }
}
