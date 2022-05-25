package ui;

import logic.Member;
import logic.competitor.CompetitionMember;
import logic.competitor.Discipline;
import logic.competitor.RankingGroup;
import logic.competitor.Competition;

import java.util.*;

public class ConsoleUI {


    //skal denne bruges?
    /*public void displayMember(Member member) {
        System.out.println(member);
    }*/

    public void displayInputOptions() {
        System.out.println("""
            1. Add a member
            2. Edit a member
            3. Show members
            4. Show competitions
            5. Show disciplines
            6. View rankings
            7. View subscriptions
            8. Delete a member
            9. Save & exit
            """);
    }

    public void displayInputSortingMember(){
        System.out.println("""
                Choose sorting method:
                
                1. Sort by name
                2. Sort by date of birth
                3. Sort by ID
                """);
    }
    public void displayInputEditMemberChooseMember(){
        System.out.println("Enter the member ID you wish to edit: ");
    }

    public void displayDeleteMember(){
        System.out.println("Enter the memberID you wish to delete: ");
    }

    public void displayMemberDeleted(){
        System.out.println("MemberID has been deleted\n-----------");
    }

    public void displayInvalidMemberID(){
        System.out.println("You have entered an invalid memberID\n------------");
    }


    public void displayInputEditMember(){
        System.out.println("""
                1. Change first name
                2. Change last name
                3. Change birthday (dd/mm/yyyy) format.
                4. Change address
                5. Change email
                6. Change phone number
                7. Change member status
                8. Add competition
                9. Edit competition
                10. Add discipline
                11. Edit discipline
                """);
    }
    public void displayInputEditMember2(){
        System.out.print("""
                            Choose the type of member you wish to edit:
                            
                            1. Competitive member
                            2. Non-competitive member
                            """);
    }
    //Skal denne bruges?
    /*public void displayIncorrectMemberType(){
        System.out.println("Invalid type of member - go back and pick another/opposite member type!\n");
    }*/

    public void nowEditing(Member member){
        System.out.println("Now editing: " + member.getUserID());
    }

    public void displayNowEditingChoiceDisplay(int option){
        switch (option) {
            case 1 -> System.out.println("Now editing First Name");
            case 2 -> System.out.println("Now editing Last Name");
            case 3 -> System.out.println("Now editing Birthday(dd/mm/yyyy)");
            case 4 -> System.out.println("Now editing Address");
            case 5 -> System.out.println("Now editing Email");
            case 6 -> System.out.println("Now editing Phone number");
            case 7 -> System.out.println("Now editing Member status(aktiv/passiv)");
            case 8 -> System.out.println("Now adding competition");
            case 9 -> System.out.println("Now editing competitions");
            case 10 -> System.out.println("Now adding discipline");
            case 11 -> System.out.println("Now editing disciplines");

        }
    }



    public HashMap<MemberInformation,String> askForMemberInformation() {
        Scanner userInput = new Scanner(System.in);
        HashMap<MemberInformation,String> listOfMemberInformation = new HashMap<>();

        MemberInformation[] memberInformation = MemberInformation.values();

        for (int i = 0; i < memberInformation.length; i++) {
            String name;
            if (memberInformation[i].name().toLowerCase().contains("_")) {
                String[] split = memberInformation[i].name().toLowerCase().split("_");
                name = split[0] + " " + split[1];
            } else {
                name = memberInformation[i].name().toLowerCase();
            }
            System.out.printf("(Step %s/%s) Enter '%s'%s : ",i+1,memberInformation.length, name, memberInformation[i].getSpecificInformation());
            listOfMemberInformation.put(memberInformation[i],userInput.nextLine());
        }

        return listOfMemberInformation;
    }

    public void fileNotFoundErrorMessage(){
        System.err.println("ERROR: File could not be found! Initializing new save file.");
    }

    public void printArray(Member member){
        String[] memberInformation = member.toString().split(";");
        for (String info : memberInformation) {
            System.out.println(info);
        }
        System.out.println("---------------------");


    }
    public void competitionsPrintArray(Competition competition){
        String[] competitionInformation = competition.toString().split(";");
        for (String info : competitionInformation) {
            System.out.println(info);
        }
        System.out.println("---------------------");


    }
    public void disciplinePrintArray(Discipline discipline){
        String[] disciplineInformation = discipline.toString().split(";");
        for (String info : disciplineInformation) {
            System.out.println(info);
        }
        System.out.println("---------------------");


    }


