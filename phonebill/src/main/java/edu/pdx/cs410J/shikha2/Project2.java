package edu.pdx.cs410J.shikha2;

import edu.pdx.cs410J.ParserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

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
                arg = args[+1];
                continue;
            }

            if (name == null) {
                name = arg;
            } else if (caller_number == null) {
                caller_number = arg;
            } else if (callee_number == null) {
                callee_number = arg;
            } else if (start_date == null) {
                start_date = arg;
            } else if (start_time == null) {
                start_time = arg;
            } else if (end_date == null) {
                end_date = arg;
            } else if (end_time == null) {
                end_time = arg;
            }
        }

        Validation val = new Validation(name, caller_number, callee_number, start_date, start_time, end_date, end_time);

        TextParser txtParser = new TextParser(file_path,name);

        try {
            if (caller_number != null) {
                PhoneCall new_call = new PhoneCall(val);
                TextDumper txtDumper = new TextDumper(file_path,new_call);
                txtDumper.dump(txtParser.parse());

            } else {
                txtParser.parse();
            }
        } catch (ParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Collection<PhoneCall> phoneCall = bill.getPhoneCalls();
//        //for (PhoneCall c : phoneCall) System.out.println(c);

       // if (args[0].matches("-print") || args[1].matches("-print") || args[2].matches("-print")) {
       //     System.out.println("Caller Information for customer : " + bill.getCustomer() + " is: " + call.toString());
     //   }

        System.exit(0);
    }

    private static void printErrorMessageAndExit(String s) {
        System.err.println(s);
        System.exit(1);
    }

}
