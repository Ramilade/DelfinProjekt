package logic.competitor;

public class Discipline {

  private DisciplineType type;
  private String name;
  private int distance;
  private double record;

  public Discipline(DisciplineType type, String name, int distance, double record) {
    this.type = type;
    this.name = name;
    this.distance = distance;
    this.record = record;
  }

  public Discipline() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getDistance() {
    return distance;
  }

  public void setDistance(int distance) {
    this.distance = distance;
  }

  public double getRecord() {
    return record;
  }

  public void setRecord(double record) {
    this.record = record;
  }
}
