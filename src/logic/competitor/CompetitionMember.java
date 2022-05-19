package logic.competitor;

import logic.Member;

import java.util.ArrayList;

public class CompetitionMember extends Member {

    private final ArrayList<Discipline> disciplines;
    private String team;
    private final ArrayList<Competition> competitions;

    public CompetitionMember(int userID, String firstName, String lastName, String birthday, String address, String email, String phoneNumber) {
        super(userID, firstName, lastName, birthday, address, email, phoneNumber);
        disciplines = new ArrayList<>();
        competitions = new ArrayList<>();
    }


    public ArrayList<Competition> getCompetitions() {
        return competitions;
    }

    public void addCompetitions(ArrayList<Competition> competition) {
        this.disciplines.addAll(disciplines);
    }

    public ArrayList<Discipline> getDisciplines() {
        return disciplines;
    }

    public void addDisciplines(ArrayList<Discipline> disciplines) {
        this.disciplines.addAll(disciplines);
    }
}
