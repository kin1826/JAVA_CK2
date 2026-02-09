<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vietnam Travel - Khám phá vẻ đẹp Việt Nam</title>
    <link rel="stylesheet" href="styles.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&family=Montserrat:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/swiper@9/swiper-bundle.min.css">
</head>
<body>
<!-- Preloader -->
<div class="preloader">
    <div class="preloader-content">
        <div class="plane">
            <i class="fas fa-plane"></i>
        </div>
        <h3>Đang tải hành trình...</h3>
    </div>
</div>

<!-- Header -->
<header class="header">
    <div class="container">
        <div class="logo">
            <i class="fas fa-mountain-sun"></i>
            <h1>Vietnam<span>Travel</span></h1>
        </div>

        <nav class="navbar">
            <ul class="nav-links">
                <li><a href="#home" class="active"><i class="fas fa-home"></i> Trang chủ</a></li>
                <li><a href="#destinations"><i class="fas fa-map-marked-alt"></i> Điểm đến</a></li>
                <li><a href="#tours"><i class="fas fa-suitcase-rolling"></i> Tour du lịch</a></li>
                <li><a href="#experience"><i class="fas fa-star"></i> Trải nghiệm</a></li>
                <li><a href="#blog"><i class="fas fa-blog"></i> Blog du lịch</a></li>
                <li><a href="#contact"><i class="fas fa-envelope"></i> Liên hệ</a></li>
            </ul>

            <div class="header-actions">
                <div class="search-box">
                    <i class="fas fa-search"></i>
                    <input type="text" placeholder="Tìm kiếm điểm đến...">
                </div>
                <button class="btn btn-primary" id="book-now">
                    <i class="fas fa-calendar-check"></i> Đặt tour ngay
                </button>
                <div class="language-switcher">
                    <i class="fas fa-globe"></i>
                    <select>
                        <option value="vi">Tiếng Việt</option>
                        <option value="en">English</option>
                        <option value="fr">Français</option>
                    </select>
                </div>
            </div>
        </nav>

        <div class="mobile-menu-btn">
            <i class="fas fa-bars"></i>
        </div>
    </div>
</header>

<!-- Hero Section -->
<section id="home" class="hero">
    <div class="swiper hero-slider">
        <div class="swiper-wrapper">
            <div class="swiper-slide slide-1">
                <div class="container">
                    <div class="hero-content">
                        <h2>Vịnh Hạ Long - Kỳ quan thiên nhiên thế giới</h2>
                        <p>Khám phá vẻ đẹp huyền bí của hơn 1.600 hòn đảo đá vôi nhấp nhô trên làn nước xanh ngọc bích</p>
                        <div class="hero-buttons">
                            <a href="#tours" class="btn btn-primary">Xem tour ngay</a>
                            <a href="#destinations" class="btn btn-outline">Khám phá thêm</a>
                        </div>
                    </div>
                </div>
            </div>

            <div class="swiper-slide slide-2">
                <div class="container">
                    <div class="hero-content">
                        <h2>Phố cổ Hội An - Thành phố đèn lồng</h2>
                        <p>Dạo bước trong không gian cổ kính với những ngôi nhà mái ngói rêu phong và hàng trăm chiếc đèn lồng đầy màu sắc</p>
                        <div class="hero-buttons">
                            <a href="#tours" class="btn btn-primary">Xem tour ngay</a>
                            <a href="#destinations" class="btn btn-outline">Khám phá thêm</a>
                        </div>
                    </div>
                </div>
            </div>

            <div class="swiper-slide slide-3">
                <div class="container">
                    <div class="hero-content">
                        <h2>Sa Pa - Thành phố trong sương</h2>
                        <p>Chinh phục đỉnh Fansipan - nóc nhà Đông Dương và khám phá văn hóa độc đáo của các dân tộc vùng cao</p>
                        <div class="hero-buttons">
                            <a href="#tours" class="btn btn-primary">Xem tour ngay</a>
                            <a href="#destinations" class="btn btn-outline">Khám phá thêm</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="swiper-pagination"></div>
        <div class="swiper-button-next"></div>
        <div class="swiper-button-prev"></div>
    </div>

    <div class="hero-search">
        <div class="container">
            <div class="search-card">
                <h3>Tìm tour du lịch hoàn hảo</h3>
                <form class="search-form">
                    <div class="form-group">
                        <label><i class="fas fa-map-marker-alt"></i> Điểm đến</label>
                        <select>
                            <option value="">Chọn điểm đến</option>
                            <option value="hanoi">Hà Nội</option>
                            <option value="halong">Hạ Long</option>
                            <option value="sapa">Sa Pa</option>
                            <option value="hoian">Hội An</option>
                            <option value="hue">Huế</option>
                            <option value="nhatrang">Nha Trang</option>
                            <option value="dalat">Đà Lạt</option>
                            <option value="phuquoc">Phú Quốc</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label><i class="fas fa-calendar"></i> Ngày đi</label>
                        <input type="date">
                    </div>

                    <div class="form-group">
                        <label><i class="fas fa-user-friends"></i> Số người</label>
                        <div class="quantity-selector">
                            <button type="button" class="qty-btn minus"><i class="fas fa-minus"></i></button>
                            <input type="number" value="2" min="1" max="20">
                            <button type="button" class="qty-btn plus"><i class="fas fa-plus"></i></button>
                        </div>
                    </div>

                    <div class="form-group">
                        <label><i class="fas fa-clock"></i> Thời gian</label>
                        <select>
                            <option value="">Chọn thời lượng</option>
                            <option value="1">1-3 ngày</option>
                            <option value="2">4-7 ngày</option>
                            <option value="3">8-14 ngày</option>
                            <option value="4">Trên 14 ngày</option>
                        </select>
                    </div>

                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-search"></i> Tìm kiếm
                    </button>
                </form>
            </div>
        </div>
    </div>
