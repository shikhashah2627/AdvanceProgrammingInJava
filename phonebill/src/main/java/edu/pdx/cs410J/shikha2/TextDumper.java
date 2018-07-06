package edu.pdx.cs410J.shikha2;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.io.File;
import java.io.IOException;

public class TextDumper implements edu.pdx.cs410J.PhoneBillDumper {
    public String file_name = "";

    /**
     * Dumps a phone bill to some destination.
     *
     * @param bill
     */
    @Override
    public void dump(AbstractPhoneBill bill) throws IOException {
        String customer_name = bill.getCustomer();
        System.out.println(customer_name);
        File f = new File(getFile_name());
        //f.open();

    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }
}
