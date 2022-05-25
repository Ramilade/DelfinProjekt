package logic;

import data.Database;
import logic.comparators.BirthDayComparator;
import logic.comparators.IDComparator;
import logic.comparators.NameComparator;
import logic.comparators.ResultComparator;
import logic.competitor.*;
import logic.exceptions.MemberStatusMismatchException;
import logic.exceptions.MemberTypeMismatchException;
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
        this.DB = new Database();
        this.input = new Scanner(System.in);
    }

    public void run() {
        try {
            members = DB.loadMembers();
            competitionMembers = DB.loadCompetetiveMembers();
        } catch (FileNotFoundException e) {
            members = new ArrayList<>();
            UI.fileNotFoundErrorMessage();
        }

        determineID();
        while (running) {
            UI.displayInputOptions();
            select(input.nextLine().toLowerCase());

        }
        try {
            DB.saveMembers(members);
            DB.saveCompetetiveMembers(competitionMembers);
        } catch (FileNotFoundException e) {
            UI.displayCouldNotSaveToFile();
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
                } catch (MemberTypeMismatchException e) {
                    UI.displayWrongMemberType(e.getMessage());
                } catch (MemberStatusMismatchException e) {
                    UI.displayWrongMemberStatus(e.getMessage());
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
        switch (option) {
            case "1", "competitive" -> {
                UI.displayInputEditMemberChooseMember();
                int requestedID = 0;
                try {
                    requestedID = Integer.parseInt(input.nextLine());
                } catch (NumberFormatException e){
                    UI.notValidChoice();
                }
                for (CompetitionMember competitionMember : competitionMembers) {
                    if (competitionMember.getUserID() == requestedID) {
                        UI.nowEditing(competitionMember);
                        System.out.println();
                        editMember(competitionMember, competitionMember);
                    }
                }
            }
            case "2", "member" -> {
                UI.displayInputEditMemberChooseMember();
                int requestedID = 0;
                try {
                    requestedID = Integer.parseInt(input.nextLine());
                } catch (NumberFormatException e){
                    UI.notValidChoice();
                }
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
                try {
                    member.setBirthday(input.nextLine());
                } catch (DateTimeParseException e){
                    UI.displayWrongDateFormat(e.getParsedString());
                }
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
                addNewComp(competitionMember);
            }
            case "9", "Edit competitions" -> {
                UI.displayNowEditingChoiceDisplay(9);
                UI.enterVariable(11);
                editCompetitionAttributes(input.nextLine(),competitionMember);
            }
            case "10", "Add discipline" -> {
                UI.displayNowEditingChoiceDisplay(10);
                addNewDisci(competitionMember);
            }
            case "11", "Edit discipline" -> {
                UI.displayNowEditingChoiceDisplay(11);
                UI.enterVariable(9);
                String check = input.nextLine().toUpperCase();
                    try {
                        DisciplineType type = DisciplineType.valueOf(check);
                            editDisciplineAttributes(type,competitionMember);
                    } catch (IllegalArgumentException e){
                        UI.notValidChoice();
                    }
                }
            }
        }
    public void addNewDisci(CompetitionMember member){
        Scanner disci = new Scanner(System.in);
        UI.enterVariable(9);
        DisciplineType type = null;
        try {
            type = DisciplineType.valueOf(disci.next().toUpperCase());
        } catch (IllegalArgumentException e){
            UI.notValidChoice();
            addNewDisci(member);
        }
        UI.enterVariable(10);
        double record = 0;
        try {
            record = disci.nextDouble();
        } catch (InputMismatchException e){
            UI.notValidChoice();
            addNewDisci(member);
        }
        UI.enterVariable(14);
        String date = disci.next();
        try {
        member.getDisciplines().add(new Discipline(type,record,date));
    } catch (DateTimeParseException e){
        UI.displayWrongDateFormat(e.getParsedString());
    }
    }
    public void editDisciplineAttributes(DisciplineType type, CompetitionMember member){
        Discipline discipline = null;
        for (Discipline disc :member.getDisciplines()) {
            if (disc.getType() == type) {
                discipline = disc;
                UI.enterVariable(10);
                try {
                    disc.setRecord(input.nextDouble());
                } catch (InputMismatchException e){
                    UI.notValidChoice();
                }
                input.nextLine();
                UI.enterVariable(14);
                try {
                    disc.setDate(input.nextLine());
                } catch (DateTimeParseException e){
                    UI.displayWrongDateFormat(e.getParsedString());
                }
            }
        }
    }
    public void addNewComp(CompetitionMember member){
        Scanner comp = new Scanner(System.in);
        UI.enterVariable(11);
        String place = comp.next();
        UI.enterVariable(12);
        int rank = 0;
        try {
            rank = comp.nextInt();
        } catch (InputMismatchException e){
            UI.notValidChoice();
            addNewComp(member);
        }
        UI.enterVariable(14);
        String date = comp.next();
        UI.enterVariable(13);
        double time = 0;
        try {
            time = comp.nextDouble();
        } catch (InputMismatchException e){
            UI.notValidChoice();
            addNewComp(member);
        }
        try {
            member.getCompetitions().add(new Competition(place,rank,date,time));
        } catch (DateTimeParseException e){
            UI.displayWrongDateFormat(e.getParsedString());
        }
    }
    public void editCompetitionAttributes(String place, CompetitionMember member){
        for (Competition competition : member.getCompetitions()) {
            if (competition.getPlace().equals(place)) {
                UI.enterVariable(11);
                competition.setPlace(input.nextLine());
                UI.enterVariable(12);
                competition.setRanking(input.nextInt());
                String fix = input.nextLine();
                UI.enterVariable(14);
                try {
                    competition.setDate(input.nextLine());
                } catch (DateTimeParseException e){
                    UI.displayWrongDateFormat(e.getParsedString());
                }
                UI.enterVariable(13);
                competition.setTime(input.nextDouble());
                input.nextLine();
            }
        }
    }


    private void inputAddMember() {
        HashMap<MemberInformation, String> memberInformationMap = UI.askForMemberInformation();
        currentHighestId++;
        String status = memberInformationMap.get(MemberInformation.STATUS);
        if (!(status.equals("aktiv") || status.equals("passiv"))) {
            throw new MemberStatusMismatchException(status);
        }
        switch (memberInformationMap.get(MemberInformation.TYPE).toLowerCase()) {
            case "konkurrent" -> addCompetitionMember(memberInformationMap);
            case "motionist" -> addMember(memberInformationMap);
            default -> throw new MemberTypeMismatchException(memberInformationMap.get(MemberInformation.TYPE));
        }
    }


    private void addMember(HashMap<MemberInformation, String> information) {
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

    private void addCompetitionMember(HashMap<MemberInformation, String> information) {
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
            UI.enterVariable(1);
            int target = 0;
            try {
                target = input.nextInt();
            } catch (InputMismatchException e){
                UI.notValidChoice();
            }
            String fix = input.nextLine();
            ArrayList<Competition> comp = new ArrayList<>();
            for (CompetitionMember competitionMember : competitionMembers) {
                if (target == competitionMember.getUserID()) {
                    comp.addAll(competitionMember.getCompetitions());
                    for (Competition competition : comp) {
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
            UI.enterVariable(1);
            int target = 0;
            try {
                target = input.nextInt();
            } catch (InputMismatchException e){
                UI.notValidChoice();
            }
            String fix = input.nextLine();
            ArrayList<Discipline> disci = new ArrayList<>();
            for (CompetitionMember competitionMember : competitionMembers) {
                if (target == competitionMember.getUserID()) {
                    disci.addAll(competitionMember.getDisciplines());
                    for (Discipline discipline : disci) {
                        UI.disciplinePrintArray(discipline);
                    }
                }
            }
        } else {
            UI.printCantFindMember();
        }
    }

    private void deleteMember(){
        Member found = null;
        UI.displayDeleteMember();
        int data = Integer.parseInt(input.nextLine());
        for (Member member : members) {
            if (data == member.getUserID()) {
                found = member;
                UI.displayMemberDeleted();
            }
        }
        if (found == null) {
            UI.displayInvalidMemberID();
        } else {
            members.remove(found);
        }
    }

    private HashMap<RankingGroup, ArrayList<CompetitionMember>> initializeRanking() {
        HashMap<RankingGroup, ArrayList<CompetitionMember>> rankings = new HashMap<>();
        for (RankingGroup rankingGroup : RankingGroup.values()) {
            rankings.put(rankingGroup, new ArrayList<>());
        }
        return rankings;
    }

    private void inputCheckRankings() {
        HashMap<RankingGroup, ArrayList<CompetitionMember>> rankings = initializeRanking();
        for (CompetitionMember competitionMember : competitionMembers) {
            if (competitionMember.getAge() < 18) {
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
        for (Map.Entry<RankingGroup, ArrayList<CompetitionMember>> set : rankings.entrySet()) {
            Collections.sort(set.getValue(), new ResultComparator(set.getKey()));
        }
        UI.displayRankings(rankings);
    }

    private void inputCheckSubscriptions() {
        UI.printInputCaseCheckSubscription();
        int editOption = input.nextInt();
        switch (editOption) {
            case 1 -> inputCheckSubscriptionsView();
            case 2 -> inputCheckSubscriptionsChangeMember();
        }
    }

    private void inputCheckSubscriptionsChangeMember() {
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


    private void inputCheckSubscriptionsView(){

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
                if (yearPay < yearPresent || (yearPay == yearPresent && monthPay < monthPresent) || (yearPay == yearPresent && monthPay == monthPresent && dayPay < dayPresent) || (yearPay == yearPresent && monthPay == monthPresent && dayPay == dayPresent)) {
                    yearPay++;
                    String newDatePaid = memberPayDateArray[0] + "/" + memberPayDateArray[1] + "/" + yearPay;
                    member.setDatePaid(newDatePaid);
                }
                UI.printDateOfPay(yearPay, memberPayDateArray[1], memberPayDateArray[0]);
                UI.userPaidInTime(true);
            } else {
                UI.printDateOfPay(yearPay, memberPayDateArray[1], memberPayDateArray[0]);
                if (yearPay < yearPresent || (yearPay == yearPresent && monthPay < monthPresent) || (yearPay == yearPresent && monthPay == monthPresent && dayPay < dayPresent) || (yearPay == yearPresent && monthPay == monthPresent && dayPay == dayPresent)) {
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