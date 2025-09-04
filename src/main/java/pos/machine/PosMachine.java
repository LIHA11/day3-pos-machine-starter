package pos.machine;

import java.util.*;

public class PosMachine {

    public String printReceipt(List<String> barcodes) {
        List<Item> allItems = ItemsLoader.loadAllItems();
        validateBarcodes(barcodes, allItems);
        List<Item> shoppingList = scanBarcode(barcodes, allItems);
        List<ReceiptItem> receiptItems = calculate(shoppingList);
        return printReceiptlist(receiptItems);
    }


    private void validateBarcodes(List<String> barcodes, List<Item> allItems) {
        Set<String> validBarcodeSet = new HashSet<>();
        for (Item item : allItems) {
            validBarcodeSet.add(item.getBarcode());
        }
        List<String> invalidBarcodes = new ArrayList<>();
        for (String bc : barcodes) {
            if (!validBarcodeSet.contains(bc)) {
                invalidBarcodes.add(bc);
            }
        }
        if (!invalidBarcodes.isEmpty()) {
            throw new IllegalArgumentException("Invalid barcodes found: " + invalidBarcodes);
        }
    }


    private List<Item> getShoppingList(List<Item> allItems) {
        return new ArrayList<>(allItems);
    }


    private List<ReceiptItem> getSumprice(List<Item> shoppingList) {
        Map<String, Integer> quantityMap = new LinkedHashMap<>();
        Map<String, Item> itemMap = new LinkedHashMap<>();

        for (Item item : shoppingList) {
            String barcode = item.getBarcode();
            quantityMap.put(barcode, quantityMap.getOrDefault(barcode, 0) + 1);
            itemMap.putIfAbsent(barcode, item);
        }

        List<ReceiptItem> receiptItems = new ArrayList<>();
        for (String barcode : quantityMap.keySet()) {
            Item item = itemMap.get(barcode);
            int quantity = quantityMap.get(barcode);
            receiptItems.add(new ReceiptItem(item.getName(), quantity, item.getPrice()));
        }
        return receiptItems;
    }

    private List<ReceiptItem> calculate(List<Item> allItems) {
        List<Item> shoppingList = getShoppingList(allItems);
        return getSumprice(shoppingList);
    }


    private String printItemList(List<ReceiptItem> receiptItems) {
        StringBuilder sb = new StringBuilder();
        int total = 0;

        for (ReceiptItem item : receiptItems) {
            sb.append(String.format("Name: %s, Quantity: %d, Unit price: %d (yuan), Subtotal: %d (yuan)\n",
                    item.getName(), item.getQuantity(), item.getUnitPrice(), item.getSubTotal()));
            total += item.getSubTotal();
        }

        sb.append("----------------------\n");
        sb.append(String.format("Total: %d (yuan)\n", total));

        return sb.toString();
    }

    private String printReceiptlist(List<ReceiptItem> receiptItems) {
        StringBuilder sb = new StringBuilder();
        sb.append("***<store earning no money>Receipt***\n");
        sb.append(printItemList(receiptItems));
        sb.append("**********************");
        return sb.toString();
    }


}
