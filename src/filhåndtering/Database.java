package filhåndtering;

import com.company.Member;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class Database {
    public void createMember() throws FileNotFoundException {
        Member bob = new Member("Bob",29);
        PrintStream out = new PrintStream("Svømmeclub.csv");
        out.print(bob.getName());
        out.print(";");
        out.print(bob.getAge());

    }
}
