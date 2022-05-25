package logic;

public class SubscriptionCalculator {


    public double subscribeCal(int age, boolean active){
        double subCost = 500;
        if (active) {
            subCost = 1000;
            if (age > 17) {
                subCost = 1600;
            }
            if (age > 60) {
                subCost = subCost * 0.75;
            }
        }
        return subCost;
    }
}