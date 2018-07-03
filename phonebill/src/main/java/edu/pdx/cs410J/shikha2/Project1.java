package edu.pdx.cs410J.shikha2;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

    /**
     * @param name
     */
    public void name_check(String name) {
        String pattern = "([a-z] ?)+[a-z]";
        if (name.matches(pattern)) {
            System.out.println("You have entered correct syntax name");
        } else {
            throw new UnsupportedOperationException("Name should not have special characters!");
        }
    }

    /**
     * @param number
     */
    public void number_format_check(String number) {
        String pattern1 = "^\\d\\d\\d-\\d\\d\\d-\\d\\d\\d\\d";
        if (number.matches(pattern1)) {
            System.out.println("You have entered correct syntax of phone number ");
        } else {
            throw new UnsupportedOperationException("Phone number should be of format XXX-XXX-XXXX");
        }
  }

    /**
     * @param date_time
     */
    public void check_date_format(String date_time) {
        String dateTimeFormat = "([0-9]{1,2})/([0-9]{1,2})/([0-9]{4}) " +
                "([0-9]{1,2}):([0-9]{1,2})";
        if (date_time.matches(dateTimeFormat)) {
            System.out.println("You have entered correct Time");
        } else {
            throw new UnsupportedOperationException("Please enter proper time");
        }
    }

    /**
     * @param args
     */

    public static void main(String[] args) {
        PhoneCall call = new PhoneCall();  // Refer to one of Dave's classes so that we can be sure it is on the classpath
        String name, caller_number, callee_number, start_time, end_time;
        name = caller_number = callee_number = start_time = end_time = null;
        PhoneBill bill = new PhoneBill(); // refers to phone bill class's object.
        bill.addPhoneCall(call);
        Project1 pro_obj = new Project1();
        for (String arg : args) {
            if (name == null) {
                name = arg;
                pro_obj.name_check(name);
            } else if (caller_number == null) {
                caller_number = arg;
                pro_obj.number_format_check(caller_number);
            } else if (callee_number == null) {
                callee_number = arg;
                pro_obj.number_format_check(callee_number);
            } else if (start_time == null) {
                start_time = arg;
                pro_obj.check_date_format(start_time);
            } else if (end_time == null) {
                end_time = arg;
                pro_obj.check_date_format(end_time);
            }
            System.out.println(arg);
        }

        if (name == null) {
            printErrorMessageAndExit("Missing name argument");
        } else if (caller_number == null) {
            printErrorMessageAndExit("Missing caller_number argument");
        } else if (callee_number == null) {
            printErrorMessageAndExit("Missing callee_number argument");
        } else if (start_time == null) {
            printErrorMessageAndExit("Missing start_time argument");
        } else if (end_time == null) {
            printErrorMessageAndExit("Missing end_time argument");
        }


    }

    private static void printErrorMessageAndExit(String s) {
        System.err.println(s);
        System.exit(1);
    }
}