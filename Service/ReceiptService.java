package Service;

import Domain.Product;
import Domain.ProductPurchase;
import Domain.Receipt;
import Repository.ReceiptRepository;
import java.text.DecimalFormat;
import java.util.*;

public class ReceiptService {

    protected static DecimalFormat df2 = new DecimalFormat(".##");
    static  ReceiptRepository receiptRepository = new ReceiptRepository();

    public void index(){

        System.out.println("1 - to register a purchase");
        System.out.println("2 - to print a receipt");

        Scanner sc = new Scanner(System.in);

        int option = sc.nextInt();

        if(option == 1 ) {

            Receipt receipt = purchase();

            printReceipt(receipt);

        } else if(option == 2){

            System.out.println("Enter the receipt's number: ");

            String receiptId = sc.next();

            Receipt receipt = getReceipt(receiptId);

            printReceipt(receipt);

        } else {
            System.out.println("Invalid data");
        }

    }

    public Receipt getReceipt(String receiptId){

        Receipt receipt = receiptRepository.getReceipt(receiptId);
        return receipt;
    }

    public Receipt purchase(){

        String receiptId = UUID.randomUUID().toString();
        List<ProductPurchase> productPurchase = new ArrayList<ProductPurchase>();

        Scanner sc = new Scanner(System.in);
        int productLoop = 10000;

        for(int i = 0; i<productLoop; i++){

            System.out.print("Product's name: ");
            String name = sc.next();

            System.out.print("Product's price: ");
            float price = sc.nextFloat();

            System.out.print("Amount: ");
            int amount = sc.nextInt();

            Product product = new Product(i, name);
            ProductPurchase pP = new ProductPurchase(product, price, amount);

            productPurchase.add(pP);

            System.out.println("Do you like to register another product? (Y/N)");
            String exit = sc.next();
            exit = exit.toUpperCase();

            if(exit.equals("N")){
                i=productLoop;
            }
        }

        Receipt receipt = new Receipt(receiptId, productPurchase);

        receiptRepository.SaveReceipt(receipt);

        return receipt;
    }

    public void printReceipt(Receipt receipt){

        float total = 0;
        double gst;
        double pst;

        System.out.println("Receipt number: "+receipt.getReceiptId());
        System.out.println("Product  -  Price  -  Amount  -  Total");
        for (ProductPurchase products:receipt.getProductsPurchase()) {

            System.out.println(products.getProduct().getProductName()+"  -  "+products.getPrice()+"  -  "+products.getAmount()+"  -  "+(products.getAmount()*products.getPrice()));
            total = total + (products.getPrice() * products.getAmount());
        }

        System.out.println("Total: "+df2.format(total));

        gst = total*0.05;
        System.out.println("GST: "+df2.format(gst));

        pst = total*0.08;
        System.out.println("PST: "+df2.format(pst));

        System.out.println("Total: "+df2.format((total+gst+pst)));
    }
}
