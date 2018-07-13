package edu.pdx.cs410J.shikha2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.Collection;

public class TextParser implements edu.pdx.cs410J.PhoneBillParser {

    String filename = "";
    String customer_name = "";

    public TextParser(String filename, String customer_name) throws FileNotFoundException {
        this(new File(filename));
        this.customer_name = customer_name;
    }

    /**
     * Creates a new text parser that reads its input from the given
     * file.
     */
    public TextParser(File file) throws FileNotFoundException {
        this(new FileReader(file));
    }

    public TextParser(FileReader fileReader) {
    }

    /**
     * Parses some source and returns a phone bill
     *
     * @throws ParserException If the source cannot be parsed
     */
    @Override
    public AbstractPhoneBill parse() throws ParserException {
        return null;
    }
}
