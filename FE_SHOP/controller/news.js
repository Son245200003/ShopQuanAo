


const jwtToken = localStorage.getItem('jwtToken');

    const headers = {
        'Authorization': `Bearer ${jwtToken}`
    };


function getMunberCart() {
    fetch('http://192.168.172.128:8080/api-carts/getCart', {
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
function getdata(){
    let url = "http://192.168.172.128:8080/api-new";
    fetch(url,{
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + jwtToken,
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
            loadData(data);
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
}


function loadData(rep) {
    let new_container = document.querySelector(".container");
    let html = "";
    if (Array.isArray(rep)) {
        rep.forEach((data) => {
            html += `<div class="container_news">
            <ul class="list_news">
                <li class="list_news_item">
                    <a href="../view/newsDetail.html?id=${data.idNew}">
                        <div class="news_item">
                            <div class="news_item_img"><img src="${base64ToImageUrl(data.img)}" alt=""></div>
                            <div class="news_item_content">
                                <div class="new_title">
                                    <h2>${data.title}</h2>
                                </div>
                          
                                <div class="new_content">
                                    <p>${data.discribe}</p>
                                </div>
                            </div>
                        </div>
                    </a>
                </li>
            </ul>
        </div>`;
        });
    } else {
        console.error("Dữ liệu không phải là một mảng.");
    }
    new_container.innerHTML = html;
}
function base64ToImageUrl(base64String) {
    return 'data:image/jpeg;base64,' + base64String;
}


getdata()
