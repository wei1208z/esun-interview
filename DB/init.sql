CREATE DATABASE IF NOT EXISTS esun_bank_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE esun_bank_db;

-- 1. 建立 User 使用者資料表 (欄位符合規格) [cite: 35, 36]
CREATE TABLE IF NOT EXISTS User (
    UserID VARCHAR(50) PRIMARY KEY COMMENT '使用者ID(Primary Key)',
    UserName VARCHAR(50) NOT NULL COMMENT '使用者名稱',
    Email VARCHAR(100) NOT NULL COMMENT '使用者電子郵件',
    Account VARCHAR(50) NOT NULL COMMENT '扣款帳號'
);

-- 2. 建立 Product 產品資料表 (欄位符合規格) [cite: 43, 44]
CREATE TABLE IF NOT EXISTS Product (
    No INT AUTO_INCREMENT PRIMARY KEY COMMENT 'Primary Key 產品流水號',
    ProductName VARCHAR(100) NOT NULL COMMENT '產品名稱',
    Price DECIMAL(10, 2) NOT NULL COMMENT '產品價格',
    FeeRate DECIMAL(5, 4) NOT NULL COMMENT '手續費率(%) e.g. 0.1 代表 10%'
);

-- 3. 建立 LikeList 喜好清單資料表 (欄位符合規格) [cite: 45, 46]
CREATE TABLE IF NOT EXISTS LikeList (
    SN INT AUTO_INCREMENT PRIMARY KEY COMMENT 'Primary Key 流水序號',
    UserID VARCHAR(50) NOT NULL,
    ProductNo INT NOT NULL,
    PurchaseQuantity INT NOT NULL COMMENT '購買數量',
    Account VARCHAR(50) NOT NULL COMMENT '扣款帳號',
    TotalFee DECIMAL(10, 2) NOT NULL COMMENT '總手續費用(台幣計價)',
    TotalAmount DECIMAL(10, 2) NOT NULL COMMENT '預計扣款總金額',
    FOREIGN KEY (UserID) REFERENCES User(UserID),
    FOREIGN KEY (ProductNo) REFERENCES Product(No)
);

-- 4. 寫入初始範例資料 [cite: 37, 38, 39, 40, 41, 44]
INSERT INTO User (UserID, UserName, Email, Account) 
VALUES ('A1236456789', '王明', 'test@email.com', '1111999666')
ON DUPLICATE KEY UPDATE UserID=UserID;

INSERT INTO Product (ProductName, Price, FeeRate) 
VALUES ('玉山優勢基金', 10000.00, 0.0100), 
       ('黃金現貨商品', 50000.00, 0.0050);


-- ========================================================
-- STORED PROCEDURES (符合題目要求透過 SP 存取資料庫之規格) [cite: 28]
-- ========================================================

DELIMITER //

-- 功能 1：查詢喜好金融商品清單 (包含產品名稱、帳號、總金額、總手續費、信箱) [cite: 13, 14]
DROP PROCEDURE IF EXISTS sp_GetFavoriteList //
CREATE PROCEDURE sp_GetFavoriteList()
BEGIN
    SELECT 
        l.SN as sn,
        p.ProductName as productName,
        l.Account as account,
        l.TotalAmount as totalAmount,
        l.TotalFee as totalFee,
        u.Email as email
    FROM LikeList l
    JOIN User u ON l.UserID = u.UserID
    JOIN Product p ON l.ProductNo = p.No;
END //

-- 功能 2：根據 SN 查詢單一喜好商品詳細資訊 (供修改頁面反填表單使用) [cite: 17, 18]
DROP PROCEDURE IF EXISTS sp_GetFavoriteById //
CREATE PROCEDURE sp_GetFavoriteById(IN p_sn INT)
BEGIN
    SELECT 
        l.SN as sn,
        l.UserID as userId,
        l.ProductNo as productNo,
        p.ProductName as productName,
        p.Price as price,
        p.FeeRate as feeRate,
        l.Account as account,
        l.PurchaseQuantity as purchaseQuantity
    FROM LikeList l
    JOIN Product p ON l.ProductNo = p.No
    WHERE l.SN = p_sn;
END //

-- 功能 3：新增喜好金融商品 [cite: 11, 12]
DROP PROCEDURE IF EXISTS sp_InsertFavorite //
CREATE PROCEDURE sp_InsertFavorite(
    IN p_userId VARCHAR(50),
    IN p_productNo INT,
    IN p_quantity INT,
    IN p_account VARCHAR(50),
    IN p_totalFee DECIMAL(10,2),
    IN p_totalAmount DECIMAL(10,2)
)
BEGIN
    INSERT INTO LikeList (UserID, ProductNo, PurchaseQuantity, Account, TotalFee, TotalAmount)
    VALUES (p_userId, p_productNo, p_quantity, p_account, p_totalFee, p_totalAmount);
END //

-- 功能 4：更改喜好金融商品資訊 [cite: 17, 18]
DROP PROCEDURE IF EXISTS sp_UpdateFavorite //
CREATE PROCEDURE sp_UpdateFavorite(
    IN p_sn INT,
    IN p_quantity INT,
    IN p_account VARCHAR(50),
    IN p_totalFee DECIMAL(10,2),
    IN p_totalAmount DECIMAL(10,2)
)
BEGIN
    UPDATE LikeList 
    SET PurchaseQuantity = p_quantity,
        Account = p_account,
        TotalFee = p_totalFee,
        TotalAmount = p_totalAmount
    WHERE SN = p_sn;
END //

-- 功能 5：刪除喜好金融商品資訊 [cite: 15, 16]
DROP PROCEDURE IF EXISTS sp_DeleteFavorite //
CREATE PROCEDURE sp_DeleteFavorite(IN p_sn INT)
BEGIN
    DELETE FROM LikeList WHERE SN = p_sn;
END //

-- 輔助功能：撈取下拉選單用商品列表
DROP PROCEDURE IF EXISTS sp_GetAllProducts //
CREATE PROCEDURE sp_GetAllProducts()
BEGIN
    SELECT No as no, ProductName as productName, Price as price, FeeRate as feeRate FROM Product;
END //

DELIMITER ;