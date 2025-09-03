package pos.machine;

public class ReceiptItem {
    private final String name;
    private final int quantity;
    private final int unitPrice;
    private final int subTotal;

    public ReceiptItem(String name, int quantity, int unitPrice) {
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subTotal = quantity * unitPrice;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public int getSubTotal() {
        return subTotal;
    }
}