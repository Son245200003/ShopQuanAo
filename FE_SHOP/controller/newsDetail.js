
let img = document.querySelector(".container .container_news .container_news_img img");
let title = document.querySelector(".container .container_news .container_new_content .title_new_content p")
let content = document.querySelector(".container .container_news .container_new_content .new_content p")
let params = new URLSearchParams(location.search);
const Id = params.get('id'); // Sử dụng params thay vì urlParams
console.log(Id); // Sử dụng Id thay vì id
const jwtToken = localStorage.getItem('jwtToken');

    const headers = {
        'Authorization': `Bearer ${jwtToken}`
    };
function getdata(){
    let url=`http://192.168.172.128:8080/api-new/${Id}`
    fetch(url,{
        method: 'GET',
        headers: {
            //   'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json'
        }
    }).then((res)=>res.json()).then((res)=>loadData(res))
}
 function loadData(data){
    img.setAttribute("src",base64ToImageUrl(data.img));
    title.innerHTML=data.title;
    content.innerHTML=data.content;
 }
function base64ToImageUrl(base64String) {
    return 'data:image/jpeg;base64,' + base64String;}
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
    getMunberCart();
getdata()