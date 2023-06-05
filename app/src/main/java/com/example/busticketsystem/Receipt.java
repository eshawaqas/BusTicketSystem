package com.example.busticketsystem;

public class Receipt {
    private String receiptId;
    private String imageUrl;

    public Receipt() {
        // Empty constructor required for Firebase
    }

    public Receipt(String receiptId, String imageUrl) {
        this.receiptId = receiptId;
        this.imageUrl = imageUrl;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
