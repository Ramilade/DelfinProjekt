package logic;

import java.util.ArrayList;

public class CompetitionMember extends Member{

    private final ArrayList<Discipline> disciplines;


    public CompetitionMember(String name, int age, boolean active) {
        super(name, age, active);
        disciplines = new ArrayList<>();
    }
}
