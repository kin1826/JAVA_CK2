<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang Web Cá Nhân - Quản Lý Công Việc</title>
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&family=Roboto:wght@300;400;500&display=swap" rel="stylesheet">
</head>
<body>
<!-- Header và Navigation -->
<header class="header">
    <div class="container">
        <div class="logo">
            <i class="fas fa-check-circle"></i>
            <h1>Todo<span>Master</span></h1>
        </div>
        <nav class="navbar">
            <ul class="nav-links">
                <li><a href="#home" class="active"><i class="fas fa-home"></i> Trang chủ</a></li>
                <li><a href="#features"><i class="fas fa-star"></i> Tính năng</a></li>
                <li><a href="#about"><i class="fas fa-user"></i> Giới thiệu</a></li>
                <li><a href="#contact"><i class="fas fa-envelope"></i> Liên hệ</a></li>
            </ul>
            <div class="theme-toggle">
                <i class="fas fa-moon" id="theme-icon"></i>
            </div>
        </nav>
        <div class="menu-toggle">
            <i class="fas fa-bars"></i>
        </div>
    </div>
</header>

<!-- Main Content -->
<main class="main-content">
    <section id="home" class="hero-section">
        <div class="container">
            <div class="hero-content">
                <h2>Quản Lý Công Việc Hiệu Quả</h2>
                <p>Tổ chức cuộc sống và công việc của bạn với ứng dụng quản lý công việc trực tuyến mạnh mẽ. Thêm, chỉnh sửa, hoàn thành và theo dõi tiến độ công việc một cách dễ dàng.</p>
                <div class="cta-buttons">
                    <button class="btn btn-primary" id="get-started">Bắt đầu ngay</button>
                    <button class="btn btn-secondary" id="learn-more">Tìm hiểu thêm</button>
                </div>
            </div>
            <div class="hero-image">
                <img src="https://images.unsplash.com/photo-1551288049-bebda4e38f71?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80" alt="Quản lý công việc">
            </div>
        </div>
    </section>

    <section class="todo-section">
        <div class="container">
            <div class="section-header">
                <h2><i class="fas fa-tasks"></i> Danh Sách Công Việc Của Tôi</h2>
                <p>Thêm công việc mới và quản lý chúng một cách hiệu quả</p>
            </div>

            <div class="todo-container">
                <div class="todo-input-section">
                    <div class="input-group">
                        <input type="text" id="todo-input" placeholder="Thêm công việc mới...">
                        <button id="add-todo" class="btn btn-primary">
                            <i class="fas fa-plus"></i> Thêm
                        </button>
                    </div>
                    <div class="input-options">
                        <div class="category-select">
                            <label for="category"><i class="fas fa-tag"></i> Danh mục:</label>
                            <select id="category">
                                <option value="personal">Cá nhân</option>
                                <option value="work">Công việc</option>
                                <option value="shopping">Mua sắm</option>
                                <option value="health">Sức khỏe</option>
                                <option value="education">Học tập</option>
                            </select>
                        </div>
                        <div class="priority-select">
                            <label for="priority"><i class="fas fa-exclamation-circle"></i> Ưu tiên:</label>
                            <select id="priority">
                                <option value="low">Thấp</option>
                                <option value="medium" selected>Trung bình</option>
                                <option value="high">Cao</option>
                            </select>
                        </div>
                        <div class="date-select">
                            <label for="due-date"><i class="fas fa-calendar-alt"></i> Hạn chót:</label>
                            <input type="date" id="due-date">
                        </div>
                    </div>
                </div>

                <div class="todo-controls">
                    <div class="filter-buttons">
                        <button class="filter-btn active" data-filter="all">Tất cả</button>
                        <button class="filter-btn" data-filter="active">Chưa hoàn thành</button>
                        <button class="filter-btn" data-filter="completed">Đã hoàn thành</button>
                    </div>
                    <div class="sort-options">
                        <label for="sort-by">Sắp xếp theo:</label>
                        <select id="sort-by">
                            <option value="date-added">Ngày thêm</option>
                            <option value="due-date">Hạn chót</option>
                            <option value="priority">Mức độ ưu tiên</option>
                            <option value="alphabetical">Thứ tự chữ cái</option>
                        </select>
                    </div>
                </div>

                <div class="todo-stats">
                    <div class="stat-card">
                        <h3 id="total-tasks">0</h3>
                        <p>Tổng công việc</p>
                    </div>
                    <div class="stat-card">
                        <h3 id="active-tasks">0</h3>
                        <p>Chưa hoàn thành</p>
                    </div>
                    <div class="stat-card">
                        <h3 id="completed-tasks">0</h3>
                        <p>Đã hoàn thành</p>
                    </div>
                    <div class="stat-card">
                        <h3 id="high-priority-tasks">0</h3>
                        <p>Ưu tiên cao</p>
                    </div>
                </div>

                <div class="todo-list-container">
                    <ul id="todo-list">
                        <!-- Các công việc sẽ được thêm vào đây bằng JavaScript -->
                    </ul>
                    <div id="empty-state" class="empty-state">
                        <i class="fas fa-clipboard-list"></i>
                        <h3>Chưa có công việc nào</h3>
                        <p>Thêm công việc đầu tiên của bạn bằng cách sử dụng biểu mẫu phía trên!</p>
                    </div>
                </div>

                <div class="todo-bulk-actions">
                    <button id="delete-completed" class="btn btn-danger">
                        <i class="fas fa-trash"></i> Xóa công việc đã hoàn thành
                    </button>
                    <button id="mark-all-complete" class="btn btn-success">
                        <i class="fas fa-check-double"></i> Đánh dấu tất cả là hoàn thành
                    </button>
                </div>
            </div>
        </div>
    </section>

    <section id="features" class="features-section">
        <div class="container">
            <div class="section-header">
                <h2><i class="fas fa-cogs"></i> Tính Năng Nổi Bật</h2>
                <p>Khám phá các tính năng mạnh mẽ giúp bạn quản lý công việc hiệu quả</p>
            </div>
            <div class="features-grid">
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-bell"></i>
                    </div>
                    <h3>Nhắc nhở thông minh</h3>
                    <p>Nhận thông báo nhắc nhở cho các công việc quan trọng sắp đến hạn, giúp bạn không bỏ lỡ bất kỳ nhiệm vụ nào.</p>
                </div>
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-layer-group"></i>
                    </div>
                    <h3>Phân loại công việc</h3>
                    <p>Tổ chức công việc theo danh mục và mức độ ưu tiên, giúp bạn dễ dàng tập trung vào những việc quan trọng nhất.</p>
                </div>
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-chart-line"></i>
                    </div>
                    <h3>Theo dõi tiến độ</h3>
                    <p>Xem báo cáo trực quan về tiến độ hoàn thành công việc của bạn theo ngày, tuần và tháng.</p>
                </div>
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-sync-alt"></i>
                    </div>
                    <h3>Đồng bộ đa nền tảng</h3>
                    <p>Truy cập danh sách công việc của bạn từ mọi thiết bị - điện thoại, máy tính bảng hoặc máy tính để bàn.</p>
                </div>
            </div>
        </div>
    </section>

    <section id="about" class="about-section">
        <div class="container">
            <div class="section-header">
                <h2><i class="fas fa-info-circle"></i> Về Chúng Tôi</h2>
                <p>TodoMaster - Giải pháp quản lý công việc đơn giản nhưng mạnh mẽ</p>
            </div>
            <div class="about-content">
                <div class="about-text">
                    <h3>Sứ mệnh của chúng tôi</h3>
                    <p>TodoMaster được tạo ra với mục tiêu giúp mọi người quản lý thời gian và công việc hiệu quả hơn. Chúng tôi tin rằng một công cụ đơn giản, trực quan có thể tạo ra sự khác biệt lớn trong năng suất và chất lượng cuộc sống.</p>
                    <p>Với giao diện thân thiện và các tính năng mạnh mẽ, TodoMaster giúp bạn tập trung vào những điều quan trọng, giảm bớt căng thẳng và hoàn thành nhiều việc hơn trong thời gian ngắn hơn.</p>
                    <div class="about-stats">
                        <div class="about-stat">
                            <h4>10,000+</h4>
                            <p>Người dùng hài lòng</p>
                        </div>
                        <div class="about-stat">
                            <h4>500,000+</h4>
                            <p>Công việc đã được quản lý</p>
                        </div>
                        <div class="about-stat">
                            <h4>99%</h4>
                            <p>Người dùng cảm thấy hiệu quả hơn</p>
                        </div>
                    </div>
                </div>
                <div class="about-image">
                    <img src="https://images.unsplash.com/photo-1521791136064-7986c2920216?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1469&q=80" alt="Về chúng tôi">
                </div>
            </div>
        </div>
    </section>

    <section id="contact" class="contact-section">
        <div class="container">
            <div class="section-header">
                <h2><i class="fas fa-envelope"></i> Liên Hệ Với Chúng Tôi</h2>
                <p>Có câu hỏi hoặc góp ý? Hãy liên hệ với chúng tôi!</p>
            </div>
            <div class="contact-container">
                <div class="contact-info">
                    <div class="contact-item">
                        <i class="fas fa-map-marker-alt"></i>
                        <div>
                            <h3>Địa chỉ</h3>
                            <p>123 Đường ABC, Quận 1, TP. Hồ Chí Minh</p>
                        </div>
                    </div>
                    <div class="contact-item">
                        <i class="fas fa-phone"></i>
                        <div>
                            <h3>Điện thoại</h3>
                            <p>+84 123 456 789</p>
                        </div>
                    </div>
                    <div class="contact-item">
                        <i class="fas fa-envelope"></i>
                        <div>
                            <h3>Email</h3>
                            <p>info@todomaster.com</p>
                        </div>
                    </div>
                    <div class="contact-item">
                        <i class="fas fa-clock"></i>
                        <div>
                            <h3>Giờ làm việc</h3>
                            <p>Thứ 2 - Thứ 6: 8:00 - 17:00</p>
                        </div>
                    </div>
                </div>
                <div class="contact-form">
                    <form id="contactForm">
                        <div class="form-group">
                            <input type="text" id="name" placeholder="Họ và tên" required>
                        </div>
                        <div class="form-group">
                            <input type="email" id="email" placeholder="Email" required>
                        </div>
                        <div class="form-group">
                            <input type="text" id="subject" placeholder="Tiêu đề" required>
                        </div>
                        <div class="form-group">
                            <textarea id="message" rows="5" placeholder="Nội dung tin nhắn" required></textarea>
                        </div>
                        <button type="submit" class="btn btn-primary">Gửi tin nhắn</button>
                    </form>
                </div>
            </div>
        </div>
    </section>
