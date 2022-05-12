package logic;

import ui.ConsoleUI;

import java.util.Scanner;

public class Controller {
    private boolean running;
    private final ConsoleUI UI = new ConsoleUI();
    private final Scanner sc = new Scanner(System.in);

    public Controller() {
        this.running = true;
    }

    public void run() {
        UI.displayInputOptions();
        while (running) {
            select(sc.nextLine().toLowerCase());
        }
    }

    private void select(String input) {
        switch (input) {
            case "1", "add member" -> inputAddMember();
            case "2", "check rankings" -> inputCheckRankings();
            case "3", "check subscriptions" -> inputCheckSubscriptions();
            case "4", "exit" -> running = false;
        }

    }

    private void inputAddMember() {
        System.out.println("TODO");
    }

    private void inputCheckRankings() {
        System.out.println("TODO");
    }

    private void inputCheckSubscriptions() {
        System.out.println("TODO");
    }


}
