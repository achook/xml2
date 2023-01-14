package pl.edu.agh.kis.pz1;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

public class XLSXReader {
    private final String fileName;
    private final FileInputStream fileStream;
    private final ArrayList<Invoice> invoices;

    public XLSXReader(String f) throws FileNotFoundException {
        fileName = f;
        fileStream = new FileInputStream(fileName);
        invoices = new ArrayList<>();
    }

    public void read() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(fileStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> records = sheet.iterator();
        records.next();

        ArrayList<String> parsedInvoices = new ArrayList<>();

        for (Row record : sheet) {
            String product = record.getCell(6).getStringCellValue();
            var amount = new BigDecimal(record.getCell(7).getStringCellValue());
            var price = new BigDecimal(record.getCell(8).getStringCellValue()
                    .replace(" ", "")
                    .replace("zł", "")
                    .replace(",", "."));
            int taxRate = (int) record.getCell(9).getNumericCellValue();
            var taxAmount = new BigDecimal(record.getCell(10).getStringCellValue()
                    .replace(" ", "")
                    .replace("zł", "")
                    .replace(",", "."));
            var itemNetValue = new BigDecimal(record.getCell(11).getStringCellValue()
                    .replace(" ", "")
                    .replace("zł", "")
                    .replace(",", "."));
            var itemGrossValue = new BigDecimal(record.getCell(12).getStringCellValue()
                    .replace(" ", "")
                    .replace("zł", "")
                    .replace(",", "."));

            var item = new Item(product, amount, price, taxRate, taxAmount, itemNetValue, itemGrossValue);

            String invoiceNumber = record.getCell(5).getStringCellValue();
            if (!parsedInvoices.contains(invoiceNumber)) {
                parsedInvoices.add(invoiceNumber);

                String companyName = record.getCell(0).getStringCellValue();
                String address = record.getCell(1).getStringCellValue();
                String nip = record.getCell(2).getStringCellValue();
                String date = record.getCell(3).getStringCellValue();
                String sellDate = record.getCell(4).getStringCellValue();

                var netValue = new BigDecimal(record.getCell(13).getStringCellValue()
                        .replace(" ", "")
                        .replace("zł", "")
                        .replace(",", "."));
                var grossValue = new BigDecimal(record.getCell(14).getStringCellValue()
                        .replace(" ", "")
                        .replace("zł", "")
                        .replace(",", "."));

                var invoice = new Invoice(companyName, address, nip, date, sellDate, invoiceNumber,
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
}
