package dto;

import models.Currency;

public class ExchangeRateDTO {
    private int id;
    private Currency baseCurrency;
    private Currency targetCurrency;
    private double rate;

    public void setId(int id) {
        this.id = id;
    }

    public void setRate(double rate) {
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

    public double getRate() {
        return rate;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public Currency getTargetCurrency() {
        return targetCurrency;
    }
}
