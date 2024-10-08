
const jwtToken = localStorage.getItem('jwtToken');
        if (jwtToken) {
            const headers = {
                'Authorization': `Bearer ${jwtToken}`
            };
            getdatabill(headers);
            renderdatainfo(headers);
        } else {
            console.error('JWT token not found in localStorage');
        }

function fomartMoney(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}
function MoneytoInt(x){
    return parseInt(x.replaceAll(".",""))
}
function getdatabill(headers){
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
    .then(data => {
        console.log(data)
        renderdatabill(data);
    })
    .catch(error => {
        console.error('Error:', error);
    });
}
function renderdatabill(datas){
    console.log(datas)
    let estimate_product = 0
    let table_total_money= document.querySelector(".container .content .bill .total_money_cart .table_total_money tbody");
    let estimate =table_total_money.querySelector(".estimate");
    
    datas.map(
        (data)=>{
            let size = data.size;
            estimate_product+=(data.productId.price*data.quantity)
            let tr = document.createElement("tr"); 
            tr.innerHTML =`
            <th><span class="titel">${data.productId.title}</span> - <span class="size">${size}</span>  x <span class="Quantity">${data.quantity}</span></td>
            <td>${fomartMoney(parseInt(data.productId.price)*data.quantity)} ₫</td>
            `
            table_total_money.insertBefore(tr,estimate);

        } 
    )
    estimate.querySelector("td span").innerHTML=fomartMoney(estimate_product)
    if(estimate_product!=0){
        let total = (estimate_product+30000);
        document.querySelector(".container .content .bill .total_money_cart .table_total_money tbody tr .total_money span").innerHTML=fomartMoney(total)
    }
}


function renderdatainfo(headers){
    fetch('http://192.168.172.130:8080/admin/users/test', {
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
    .then(data => {
        console.log(data)
        let inputName = document.querySelector(".container .content .bill .info_user_order .info-input input.name")
    let inputAddress = document.querySelector(".container .content .bill .info_user_order .info-input input.address")
    let inputPhoneNumber = document.querySelector(".container .content .bill .info_user_order .info-input input.phonenumber")
    inputName.value =data.username
    inputAddress.value=data.address
    inputPhoneNumber.value=data.numberphone
    })
    .catch(error => {
        console.error('Error:', error);
    });
    
}
renderdatainfo()

function addOrder(order, headers) {
    fetch('http://192.168.172.130:8080/api-orders/addOrder', {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(order)
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            throw new Error('Failed to place order');
        }
    })
    .then(data => {
        console.log(data); // Xử lý dữ liệu phản hồi ở đây (nếu cần)
        alert('Đặt đơn hàng thành công!');
        window.location.href = '../view/home.html'
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Failed to place order. Please try again later.');
    });
}

// Hàm để lấy thông tin đơn hàng từ backend
function getOrderInfo(headers) {
    return fetch('http://192.168.172.130:8080/api-carts/getCart', {
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
    .catch(error => {
        console.error('Error:', error);
    });
}

// Hàm để lấy thông tin người dùng từ backend
function getUserInfo(headers) {
    return fetch('http://192.168.172.130:8080/admin/users/test', {
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
    .catch(error => {
        console.error('Error:', error);
    });
}

// Thực hiện lấy thông tin cả của đơn hàng và người dùng và thêm đơn hàng khi trang web được tải
function clickAddOrder() {
    const jwtToken = localStorage.getItem('jwtToken');
    if (jwtToken) {
        const headers = {
            'Authorization': `Bearer ${jwtToken}`,
            'Content-Type': 'application/json'
        };
        const orderNote = document.getElementById('orderNote').value;
        // Lấy thông tin đơn hàng và người dùng song song
        Promise.all([getOrderInfo(headers), getUserInfo(headers)])
        .then(([orderData, userData]) => {
            // Kết hợp thông tin đơn hàng và người dùng
            const order = {
                ...orderData,
                user: userData,
                note: orderNote
            };
            // Thêm đơn hàng
            addOrder(order, headers);
        })
        .catch(error => {
            console.error('Error:', error);
        });
    } else {
        console.error('JWT token not found in localStorage');
        alert('Please log in before placing an order.');
    }
    
}
// Lắng nghe sự kiện click cho nút "Đặt hàng"
document.getElementById('btnCheckout').addEventListener('click', clickAddOrder);
