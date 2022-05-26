package logic.comparators;

import logic.member.Member;

import java.util.Comparator;

public class NameComparator implements Comparator<Member> {
    @Override
    public int compare(Member o1, Member o2) {
        return o1.getFirstName().compareTo(o2.getFirstName());
    }
}
