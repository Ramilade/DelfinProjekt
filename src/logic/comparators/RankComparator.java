package logic.comparators;

import logic.exceptions.CompetitorDontHaveDisciplineException;
import logic.member.CompetitionMember;
import logic.member.Discipline;
import logic.member.DisciplineType;
import logic.member.RankingGroup;

import java.util.Comparator;

public class RankComparator implements Comparator<CompetitionMember> {
    private DisciplineType type;
    private RankingGroup rankingGroup;

    public RankComparator(RankingGroup rankingGroup) {
        this.rankingGroup = rankingGroup;
        determineDiscipline();
    }

    @Override
    public int compare(CompetitionMember o1, CompetitionMember o2) {
        double o1Result = 0;
        double o2Result = 0;

        try {
            o1Result = findRecord(o1);
            o2Result = findRecord(o2);
        } catch (CompetitorDontHaveDisciplineException e) {
            if (o1Result == 0) {
                return -1;
            }
            if (o2Result == 0) {
                return 1;
            }
            return 0;
        }

        if (o1Result - o2Result > 0) {
            return 1;
        } else if (o1Result - o2Result < 0) {
            return -1;
        } else {
            return 0;
        }
    }


    private double findRecord(CompetitionMember member) {
        for (Discipline discipline : member.getDisciplines()) {
            if (discipline.getType() == type) {
                return discipline.getRecord();
            }
        }
        throw new CompetitorDontHaveDisciplineException();
    }


    private void determineDiscipline() {
        switch (rankingGroup) {
            case JUNIOR_CRAWL, SENIOR_CRAWL -> type = DisciplineType.CRAWL;
            case JUNIOR_RYGCRAWL, SENIOR_RYGCRAWL -> type = DisciplineType.RYGCRAWL;
            case JUNIOR_BRYSTSVØMNING, SENIOR_BRYSTSVØMNING -> type = DisciplineType.BRYSTSWØMNING;
            case JUNIOR_BUTTERFLY, SENIOR_BUTTERFLY -> type = DisciplineType.BUTTERFLY;
        }
    }
}
