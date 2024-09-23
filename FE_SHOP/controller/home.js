let autoPlay;

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
getdata();
// addtippy();
// addHodeFunctionProduct();
clickNextSlide();
slideAutoPlay();


//
let header = document.querySelector("header");
document.querySelector("body").onscroll= function(e){
  if(scrollY>200 ){
   header.classList.add("sticky-fix");
   
  }
  if(scrollY<10){
    header.classList.remove("sticky-fix");
  }
 
}
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
// 
function slideAutoPlay(){
  let slideList= document.querySelector(".container .separator-top .product-slide .slide-list");
   autoPlay = setInterval(() => {
    let products= document.querySelectorAll(".container .separator-top .product-slide .product");
    slideList.appendChild(products[0])
  }, 5000);
}

function clickNextSlide(){
  let btnNext= document.querySelector(".container .separator-top .product-slide .product-slide-next")
  let btnPre= document.querySelector(".container .separator-top .product-slide .product-slide-previous")
  let slideList= document.querySelector(".container .separator-top .product-slide .slide-list");
  btnNext.onclick=()=>{
    clearInterval(autoPlay)
    let products= document.querySelectorAll(".container .separator-top .product-slide .product");
    slideList.appendChild(products[0])
    autoPlay = setInterval(() => {
      let products= document.querySelectorAll(".container .separator-top .product-slide .product");
      slideList.appendChild(products[0])
    }, 5000);
  }
  btnPre.onclick=()=>{
    let products= document.querySelectorAll(".container .separator-top .product-slide .product");
    clearInterval(autoPlay)
    let number= products.length
    slideList.prepend(products[number-1])
    autoPlay = setInterval(() => {
      let products= document.querySelectorAll(".container .separator-top .product-slide .product");
      slideList.appendChild(products[0])
    }, 5000);
  }
}

  
function getdata(){
  let url="http://192.168.172.128:8080/products/getlimit";
  fetch(url).then((Response)=>Response.json())
  .then(Response=>{
      console.log(Response);
      loadData(Response);
  })

  
}

function loadData(rep){
  let slideList= document.querySelector(".container .separator-top .product-slide .slide-list");
  // console.log(rep);
  let html="";
  rep && rep.map((product)=>{
    // console.log(product);

    
   html+=`<div class="product">
   <div class="product-info">
       <div class="thumnail">
       <img class="img-1" src="${base64ToImageUrl(product.img)}"
       alt="">
   <img class="img-2"
       src="${base64ToImageUrl(product.img)}" alt="">
       </div>
       <button class="sale-off"><span>-50%</span></button>
       <div class="description">
           <a href="../view/productDetail.html?id=${product.id}"><span>${product.title}</span></a>
           <span class="price">${fomartMoney(product.price)} &#8363;</span>
       </div>
   </div>
   <div class="product-function">
       <ul class="list-function">
           <li class="function-item quick-search"><button><i
                       class="fa-solid fa-magnifying-glass"></i></button></li>
           <li class="function-item wishlist"><button><i class="fa-regular fa-heart"></i></button>
           </li>
           <li class="function-item option"><button><a href="/view/productDetail.html?id=${product.id}"><i class="fa-solid fa-cart-shopping"></i></a></button></li>
       </ul>
   </div>
</div>`

  })
  slideList.innerHTML=html;
// thêm thư viện tooltip
  addtippy();
// hold
  addHoldFunctionProduct();
}
function base64ToImageUrl(base64String) {
    return 'data:image/jpeg;base64,' + base64String;
}
function fomartMoney(x) {
  return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
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

