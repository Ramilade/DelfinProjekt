package logic.member;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Discipline {

  private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
  private DisciplineType type;
  private LocalDate date;
  private double record;

  public Discipline(DisciplineType type, double record, String date) {
    this.type = type;
    this.record = record;
    setDate(date);
  }

  public DisciplineType getType() {
    return type;
  }

  public void setType(DisciplineType type) {
    this.type = type;
  }

  public String getDate() {
    return date.format(dtf);
  }

  public void setDate(String date) {
    this.date = LocalDate.parse(date,dtf);
  }

  public double getRecord() {
    return record;
  }

  public void setRecord(double record) {
    this.record = record;
  }

  @Override
  public String toString() {
    return "Type: " + type + ";"
        + "Date: " + date + ";"
        + "Record: " + record;
  }
}
