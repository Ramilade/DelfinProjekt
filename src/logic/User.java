/*package logic;

import java.util.ArrayList;
import java.util.Scanner;

public class User {
  private String login;
  private String password;

  private static ArrayList<User> all = new ArrayList();


  public User(String login, String password)
  {
    this.login = login;
    this.password = password;
  }

  public String toString()
  {
    return login + ":" + password;
  }


  public boolean equals(Object other)
  {
    if (this == other)
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  public static boolean login()
  {
    Scanner loginScanner = new Scanner(System.in);
    String userLogin;
    String userPassword;
    boolean success;

    success = false;

    System.out.print("Username:");
    userLogin = loginScanner.nextLine();

    System.out.print("Password:");
    userPassword = loginScanner.nextLine();

    for (User user : all)
    {
      if (user.login.equals(userLogin) && user.password.equals(userPassword))
      {
        System.out.println("Login prompt");
        success = true;
      }
    }

    if (success == false)
    {
      System.out.println("Wrong credentials or password");

    }

    return success;
  }

  public static void populate()
  {
    User admin;

    admin = new User("admin", "password");


    all.add(admin);

  }
}
*/