package pl.edu.agh.kis.pz1;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Wrong number of arguments");
        }

        var inputFileName = args[0];
        var outputFileName = args[1];

        var reader = new CSVReader("input.csv");
        reader.read();

        System.out.println(reader.getInvoices());

    }
}
