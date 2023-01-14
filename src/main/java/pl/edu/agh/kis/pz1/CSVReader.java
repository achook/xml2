package pl.edu.agh.kis.pz1;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

public class CSVReader {
    private final String fileName;
    private final FileReader fileReader;
    private final ArrayList<Invoice> invoices;

    public CSVReader(String f) throws FileNotFoundException {
        fileName = f;
        fileReader = new FileReader(fileName);
        invoices = new ArrayList<>();
    }

    public void read() throws IOException {//wczytanie pliku CSV
        Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter('\t').parse(fileReader);
        records.iterator().next();//pominięcie pierwszej linii

        ArrayList<String> parsedInvoices = new ArrayList<>();

        for (CSVRecord record : records) {
            String product = record.get(6);
            BigDecimal amount = new BigDecimal(record.get(7)
                    .replace(",", "."));
            BigDecimal price = new BigDecimal(record.get(8)
                    .replace(" ", "")
                    .replace("zł", "")
                    .replace(",", "."));

            int taxRate = Integer.parseInt(record.get(9));
            BigDecimal taxAmount = new BigDecimal(record.get(10)
                    .replace(" ", "")
                    .replace("zł", "")
                    .replace(",", "."));

            BigDecimal itemNetValue = new BigDecimal(record.get(11)
                    .replace(" ", "")
                    .replace("zł", "")
                    .replace(",", "."));
            BigDecimal itemGrossValue = new BigDecimal(record.get(12)
                    .replace(" ", "")
                    .replace("zł", "")
                    .replace(",", "."));

            var item = new Item(product, amount, price, taxRate, taxAmount, itemNetValue, itemGrossValue);

            String invoiceNumber = record.get(5);
            if (!parsedInvoices.contains(invoiceNumber)) {
                parsedInvoices.add(invoiceNumber);

                String companyName = record.get(0);
                String address = record.get(1);
                String nip = record.get(2);
                String date = record.get(3);
                String sellDate = record.get(4);

                BigDecimal netValue = new BigDecimal(record.get(13)
                        .replace(" ", "")
                        .replace("zł", "")
                        .replace(",", "."));
                BigDecimal grossValue = new BigDecimal(record.get(14)
                        .replace(" ", "")
                        .replace("zł", "")
                        .replace(",", "."));

                Invoice invoice = new Invoice(companyName, address, nip, date, sellDate, invoiceNumber,
                        netValue, grossValue);

                invoice.addItem(item);
                invoices.add(invoice);
                continue;
            }

            for (Invoice invoice : invoices) {
                if (invoice.number.equals(invoiceNumber)) {
                    invoice.addItem(item);
                    break;
                }
            }
        }
    }

    public ArrayList<Invoice> getInvoices() {
        return invoices;
    }
}