</section>

<!-- Destinations Section -->
<section id="destinations" class="destinations section-padding">
    <div class="container">
        <div class="section-header">
            <h2>Điểm đến nổi bật</h2>
            <p>Khám phá những địa điểm du lịch tuyệt vời nhất Việt Nam</p>
        </div>

        <div class="destinations-grid">
            <div class="destination-card">
                <div class="destination-img">
                    <img src="https://images.unsplash.com/photo-1528127269322-539801943592?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80" alt="Hạ Long">
                    <div class="destination-overlay">
                            <span class="destination-rating">
                                <i class="fas fa-star"></i> 4.8
                            </span>
                    </div>
                </div>
                <div class="destination-content">
                    <h3>Vịnh Hạ Long</h3>
                    <p class="destination-location">
                        <i class="fas fa-map-marker-alt"></i> Quảng Ninh
                    </p>
                    <p class="destination-desc">Kỳ quan thiên nhiên thế giới với hàng nghìn đảo đá vôi hùng vĩ</p>
                    <div class="destination-footer">
                        <span class="destination-price">Từ 2.500.000đ</span>
                        <button class="btn btn-sm btn-outline">Xem chi tiết</button>
                    </div>
                </div>
            </div>

            <div class="destination-card">
                <div class="destination-img">
                    <img src="https://images.unsplash.com/photo-1524781289445-ddf8f5695861?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80" alt="Hội An">
                    <div class="destination-overlay">
                            <span class="destination-rating">
                                <i class="fas fa-star"></i> 4.9
                            </span>
                    </div>
                </div>
                <div class="destination-content">
                    <h3>Phố cổ Hội An</h3>
                    <p class="destination-location">
                        <i class="fas fa-map-marker-alt"></i> Quảng Nam
                    </p>
                    <p class="destination-desc">Thành phố di sản với kiến trúc cổ và văn hóa truyền thống độc đáo</p>
                    <div class="destination-footer">
                        <span class="destination-price">Từ 1.800.000đ</span>
                        <button class="btn btn-sm btn-outline">Xem chi tiết</button>
                    </div>
                </div>
            </div>

            <div class="destination-card">
                <div class="destination-img">
                    <img src="https://images.unsplash.com/photo-1552465011-b4e30bf7349d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1469&q=80" alt="Sa Pa">
                    <div class="destination-overlay">
                            <span class="destination-rating">
                                <i class="fas fa-star"></i> 4.7
                            </span>
                    </div>
                </div>
                <div class="destination-content">
                    <h3>Sa Pa</h3>
                    <p class="destination-location">
                        <i class="fas fa-map-marker-alt"></i> Lào Cai
                    </p>
                    <p class="destination-desc">Vùng đất của những thửa ruộng bậc thang và văn hóa dân tộc độc đáo</p>
                    <div class="destination-footer">
                        <span class="destination-price">Từ 1.500.000đ</span>
                        <button class="btn btn-sm btn-outline">Xem chi tiết</button>
                    </div>
                </div>
            </div>

            <div class="destination-card">
                <div class="destination-img">
                    <img src="https://images.unsplash.com/photo-1583417319070-4a69db38a482?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80" alt="Phú Quốc">
                    <div class="destination-overlay">
                            <span class="destination-rating">
                                <i class="fas fa-star"></i> 4.8
                            </span>
                    </div>
                </div>
                <div class="destination-content">
                    <h3>Đảo Phú Quốc</h3>
                    <p class="destination-location">
                        <i class="fas fa-map-marker-alt"></i> Kiên Giang
                    </p>
                    <p class="destination-desc">Thiên đường biển đảo với những bãi cát trắng và làn nước trong xanh</p>
                    <div class="destination-footer">
                        <span class="destination-price">Từ 3.200.000đ</span>
                        <button class="btn btn-sm btn-outline">Xem chi tiết</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="section-footer">
            <a href="#" class="btn btn-outline">Xem tất cả điểm đến <i class="fas fa-arrow-right"></i></a>
        </div>
    </div>
