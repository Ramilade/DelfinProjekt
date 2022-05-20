package ui;

public enum MemberInformation {
    FIRST_NAME (""),
    LAST_NAME (""),
    BIRTHDAY ("[dd/mm/yyyy]"),
    EMAIL (""),
    ADDRESS(""),
    PHONE_NUMBER(""),
    STATUS("[aktiv/passiv]"),
    TYPE("[motionist/konkurrent]");


    private String specificInformation;
    MemberInformation(String additionalInformation) {
        this.specificInformation = additionalInformation;
    }

    public String getSpecificInformation() {
        return specificInformation;
    }


}
