package dto;

import models.Currency;

public class ExchangeRateDTO {
    private Integer id;
    private Currency baseCurrency;
    private Currency targetCurrency;
    private Double rate;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRate(Double rate) {
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
