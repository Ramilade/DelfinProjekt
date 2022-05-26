package data;

import logic.Member;
import logic.competitor.Competition;
import logic.competitor.CompetitionMember;
import logic.competitor.Discipline;
import logic.competitor.DisciplineType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class FileHandler {
    public void saveMembers(ArrayList<Member> members) throws FileNotFoundException {
        PrintStream out = new PrintStream("Svømmeclub.csv");
        for (Member member : members) {
            out.print(member.getUserID());
            out.print(";");
            out.print(member.getFirstName());
            out.print(";");
            out.print(member.getLastName());
            out.print(";");
            out.print(member.getBirthday());
            out.print(";");
            out.print(member.getAddress());
            out.print(";");
            out.print(member.getEmail());
            out.print(";");
            out.print(member.getPhoneNumber());
            out.print(";");
            out.print(member.getActive());
            out.print(";");
            out.print(member.getCreationDate());
            out.print(";");
            out.print(member.getDatePaid());
            out.print(";");
            out.print(member.getHasPaidNextYear());
            out.print("\n");
        }

    }

    public void saveCompetetiveMembers(ArrayList<CompetitionMember> competitionMembers) throws FileNotFoundException {
        PrintStream outMembers = new PrintStream("CompetetionMembers.csv");
        PrintStream outDisciplines = new PrintStream("Disciplines.csv");
        PrintStream outCompetitions = new PrintStream("Competitions.csv");

        for (CompetitionMember competitionMember : competitionMembers) {
            outMembers.print(competitionMember.getUserID());
            outMembers.print(";");
            outMembers.print(competitionMember.getFirstName());
            outMembers.print(";");
            outMembers.print(competitionMember.getLastName());
            outMembers.print(";");
            outMembers.print(competitionMember.getBirthday());
            outMembers.print(";");
            outMembers.print(competitionMember.getAddress());
            outMembers.print(";");
            outMembers.print(competitionMember.getEmail());
            outMembers.print(";");
            outMembers.print(competitionMember.getPhoneNumber());
            outMembers.print(";");
            outMembers.print(competitionMember.getActive());
            outMembers.print(";");
            outMembers.print(competitionMember.getCreationDate());
            outMembers.print(";");
            outMembers.print(competitionMember.getDatePaid());
            outMembers.print(";");
            outMembers.print(competitionMember.getHasPaidNextYear());
            outMembers.print("\n");

            saveDisciplines(outDisciplines, competitionMember);
            saveCompetitions(outCompetitions, competitionMember);

        }

    }


    private void saveDisciplines(PrintStream out, CompetitionMember member) {
        for (Discipline discipline : member.getDisciplines()) {
            out.print(member.getUserID());
            out.print(";");
            out.print(discipline.getType());
            out.print(";");
            out.print(discipline.getDate());
            out.print(";");
            out.print(discipline.getRecord());
            out.print("\n");
        }
    }

    private void saveCompetitions(PrintStream out, CompetitionMember member) {
        for (Competition competition : member.getCompetitions()) {
            out.print(member.getUserID());
            out.print(";");
            out.print(competition.getPlace());
            out.print(";");
            out.print(competition.getRanking());
            out.print(";");
            out.print(competition.getDate());
            out.print(";");
            out.print(competition.getTime());
            out.print("\n");
        }
    }


    public ArrayList<Member> loadMembers() throws FileNotFoundException {
        ArrayList<Member> members = new ArrayList<>();
        File file = new File("Svømmeclub.csv");
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            Scanner find = new Scanner(sc.nextLine());
            find.useDelimiter(";");
            find.useLocale(Locale.ENGLISH);
            int userID = find.nextInt();
            String firstName = find.next();
            String lastName = find.next();
            String birthday = find.next();
            String address = find.next();
            String email = find.next();
            String mobile = find.next();
            String status = find.next();
            String creationDate = find.next();
            String datePaid = find.next();
            String hasPaidInTime = find.next();
            members.add(new Member(userID, firstName, lastName, birthday, address, email, mobile, status, creationDate,datePaid, hasPaidInTime));
        }
        return members;
    }

    public ArrayList<CompetitionMember> loadCompetetiveMembers() throws FileNotFoundException {
        ArrayList<CompetitionMember> competitionMembers = new ArrayList<>();
        File file = new File("CompetetionMembers.csv");
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            Scanner find = new Scanner(sc.nextLine());
            find.useDelimiter(";");
            find.useLocale(Locale.ENGLISH);
            int userID = find.nextInt();
            String firstName = find.next();
            String lastName = find.next();
            String birthday = find.next();
            String address = find.next();
            String email = find.next();
            String mobile = find.next();
            String status = find.next();
            String creationDate = find.next();
            String datePaid = find.next();
            String hasPaidInTime = find.next();
            CompetitionMember member = new CompetitionMember(userID, firstName, lastName,
                    birthday, address, email, mobile, status, creationDate, datePaid, hasPaidInTime);
            competitionMembers.add(member);
            member.addDisciplines(loadDisciplines(userID));
            member.addCompetitions(loadCompetitions(userID));

        }
        return competitionMembers;
    }

    private ArrayList<Discipline> loadDisciplines(int userID) throws FileNotFoundException {
        ArrayList<Discipline> disciplines = new ArrayList<>();
        File file = new File("Disciplines.csv");
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            Scanner find = new Scanner(sc.nextLine());
            find.useDelimiter(";");
            find.useLocale(Locale.ENGLISH);
            int id = find.nextInt();
            if (id == userID) {
                DisciplineType type = DisciplineType.valueOf(find.next());
                String date = find.next();
                double record = find.nextDouble();
                disciplines.add(new Discipline(type, record, date));
            }

        }
        return disciplines;
    }

    private ArrayList<Competition> loadCompetitions(int userID) throws FileNotFoundException {
        ArrayList<Competition> competitions = new ArrayList<>();
        File file = new File("Competitions.csv");
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            Scanner find = new Scanner(sc.nextLine());
            find.useDelimiter(";");
            find.useLocale(Locale.ENGLISH);
            int id = find.nextInt();
            if (id == userID) {
                String place = find.next();
                int ranking = find.nextInt();
                String date = find.next();
                double time = find.nextDouble();
                competitions.add(new Competition(place, ranking, date, time));
            }

        }
        return competitions;
    }

}
