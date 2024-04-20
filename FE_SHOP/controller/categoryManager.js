// Định nghĩa hàm để lấy dữ liệu danh mục từ endpoint
// const token = localStorage.getItem('jwtToken');
// if(!token){
//     window.location.href='../../web api nam 3 ki2/view/login.html'
// }
selectedcategoryId=null;//id của danh mục
var inputCategoryName = document.querySelector("#inputCategoryName");
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
function getCategories() {
    fetch('http://localhost:8080/admin/category',
        {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + token,
                'Content-Type': 'application/json'
            }
        })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            // Xử lý dữ liệu trả về, ví dụ: hiển thị danh sách các danh mục lên bảng
            updateTable(data);

        })
        .catch(error => console.error('Error fetching data:', error));
}
// hiển thị thông tin lên table
function updateTable(data){
    const categoryTableBody = document.getElementById('categoryTableBody');
    categoryTableBody.innerHTML = ''; // Xóa nội dung hiện có của bảng

    data.forEach(category => {
        const row = `
                    <tr>
                        <th scope="row">${category.id}</th>
                        <td>${category.nameCategory}</td>
                        <td>
                            <button type="button" class="btn btn-primary" onclick="showProducts(${category.id})">Chi tiết sản phẩm</button>
                            <button type="button" class="btn btn-danger" onclick="deleteCategory(${category.id})">Xóa</button>
                        </td>
                    </tr>
                `;
        categoryTableBody.innerHTML += row; // Thêm dòng mới vào bảng
        tableSelectRow();
    });
}

function tableSelectRow() {

    const tr = document.querySelectorAll("tr");
    let quantity = tr.length;

    for (let i = 0; i < quantity; i++) {
        tr[i].onclick = () => {
            const th = tr[i].querySelector('th[scope="row"]'); // Lấy ô <th> chứa ID
            const td = tr[i].querySelectorAll("td");
            inputCategoryName.value = td[0].innerHTML;


            // Lấy ID của danh mục chọn
            selectedcategoryId = th.textContent;  // Lấy nội dung của ô <th> (ID của người dùng)
                console.log(selectedcategoryId);

        }
    }
}
// Hàm xóa danh mục
function deleteCategory(id) {
    if(confirm("Bạn có chắc muốn xóa không")){
    fetch(`http://localhost:8080/admin/category/delete/${id}`, {
        method: 'DELETE',
        headers: {
                'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {

                throw new Error('Failed to delete category.');
            }
            // Nếu xóa thành công, cập nhật lại bảng danh sách danh mục
            getCategories();
            alert("Xóa thành công"); // Hiển thị thông báo về việc xóa thành công
        })
        .catch(error => console.error('Error deleting category:', error));}
}
//hàm add danh mục
// Hàm thêm mới danh mục
function addCategory() {
    // Lấy giá trị nhập từ input
    const categoryName = document.getElementById('inputCategoryName').value;

    // Tạo một object chứa thông tin mới
    const newCategory = {
        nameCategory: categoryName
    };

    // Gửi yêu cầu POST đến endpoint
    fetch('http://localhost:8080/admin/category/add', {
        method: 'POST',
        headers: {
              'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newCategory) // Chuyển đổi object thành JSON
    })
        .then(response => {
            // Kiểm tra phản hồi từ máy chủ
            if (!response.ok) {
                throw new Error('Failed to add category');
            }
            // Nếu thành công, cập nhật lại bảng
            getCategories();
        })
        .catch(error => console.error('Error adding category:', error));
}
//hàm update danh mục
function updateCategory() {
    // Lấy giá trị ID từ dòng được chọn
    const categoryId = selectedcategoryId;

    // Lấy giá trị nhập từ input
    const categoryName = document.getElementById('inputCategoryName').value;

    // Tạo một object chứa thông tin cập nhật
    const updatedCategory = {

        nameCategory: categoryName
    };

    // Gửi yêu cầu PUT đến endpoint
    fetch(`http://localhost:8080/admin/category/update/${categoryId}`, {
        method: 'PUT',
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(updatedCategory)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to update category');
            }
            // Nếu thành công, cập nhật lại bảng
            getCategories();
            alert("Cập nhật thành công");
        })
        .catch(error => console.error('Error updating category:', error));
}
//tim kiem theo ten
function searchByUsername() {
    const searchInput = document.getElementById('searchInput').value;

    // Gửi yêu cầu GET đến API backend để tìm kiếm theo tên người dùng
    fetch(`http://localhost:8080/admin/category/search?keyword=${searchInput}`,
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
//show product thuộc danh mục
function showProducts(id){
    // Chuyển hướng đến trang quản lý sản phẩm với id của danh mục được chọn
    window.location.href = `../view/productmanager.html?category_id=${id}`;
}
// Gọi hàm getCategories khi trang được tải
document.addEventListener('DOMContentLoaded', getCategories);
