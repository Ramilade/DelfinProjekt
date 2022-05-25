package logic;

import data.Database;
import logic.comparators.BirthDayComparator;
import logic.comparators.IDComparator;
import logic.comparators.NameComparator;
import logic.comparators.ResultComparator;
import logic.competitor.Competition;
import logic.competitor.CompetitionMember;
import logic.competitor.Discipline;
import logic.competitor.RankingGroup;
import ui.ConsoleUI;
import ui.MemberInformation;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
            case "1", "add member" -> {
                try {
                    inputAddMember();

                } catch (DateTimeParseException e) {
                    UI.displayWrongDateFormat(e.getParsedString());
                }

            }
            case "2", "edit member" -> inputEditMember();
            case "3", "show members" -> inputShowMember();
            case "4", "show competitions" -> inputShowCompetitions();
            case "5", "show disciplines" -> inputShowDisciplines();
            case "6", "check rankings" -> inputCheckRankings();
            case "7", "check subscriptions" -> inputCheckSubscriptions();
            case "8", "delete member" -> deleteMember();
            case "9", "exit" -> running = false;
        }

    }
    private void inputEditMember() {
        UI.displayInputEditMember2();
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
            case "1", "Firstname" -> {
                UI.displayNowEditingChoiceDisplay(1);
                member.setFirstName(input.nextLine());
            }
            case "2", "Lastname" -> {
                UI.displayNowEditingChoiceDisplay(2);
                member.setLastName(input.nextLine());
            }
            case "3", "Birthday" -> {
                UI.displayNowEditingChoiceDisplay(3);
                member.setBirthday(input.nextLine());
            }
            case "4", "Address" -> {
                UI.displayNowEditingChoiceDisplay(4);
                member.setAddress(input.nextLine());
            }
            case "5", "Email" -> {
                UI.displayNowEditingChoiceDisplay(5);
                member.setEmail(input.nextLine());
            }
            case "6", "Phone number" -> {
                UI.displayNowEditingChoiceDisplay(6);
                member.setPhoneNumber(input.nextLine());
            }
            case "7", "Member status" -> {
                UI.displayNowEditingChoiceDisplay(7);
                member.setStatus(input.nextLine());
            }
            case "8", "Add competition result" -> {
                UI.displayNowEditingChoiceDisplay(8);
                competitionMember.addNewComp();
            }case "9", "Edit competitions" -> {
                UI.displayNowEditingChoiceDisplay(9);
                competitionMember.addNewComp();
            }case "10", "Add discipline" -> {
                UI.displayNowEditingChoiceDisplay(10);
                competitionMember.addNewDisci();
            }case "11", "Edit discipline" -> {
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
        UI.printUserHasBeenCreated(member);
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
        UI.printUserHasBeenCreated(member);
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
    private void inputShowCompetitions() {
        if (competitionMembers.size() > 0){
            UI.enterID();
            int target = input.nextInt();
            String fix = input.nextLine();
            ArrayList<Competition> comp = new ArrayList<>();
            for (CompetitionMember competitionMember :competitionMembers ) {
                if(target == competitionMember.getUserID()) {
                        comp.addAll(competitionMember.getCompetitions());
                    for (Competition competition :comp ) {
                        UI.competitionsPrintArray(competition);

                    }
                }
            }
        } else {
            UI.printCantFindMember();
        }
    }
    private void inputShowDisciplines() {
        if (competitionMembers.size() > 0){
            UI.enterID();
            int target = input.nextInt();
            String fix = input.nextLine();
            ArrayList<Discipline> disci = new ArrayList<>();
            for (CompetitionMember competitionMember :competitionMembers ) {
                if(target == competitionMember.getUserID()) {
                    disci.addAll(competitionMember.getDisciplines());
                    for (Discipline discipline :disci ) {
                        UI.disciplinePrintArray(discipline);

                    }
                }
            }
        } else {
            UI.printCantFindMember();
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

    private HashMap<RankingGroup,ArrayList<CompetitionMember>> initializeRanking() {
        HashMap<RankingGroup,ArrayList<CompetitionMember>> rankings = new HashMap<>();
        for (RankingGroup rankingGroup : RankingGroup.values()) {
            rankings.put(rankingGroup,new ArrayList<>());

        }
        return rankings;

    }
    private void inputCheckRankings() {

        HashMap<RankingGroup,ArrayList<CompetitionMember>> rankings = initializeRanking();

        for (CompetitionMember competitionMember : competitionMembers) {
            if(competitionMember.getAge() < 18) {
                for (Discipline discipline : competitionMember.getDisciplines()) {
                    switch (discipline.getType()) {
                        case CRAWL -> rankings.get(RankingGroup.JUNIOR_CRAWL).add(competitionMember);
                        case BRYSTSWØMNING -> rankings.get(RankingGroup.JUNIOR_BRYSTSVØMNING).add(competitionMember);
                        case RYGCRAWL -> rankings.get(RankingGroup.JUNIOR_RYGCRAWL).add(competitionMember);
                        case BUTTERFLY -> rankings.get(RankingGroup.JUNIOR_BUTTERFLY).add(competitionMember);
                    }
                }

            } else {
                for (Discipline discipline : competitionMember.getDisciplines()) {
                    switch (discipline.getType()) {
                        case CRAWL -> rankings.get(RankingGroup.SENIOR_CRAWL).add(competitionMember);
                        case BRYSTSWØMNING -> rankings.get(RankingGroup.SENIOR_BRYSTSVØMNING).add(competitionMember);
                        case RYGCRAWL -> rankings.get(RankingGroup.SENIOR_RYGCRAWL).add(competitionMember);
                        case BUTTERFLY -> rankings.get(RankingGroup.SENIOR_BUTTERFLY).add(competitionMember);
                    }
                }
            }
        }

        for (Map.Entry<RankingGroup,ArrayList<CompetitionMember>> set : rankings.entrySet()) {
            Collections.sort(set.getValue(),new ResultComparator(set.getKey()));

        }

        UI.displayRankings(rankings);

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


    public void inputCheckSubscriptionsView(){

        ArrayList<Member> membersSubscription = new ArrayList<>();
        membersSubscription.addAll(this.members);
        membersSubscription.addAll(competitionMembers);

        String presentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        int subscriptions = 0;


        for (Member member : membersSubscription) {

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
                if (yearPay < yearPresent || (yearPay == yearPresent && monthPay < monthPresent) || (yearPay == yearPresent && monthPay == monthPresent && dayPay < dayPresent) || (yearPay == yearPresent && monthPay == monthPresent && dayPay == dayPresent)){
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