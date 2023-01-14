package pl.edu.agh.kis.pz1;

import java.math.BigDecimal;

public class Item {
    public String name;

    public BigDecimal quantity;
    public BigDecimal singlePrice;

    public int taxRate;
    public BigDecimal taxAmount;

    public BigDecimal totalNetPrice;
    public BigDecimal totalGrossPrice;

    public Item(String name, BigDecimal amount, BigDecimal price,
                int taxRate, BigDecimal taxAmount,
                BigDecimal itemNetValue, BigDecimal itemGrossValue) {
        this.name = name;
        this.quantity = amount;
        this.singlePrice = price;
        this.taxRate = taxRate;
        this.taxAmount = taxAmount;
        this.totalNetPrice = itemNetValue;
        this.totalGrossPrice = itemGrossValue;
    }
}
