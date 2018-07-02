package edu.pdx.cs410J.shikha2;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

  public Project1(String name, String phone_number, String DateTime ) {
    String pattern1 = "^\\d\\d\\d-\\d\\d\\d-\\d\\d\\d\\d";
    String pattern = "([a-z] ?)+[a-z]";
    String dateTimeFormat = "([0-9]{1,2})/([0-9]{1,2})/([0-9]{4}) ([0-9]{1,2}):([0-9]{1,2}):([0-9]{1,2})";
    if (phone_number.matches(pattern1)) {
      System.out.println("You have entered correct syntax of phone number ");
    } else {
      throw new UnsupportedOperationException("Phone number should be of format XXX-XXX-XXXX");
    }
    if (name.matches(pattern)) {
      System.out.println("You have entered correct syntax name");
    } else {
      throw new UnsupportedOperationException("Name should not have special characters!");
    }
    if (DateTime.matches(dateTimeFormat)) {
      System.out.println("You have entered correct Time");
    } else {
      throw new UnsupportedOperationException("Please enter proper time");
    }
  }

  public static void main(String[] args) {
    PhoneCall call = new PhoneCall();  // Refer to one of Dave's classes so that we can be sure it is on the classpath

    //PhoneBill bill = new PhoneBill(); // refers to phone bill class's object.
    //bill.addPhoneCall(call);

    //Collection<PhoneCall> phonecalls = bill.getPhoneCalls();
    //for (PhoneCall c: phonecalls) System.out.println("c");

    System.err.println("Missing command line arguments");

    for (String arg : args) {
      //checkPhoneNumber(args[2]);
      System.out.println(arg);
    }

  }
}