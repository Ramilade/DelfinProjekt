package logic.competitor;

import java.time.LocalDate;

public class Discipline {

  private DisciplineType type;
  private LocalDate date;
  private double record;

  public Discipline(DisciplineType type, double record, LocalDate date) {
    this.type = type;
    this.record = record;
    this.date = date;
  }

  public DisciplineType getType() {
    return type;
  }

  public void setType(DisciplineType type) {
    this.type = type;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public double getRecord() {
    return record;
  }

  public void setRecord(double record) {
    this.record = record;
  }
}
