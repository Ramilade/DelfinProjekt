package data;

import logic.Member;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Database {
    public void createMember(Member member) throws FileNotFoundException {
        PrintStream out = new PrintStream("Svømmeclub.csv");
        out.print(member.getName());
        out.print(";");
        out.print(member.getAge());

    }
    public ArrayList<Member> memberList() throws FileNotFoundException {
        ArrayList<Member> members = new ArrayList<>();
        File file = new File("Svømmeclub.csv");
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()){
            Scanner find = new Scanner(sc.nextLine());
            find.useDelimiter(";");
            find.useLocale(Locale.ENGLISH);
            String name = find.next();
            int age = Integer.parseInt(find.next());
            members.add(new Member(name,age));
        }
        return members;
    }
    public void displayDatabase() throws FileNotFoundException {
        System.out.println(memberList());
    }
}
