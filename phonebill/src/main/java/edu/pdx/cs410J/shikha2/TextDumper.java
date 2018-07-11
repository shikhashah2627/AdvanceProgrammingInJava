package edu.pdx.cs410J.shikha2;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

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


        Collection<PhoneCall> call = bill.getPhoneCalls();

        String data = call.stream().map(Object::toString)
                .collect(Collectors.joining(", "));

        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(data);
        writer.close();


    }
}

