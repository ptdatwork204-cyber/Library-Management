# Library-Management
Basic console app quản lý thư viện 

Đây là project Java Console App mô phỏng hệ thống quản lý thư viện đơn giản nhằm luyện tập java core:

-  OOP
-  CRUD
-  MVC structure
-  File I/O
-  Interface
-  Strategy Pattern
-  Làm việc với file Text và Excel

Project được viết bằng Java Core, không sử dụng framework.

Chức năng :
-  Thêm sách
-  Xem danh sách sách
-  Cập nhật sách
-  Xóa sách
-  Tìm kiếm sách
-  Lưu dữ liệu ra file .txt
-  Lưu dữ liệu ra file .xlsx
-  Load dữ liệu từ file


Project sử dụng Apache POI để thao tác với file Excel.

Cách chạy project
Bước 1. Compile : javac -cp ".;libs/*" -d bin Main.java ui\*.java service\*.java repository\*.java model\*.java storage\*.java
Bước 2. Chạy : java -cp "bin;libs/*" Main

Note: 
-  Dữ liệu sẽ mất khi tắt chương trình nếu chưa save.
-  File Excel yêu cầu Apache POI.
