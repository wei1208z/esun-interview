package com.esun.bank.model;

public class FavoriteRequest {
    private String userId;
    private Integer productNo;
    private Integer purchaseQuantity;
    private String account;

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public Integer getProductNo() { return productNo; }
    public void setProductNo(Integer productNo) { this.productNo = productNo; }
    public Integer getPurchaseQuantity() { return purchaseQuantity; }
    public void setPurchaseQuantity(Integer purchaseQuantity) { this.purchaseQuantity = purchaseQuantity; }
    public String getAccount() { return account; }
    public void setAccount(String account) { this.account = account; }
}