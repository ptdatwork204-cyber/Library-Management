# Library-Management
Basic console app quản lý thư viện 

Đây là project Java Console App mô phỏng hệ thống quản lý thư viện đơn giản nhằm luyện tập java core. đã được mở rộng để tích hợp PostgreSQL thông qua JDBC, bên cạnh các cơ chế lưu trữ cũ (Memory / Text File / Excel). Hiện tại codebase đang trong trạng thái lai kiến trúc (hybrid) và chưa được tinh chỉnh lại một cách chỉn chu (rất lộn xộn ><).

-  OOP
-  CRUD
-  MVC structure (not quite lol)
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
-  Lưu dữ liệu ra database
-  Load dữ liệu từ file


Project sử dụng Apache POI để thao tác với file Excel.

Cách chạy project
Bước 1. Compile : javac -cp ".;libs/*" -d bin Main.java ui\*.java service\*.java repository\*.java model\*.java storage\*.java

Bước 2. Chạy : java -cp "bin;libs/*" Main

Note: 
-  Dữ liệu sẽ mất khi tắt chương trình nếu chưa save.
-  File Excel yêu cầu Apache POI.


---
# Update 
-  Sửa lại logic của save/load để chỉ cần chọn kiểu lưu data 1 lần và data sẽ tự động load theo lựa chọn
-  Áp dụng 1 số SOLID principle dù vẫn chưa ok lắm
-  Đã thêm tầng lưu trữ dữ liệu sử dụng JDBC:
- database/DBConnection.java
- repository/PostgresBookRepository.java
- Thêm thư viện JDBC postgresql-42.7.11.jar vào thư mục libs
- Thay đổi runtime behavior
- Khi chọn chế độ PostgreSQL:
- Hệ thống sử dụng PostgresBookRepository
- Dữ liệu được lưu trực tiếp xuống database
- Không còn phụ thuộc vào in-memory storage cho persistence chính (mặc dù tính năng vẫn tồn tại)

---
# Vấn đề:
- Hiện tại service layer (LibraryServiceImpl vẫn chứa scanner và xử lý println) đang làm cả business logic lẫn console input/output, trong khi input/output nên thuộc UI layer
- LibraryMenu hiện vẫn đang thực hiện nhiều trách nhiệm như hiển thị menu, chọn storage, tạo dependency và khởi tạo flow
-Persistence đang bị “lai”
+ Memory / Text File / Excel vẫn còn tồn tại
+ PostgreSQL được thêm như một lựa chọn song song
+ Chưa có một abstraction thống nhất cho persistence
- Coupling từ logic cũ vẫn còn
+ LibraryServiceImpl vẫn phụ thuộc: StorageStrategy, loadBooks(), saveBooks()
 Các thành phần này không còn thực sự cần thiết khi đã có database

* Method tương thích tạm thời
setBooks(List<Book>) vẫn còn tồn tại trong BookRepository
Chủ yếu để tương thích với logic load file cũ
Với PostgreSQL thì không có ý nghĩa thực tế (implement dạng no-op)



UI vẫn giữ lựa chọn storage cũ
-Menu hiện tại:

Memory
Text File
Excel File
PostgreSQL (mới)

