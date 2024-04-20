
function sendotp() {
    const sendCodeBtn = document.getElementById('send-code-btn');
    const statusMsg = document.getElementById('status-msg');

    sendCodeBtn.addEventListener('click', function(event) {
        event.preventDefault(); // Ngăn chặn việc gửi form mặc định

        const phone = document.getElementById('phone').value;

        // Kiểm tra xem số điện thoại có đúng định dạng hay không
        const phonePattern = /^\d{10}$/; // Biểu thức chính quy kiểm tra 10 chữ số
        if (!phonePattern.test(phone)) {
            statusMsg.textContent = 'Số điện thoại không hợp lệ';
            return;
        }

        // Gửi dữ liệu điện thoại lên máy chủ Spring Boot
        fetch('http://localhost:8080/api/sendOtp', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body:(phone)  // Gửi số điện thoại dưới dạng chuỗi
        })
            .then(response => response.text())
            .then(data => {
                console.log(data); // Log response từ máy chủ Spring Boot
                statusMsg.textContent = 'Đã gửi mã'; // Hiển thị thông báo "Đã gửi mã"
            })
            .catch(error => {
                console.error('Error:', error);
                statusMsg.textContent = 'Lỗi khi gửi mã';
            });
    });
}
function verifyandregister() {
    const registerForm = document.getElementById('register-form');

    registerForm.addEventListener('submit', function(event) {
        event.preventDefault(); // Ngăn chặn việc gửi form mặc định

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirm-password').value;
        const phone = document.getElementById('phone').value;

        const verificationCode = document.getElementById('verification-code').value;

        // Kiểm tra xem người dùng đã nhập số điện thoại hay chưa
        if (!phone) {
            alert("Vui lòng nhập số điện thoại trước khi đăng ký");
            return;
        }

        // Kiểm tra mật khẩu và mật khẩu xác nhận
        if (password !== confirmPassword) {
            alert("Mật khẩu xác nhận không khớp với mật khẩu ban đầu");
            return;
        }

        // Gửi dữ liệu đăng ký và mã OTP lên máy chủ
        fetch('http://localhost:8080/register/verify?otp=' + verificationCode, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: username,
                password: password,
                confirmPassword: confirmPassword,
                phone: phone
            })
        })
            .then(response => {
                if (response.ok) {
                    window.location.href = "../view/login.html";
                    return response.text();
                } else if (response.status === 409) { // HttpStatus.CONFLICT
                    throw Error('Username already exists');
                } else if (response.status === 401) { // HttpStatus.UNAUTHORIZED
                    throw Error('Invalid OTP or OTP expired');
                }
            })
            .then(data => {
                console.log(data); // Log response từ máy chủ Spring Boot
                // Xử lý phản hồi từ máy chủ nếu cần
            })
            .catch(error => {
                console.error('Error:', error.message);
                // Xử lý lỗi nếu có
            });
    });
}

sendotp();
verifyandregister();
