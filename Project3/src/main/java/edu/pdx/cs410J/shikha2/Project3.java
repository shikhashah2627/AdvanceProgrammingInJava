package edu.pdx.cs410J.shikha2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public class Project3 {
    static final String README = "Author for Project 3: PhoneCall Project is : Shikha Shah. " + "\n" +
            "This project takes the command line argument of customer name , phone number of caller and callee and date and time from start to end" +
            "You can also print the the args by specifying -print option before passing the argument. " + "\n" + "Adding to that now " +
            "there is an option to read the bill from text file or append the existing bill with new phone call by providing -textFile option." +
            "Adding to Project 2, now you can get user friendly data in a sorted manner by providing -pretty and if you want it in file provide 'filename' or if stadnard out "+
            "just provide '-' after -pretty command.";


    /**
     * <code>main</code>
     * Invokes main class where all the arguments are parsed.
     *
     * @param args args contains caller_name, caller_number, callee_number, start_date, start_time, end_date, end_time
     */

    public static void main(String[] args) {

        String name, caller_number, callee_number, start_date, start_time, end_date, end_time, file_path, start_AMorPM, end_AMorPM,pretty_file;
        name = caller_number = callee_number = start_date = start_time = end_date = end_time = file_path = start_AMorPM = end_AMorPM = pretty_file = null;
        Boolean x = false;
        if (args.length == 0) {
            System.err.println("Missing command line arguments");
            System.exit(1);
        }

        if (containsOption(args, "-README")) {
            System.out.println(README);
            System.exit(0);
        }

        for (String arg : args) {
            if (arg.startsWith("-")) {
                if ((arg.matches("-print") || arg.matches("-textFile") || arg.matches("-pretty") || arg.matches("-"))) {
                } else {
                    System.out.println("Your -options are inappropriate!");
                    System.exit(0);
                }
            }
            if(arg.matches("-pretty")) {
                x = true;
                continue;
            }

            if (containsOption(args, "-pretty") && pretty_file == null && x) {
                pretty_file = arg;
                continue;
            }
            if (arg.matches("-print")) {
                continue;
            }
            if (arg.matches("-textFile")) {
                continue;
            }
            if (containsOption(args, "-textFile") && file_path == null) {
                file_path = arg;
                //check_file(file_path);
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
            } else if (start_AMorPM == null) {
                if (arg.toLowerCase().matches("am") || arg.toLowerCase().matches("pm")) {
                    start_AMorPM = arg;
                } else {
                    System.out.println("Your 12-hour format for start time is not specified properly!");
                    System.exit(0);
                }
            } else if (end_date == null) {
                end_date = arg;
            } else if (end_time == null) {
                end_time = arg;
            } else if ( end_AMorPM == null) {
                if (arg.toLowerCase().matches("am") || arg.toLowerCase().matches("pm")) {
                    end_AMorPM = arg;
                } else {
                    System.out.println("Your 12-hour format is not specified properly!");
                    System.exit(0);
                }
            } else {
                System.out.println("Wrong input is provided");
                System.exit(0);
            }
        }


        Validation val = new Validation(name, caller_number, callee_number, start_date, start_time, end_date, end_time, start_AMorPM, end_AMorPM);
        PhoneCall  new_call  = new PhoneCall(val);

        if (file_path != null) {
            TextParser txtParser = new TextParser(file_path, name);

            try {
                if (caller_number != null) {

                    TextDumper txtDumper = new TextDumper(file_path, new_call);
                    TextDumper.check_file(file_path);
                    txtDumper.dump(txtParser.parse());
                    txtParser.parse();

                    if(containsOption(args, "-pretty") && pretty_file.matches("-")) {
                        PrettyPrinter prettyPrinter = new PrettyPrinter(val.name);
                        prettyPrinter.dump(txtParser.sorted());
                    } else if(containsOption(args, "-pretty") && pretty_file != ""){
                        PrettyPrinter prettyPrinter = new PrettyPrinter(pretty_file,val.name);
                        prettyPrinter.dump(txtParser.sorted());
                    }

                } else {
                    txtParser.parse();
                }
            } catch (ParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (containsOption(args, "-print")) {
                PhoneBill bill = new  PhoneBill(name);
                try {
                    TextParser txtParser1 = new TextParser(file_path, name);
                    bill = (PhoneBill) txtParser1.parse();
                    Collection<PhoneCall> phoneCall = bill.getPhoneCalls();
                    for (PhoneCall c : phoneCall) System.out.println(c);
                } catch (ParserException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (containsOption(args, "-print")) {
                PhoneBill bill = new PhoneBill(name);
                bill.addPhoneCall(new_call);
                System.out.println("Caller Information for customer : " + bill.getCustomer() + " is: " + new_call.toString());
            }

        }



        System.exit(0);
    }
    /**
     * <code>containsOption</code>
     * @param args
     * @param option
     * @return list of all the options passed in the argument.
     */
    private static boolean containsOption(String[]args, String option){
        return Arrays.stream(args).anyMatch(s -> s.equals(option));
    }
}