</section>

<!-- Tours Section -->
<section id="tours" class="tours section-padding bg-light">
    <div class="container">
        <div class="section-header">
            <h2>Tour du lịch phổ biến</h2>
            <p>Lựa chọn trong số những hành trình được yêu thích nhất</p>
        </div>

        <div class="tours-tabs">
            <div class="tab-buttons">
                <button class="tab-btn active" data-tab="all">Tất cả</button>
                <button class="tab-btn" data-tab="north">Miền Bắc</button>
                <button class="tab-btn" data-tab="central">Miền Trung</button>
                <button class="tab-btn" data-tab="south">Miền Nam</button>
                <button class="tab-btn" data-tab="adventure">Phiêu lưu</button>
                <button class="tab-btn" data-tab="cultural">Văn hóa</button>
            </div>

            <div class="tab-content active" id="tab-all">
                <div class="tours-grid">
                    <!-- Tour items sẽ được thêm bằng JavaScript -->
                </div>
            </div>
        </div>
    </div>
</section>

<!-- Experience Section -->
<section id="experience" class="experience section-padding">
    <div class="container">
        <div class="section-header">
            <h2>Trải nghiệm độc đáo</h2>
            <p>Những hoạt động không thể bỏ lỡ khi đến Việt Nam</p>
        </div>

        <div class="experience-grid">
            <div class="experience-card">
                <div class="experience-icon">
                    <i class="fas fa-utensils"></i>
                </div>
                <h3>Ẩm thực đường phố</h3>
                <p>Khám phá hương vị đặc trưng với các món ăn đường phố nổi tiếng như phở, bánh mì, bún chả...</p>
            </div>

            <div class="experience-card">
                <div class="experience-icon">
                    <i class="fas fa-ship"></i>
                </div>
                <h3>Du thuyền vịnh Hạ Long</h3>
                <p>Trải nghiệm đêm trên vịnh với du thuyền sang trọng, ngắm bình minh và hoàng hôn tuyệt đẹp</p>
            </div>

            <div class="experience-card">
                <div class="experience-icon">
                    <i class="fas fa-hiking"></i>
                </div>
                <h3>Trekking ở Sa Pa</h3>
                <p>Chinh phục những cung đường trekking qua các bản làng dân tộc và ruộng bậc thang</p>
            </div>

            <div class="experience-card">
                <div class="experience-icon">
                    <i class="fas fa-swimming-pool"></i>
                </div>
                <h3>Lặn biển ở Nha Trang</h3>
                <p>Khám phá thế giới đại dương với những rạn san hô đầy màu sắc và sinh vật biển phong phú</p>
            </div>
        </div>
    </div>
