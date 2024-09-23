let selectedUserId = null;

const tableBody = document.getElementById('userTableBody');
const previousPageBtn = document.getElementById('previousPageBtn');
const nextPageBtn = document.getElementById('nextPageBtn');
const currentPageElement = document.getElementById('currentPage');
let totalPages = 0; // Khai báo và khởi tạo totalPages ở ngoài các hàm
let currentPage = 0; // Khai báo và khởi tạo currentPage
// Lấy JWT từ localStorage
const token = localStorage.getItem('jwtToken');
if(!token){
    window.location.href='../../web api nam 3 ki2/view/login.html'
}
const admin = localStorage.getItem('admin');
if(!admin){
    alert("bạn không có quyền truy cập trang này ");
    window.location.href="home.html";
}
function fetchUsers(page) {
    fetch(`http://192.168.172.128:8080/admin/users/listusers?page=${page}`,{
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
                console.log(data)
            updateTable(data.content);
            totalPages = data.totalPages; // Cập nhật giá trị totalPages
            updatePagination(data.number, totalPages);
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}

function updateTable(userData) {
    tableBody.innerHTML = ''; // Xóa dữ liệu cũ

    userData.forEach(user => {
        let  role = user.roles.some(r=>r.name);
        if(role==1){
            role ="User";
        }
        const row = document.createElement('tr');
        row.innerHTML = `
            <th scope="row" >${user.id}</th>
            <td>${user.username}</td>
            <td class="td-pass">${user.password}</td>
             <td>${role}</td>
            <td>${user.email}</td>
            <td>${user.numberphone}</td>
            <td>${user.address}</td>
           <td>
         
             <button type="button" class="btn btn-danger" onclick="deleteRow(${user.id})">Delete</button>
            </td>

        `;
        tableBody.appendChild(row);
    });
    tableSelectRow();
}

function updatePagination(currentPage, totalPages) {
    currentPageElement.textContent = `Page ${currentPage + 1}`; // Hiển thị trang hiện tại

    previousPageBtn.disabled = currentPage === 0; // Bật/tắt nút Previous
    nextPageBtn.disabled = currentPage === totalPages - 1; // Bật/tắt nút Next
}

previousPageBtn.addEventListener('click', () => {
    if (currentPage > 0) { // Kiểm tra nếu currentPage không phải là 0
        currentPage--;
        fetchUsers(currentPage);
    }
});

nextPageBtn.addEventListener('click', () => {
    if (currentPage < totalPages - 1) { // Kiểm tra nếu currentPage không phải là totalPages - 1
        currentPage++;
        fetchUsers(currentPage);
    }
});


function add() {
    // Lấy các giá trị từ các trường nhập liệu
    const username = document.querySelector("#inputUserName").value;
    const password = document.querySelector("#inputPassword").value;
    const email = document.querySelector("#inputEmail").value;
    const phone = document.querySelector("#inputPhone").value;
    const address = document.querySelector("#inputAddress").value;

    // Tạo đối tượng chứa dữ liệu của người dùng mới
    const newUser = {
        username: username,
        password: password,
        email: email,
        numberphone: phone,
        address: address
    };

    // Gửi yêu cầu POST đến endpoint "/adduser"
    fetch('http://192.168.172.128:8080/admin/users/adduser', {
        method: 'POST',
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newUser)
    })
        .then(response => {
            if (!response.ok) {
                if (response.status === 409) {
                    alert("Tên người dùng đã tồn tại");
                }
            }
            return response.json();
        })
        .then(data => {
            window.location.href = "../view/accountManager.html";
                alert("Thêm người dùng thành công!");

        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}
function deleteRow(id) {
    if (confirm("Bạn có chắc chắn muốn xóa người dùng này không?")) {
        // Nếu người dùng chấp nhận xóa, thực hiện yêu cầu DELETE
        fetch(`http://192.168.172.128:8080/admin/users/delete/${id}`, {
            method: 'DELETE',
            headers: {
                'Authorization': 'Bearer ' + token,
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.text();
            })
            .then(data => {
                alert(data); // Hiển thị thông báo về việc xóa thành công
                fetchUsers(currentPage); // Load lại trang hiện tại để cập nhật bảng
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });
    }
}


// Load trang đầu tiên khi trang được load
document.addEventListener('DOMContentLoaded', () => {
    fetchUsers(0);
});


function tableSelectRow() {
    const tr = document.querySelectorAll("tr");
    let quantity = tr.length;

    for (let i = 0; i < quantity; i++) {
        tr[i].onclick = () => {
            const th = tr[i].querySelector('th[scope="row"]'); // Lấy ô <th> chứa ID
            const td = tr[i].querySelectorAll("td");
            inputUserName.value = td[0].innerHTML;
            inputPassword.value = td[1].innerHTML;
            inputEmail.value = td[3].innerHTML;
            inputPhone.value = td[4].innerHTML;
            inputAddress.value = td[5].innerHTML;

            // Lấy ID của người dùng được chọn
            selectedUserId = th.textContent;  // Lấy nội dung của ô <th> (ID của người dùng)


        }
    }
}
//
function updateUser() {
    // Lấy các giá trị từ các trường nhập liệu
    const updatedUsername = document.querySelector("#inputUserName").value;
    const updatedPassword = document.querySelector("#inputPassword").value;
    const updatedEmail = document.querySelector("#inputEmail").value;
    const updatedPhone = document.querySelector("#inputPhone").value;
    const updatedAddress = document.querySelector("#inputAddress").value;

    // Lấy ID của người dùng được chọn
    const userId = selectedUserId;

    // Tạo đối tượng chứa thông tin đã cập nhật
    const updatedUser = {
        username: updatedUsername,
        password: updatedPassword,
        email: updatedEmail,
        numberphone: updatedPhone,
        address: updatedAddress
    };

    // Gửi yêu cầu PUT để cập nhật thông tin người dùng
    fetch(`http://192.168.172.128:8080/admin/users/update/${userId}`, {
        method: 'PUT',
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(updatedUser)
    })
        .then(response => {
            if (!response.ok) {
                if(response.status === 400){
                    // Xử lý lỗi nếu tên người dùng đã tồn tại
                    alert("Tên người dùng đã tồn tại");
                }
            }
            return response.json();
        })
        .then(data => {
            console.log(data)
            // Thực hiện các hành động sau khi cập nhật thành công
            alert("Cập nhật người dùng thành công!");
            fetchUsers(currentPage); // Load lại bảng
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}
function searchByUsername() {
    const searchInput = document.getElementById('searchInput').value;

    // Gửi yêu cầu GET đến API backend để tìm kiếm theo tên người dùng
    fetch(`http://192.168.172.128:8080/admin/users/search?keyword=${searchInput}`,
        {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + token,
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            // Cập nhật bảng với kết quả tìm kiếm
           updateTable(data);

        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}

const searchBtn = document.getElementById('searchBtn');
searchBtn.addEventListener('click', searchByUsername);