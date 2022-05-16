package logic;

import java.util.Comparator;

public class BirthDayComparator implements Comparator<Member> {


    @Override
    public int compare(Member o1, Member o2) {
        return o1.getBirthday().compareTo(o2.getBirthday());
    }
}
