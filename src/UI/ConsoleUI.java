package UI;

import com.company.Member;

public class ConsoleUI {

    public void displayMember(Member member) {
        System.out.println(member);
    }

    public void displayInputOptions() {
        System.out.println("""
            1. add a member
            2. check the rankings
            3. check the subscriptions
            4. exit
            """);
    }

}
