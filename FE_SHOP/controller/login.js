localStorage.clear()


let video = document.querySelector(".login video");
video.playbackRate =1.2;
document.addEventListener('DOMContentLoaded', function() {
    const loginForm = document.getElementById('loginForm');
    const loginError = document.getElementById('loginError');

    loginForm.addEventListener('submit', function(event) {
        event.preventDefault(); // Ngăn chặn việc gửi yêu cầu mặc định của form

        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;

        const loginData = {
            username: username,
            password: password
        };

        fetch('http://192.168.172.130:8080/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(loginData)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Invalid username or password');
                }
                return response.json();
            })
            
            .then(data => {
                localStorage.setItem('jwtToken', data.token);
                localStorage.setItem('refreshToken', data.refreshToken); // Lưu refresh token
                 console.log(data)
                 if(data.roles.some(r=>r.name==='ROLE_ADMIN')){
                    localStorage.setItem('admin', "ROLE_ADMIN");
                     window.location.href = "../view/accountManager.html";
                 }else{
                 window.location.href = "../view/home.html";}
            })
            .catch(error => {
                loginError.style.display = 'block'; // Hiển thị thông báo lỗi
                console.error('Lỗi:', error.message);
            });
    });
});

