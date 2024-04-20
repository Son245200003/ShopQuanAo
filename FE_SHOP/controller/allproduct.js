const jwtToken = localStorage.getItem('jwtToken');

    const headers = {
        'Authorization': `Bearer ${jwtToken}`
    };

getdata();
function getMunberCart() {
  fetch('http://localhost:8080/api-carts/getCart', {
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

//<== sticky
let header = document.querySelector("header");
document.querySelector("body").onscroll= function(e){
    if(scrollY>200 ){
     header.classList.add("sticky-fix");
     
    }
    if(scrollY<10){
      header.classList.remove("sticky-fix");
    }
   
  }
// sticky ==>
  function addHoldFunctionProduct(){
    let productions= document.querySelectorAll(".product-function");
    let img2= document.querySelectorAll(".img-2");
    
    for (const index in productions) {
        productions[index].onmousemove=function(){
            img2[index].classList.add("show")
            }
            productions[index].onmouseleave=function(){
            img2[index].classList.remove("show")
            }
    }
}

// <== getData
function getdata(){
  let url="http://localhost:8080/products/getAll";
  fetch(url,{
    method: 'GET',
    headers: headers
  }).then((Response)=>Response.json())
  .then(Response=>{
      console.log(Response);
      loadData(Response);})

  
}
// getData ==>
function loadData(rep){
  // console.log(rep);
  let listProduct= document.querySelector(".container .list-product");
  // console.log(listProduct)
  let html="";
  rep.map((product)=>{
    // console.log(product);
   html+=`<div class="product">
   <div class="product-info">
       <div class="thumnail">
       <img class="img-1" src="${base64ToImageUrl(product.img)}"
       alt="">
   <img class="img-2"
       src="${base64ToImageUrl(product.img)}" alt="">
       </div>
       <button class="sale-off"><span>- 50%</span></button>
       <div class="description">
           <a href="/view/productDetail.html?id=${product.id}"><span>${product.title}</span></a>
           <span class="price">${fomartMoney(product.price)}&#8363;</span>
       </div>
   </div>
   <div class="product-function">
       <ul class="list-function">
           <li class="function-item quick-search"><button><i
                       class="fa-solid fa-magnifying-glass"></i></button></li>
           <li class="function-item wishlist"><button><i class="fa-regular fa-heart"></i></button>
           </li>
                <li class="function-item option"><button><a href="./view/productDetail.html?id=${product.id}"><i class="fa-solid fa-cart-shopping"></i></a></button></li>
       </ul>
   </div>
</div>`

  })
  listProduct.innerHTML=html;
// thêm thư viện tooltip
  addtippy();
  // hold
  addHoldFunctionProduct();
}

function fomartMoney(x) {
  return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}
function base64ToImageUrl(base64String) {
    return 'data:image/jpeg;base64,' + base64String;
}
function addtippy(){
  tippy('.product-function .list-function .function-item.quick-search button', {
    content: 'Quick view',
    arrow: false,
    placement: 'left'
  });

  tippy('.product-function .list-function .function-item.wishlist button', {
    content: 'Wishlist',
    arrow: false,
    placement: 'left'
  });

  tippy('.product-function .list-function .function-item.option button', {
    content: 'Lựa chọn các tùy chọn',
    arrow: false,
    placement: 'left',
    delay:100
  });
}