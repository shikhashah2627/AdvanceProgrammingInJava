package edu.pdx.cs410J.shikha2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Pattern;

public class TextParser implements edu.pdx.cs410J.PhoneBillParser {
    /**
     * Parses some source and returns a phone bill
     *
     * @throws ParserException If the source cannot be parsed
     */
    @Override
    public AbstractPhoneBill parse() throws ParserException {

        String line = "";
        String caller_number, callee_number, start_date, start_time, end_date, end_time;
        caller_number = callee_number = start_date = start_time = end_date = end_time = "";
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String regex = "Phone call from \\S+ to \\S+ from \\S+ to \\S+ ";
        Pattern pattern = Pattern.compile(regex);
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            /*
            Matcher matcher = pattern.matcher( line );
            if( matcher.find() ) {
            System.out.println("Found: "+matcher.group() );
            for( int i = 1; i <= matcher.groupCount(); i++ ) {
                System.out.println( "   Match " + i + ": " + matcher.group( i ));
            }
        }*/
            caller_number = line.substring(15, 28).trim();
            callee_number = line.substring(32, 44).trim();
            start_date = line.substring(50, 60).trim();
            start_time = line.substring(61, 66).trim();
            end_date = line.substring(69, 79).trim();
            end_time = line.substring(80, 86).trim();
            //System.out.println(caller_number + " " + callee_number + " " + start_date + " " + start_time + " " + end_date + " " +end_time);
            PhoneCall call_object = new PhoneCall(caller_number, callee_number, start_date, start_time, end_date, end_time);
            bill.addPhoneCall(call_object);
        }

        // Always close files.
        reader.close();
        return null;
    }
}