</main>


<!-- Footer -->
<footer class="footer">
    <div class="container">
        <div class="footer-content">
            <div class="footer-col">
                <div class="logo">
                    <i class="fas fa-mountain-sun"></i>
                    <h2>Vietnam<span>Travel</span></h2>
                </div>
                <p>Vietnam Travel - Đồng hành cùng bạn trên mọi nẻo đường khám phá vẻ đẹp Việt Nam.</p>
                <div class="payment-methods">
                    <i class="fab fa-cc-visa"></i>
                    <i class="fab fa-cc-mastercard"></i>
                    <i class="fab fa-cc-paypal"></i>
                    <i class="fab fa-cc-amazon-pay"></i>
                </div>
            </div>

            <div class="footer-col">
                <h3>Liên kết nhanh</h3>
                <ul>
                    <li><a href="#home">Trang chủ</a></li>
                    <li><a href="#destinations">Điểm đến</a></li>
                    <li><a href="#tours">Tour du lịch</a></li>
                    <li><a href="#experience">Trải nghiệm</a></li>
                    <li><a href="#blog">Blog du lịch</a></li>
                    <li><a href="#contact">Liên hệ</a></li>
                </ul>
            </div>

            <div class="footer-col">
                <h3>Dịch vụ</h3>
                <ul>
                    <li><a href="#">Tour trong nước</a></li>
                    <li><a href="#">Tour nước ngoài</a></li>
                    <li><a href="#">Vé máy bay</a></li>
                    <li><a href="#">Khách sạn</a></li>
                    <li><a href="#">Visa du lịch</a></li>
                    <li><a href="#">Bảo hiểm du lịch</a></li>
                </ul>
            </div>

            <div class="footer-col">
                <h3>Thông tin</h3>
                <ul>
                    <li><a href="#">Về chúng tôi</a></li>
                    <li><a href="#">Điều khoản sử dụng</a></li>
                    <li><a href="#">Chính sách bảo mật</a></li>
                    <li><a href="#">Chính sách hoàn tiền</a></li>
                    <li><a href="#">Câu hỏi thường gặp</a></li>
                    <li><a href="#">Tuyển dụng</a></li>
                </ul>
            </div>
        </div>

        <div class="footer-bottom">
            <p>&copy; 2023 Vietnam Travel. Tất cả các quyền được bảo lưu.</p>
            <p>Giấy phép kinh doanh lữ hành số: 123456789 do Tổng cục Du lịch cấp ngày 01/01/2023</p>
        </div>
    </div>
</footer>

<!-- Back to Top Button -->
<button class="back-to-top">
    <i class="fas fa-chevron-up"></i>
</button>

<!-- Booking Modal -->
<div class="modal" id="bookingModal">
    <div class="modal-content">
        <div class="modal-header">
            <h3>Đặt tour du lịch</h3>
            <button class="modal-close">&times;</button>
        </div>
        <div class="modal-body">
            <!-- Nội dung modal sẽ được thêm bằng JavaScript -->
        </div>
    </div>
</div>

<!-- JavaScript -->
<script src="js/jsngay4.js"></script>
</body>
</html>