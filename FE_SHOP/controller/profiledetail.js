const jwtToken = localStorage.getItem('jwtToken');

    const headers = {
        'Authorization': `Bearer ${jwtToken}`
    };
function getMunberCart() {
    fetch('http://192.168.172.130:8080/api-carts/getCart', {
        method: 'GET',
        headers: headers
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('Failed to fetch cart data');
        }
    })
    .then(function(Response){
      document.querySelector("header .content .funtion .funtion-list .cart span.cart-number").innerHTML=Response.length
  })

    .catch(error => {
        console.error('Error:', error);
    });
}
getMunberCart()
let inputUserName = document.querySelector(".container .container-content input[id=username]")
let inputFullName = document.querySelector(".container .container-content input[id=fullname]")
let inputAddress = document.querySelector(".container .container-content input[id=address]")
let inputPhoneNumber = document.querySelector(".container .container-content input[id=phonenumber]")
let btnUpdate = document.querySelector(".container .container-content button")

function checkdata(data){
    console.log(data.userId.id)
    // if(inputFullName.value && inputFullName.value && inputAddress.value && inputPhoneNumber.value){
    //     btnUpdate.classList.add("enable")
    // }
    // else{
        
        btnUpdate.onclick = ()=>{
            const jwtToken = localStorage.getItem('jwtToken');
            const headers = {
                'Authorization': `Bearer ${jwtToken}`,
                'Content-Type': 'application/json'
            };
            let data_send ={
                username : inputUserName.value,
                address : inputAddress.value,
                numberphone : inputPhoneNumber.value
            }
            
            fetch(`http://192.168.172.130:8080/admin/users/update/${data.userId.id}`, {
                method: 'PUT',
                headers: headers,
                body: JSON.stringify(data_send)
            })
            .then(response => {
                if (response.ok) {
                    alert("Thông tin đc sửa thành công");
                    fetchDataAndDisplay();
                } else {
                    alert("Cập nhật không thành công.");
                }
            })
            .catch(error => console.error('Lỗi:', error));
            

        }
    }
// }

fetchDataAndDisplay()

loaddata()
function loaddata(){
    const jwtToken = localStorage.getItem('jwtToken');
    const headers = {
        'Authorization': `Bearer ${jwtToken}`,
        'Content-Type': 'application/json'
    };
    fetch('http://192.168.172.130:8080/admin/users/test', {
        method: 'GET',
        headers: headers
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('Failed to fetch user data');
        }
    })
    .then(datas => {
        console.log(datas)
        inputUserName.value=datas.username
        inputFullName.value=datas.username
        inputAddress.value=datas.address
        inputPhoneNumber.value=datas.numberphone
        checkdata(datas)
    })
    .catch(error => {
        console.error('Error:', error);
    });
    
    
}



// OrderUser
        // Hàm để lấy dữ liệu từ API và hiển thị trên trang HTML
        function fetchDataAndDisplay() {
            const jwtToken = localStorage.getItem('jwtToken');
            const headers = {
                'Authorization': `Bearer ${jwtToken}`,
                'Content-Type': 'application/json'
            };
            
            let stt=0;
            fetch('http://192.168.172.130:8080/api-orders/user', {
                method: 'GET',
                headers: headers
            })
                .then(response => response.json())
                .then(data => {
                    console.log(data)
                    // Xóa dữ liệu cũ trước khi thêm dữ liệu mới
                    document.getElementById('ordersContainer').innerHTML = '';
                    // Duyệt qua từng đơn hàng và hiển thị thông tin

                    let table = '<table class="table table-striped mt-5">';
                        table += `<tr>
                                    <th scope="col">STT</th>
                                    <th scope="col">Tên người dùng</th>
                                    <th scope="col">SĐT</th>
                                    <th scope="col">Địa chỉ</th>
                                    <th scope="col">Note</th>
                                    
                                    <th scope="col">Tổng tiền</th>
                                    <th scope="col">Trạng thái</th>
                                    <th scope="col">Chức năng</th>
                                </tr>`;
                    data.forEach(order => {
                        stt++
                        table +=`
                            <tr>
                                <th scope="row">${stt}</th>
                                <td>${order.userId.username}</td>
                                <td>${order.userId.numberphone}</td>
                                <td>${order.userId.address}</td>
                                <td style="overflow:hidden;max-width: 200px;text-overflow: ellipsis;">${order.note}</td>
                                
                                <td>${order.totalMoney}</td>
                                <td>${order.status}</td>
                                <td>
                                    <button class='btn btn-primary' onclick="viewOrderDetails(${order.orderId})">Xem chi tiết</button>
                                </td>
                            </tr>
                        `;
                    });
                    table += '</table>';
                    document.getElementById('ordersContainer').innerHTML = table;
                })
                .catch(error => console.error('Lỗi:', error));
        }

        
        function viewOrderDetails(orderId) {
            window.location.href = `orderdetails.html?orderId=${orderId}`;
        }

        // Hàm để cập nhật trạng thái đơn hàng
       

        // Gọi hàm để lấy dữ liệu và hiển thị khi trang được tải
        window.onload = fetchDataAndDisplay;