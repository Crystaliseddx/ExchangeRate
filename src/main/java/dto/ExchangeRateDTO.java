package dto;

import models.Currency;

import java.math.BigDecimal;

public class ExchangeRateDTO {
    private int id;
    private Currency baseCurrency;
    private Currency targetCurrency;
    private BigDecimal rate;

    public void setId(int id) {
        this.id = id;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public void setBaseCurrency(Currency baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public void setTargetCurrency(Currency targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public Currency getTargetCurrency() {
        return targetCurrency;
    }
}
