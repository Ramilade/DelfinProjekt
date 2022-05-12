package logic;

public class Member {

  private String name;
  private int age;
  private boolean active;
  private int subscription;
  private Discipline crawl;
  private Discipline backCrawl;
  private Discipline breastStroke;
  private Discipline butterfly;

  public Member(String name, int age, boolean active) {
    this.name = name;
    this.age = age;
    this.active = active;
    this.crawl = new Discipline();
    this.backCrawl = new Discipline();
    this.breastStroke = new Discipline();
    this.butterfly = new Discipline();
    calculateSub();
  }

  public Member(String name, int age) {
    this.name = name;
    this.age = age;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }
  private void calculateSub(){
    double subCost = 500;
    if(active){
      subCost = 1000;
      if (getAge()>17){
        subCost = 1600;
      }
      if (getAge()>60){
        subCost = subCost*0.75;
      }
    }
  }

  @Override
  public String toString() {
    return getName() + " " + getAge() + "";
  }
}
