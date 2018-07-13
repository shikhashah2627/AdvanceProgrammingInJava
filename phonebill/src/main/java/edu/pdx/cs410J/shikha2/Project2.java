package edu.pdx.cs410J.shikha2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Project2 {

    static final String README = "Author for Project 1: PhoneCall Project is : Shikha Shah" +
            "This project takes the command line argument of customer name , phone number of caller and callee and date and time from start to end" +
            "You can also print the the args by specifying -print option before passing the argument";

    /**
     * @param file_path
     */
    public static void check_file(String file_path) {
        try {
            File f = new File(file_path);
            f.createNewFile();
        } catch (IOException e) {
            System.out.println("Exception Occurred:");
            System.exit(0);
        }
    }

    /**
     * <code>main</code>
     * Invokes main class where all the arguments are parsed.
     *
     * @param args args contains caller_name, caller_number, callee_number, start_date, start_time, end_date, end_time
     */

    public static void main(String[] args) {

        String name, caller_number, callee_number, start_date, start_time, end_date, end_time, file_path = "";
        name = caller_number = callee_number = start_date = start_time = end_date = end_time = file_path = null;

        if (args.length == 0) {
            System.err.println("Missing command line arguments");
            System.exit(1);
        }

        for (String arg : args) {

            if (arg.matches("-README")) {
                System.out.println(README);
                System.exit(0);
            }

            if (arg.matches("-print")) {
                continue;
            }
            if (arg.matches("-textFile")) {
                System.out.println("You have given the file name");
                continue;
            }

                if (file_path == null) {
                    file_path = args[+1];
                    check_file(file_path);

                } else if (name == null) {
                name = arg;
                //name_check(name);
            } else if (caller_number == null) {
                caller_number = arg;
                //number_format_check(caller_number);
            } else if (callee_number == null) {
                callee_number = arg;
                //number_format_check(callee_number);
            } else if (start_date == null) {
                start_date = arg;
                //check_date_format(start_date);
            } else if (start_time == null) {
                start_time = arg;
               // check_time_format(start_time);
            } else if (end_date == null) {
                end_date = arg;
               // check_date_format(end_date);
            } else if (end_time == null) {
                end_time = arg;
              //  check_time_format(end_time);
            }
            //System.out.println(arg);
        }

        Validation val = new Validation(name,caller_number,callee_number,start_date,start_time,end_date,end_time);

        PhoneCall call = new PhoneCall(val);

        PhoneBill bill = new PhoneBill(val.name);

        bill.addPhoneCall(call);

        Map<String, List<String>> map = new HashMap<String, List<String>>();

        // create list one and store values

        List<String> valSetOne = new ArrayList<String>();

        valSetOne.add(call.getCaller());
        valSetOne.add(call.getCallee());
        valSetOne.add(call.getStartTimeString());
        valSetOne.add(call.getEndTimeString());

        // put values into map

        map.put(val.name, valSetOne);

        // iterate and display values

        System.out.println("Fetching Keys and corresponding [Multiple] Values n");

        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            String key = entry.getKey();
            List<String> values = entry.getValue();
            System.out.println("Key = " + key);
            System.out.println("Values = " + values);

        }






//        Collection<PhoneCall> phoneCall = bill.getPhoneCalls();
//        //for (PhoneCall c : phoneCall) System.out.println(c);

        try {
            TextParser txtparser = new TextParser(file_path, name);
            //txtparser.parse();
        } catch (FileNotFoundException ex) {

        }

//        try {
//        TextDumper txtdump = new TextDumper(file_path);
//
//        } catch (ParserException e) {
//            e.printStackTrace();
//        }
        /*try {
            txtdump.dump(bill);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        if (args[0].matches("-print") || args[1].matches("-print") || args[2].matches("-print")) {
            System.out.println("Caller Information for customer : " + bill.getCustomer() + " is: " + call.toString());
        }

        System.exit(0);
    }

    private static void printErrorMessageAndExit(String s) {
        System.err.println(s);
        System.exit(1);
    }

}
