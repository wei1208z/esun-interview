package com.esun.bank.service;

import com.esun.bank.model.FavoriteDTO;
import com.esun.bank.model.FavoriteRequest;
import com.esun.bank.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    public List<FavoriteDTO> getFavoriteList() { return favoriteRepository.callGetFavoriteList(); }
    public List<Map<String, Object>> getAllProducts() { return favoriteRepository.callGetAllProducts(); }
    public Map<String, Object> getFavoriteById(Integer sn) { return favoriteRepository.callGetFavoriteById(sn); }

    @Transactional(rollbackFor = Exception.class)
    public void addFavorite(FavoriteRequest request) {
        Map<String, Object> product = favoriteRepository.findProductById(request.getProductNo());
        if (product == null) throw new IllegalArgumentException("找不到該金融商品");

        BigDecimal price = (BigDecimal) product.get("Price");
        BigDecimal feeRate = (BigDecimal) product.get("FeeRate");
        BigDecimal quantity = new BigDecimal(request.getPurchaseQuantity());

        BigDecimal totalFee = price.multiply(quantity).multiply(feeRate).setScale(0, RoundingMode.HALF_UP);
        BigDecimal totalAmount = price.multiply(quantity).add(totalFee);

        Map<String, Object> params = new HashMap<>();
        params.put("userId", request.getUserId());
        params.put("productNo", request.getProductNo());
        params.put("quantity", request.getPurchaseQuantity());
        params.put("account", request.getAccount());
        params.put("totalFee", totalFee);
        params.put("totalAmount", totalAmount);

        favoriteRepository.callInsertFavorite(params);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateFavorite(Integer sn, FavoriteRequest request) {
        Map<String, Object> currentFavorite = favoriteRepository.callGetFavoriteById(sn);
        if (currentFavorite == null) throw new IllegalArgumentException("找不到紀錄");
        Integer productNo = (Integer) currentFavorite.get("productNo");

        Map<String, Object> product = favoriteRepository.findProductById(productNo);
        BigDecimal price = (BigDecimal) product.get("Price");
        BigDecimal feeRate = (BigDecimal) product.get("FeeRate");
        BigDecimal quantity = new BigDecimal(request.getPurchaseQuantity());

        BigDecimal totalFee = price.multiply(quantity).multiply(feeRate).setScale(0, RoundingMode.HALF_UP);
        BigDecimal totalAmount = price.multiply(quantity).add(totalFee);

        Map<String, Object> params = new HashMap<>();
        params.put("sn", sn);
        params.put("quantity", request.getPurchaseQuantity());
        params.put("account", request.getAccount());
        params.put("totalFee", totalFee);
        params.put("totalAmount", totalAmount);

        favoriteRepository.callUpdateFavorite(params);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteFavorite(Integer sn) { favoriteRepository.callDeleteFavorite(sn); }
}