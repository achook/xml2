package pl.edu.agh.kis.pz1;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Invoice {
    public String companyName;
    public String companyAddress;
    public String companyNIP;

    public String invoiceDate;
    public String sellDate;

    public String number;

    public BigDecimal totalNetPrice;
    public BigDecimal totalGrossPrice;

    public ArrayList<Item> items;

    public Invoice(String companyName, String address, String nip,
                   String date, String sellDate, String invoiceNumber,
                   BigDecimal totalNetPrice, BigDecimal totalGrossPrice) {
        this.companyName = companyName;
        this.companyAddress = address;
        this.companyNIP = nip;

        this.invoiceDate = date;
        this.sellDate = sellDate;

        this.number = invoiceNumber;

        this.totalNetPrice = totalNetPrice;
        this.totalGrossPrice = totalGrossPrice;

        this.items = new ArrayList<>();
    }

    public void addItem (Item item) {
        items.add(item);
    }
}
