# Library-Management
Basic console app quản lý thư viện 

Đây là project Java Console App mô phỏng hệ thống quản lý thư viện đơn giản nhằm luyện tập java core. Đã chuyển sang PostgreSQL (JDBC). Toàn bộ hệ thống đã chuyển từ lưu trữ tạm sang PostgreSQL. Sử dụng JDBC thuần với Repository Pattern. Dữ liệu được lưu vĩnh viễn trong database.

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
-  Lưu dữ liệu ra database
-  Load dữ liệu từ file


Cách chạy project
- Bước 1. Compile : javac -cp ".;libs/*" -d bin Main.java ui\*.java service\*.java repository\*.java model\*.java storage\*.jav
- Bước 2. Chạy : java -cp "bin;libs/*" Main

- Hoặc chạy file run.bat cho nhanh (no virus trust 👍)

---
# Update 
-  Đã thêm tầng lưu trữ dữ liệu sử dụng JDBC:
-  Áp dụng 1 số SOLID principle dù vẫn chưa ok lắm
-  Đã thêm tầng lưu trữ dữ liệu sử dụng JDBC:
-  database/DBConnection.java
- repository/PostgresBookRepository.java
- Thêm thư viện JDBC postgresql-42.7.11.jar vào thư mục libs
- Thay đổi runtime behavior
- Khi chọn chế độ PostgreSQL:
- Hệ thống sử dụng PostgresBookRepository
- Dữ liệu được lưu trực tiếp xuống database
- Áp dụng tsvector + tsquery để tìm kiếm nhanh, Hỗ trợ tìm kiếm theo keyword thay vì chỉ ID
- Áp dụng transaction cho các thao tác ghi: Add Book, Update Book, Delete Book
- Thêm bảng audit_logs để ghi lại lịch sử thay đổi

NOTE
- Người dùng khác vẫn có thể bị lỗi nếu terminal của họ không set UTF-8.
- Khuyến nghị chạy với: java -Dfile.encoding=UTF-8 Main
- Hoặc chạy file run.bat