    public void noMembersInList() {
        System.out.println("There are no members listed at this moment!");
    }

    public void printUserHasBeenCreated(Member member){
        System.out.println("\nUser has been created with the following information: ");
        System.out.println(member + "\n");
    }

    public void printSubscriptionDuePayment(Member member) {
        System.out.print("Member first name: " + member.getFirstName() + ", ID: " + member.getUserID() + " Created: " + member.getCreationDate() + " Active: " + member.getActive() + " Subscription amount: " + member.getSubscription() + "kr.- is due: ");
    }

    public void userPaidInTime(boolean paid) {
        if (paid){
            System.out.println(" Member has paid their subscription");
        } else {
            System.out.println(" Member has NOT paid their subscription");
        }
    }

    public void totalSubscriptionNumber(int subscriptions) {
        System.out.println();
        System.out.println(" Total amount of payment expected: " + subscriptions + "kr.-");
    }

    public void printDateOfPay(int year, String month, String day) {
        System.out.print(day + "/" + month + "/" + year);
    }

    public void printInputCaseCheckSubscription(){
        System.out.println("""
                1. View subscriptions
                2. Change a member's subscription status
                """);
    }

    public void printSubscriptionCaseChangeSub(){
        System.out.println("""
                Enter the member's ID you want to change. (NOTE: Have they paid their subscription within this year?)
                """);
    }

    public void printSubscriptionCaseChosenID(Member member){
        System.out.println("Changing user: " + member.getFirstName() + " with ID: " + member.getUserID() + " with subscription date: " + member.getDatePaid() + " with status: (Has paid in time) " + member.hasPaid() + " to ? (1. for inactive or 2. for active)");
    }

    public void printCantFindMember() {
        System.out.println("Cannot find member with requested ID!");
    }

    public void notValidChoice() {
        System.out.println("Not a valid choice!");
    }
    public void enterVariable(int option){
        switch (option){
            //for members/comp members
            case 1 -> System.out.println("Enter ID");
            case 2 -> System.out.println("Enter FirstName");
            case 3 -> System.out.println("Enter LastName");
            case 4 -> System.out.println("Enter Birthday: format(DD/MM/YYYY)");
            case 5 -> System.out.println("Enter address");
            case 6 -> System.out.println("Enter email");
            case 7 -> System.out.println("Enter phoneNumber");
            case 8 -> System.out.println("Enter Status: format(aktiv/passiv)");
            //for discipliner
            case 9 -> System.out.println("Enter Type");
            case 10 -> System.out.println("Enter Record: format(MM,SS)");
            //for competitions
            case 11 -> System.out.println("Enter Place");
            case 12 -> System.out.println("Enter Rank");
            case 13 -> System.out.println("Enter Time");
            //for comp og disci
            case 14 -> System.out.println("Enter Date: format(DD/MM/YYYY)");

        }
    }

    public void displayRankings(HashMap<RankingGroup, ArrayList<CompetitionMember>> rankings) {
        RankingGroup[] groups = RankingGroup.values();
        for (RankingGroup group : groups) {
            System.out.println("----" + group.name() + "----");
            int rankingLength = Math.min(rankings.get(group).size(), 5);
            for (int i = 0; i < rankingLength; i++) {
                CompetitionMember member = rankings.get(group).get(i);
                Discipline discipline = member.findDiscipline(group.translateToDisciplineType());
                String[] split = discipline.toString().split(";");
                String[] splitTime = split[2].split("\\.");
                if (splitTime[0].equals("0")) {
                    System.out.printf("%s | Time: %sS | Date %s\n",member.getFullName(), splitTime[1],split[1]);
                } else {
                    System.out.printf("%s | Time: %sM %sS | Date %s\n",member.getFullName(),splitTime[0],splitTime[1],split[1]);
                }
            }
        }
    }

    public void displayWrongDateFormat(String parsedString) {
        System.err.println("You cannot write " + parsedString + ". Please follow the format provided when creating a member");
    }

    public void displayCouldNotSaveToFile() {
        System.err.println("Could not save to file, it may be open in another program or not accessible entirely");
    }

    public void displayWrongMemberType(String message) {
        System.err.println(message + " is not a type of member, please use \"motionist\"/\"konkurrent\"");
    }

    public void displayWrongMemberStatus(String message) {
        System.err.println(message + " is not a status of member, please use \"aktiv\"/\"passiv\"");

    }
}
