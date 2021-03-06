package logic.comparators;

import logic.member.Member;

import java.util.Comparator;

public class IDComparator implements Comparator<Member> {
    @Override
    public int compare(Member o1, Member o2) {
        return o1.getUserID() - o2.getUserID();
    }
}