</section>

<!-- Blog Section -->
<section id="blog" class="blog section-padding bg-light">
    <div class="container">
        <div class="section-header">
            <h2>Blog du lịch</h2>
            <p>Chia sẻ kinh nghiệm và cập nhật thông tin du lịch mới nhất</p>
        </div>

        <div class="blog-grid">
            <div class="blog-card">
                <div class="blog-img">
                    <img src="https://images.unsplash.com/photo-1518548419970-58e3b4079ab2?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80" alt="Mùa du lịch">
                    <div class="blog-date">15 Tháng 7, 2023</div>
                </div>
                <div class="blog-content">
                    <h3>Thời điểm tốt nhất để du lịch Việt Nam</h3>
                    <p>Hướng dẫn chi tiết về khí hậu và thời điểm lý tưởng để khám phá các vùng miền Việt Nam</p>
                    <div class="blog-footer">
                        <span><i class="fas fa-user"></i> Nguyễn Văn A</span>
                        <a href="#" class="read-more">Đọc tiếp <i class="fas fa-arrow-right"></i></a>
                    </div>
                </div>
            </div>

            <div class="blog-card">
                <div class="blog-img">
                    <img src="https://images.unsplash.com/photo-1552465011-b4e30bf7349d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1469&q=80" alt="Ẩm thực">
                    <div class="blog-date">22 Tháng 6, 2023</div>
                </div>
                <div class="blog-content">
                    <h3>10 món ăn đường phố không thể bỏ lỡ tại Hà Nội</h3>
                    <p>Khám phá những quán ăn ngon và địa chỉ ẩm thực đường phố nổi tiếng nhất thủ đô</p>
                    <div class="blog-footer">
                        <span><i class="fas fa-user"></i> Trần Thị B</span>
                        <a href="#" class="read-more">Đọc tiếp <i class="fas fa-arrow-right"></i></a>
                    </div>
                </div>
            </div>

            <div class="blog-card">
                <div class="blog-img">
                    <img src="https://images.unsplash.com/photo-1528127269322-539801943592?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80" alt="Du lịch bụi">
                    <div class="blog-date">5 Tháng 6, 2023</div>
                </div>
                <div class="blog-content">
                    <h3>Hành trình xuyên Việt bằng xe máy trong 30 ngày</h3>
                    <p>Kinh nghiệm và lịch trình chi tiết cho chuyến phiêu lưu khám phá Việt Nam từ Bắc vào Nam</p>
                    <div class="blog-footer">
                        <span><i class="fas fa-user"></i> Lê Văn C</span>
                        <a href="#" class="read-more">Đọc tiếp <i class="fas fa-arrow-right"></i></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<div class="lg:w-1/2">
    <h3 class="text-3xl font-bold mb-10 text-crimson fade-in-up delay-200">Experience</h3>

    <div class="timeline">
        <div class="timeline-item timeline-item-highlight fade-in-up delay-200">
            <div class="timeline-dot timeline-dot-highlight"></div>
            <div class="timeline-content">
                <h4 class="text-xl font-bold">Senior Frontend Developer</h4>
                <p class="text-crimson font-semibold mb-2">TechVision Inc. | 2022 - Present</p>
                <p class="text-gray-light">Lead frontend development for multiple enterprise applications. Implemented design systems and improved performance by 40%.</p>
            </div>
        </div>

        <div class="timeline-item fade-in-up delay-300">
            <div class="timeline-dot"></div>
            <div class="timeline-content">
                <h4 class="text-xl font-bold">UI/UX Designer & Developer</h4>
                <p class="text-crimson font-semibold mb-2">Creative Studio | 2020 - 2022</p>
                <p class="text-gray-light">Designed and developed interactive web experiences for clients in entertainment and tech industries.</p>
            </div>
        </div>
    </div>
</div>

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

<!-- Scripts -->
<script src="https://cdn.jsdelivr.net/npm/swiper@9/swiper-bundle.min.js"></script>
<script src="script.js"></script>
</body>
</html>