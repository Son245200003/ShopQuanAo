document.addEventListener('DOMContentLoaded', function() {
    // Kiểm tra xem có JWT đã lưu trong Local Storage không
    //DOMContentLoaded này chay khi load trang
    const jwtToken = localStorage.getItem('jwtToken');
    if (jwtToken) {
        // Gửi yêu cầu đến máy chủ BE để xác thực JWT và lấy thông tin người dùng
        fetch('http://localhost:8080/verifyToken', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + jwtToken // Gửi JWT trong tiêu đề Authorization
            }
        })
            .then(response => {
                if (response.ok) {

                    return response.json(); // Trả về dữ liệu JSON nếu JWT hợp lệ

                } else {
                    throw new Error('Invalid JWT'); // Ném lỗi nếu JWT không hợp lệ
                }
            })
            .then(data => {
                console.log(data)
                // Sử dụng thông tin người dùng để đặt lại trạng thái đăng nhập
                // (Thêm mã xử lý để đặt lại trạng thái đăng nhập ở đây)
            })
            .catch(error => {
                console.error('Error:', error.message);
                // Xử lý lỗi nếu JWT không hợp lệ
            });
    }else {
        window.location.href='../../web api nam 3 ki2/view/login.html'
    }
});
