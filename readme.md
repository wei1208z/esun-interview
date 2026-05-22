環境建置與啟動指南
請依照以下標準步驟部署並執行本專案：

1. 資料庫匯入 (MySQL)
打開您的 MySQL 管理工具並成功建立連線。
複製並貼上專案中 \DB\init.sql 的完整內容，並執行整個腳本。
執行成功後，重新整理，您將會看到建立好的 esun_bank_db 資料庫，其中包含 User、Product、LikeList 三張實體資料表以及 5 個專屬 Stored Procedures。

2. 後端專案配置與啟動 (Spring Boot)
使用 VS Code 點選「開啟資料夾 (Open Folder)」，請務必選取 bank 這個子資料夾（確保 pom.xml 位於根目錄下，以便 Java 擴充套件正確載入 Maven 依賴）。
打開 src/main/resources/application.properties，修改以下連線資訊：

spring.datasource.password=您的MySQL連線密碼

找到 src/main/java/com/esun/bank/BankApplication.java 檔案，點擊 main 函式上方的 Run 按鈕啟動伺服器。
當終端機印出 Started BankApplication in X.XXX seconds 且未出現報錯時，代表後端 API 伺服器已於 http://localhost:8080 成功監聽。

3. 前端網頁執行
進入 E.sun/ 根目錄，直接用瀏覽器雙擊打開 index.html。

網頁將自動透過 Axios 連動後端 API，即可開始進行金融商品的查詢、新增、更改與刪除功能完整實測。