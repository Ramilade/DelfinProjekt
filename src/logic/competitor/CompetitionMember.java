package logic.competitor;

import logic.Member;

import java.util.ArrayList;

public class CompetitionMember extends Member {

    private final ArrayList<Discipline> disciplines;
    private String team;

    public CompetitionMember(int userID, String firstName, String lastName, String birthday, String address, String email, String phoneNumber) {
        super(userID, firstName, lastName, birthday, address, email, phoneNumber);
        disciplines = new ArrayList<>();
    }



    public ArrayList<Discipline> getDisciplines() {
        return disciplines;
    }
}
