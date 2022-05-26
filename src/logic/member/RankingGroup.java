package logic.member;

public enum RankingGroup {
    SENIOR_CRAWL,
    SENIOR_RYGCRAWL,
    SENIOR_BRYSTSVØMNING,
    SENIOR_BUTTERFLY,
    JUNIOR_CRAWL,
    JUNIOR_RYGCRAWL,
    JUNIOR_BRYSTSVØMNING,
    JUNIOR_BUTTERFLY;

    public DisciplineType translateToDisciplineType() {
        DisciplineType type;
        switch (this) {
            case JUNIOR_CRAWL,SENIOR_CRAWL -> type = DisciplineType.CRAWL;
            case JUNIOR_BRYSTSVØMNING,SENIOR_BRYSTSVØMNING -> type = DisciplineType.BRYSTSWØMNING;
            case JUNIOR_RYGCRAWL,SENIOR_RYGCRAWL -> type = DisciplineType.RYGCRAWL;
            case JUNIOR_BUTTERFLY,SENIOR_BUTTERFLY -> type = DisciplineType.BUTTERFLY;
            default -> type = null;
        }
        return type;
    }
}
