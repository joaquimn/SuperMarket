package Domain;

import java.util.List;

public class Receipt {
    public String receiptId;
    public List<ProductPurchase> productsPurchases;

    public Receipt(String receiptId, List<ProductPurchase> productsPurchases) {
        this.receiptId = receiptId;
        this.productsPurchases = productsPurchases;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {

        System.out.println("set function");


        this.receiptId = receiptId;


    }

    public List<ProductPurchase> getProductsPurchase() {
        return productsPurchases;
    }

    public void setProductsPurchase(List<ProductPurchase> productsPurchases) {
        this.productsPurchases = productsPurchases;
    }
}



