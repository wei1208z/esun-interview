package com.esun.bank.controller;

import com.esun.bank.model.FavoriteDTO;
import com.esun.bank.model.FavoriteRequest;
import com.esun.bank.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping
    public ResponseEntity<List<FavoriteDTO>> getAllFavorites() {
        return ResponseEntity.ok(favoriteService.getFavoriteList());
    }

    @GetMapping("/products")
    public ResponseEntity<List<Map<String, Object>>> getAllProducts() {
        return ResponseEntity.ok(favoriteService.getAllProducts());
    }

    @GetMapping("/{sn}")
    public ResponseEntity<Map<String, Object>> getFavoriteById(@PathVariable Integer sn) {
        return ResponseEntity.ok(favoriteService.getFavoriteById(sn));
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> addFavorite(@RequestBody FavoriteRequest request) {
        favoriteService.addFavorite(request);
        return ResponseEntity.ok(Map.of("message", "新增成功"));
    }

    @PutMapping("/{sn}")
    public ResponseEntity<Map<String, String>> updateFavorite(@PathVariable Integer sn, @RequestBody FavoriteRequest request) {
        favoriteService.updateFavorite(sn, request);
        return ResponseEntity.ok(Map.of("message", "修改成功"));
    }

    @DeleteMapping("/{sn}")
    public ResponseEntity<Map<String, String>> deleteFavorite(@PathVariable Integer sn) {
        favoriteService.deleteFavorite(sn);
        return ResponseEntity.ok(Map.of("message", "刪除成功"));
    }
}