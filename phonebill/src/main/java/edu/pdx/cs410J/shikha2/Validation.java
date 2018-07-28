package edu.pdx.cs410J.shikha2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Validation {
    String name, caller_number, callee_number, start_date_time, end_date_time;
    Date start_date, end_date;


    /**
     * <code>Validation</code>
     *
     * Before inserting a new phone call or validate the existing bill objects are passed to validation class.
     *
     * @param name - customer name
     * @param Caller_Number  - Caller Number
     * @param Callee_Number - Callee Number
     * @param Start_Date - Start date of the call
     * @param Start_Time - Time when call started
     * @param End_Date - End Date of the call received.
     * @param End_time - Time when the call ended.
     * @param start_AMorPM - Start Time mode - 12 hour format
     * @param end_AMorPM - Start Time mode - 12 hour format
     */
    public Validation(String name, String Caller_Number, String Callee_Number, String Start_Date, String Start_Time, String End_Date, String End_time, String start_AMorPM, String end_AMorPM) {
        String name_pattern   = "([a-zA-Z0-9] ?)+[a-zA-Z0-9]";
        String Number_pattern = "^\\d\\d\\d-\\d\\d\\d-\\d\\d\\d\\d";

        try {
            if (name.matches(name_pattern)) {
                this.name = name;
            } else throw new UnsupportedOperationException("Insert Proper Name and no special characters are allowed.");

            if (Caller_Number.matches(Number_pattern)) {
                this.caller_number = Caller_Number;
            } else
                throw new UnsupportedOperationException("Valid Phone Number of Caller should be in format of XXX-XXX-XXXX");

            if (Callee_Number.matches(Number_pattern)) {
                this.callee_number = Callee_Number;
            } else
                throw new UnsupportedOperationException("Valid Phone Number of Callee should be in format of XXX-XXX-XXXX");

            boolean parsed = false;
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                start_date_time = Start_Date + " " + Start_Time + " " + start_AMorPM;
                end_date_time = End_Date + " " + End_time + " " + end_AMorPM;
                sdf.setLenient(false);
                this.start_date = sdf.parse(start_date_time);
                this.end_date = sdf.parse(end_date_time);

                //if((sdf.parse(start_date_time).getTime() - now.getTime()) <= 0 || (sdf.parse(end_date_time).getTime() - now.getTime()) <= 0 ) {
                if(((sdf.parse(start_date_time).getTime() - sdf.parse(end_date_time).getTime()) <= 0)) { //checks end time > start time
                    //System.out.println("Start Date and End Dates are fine..");
                    this.start_date = sdf.parse(sdf.format(start_date).toLowerCase());
                    this.end_date = sdf.parse((sdf.format(end_date).toLowerCase()));
                } else {
                    System.out.println("Your start date or End date is highly impossible..");
                    System.exit(0);
                }
            } catch (ParseException ex) {
                System.out.println(ex);
                System.exit(0);
            }


        } catch (UnsupportedOperationException ex) {
            System.out.println(ex);
            System.exit(0);
        }
    }
}