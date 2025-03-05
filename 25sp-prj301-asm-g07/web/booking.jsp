<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Booking Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="css/style.css" />
        <!--font - family-->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;700&display=swap" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <link rel="stylesheet" type="text/css" href="css/footer.css">
        <link rel="stylesheet" href="css/ionicons.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <script>
            document.addEventListener("DOMContentLoaded", function () {
                // Lấy đơn giá phòng từ hidden input
                var roomPrice = parseFloat(document.getElementById("roomPrice").value) || 0;

                // Lấy các trường ngày check-in và check-out
                var checkinInput = document.getElementById("dateIn");
                var checkoutInput = document.getElementById("dateOut");
                var totalPriceInput = document.getElementById("totalPrice");

                // Gắn sự kiện thay đổi cho hai trường ngày
                checkinInput.addEventListener("change", calculateTotalPrice);
                checkoutInput.addEventListener("change", calculateTotalPrice);

                function calculateTotalPrice() {
                    var checkinStr = checkinInput.value;
                    var checkoutStr = checkoutInput.value;

                    if (checkinStr && checkoutStr) {
                        var checkinDate = new Date(checkinStr);
                        var checkoutDate = new Date(checkoutStr);

                        // Tính số ngày chênh lệch
                        var timeDiff = checkoutDate - checkinDate;
                        var daysDiff = timeDiff / (1000 * 60 * 60 * 24);

                        // Nếu số ngày > 0, tính tổng giá
                        if (daysDiff > 0) {
                            var total = roomPrice * daysDiff;
                            // Nếu cần định dạng số theo kiểu không có dấu thập phân:
                            totalPriceInput.value = total;
                        } else {
                            totalPriceInput.value = 0;
                        }
                    } else {
                        totalPriceInput.value = "";
                    }
                }
                calculateTotalPrice();
            });
        </script>

    </head>
    <body>
        <%@ include file="navbar.jsp" %>

        <div class="container booking-container">
            <div class="row">
                <!-- Cột bên trái: Hình ảnh và mô tả phòng -->
                <div class="col-md-7">
                    <div class="details-section-booking">
                        <h1>${typeRoomDetails.typeName}</h1>
                        <p class="price">Price: <fmt:formatNumber value="${typeRoomDetails.price}" pattern="#,##0"/> VNĐ</p>
                        <p>Description: ${typeRoomDetails.typeDes}</p>
                    </div>
                    <div class="image-section">
                        <img src="images/${typeRoomDetails.typeRoomID}/${typeRoomDetails.typeRoomID}.jpg" alt="Room Image" class="main-image">
                        <div class="row">
                            <div class="col-3 mb-3">
                                <img src="images/${typeRoomDetails.typeRoomID}/${typeRoomDetails.typeRoomID}.1.jpg" alt="Room Image 1" class="img-fluid rounded">
                            </div>
                            <div class="col-3 mb-3">
                                <img src="images/${typeRoomDetails.typeRoomID}/${typeRoomDetails.typeRoomID}.2.jpg" alt="Room Image 2" class="img-fluid rounded">
                            </div>
                            <div class="col-3 mb-3">
                                <img src="images/${typeRoomDetails.typeRoomID}/${typeRoomDetails.typeRoomID}.3.jpg" alt="Room Image 3" class="img-fluid rounded">
                            </div>
                            <div class="col-3 mb-3">
                                <img src="images/${typeRoomDetails.typeRoomID}/${typeRoomDetails.typeRoomID}.4.jpg" alt="Room Image 4" class="img-fluid rounded">
                            </div>
                        </div>

                    </div>

                </div>

                <!-- Cột bên phải: Form Booking -->
                <div class="col-md-5">
                    <div class="booking-form-card">
                        <h2 style="font-weight: 600">BOOKING</h2>
                        <c:if test="${not empty error}">
                            <div class="alert alert-danger" role="alert">
                                ${error}
                            </div>
                        </c:if>
                        <form action="booking" method="get">
                            <!-- Booking ID (readonly) -->
                            <div class="mb-3">
                                <label for="bookingID" class="form-label">Booking ID</label>
                                <input type="text" class="form-control" id="bookingID" name="bookingID" value="${bookingID}" readonly>
                            </div>
                            <!-- Booked By (readonly) -->
                            <div class="mb-3">
                                <label for="bookedBy" class="form-label">Booked By</label>
                                <input type="text" class="form-control" id="bookedBy" value="${sessionScope.user.name}">
                            </div>
                            <!-- Check-in Date -->
                            <div class="mb-3">
                                <label for="checkin" class="form-label">Check-in Date</label>
                                <input type="date" class="form-control" id="dateIn" name="dateIn" value="${dateIn}">
                            </div>
                            <!-- Check-out Date -->
                            <div class="mb-3">
                                <label for="checkout" class="form-label">Check-out Date</label>
                                <input type="date" class="form-control" id="dateOut" name="dateOut" value="${dateOut}">
                            </div>
                            <!-- Phone -->
                            <div class="mb-3">
                                <label for="phone" class="form-label">Phone</label>
                                <input type="text" class="form-control" id="phone" name="phone" required="">
                            </div>
                            <input type="hidden" id="roomPrice" value="${requestScope.typeRoomDetails.price}">
                            <div class="mb-3">
                                <label for="totalPrice" class="form-label">Total Price</label>
                                <input type="text" class="form-control" id="totalPrice" name="totalPrice" value="${totalPrice}" readonly>
                            </div>
                            <!-- Hidden action attribute -->
                            <input type="hidden" name="typeRoomID" value="${requestScope.typeRoomDetails.typeRoomID}">
                            <input type="hidden" name="action" value="${requestScope.nextAction}">
                            <!-- Submit Button -->
                            <button type="submit" class="btn btn-primary btn-payment-booking">Payment</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="footer.jsp" %>                    
    </body>
</html>



