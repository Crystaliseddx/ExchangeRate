package models;

public class ExchangeRate {
    private int id;
    private String baseCurrencyId;
    private String targetCurrencyId;
    private double rate;

    public void setId(int id) {
        this.id = id;
    }

    public void setBaseCurrencyId(String baseCurrencyId) {
        this.baseCurrencyId = baseCurrencyId;
    }

    public void setTargetCurrencyId(String targetCurrencyId) {
        this.targetCurrencyId = targetCurrencyId;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
