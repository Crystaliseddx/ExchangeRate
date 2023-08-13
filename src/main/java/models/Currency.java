package models;

public class Currency {
    private int id;
    private String code;
    private String fullname;
    private String sign;

    public void setId(int id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
