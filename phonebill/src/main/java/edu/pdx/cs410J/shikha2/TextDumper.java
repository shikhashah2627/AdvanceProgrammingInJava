package edu.pdx.cs410J.shikha2;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.io.*;
import java.text.*;
import java.util.*;
import java.util.Collection;
import java.util.stream.Collectors;

public class TextDumper implements edu.pdx.cs410J.PhoneBillDumper {

    private PrintWriter pw;      // Dumping destination

    /**
     * Creates a new text dumper that dumps to a file of a given name.
     * If the file does not exist, it is created.
     */
    public TextDumper(String fileName) throws IOException {
        this(new File(fileName));
    }


    /**
     * Creates a new text dumper that dumps to a given file.
     */
    public TextDumper(File file) throws IOException {
        this(new PrintWriter(new FileWriter(file), true));
    }

    /**
     * Creates a new text dumper that prints to a
     * <code>PrintWriter</code>.  This way, we can dump to destinations
     * other than files.
     */
    public TextDumper(PrintWriter pw) {
        this.pw = pw;
    }

    /**
     * Dumps a phone bill to some destination.
        *
     * @param bill
     */
    @Override
    public void dump(AbstractPhoneBill bill) throws IOException {

        Set<PhoneCall> call = new HashSet<PhoneCall>();
        Iterator iter = bill.getPhoneCalls().iterator();


        int lines = 0;
        while (iter.hasNext()) {
            PhoneBill bill1 = (PhoneBill) iter.next();
            StringBuffer data = new StringBuffer();
            data.append("id" + bill1.getCustomer());


        }

        //Collection<PhoneCall> call = bill.getPhoneCalls();

        String data = call.stream().map(Object::toString)
                .collect(Collectors.joining(", "));

        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(data);
        writer.close();


    }
}

