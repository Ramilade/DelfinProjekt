package data;

import logic.Member;
import logic.competitor.Competition;
import logic.competitor.CompetitionMember;
import logic.competitor.Discipline;
import logic.competitor.DisciplineType;

import javax.swing.text.DateFormatter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
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
            out.print("\n");
        }

    }
    public void saveCompetetiveMembers(ArrayList<CompetitionMember> members) throws FileNotFoundException {
        PrintStream out = new PrintStream("Competetiv.csv");
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
            out.print("\n");
        }

    }
    public void saveDiciplines(ArrayList<Discipline> disciplines, ArrayList<Member> members) throws FileNotFoundException {
        PrintStream out = new PrintStream("Disciplines.csv");
        for (int i = 0; i < disciplines.size(); i++) {
            Member member = members.get(i);
            Discipline discipline = disciplines.get(i);
            out.print(member.getUserID());
            out.print(";");
            out.print(discipline.getType());
            out.print(";");
            out.print(discipline.getRecord());
            out.print(";");
            out.print(discipline.getDate());
            out.print("\n");
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
            members.add(new Member(userID,firstName,lastName,birthday,address,email,mobile));
        }
        return members;
    }
    public ArrayList<Member> loadCompetetiveMembers() throws FileNotFoundException {
        ArrayList<Member> members = new ArrayList<>();
        File file = new File("Competetive.csv");
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
            loadDisciplines(userID);
            CompetitionMember member  = new CompetitionMember(userID,firstName,lastName,
                birthday,address,email,mobile);
            members.add(member);

        }
        return members;
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
            LocalDate date = LocalDate.parse(find.next(),DateTimeFormatter.ofPattern("dd/MM/yyyy"));
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
            LocalDate date = LocalDate.parse(find.next(),DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            double time = find.nextDouble();
            competitions.add(new Competition(place,ranking,date,time));
            }

        }
        return competitions;
    }

}
