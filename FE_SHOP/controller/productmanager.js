const tableBody = document.getElementById('productTableBody');
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
var inputTitle = document.querySelector("#inputTitle");
var inputImg = document.querySelector("#img");
var inputPrice = document.querySelector("#inputPrice");
var inputDescription = document.querySelector("#inputDescription");
const urlParams = new URLSearchParams(window.location.search);
 const categoryId = urlParams.get('category_id');
 console.log(categoryId);//trích xuất từ url
// const idcategory = 1;
//add
// Function to add a new product
let  idproduct=null;
function addProduct() {
    // Lấy dữ liệu từ các trường input trên form
    var title = document.getElementById("inputTitle").value;
    var price = document.getElementById("inputPrice").value;
    var description = document.getElementById("inputDescription").value;
    var img = document.getElementById("inputImg").files[0]; // Lấy file ảnh
    var category= categoryId;

    // Tạo FormData object để gửi dữ liệu dưới dạng multipart/form-data
    var formData = new FormData();
    formData.append("title", title);
    formData.append("price", price);
    formData.append("description", description);
    formData.append("img", img);
    formData.append("category",category)

    // Gửi POST request đến endpoint /add
    fetch('http://localhost:8080/products/add', {
        method: 'POST',
        headers: {
            'Authorization': 'Bearer ' + token,

        },
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json(); // Parse response data as JSON
        })
        .then(data => {

            console.log('Product added:', data);
            window.location.href = `../view/productmanager.html?category_id=${categoryId}`;

        })
        .catch(error => {
            console.error('Error adding product:', error);
            // Xử lý lỗi nếu có
        });
}


function fetchProducts(page) {
    fetch(`http://localhost:8080/products/get/${categoryId}?page=${page}`, { // Sửa đường dẫn URL ở đây
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

            console.log(data);
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

    userData.forEach(product => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <th scope="row">${product.id}</th>
            <td>${product.title}</td>
            <td>${product.price}</td>
            <td><img src="${base64ToImageUrl(product.img)}" style="max-width: 100px; max-height: 100px;" alt="Product Image"></td>
            <td>${product.description}</td>
            <td>
                <button type="button" class="btn btn-danger" onclick="deleteRow(${product.id})">Delete</button>
            </td>
        `;
        tableBody.appendChild(row);
    });
    tableSelectRow();
}


function base64ToImageUrl(base64String) {
    return 'data:image/jpeg;base64,' + base64String;
}
function updatePagination(currentPage, totalPages) {
    currentPageElement.textContent = `Page ${currentPage + 1}`; // Hiển thị trang hiện tại

    previousPageBtn.disabled = currentPage === 0; // Bật/tắt nút Previous
    nextPageBtn.disabled = currentPage === totalPages - 1; // Bật/tắt nút Next
}

previousPageBtn.addEventListener('click', () => {
    if (currentPage > 0) { // Kiểm tra nếu currentPage không phải là 0
        currentPage--;
        fetchProducts(currentPage);
    }
});

nextPageBtn.addEventListener('click', () => {
    if (currentPage < totalPages - 1) { // Kiểm tra nếu currentPage không phải là totalPages - 1
        currentPage++;
        fetchProducts(currentPage);
    }
});
function tableSelectRow() {
    const tr = document.querySelectorAll("tr");
    let quantity = tr.length;

    for (let i = 0; i < quantity; i++) {
        tr[i].onclick = () => {
            const th = tr[i].querySelector('th[scope="row"]'); // Lấy ô <th> chứa ID
            const td = tr[i].querySelectorAll("td");
            inputTitle.value = td[0].innerHTML;
            inputImg.src = td[2].querySelector('img').src;
            inputPrice.value = td[1].innerHTML;

            inputDescription.value = td[3].innerHTML;


            // Lấy ID product
             idproduct = th.textContent;  // Lấy nội dung của ô <th> (ID của người dùng)


        }
    }

}
//delete
function deleteRow(id){

   if(confirm("bạn chắc chắn xóa không")){
       fetch(`http://localhost:8080/products/${id}`, {
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
            alert("xóa thành công");
            console.log('Product deleted:', data);
          //  window.location.href = `../view/productmanager.html?category_id=${categoryId}`;
            // Thực hiện các hành động cần thiết sau khi xóa sản phẩm thành công
            // Ví dụ: load lại danh sách sản phẩm
            fetchProducts(currentPage);

        })
        .catch(error => {
            console.error('Error deleting product:', error);
            // Xử lý lỗi nếu có
        });}
}
//update product

function updateProduct() {
    // Lấy dữ liệu từ các trường input trên form
    var title = document.getElementById("inputTitle").value;
    var imgElement = document.getElementById("img"); // Lấy phần tử img
    var imgSrc = imgElement.getAttribute("src"); // Lấy đường dẫn của hình ảnh từ thuộc tính src


    var price = document.getElementById("inputPrice").value;
    var description = document.getElementById("inputDescription").value;
    var img = document.getElementById("inputImg").files[0]; // Lấy file ảnh
    var category = categoryId;
    const productid = idproduct;
    // Tạo FormData object để gửi dữ liệu dưới dạng multipart/form-data
    var formData = new FormData();
    formData.append("title", title);
    formData.append("price", price);
    formData.append("description", description);
    if (img) {
        formData.append("img", img);
    } else {
        // Nếu không có tệp mới được chọn, tạo một blob từ đường dẫn của hình ảnh hiện có và gửi nó
        fetch(imgSrc)
            .then(response => response.blob())
            .then(blob => {
                formData.append("img", blob);
                // Gửi yêu cầu cập nhật sau khi blob được tạo
                sendUpdateRequest(formData, productid);
            })
            .catch(error => {
                console.error('Error fetching image:', error);
            });
    }
    formData.append("category", category);

    // Gửi yêu cầu cập nhật nếu không có blob được tạo
    if (img) {
        sendUpdateRequest(formData, productid);
    }
}

function sendUpdateRequest(formData, productid) {
    // Gửi PUT request đến endpoint cập nhật sản phẩm
    fetch(`http://localhost:8080/products/${productid}`, {
        method: 'PUT',
        headers: {
            'Authorization': 'Bearer ' + token,
        },
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json(); // Parse response data as JSON
        })
        .then(data => {
            console.log('Product updated:', data);
            alert("Cập nhật thành công")
            // Thực hiện các hành động cần thiết sau khi cập nhật sản phẩm thành công
            // Ví dụ: load lại danh sách sản phẩm
            fetchProducts(currentPage);
        })
        .catch(error => {
            console.error('Error updating product:', error);
            // Xử lý lỗi nếu có
        });
}

// Load trang đầu tiên khi trang được load
document.addEventListener('DOMContentLoaded', () => {
    fetchProducts(0);
});
