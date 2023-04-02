package com.example.shatabdi;

public class ModelDealers {
    String ShopName,DealerName,Phone;

    public ModelDealers(String ShopName,String DealerName, String Phone) {
        this.ShopName = ShopName;
        this.DealerName = DealerName;
        this.Phone = Phone;
    }

    public String getShopName() {
        return ShopName;
    }

    public void setShopName(String ShopName) {
        this.ShopName = ShopName;
    }

    public String getDealerName() {
        return DealerName;
    }

    public void setDealerName(String DealerName) {
        this.DealerName = DealerName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public ModelDealers() {
    }
}
