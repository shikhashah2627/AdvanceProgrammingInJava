package edu.pdx.cs410J.shikha2;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TextDumper implements edu.pdx.cs410J.PhoneBillDumper {
    public String filename;

    public TextDumper(String file_name) {
        this.filename = file_name;
    }

    /**
     * Dumps a phone bill to some destination.
     *
     * @param bill
     */
    @Override
    public void dump(AbstractPhoneBill bill) throws IOException {

        /*
        Collection<PhoneCall> call = bill.getPhoneCalls();

        String data = call.stream().map(Object::toString)
                .collect(Collectors.joining(", "));

        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(data);
        writer.close();

       */
        String line = "";
        String caller_number, callee_number, start_date, start_time, end_date, end_time;
        caller_number = callee_number = start_date = start_time = end_date = end_time = "";
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
            caller_number = regexm(line, " to");

        }

        // Always close files.
        reader.close();
    }
}

