package edu.pdx.cs410J.shikha2;

/**
 * The main class for the CS410J Phone Bill Project
 */
public class Project1 {

    /**
     * This method helps to check customer name
     * @param name
     */
    public static void name_check(String name) {
        String pattern = "([a-z] ?)+[a-z]";
        if (name.matches(pattern)) {
            System.out.println("You have entered correct syntax name");
        } else {
            throw new UnsupportedOperationException("Name should not have special characters!");
        }
    }

    /**
     * This method helps to check caller number and callee number whether it is XXX-XXX-XXXX
     * @param number
     */
    public static void number_format_check(String number) {
        String pattern1 = "^\\d\\d\\d-\\d\\d\\d-\\d\\d\\d\\d";
        if (number.matches(pattern1)) {
            System.out.println("You have entered correct syntax of phone number ");
        } else {
            throw new UnsupportedOperationException("Phone number should be of format XXX-XXX-XXXX");
        }
  }

    /**
     * This method helps to check the start data and end date entered in the format of mm/dd/yyyy
     * @param date
     */
    public static void check_date_format(String date) {
        String dateTimeFormat = "([0-9]{1,2})/([0-9]{1,2})/([0-9]{4})";
        if (date.matches(dateTimeFormat)) {
            System.out.println("You have entered correct Time");
        } else {
            throw new UnsupportedOperationException("Please enter proper time");
        }
    }


    /**
     * This method checks the time syntax of the entered start and end time in the format of hh:mm
     * @param time
     */
    public static void check_time_format(String time) {
        String time_format = "([0-9]{1,2}):([0-9]{1,2})";
        if (time.matches(time_format)) {
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
        String name, caller_number, callee_number, start_date, start_time, end_date, end_time;
        name = caller_number = callee_number = start_date = start_time = end_date = end_time = null;
        PhoneBill bill = new PhoneBill(); // refers to phone bill class's object.
        bill.addPhoneCall(call);
        Project1 pro_obj = new Project1();
        for (String arg : args) {
            if (name == null) {
                name = arg;
                name_check(name);
            } else if (caller_number == null) {
                caller_number = arg;
                number_format_check(caller_number);
            } else if (callee_number == null) {
                callee_number = arg;
                number_format_check(callee_number);
            } else if (start_date == null) {
                start_date = arg;
                check_date_format(start_date);
            } else if (start_time == null) {
                start_time = arg;
                check_time_format(start_time);
            } else if (end_date == null) {
                end_date = arg;
                check_date_format(end_date);
            } else if (end_time == null) {
                end_time = arg;
                check_time_format(end_time);
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