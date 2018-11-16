package Domain;

public class ProductPurchase {

    public Product product;
    public float price;
    public int amount;

    public ProductPurchase( Product product, float price, int amount) {
        this.product = product;
        this.price = price;
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
