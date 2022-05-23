package data;

import logic.Member;
import logic.competitor.Competition;
import logic.competitor.CompetitionMember;
import logic.competitor.Discipline;
import logic.competitor.DisciplineType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Database {
    public void saveMembers(ArrayList<Member> members) throws FileNotFoundException {
        PrintStream out = new PrintStream("Svømmeclub.csv");
        for (int i = 0; i < members.size(); i++) {
            Member member = members.get(i);
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
            out.print("\n");
        }

    }
    public void saveCompetetiveMembers(ArrayList<CompetitionMember> competitionMembers) throws FileNotFoundException {
        PrintStream out = new PrintStream("CompetetionMembers.csv");
        PrintStream out2 = new PrintStream("Disciplines.csv");
        PrintStream out3 = new PrintStream("Competitions.csv");
        for (int i = 0; i < competitionMembers.size(); i++) {
            CompetitionMember competitionMember = competitionMembers.get(i);
            out.print(competitionMember.getUserID());
            out.print(";");
            out.print(competitionMember.getFirstName());
            out.print(";");
            out.print(competitionMember.getLastName());
            out.print(";");
            out.print(competitionMember.getBirthday());
            out.print(";");
            out.print(competitionMember.getAddress());
            out.print(";");
            out.print(competitionMember.getEmail());
            out.print(";");
            out.print(competitionMember.getPhoneNumber());
            out.print(";");
            out.print(competitionMember.getActive());
            out.print("\n");
           ArrayList<Discipline> disciplines = competitionMember.getDisciplines();
           ArrayList<Competition> competitions = competitionMember.getCompetitions();

            for (Discipline discipline : disciplines) {
                out2.print(competitionMember.getUserID());
                out2.print(";");
                out2.print(discipline.getType());
                out2.print(";");
                out2.print(discipline.getDate());
                out2.print(";");
                out2.print(discipline.getRecord());
                out2.print("\n");

            }
            for (Competition competition : competitions) {
                out3.print(competitionMember.getUserID());
                out3.print(";");
                out3.print(competition.getPlace());
                out3.print(";");
                out3.print(competition.getRanking());
                out3.print(";");
                out3.print(competition.getDate());
                out3.print(";");
                out3.print(competition.getTime());
                out3.print("\n");
            }

        }

    }
    public ArrayList<Member> loadMembers() throws FileNotFoundException {
        ArrayList<Member> members = new ArrayList<>();
        File file = new File("Svømmeclub.csv");
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()){
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
            members.add(new Member(userID,firstName,lastName,birthday,address,email,mobile,status));
        }
        return members;
    }
    public ArrayList<CompetitionMember> loadCompetetiveMembers() throws FileNotFoundException {
        ArrayList<CompetitionMember> competitionMembers = new ArrayList<>();
        File file = new File("CompetetionMembers.csv");
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()){
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
            CompetitionMember member  = new CompetitionMember(userID,firstName,lastName,
                birthday,address,email,mobile,status);
            competitionMembers.add(member);
            member.addDisciplines(loadDisciplines(userID));
            member.addCompetitions(loadCompetitions(userID));

        }
        return competitionMembers;
    }
    public ArrayList<Discipline> loadDisciplines(int userID) throws FileNotFoundException {
        ArrayList<Discipline> disciplines = new ArrayList<>();
        File file = new File("Disciplines.csv");
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()){
            Scanner find = new Scanner(sc.nextLine());
            find.useDelimiter(";");
            find.useLocale(Locale.ENGLISH);
            int id = find.nextInt();
            if (id == userID){
            DisciplineType type = DisciplineType.valueOf(find.next());
            double record = find.nextDouble();
            String date = find.next();
            disciplines.add(new Discipline(type,record,date));
            }

        }
        return disciplines;
    }
    public ArrayList<Competition> loadCompetitions(int userID) throws FileNotFoundException {
        ArrayList<Competition> competitions = new ArrayList<>();
        File file = new File("Competitions.csv");
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()){
            Scanner find = new Scanner(sc.nextLine());
            find.useDelimiter(";");
            find.useLocale(Locale.ENGLISH);
            int id = find.nextInt();
            if (id == userID){
            String place = find.next();
            int ranking = find.nextInt();
            String date = find.next();
            double time = find.nextDouble();
            competitions.add(new Competition(place,ranking,date,time));
            }

        }
        return competitions;
    }

}
