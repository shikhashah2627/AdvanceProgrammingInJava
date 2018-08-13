package edu.pdx.cs410J.shikha.server;

import edu.pdx.cs410J.shikha.client.PrettyPrinter;

public class Final_Output_Display {

    public PrettyPrinter getFinalOutput(String finalOutput) {
        return new PrettyPrinter(this.board, endTime);

    }
