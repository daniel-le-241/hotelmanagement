<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contact Us</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    
    
    <style>
        body {
            background-color: #f8f9fa;
            font-family: Arial, sans-serif;
        }
        .contact-container {
            max-width: 1100px;
            margin: auto;
            padding: 20px;
        }
        .admin-card {
            text-align: center;
            border-radius: 12px;
            padding: 20px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            background: white;
            transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
        }
        .admin-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.3);
        }
        .admin-img {
            width: 120px;
            height: 120px;
            border-radius: 50%;
            object-fit: cover;
            border: 4px solid #007bff;
            margin-bottom: 15px;
        }
        .admin-card h4 {
            margin-bottom: 8px;
            color: #007bff;
        }
        .admin-card p {
            margin-bottom: 5px;
            color: #333;
        }
        .admin-card .contact-info {
            font-size: 15px;
        }
        .admin-card .contact-info a {
            text-decoration: none;
            color: #007bff;
            font-weight: bold;
        }
        .admin-card .contact-info a:hover {
            text-decoration: underline;
        }
        .contact-heading {
            text-align: center;
            color: #007bff;
            margin-bottom: 20px;
        }
        .icon {
            color: #007bff;
            margin-right: 5px;
        }
    </style>
</head>
<body>
    <div class="contact-container mt-5">
        <h2 class="contact-heading">📞 Contact Us</h2>
        <div class="row g-4">

            <!-- Admin 1 -->
            <div class="col-md-4">
                <div class="admin-card">
                    <img src="images/admin1.jpg" alt="Admin 1" class="admin-img">
                    <h4>Lê Vũ Phương Hòa</h4>
                    <p>🛠 Chức vụ: Quản lý hệ thống</p>
                    <p>📍 Địa chỉ: TP. Hồ Chí Minh, Việt Nam</p>
                    <p class="contact-info"><i class="fa-solid fa-phone icon"></i> <a href="tel:0123456789">0123 456 789</a></p>
                    <p class="contact-info"><i class="fa-solid fa-envelope icon"></i> <a href="mailto:hoa@gmail.com">hoa@gmail.com</a></p>
                </div>
            </div>

            <!-- Admin 2 -->
            <div class="col-md-4">
                <div class="admin-card">
                    <img src="images/admin2.jpg" alt="Admin 2" class="admin-img">
                    <h4>Trần Phú Thịnh</h4>
                    <p>💼 Chức vụ: Hỗ trợ khách hàng</p>
                    <p>📍 Địa chỉ: TP. Hồ Chí Minh, Việt Nam</p>
                    <p class="contact-info"><i class="fa-solid fa-phone icon"></i> <a href="tel:0987654321">0987 654 321</a></p>
                    <p class="contact-info"><i class="fa-solid fa-envelope icon"></i> <a href="mailto:thinh@gmail.com">thinh@gmail.com</a></p>
                </div>
            </div>

            <!-- Admin 3 -->
            <div class="col-md-4">
                <div class="admin-card">
                    <img src="images/admin3.jpg" alt="Admin 3" class="admin-img">
                    <h4>Ngô Thành Đạt</h4>
                    <p>⚙ Chức vụ: Kỹ thuật viên</p>
                    <p>📍 Địa chỉ: Đà Nẵng, Việt Nam</p>
                    <p class="contact-info"><i class="fa-solid fa-phone icon"></i> <a href="tel:0345678901">0345 678 901</a></p>
                    <p class="contact-info"><i class="fa-solid fa-envelope icon"></i> <a href="mailto:dat@gmail.com">dat@gmail.com</a></p>
                </div>
            </div>

        </div>
    </div>
    
</body>
</html>
