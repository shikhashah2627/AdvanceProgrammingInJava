package edu.pdx.cs410J.shikha2;

import edu.pdx.cs410J.AbstractPhoneBill;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collection;

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
//        String customer_name = bill.getCustomer();
//        System.out.println(customer_name);
//        FileWriter fileWriter = new FileWriter(filename);
//        PrintWriter printWriter = new PrintWriter(fileWriter);
//        printWriter.print(customer_name);
//        PrintStream console = System.out;
        Collection<PhoneCall> calls = bill.getPhoneCalls();
        //PrintWriter p = new PrintWriter(filename);
        /*List<PhoneCall> list = new ArrayList<>();

        for (PhoneCall c : calls ) {
            list.add(c);
        }*/
        FileOutputStream fos = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(calls);
        oos.close();
           /* try {
            FileWriter writer = new FileWriter(filename, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            for (PhoneCall c : calls ) {
                 input = c;
                //System.out.println(c);
                bufferedWriter.write(c);

            }

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
*/


        //printWriter.close();

    }
}
