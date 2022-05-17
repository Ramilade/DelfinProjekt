package data;

import logic.Member;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
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
            out.print("\n");
        }

    }
    public ArrayList<Member> loadMemberList() throws FileNotFoundException {
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

}
