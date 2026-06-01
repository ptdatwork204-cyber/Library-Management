# Library-Management
Basic console app quản lý thư viện 

Đây là project Java Console App mô phỏng hệ thống quản lý thư viện đơn giản nhằm luyện tập java core:

-  OOP
-  CRUD
-  MVC structure (not quite lol)
-  MVC structure
-  File I/O
-  Interface
-  Postgres
 

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


Cách chạy project
- Bước 1. Compile : javac -cp ".;libs/*" -d bin Main.java ui\*.java service\*.java repository\*.java model\*.java storage\*.jav
- Bước 2. Chạy : java -cp "bin;libs/*" Main

---
# Update 
-  Đã thêm tầng lưu trữ dữ liệu sử dụng JDBC:
-  Áp dụng 1 số SOLID principle dù vẫn chưa ok lắm

---
# Vấn đề:
- Hiện tại service layer (LibraryServiceImpl vẫn chứa scanner và xử lý println) đang làm cả business logic lẫn console input/output, trong khi input/output nên thuộc UI layer (đã fix)
- LibraryMenu hiện vẫn đang thực hiện nhiều trách nhiệm như hiển thị menu, chọn storage, tạo dependency và khởi tạo flow  (đã fix)

