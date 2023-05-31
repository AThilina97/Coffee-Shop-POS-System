package lk.ijse.dep10.app.model;

import java.io.Serializable;

public class CoffeeDetailsDashBord implements Serializable {
    private String code;
    private String name;
    private String unitPrice;
    private String quantyty;
    private String total;

    public CoffeeDetailsDashBord() {
    }

    public CoffeeDetailsDashBord(String code, String name, String unitPrice, String quantyty, String total) {
        this.code = code;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantyty = quantyty;
        this.total = total;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getQuantyty() {
        return quantyty;
    }

    public void setQuantyty(String quantyty) {
        this.quantyty = quantyty;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
