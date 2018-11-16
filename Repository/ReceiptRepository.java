package Repository;

import Domain.Product;
import Domain.ProductPurchase;
import Domain.Receipt;
import Service.ReceiptService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReceiptRepository extends Dbtools{

    private ReceiptService receiptService = new ReceiptService();
    private ProductPurchase productPurchase;


    public void SaveReceipt(Receipt receipt){

        makeJDBCConnection();

        try{
            String ReceiptId = receipt.getReceiptId();

            for (ProductPurchase products:receipt.getProductsPurchase()) {

                String getQueryStatement = "SELECT ProductId FROM  products WHERE ProductName = ?";
                prepareStat = varConn.prepareStatement(getQueryStatement);

                prepareStat.setString(1, products.getProduct().getProductName());

                ResultSet rs = prepareStat.executeQuery();

                int ProductId = 0;

                if( rs.isBeforeFirst() ){

                    if (rs.next()) {
                        ProductId = rs.getInt("ProductId");
                    }

                } else {

                    String query = "INSERT INTO products (ProductName) VALUES (?);";

                    prepareStat = varConn.prepareStatement(query);

                    prepareStat.setString(1, products.getProduct().getProductName());

                    prepareStat.executeUpdate();

                    ResultSet keys = prepareStat.getGeneratedKeys();

                    ProductId = 1;
                    while (keys.next()) {
                        ProductId = keys.getInt(1);
                    }
                }

                String queryStatement = "INSERT INTO productpuchases (ProductId, Price, Amout, ReceiptId) VALUES (?, ?, ?, ?)";

                prepareStat = varConn.prepareStatement(queryStatement);

                prepareStat.setInt(1, ProductId);
                prepareStat.setFloat(2, products.getPrice());
                prepareStat.setInt(3, products.getAmount());
                prepareStat.setString(4, receipt.getReceiptId());

                prepareStat.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(varConn);
        }
    }

    public Receipt getReceipt(String receiptId){

        makeJDBCConnection();
        Receipt receipt = null;

        try {
            String query = "SELECT A.ProductId, (SELECT ProductName from products where ProductId = A.ProductId) as ProductName, Price, Amout FROM productpuchases as A  WHERE ReceiptId = ?";

            prepareStat = varConn.prepareStatement(query);
            prepareStat.setString(1, receiptId);

            ResultSet rs = prepareStat.executeQuery();

            List<ProductPurchase> productPurchase = new ArrayList<ProductPurchase>();

            while (rs.next()) {

                System.out.println(receiptId);
                int productId = rs.getInt("ProductId");
                String productName = rs.getString("ProductName");
                float price = rs.getFloat("Price");
                int amount = rs.getInt("Amout");

                Product product = new Product(productId, productName);
                ProductPurchase pp = new ProductPurchase(product, price, amount);
                productPurchase.add(pp);
            }

             receipt = new Receipt( receiptId, productPurchase );

            return receipt;

        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            closeConnection(varConn);
        }

        return receipt;
    }
}
