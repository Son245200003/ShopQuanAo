const tableBody = document.getElementById('newsTableBody');
var inputTitle = document.querySelector("#inputTitle");
var inputContent = document.querySelector("#inputContent");
var inputimg = document.querySelector("#inputImg");
var img = document.querySelector("#img");
var inputDescription = document.querySelector("#inputDiscribe");
const token = localStorage.getItem('jwtToken');
let selectednewId = null;
if(!token){
    window.location.href='../../web api nam 3 ki2/view/login.html'
}
const admin = localStorage.getItem('admin');
if(!admin){
    alert("bạn không có quyền truy cập trang này ");
    window.location.href="home.html";
}
function addNew() {
    var title = document.getElementById("inputTitle").value;
    var Discribe = document.getElementById("inputDiscribe").value;
    var content = document.getElementById("inputContent").value;
    var imgFile = document.getElementById("inputImg").files[0];

    var formData = new FormData();
    formData.append("title", title);
    formData.append("discribe", Discribe);
    formData.append("content", content);
    formData.append("img", imgFile);

    fetch('http://localhost:8080/api-new/add_new', {
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
            return response.json();
        })
        .then(data => {
            console.log('New added successfully:', data);
            window.location.href = `../view/newmanager.html`;
            // Reset form fields or update UI as needed
        })
        .catch(error => {
            console.error('Error adding new:', error);
            // Handle error, display error message, etc.
        });
}
function fetchNews() {
    fetch('http://localhost:8080/api-new',{
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + token,
                'Content-Type': 'application/json'
            }
        }

        )
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            // Xử lý dữ liệu tin tức ở đây
            console.log('Received news data:', data);
            // Ví dụ: hiển thị danh sách tin tức trên giao diện người dùng
            updateTable(data);

        })
        .catch(error => {
            console.error('Error fetching news:', error);
            // Xử lý lỗi, hiển thị thông báo lỗi, vv.
        });
}

// Hàm hiển thị danh sách tin tức trên giao diện người dùng
function updateTable(newsData) {

    tableBody.innerHTML = ''; // Xóa dữ liệu cũ

    newsData.forEach(news => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <th scope="row">${news.idNew}</th>
            <td>${news.title}</td>
            <td>${news.content}</td>
            <td class="td-discribe">${news.discribe}</td>
            <td><img src="${base64ToImageUrl(news.img)}" style="max-width: 100px; max-height: 100px;" alt="News Image"></td>
            <td>
                <button type="button" class="btn btn-danger" onclick="deleteRow(${news.idNew})">Delete</button>
            </td>
        `;
        tableBody.appendChild(row);
    });
    tableSelectRow();
}
//update
function updateNews() {

    // Lấy thông tin mới của tin tức từ các trường nhập liệu trên giao diện
    var imgElement = document.getElementById("img"); // Lấy phần tử img
    var imgSrc = imgElement.getAttribute("src"); // Lấy đường dẫn của hình ảnh từ thuộc tính src
    var title = document.getElementById("inputTitle").value;
    var discribe = document.getElementById("inputDiscribe").value;
    var content = document.getElementById("inputContent").value;
    var imgFile = document.getElementById("inputImg").files[0];

    // Tạo một đối tượng FormData để gửi dữ liệu đến API
    var formData = new FormData();
    formData.append("title", title);
    formData.append("discribe", discribe);
    formData.append("content", content);
    const idnew = selectednewId;
    // Kiểm tra xem người dùng đã chọn file ảnh mới hay không
    if (imgFile) {
        // Nếu có tệp mới được chọn, thêm nó vào FormData
        formData.append("img", imgFile);
        sendUpdateRequest(formData, newsId); // Gọi hàm sendUpdateRequest khi có tệp mới được chọn
    } else {
        // Nếu không có tệp mới được chọn, tạo một blob từ đường dẫn của hình ảnh hiện có và gửi nó
        fetch(imgSrc)
            .then(response => response.blob())
            .then(blob => {
                formData.append("img", blob);
                // Gửi yêu cầu cập nhật sau khi blob được tạo
                sendUpdateRequest(formData, idnew);
            })
            .catch(error => {
                console.error('Error fetching image:', error);
            });
    }
}

// Gửi yêu cầu PUT đến API để cập nhật tin tức với ID đã chọn
function sendUpdateRequest(formData, newsId) {
    fetch(`http://localhost:8080/api-new/update_new/${newsId}`, {
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
            console.log('News updated successfully');
            // Thực hiện cập nhật lại bảng tin tức sau khi cập nhật
            fetchNews();
        })
        .catch(error => {
            console.error('Error updating news:', error);
            // Xử lý lỗi, hiển thị thông báo lỗi, vv.
        });
}

function tableSelectRow() {
    const tr = document.querySelectorAll("tr");
    let quantity = tr.length;

    for (let i = 0; i < quantity; i++) {
        tr[i].onclick = () => {
            const th = tr[i].querySelector('th[scope="row"]'); // Lấy ô <th> chứa ID
            const td = tr[i].querySelectorAll("td");
            inputTitle.value = td[0].innerHTML;
            inputContent.value = td[1].innerHTML;

            // Kiểm tra xem có hình ảnh trong td[2] hay không
            const imageSrc = td[3].querySelector('img').src;
            img.src = imageSrc; // Gán đường dẫn hình ảnh cho thuộc tính src của thẻ img

            inputDescription.value = td[2].innerHTML;
            // Lấy ID của người dùng được chọn
            selectednewId = th.textContent;
        }
    }
}

function deleteRow(newsId) {
    if(confirm("bạn có chắc xóa không?")){
    // Gửi yêu cầu DELETE đến API để xóa tin tức với ID đã cho
    fetch(`http://localhost:8080/api-new/${newsId}`, {
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
            console.log('News deleted successfully');
            // Thực hiện cập nhật lại bảng tin tức sau khi xóa
            fetchNews();
        })
        .catch(error => {
            console.error('Error deleting news:', error);
            // Xử lý lỗi, hiển thị thông báo lỗi, vv.
        });}
}

function searchNews() {
    const searchInput = document.getElementById('searchInput').value;
    // Gửi yêu cầu GET đến API để tìm kiếm tin tức dựa trên từ khóa
    fetch(`http://localhost:8080/api-new/search?keyword=${searchInput}`, {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json',
            // Các headers khác nếu cần
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            // Xử lý dữ liệu tin tức được trả về từ API ở đây
            console.log('Received search results:', data);
            // Ví dụ: cập nhật giao diện người dùng với kết quả tìm kiếm
          updateTable(data);
        })
        .catch(error => {
            console.error('Error searching news:', error);
            // Xử lý lỗi, hiển thị thông báo lỗi, vv.
        });
}

function base64ToImageUrl(base64String) {
    return 'data:image/jpeg;base64,' + base64String;
}

// Load trang đầu tiên khi trang được load
document.addEventListener('DOMContentLoaded', () => {
    fetchNews();
});
