const admin = localStorage.getItem('admin');
if(!admin){
    alert("bạn không có quyền truy cập trang này ");
    window.location.href="home.html";
}
let idfeedback = null;

function fetchFeedback() {
    fetch('http://192.168.172.128:8080/api-feedback')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json(); // Parse response data as JSON
        })
        .then(feedbackList => {
            console.log(feedbackList);
            updateTable(feedbackList);
        })
        .catch(error => {
            console.error('Error fetching feedback:', error);
            // Handle error
        });
}

function updateTable(feedbackList) {
    const tableBody = document.getElementById('feedbackTableBody');
    tableBody.innerHTML = ''; // Clear previous content

    feedbackList.forEach(feedback => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <th scope="row">${feedback.idFeedBack}</th>
            <td>${feedback.firstName}</td>
            <td>${feedback.lastName}</td>
            <td>${feedback.email}</td>
            <td>${feedback.phone}</td>
            <td>${feedback.subjectName}</td>
            <td class="td-discribe">${feedback.note}</td>
            <td>
                <button type="button" class="btn btn-danger" onclick="deleteFeedback(${feedback.idFeedBack})">Delete</button>
            </td>
        `;
        tableBody.appendChild(row);
    });

    tableSelectRow();
}

function tableSelectRow() {
    const tr = document.querySelectorAll("tr");
    let quantity = tr.length;

    for (let i = 0; i < quantity; i++) {
        tr[i].onclick = () => {
            const th = tr[i].querySelector('th[scope="row"]'); // Lấy ô <th> chứa ID
            const td = tr[i].querySelectorAll("td");
            document.querySelector("#inputFirstName").value = td[0].innerHTML;
            document.querySelector("#inputLastName").value = td[1].innerHTML;
            document.querySelector("#inputEmail").value = td[2].innerHTML;
            document.querySelector("#inputPhone").value = td[3].innerHTML;
            document.querySelector("#inputSubject").value = td[4].innerHTML;
            document.querySelector("#inputNote").value = td[5].innerHTML;

           console.log(th.textContent);
            idfeedback = th.textContent;  // Lấy nội dung của ô <th> (ID của người dùng)
        }
    }
}
//add
function addFeedback() {
    // Lấy các giá trị từ các trường nhập liệu
    const firtname = document.querySelector("#inputFirstName").value;
    const lastname = document.querySelector("#inputLastName").value;
    const email = document.querySelector("#inputEmail").value;
    const phone = document.querySelector("#inputPhone").value;
    const subject = document.querySelector("#inputSubject").value;
    const note = document.querySelector("#inputNote").value;
    const newfeedback = {
        firstName: firtname,
        lastName: lastname,
        email: email,
        phone: phone,
        subjectName: subject,
        note:note
    };
    fetch('http://192.168.172.128:8080/api-feedback/add_feedback', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(newfeedback),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error adding feedback');
            }
            // Reload the table after successful addition
            fetchFeedback();
        })
        .catch(error => {
            console.error('Error adding feedback:', error);
            // Handle error
        });
}

//delete
function deleteFeedback(id) {
   if(confirm("bạn chắc xóa không?")){

  fetch(`http://192.168.172.128:8080/api-feedback/delete/${id}`, {
        method: 'DELETE',
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error deleting feedback');
            }
            // Reload the table after successful deletion
            fetchFeedback();
        })
        .catch(error => {
            console.error('Error deleting feedback:', error);
            // Handle error
        });
}  }
//update
function updateFeedback() {
    // Lấy các giá trị từ các trường nhập liệu
    const firtname = document.querySelector("#inputFirstName").value;
    const lastname = document.querySelector("#inputLastName").value;
    const email = document.querySelector("#inputEmail").value;
    const phone = document.querySelector("#inputPhone").value;
    const subject = document.querySelector("#inputSubject").value;
    const note = document.querySelector("#inputNote").value;
    const feedbackid = idfeedback;
    const newfeedback = {
        firstName: firtname,
        lastName: lastname,
        email: email,
        phone: phone,
        subjectName: subject,
        note: note
    };
        fetch(`http://192.168.172.128:8080/api-feedback/update_feedback/${feedbackid}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(newfeedback),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error updating feedback');
                }
                alert("Câp nhật thành công");
                // Reload the table after successful update
                fetchFeedback();
            })
            .catch(error => {
                console.error('Error updating feedback:', error);
                // Handle error
            });

}
//tim kiem
function searchFeedback() {
    const searchInput = document.getElementById('searchInput').value;
    fetch(`http://192.168.172.128:8080/api-feedback/search?keyword=${searchInput}`,
        {
            method: 'GET',
            headers: {
              //  'Authorization': 'Bearer ' + token,
                'Content-Type': 'application/json'
            }}
        )
        .then(response => {
            if (!response.ok) {
                throw new Error('Error searching for feedback');
            }
            return response.json();
        })
        .then(searchResult => {
            // Xử lý kết quả tìm kiếm ở đây, ví dụ: cập nhật bảng hoặc hiển thị thông tin
            console.log(searchResult);
            updateTable(searchResult);
        })
        .catch(error => {
            console.error('Error searching for feedback:', error);
            // Xử lý lỗi
        });
}

window.onload = function() {
    fetchFeedback();

};
