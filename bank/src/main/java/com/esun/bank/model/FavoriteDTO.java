package com.esun.bank.model;

import java.math.BigDecimal;

public class FavoriteDTO {
    private Integer sn;
    private String productName;
    private String account;
    private BigDecimal totalAmount;
    private BigDecimal totalFee;
    private String email;

    public Integer getSn() { return sn; }
    public void setSn(Integer sn) { this.sn = sn; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public String getAccount() { return account; }
    public void setAccount(String account) { this.account = account; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public BigDecimal getTotalFee() { return totalFee; }
    public void setTotalFee(BigDecimal totalFee) { this.totalFee = totalFee; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}