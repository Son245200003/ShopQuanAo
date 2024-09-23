
let estimate_total_money=0;

start()

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
function start(){
    getData()
    addactionbtn()
   
}

function fomartMoney(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
}
function MoneytoInt(x){
    return parseInt(x.replaceAll(".",""))
}


function addactionbtn(){
    let minus = document.querySelectorAll("td .input-quantity input.minus");
    let plus = document.querySelectorAll("td .input-quantity input.plus");
    let inputNumber= document.querySelectorAll("td .input-quantity input.inputnumber")
    let length= minus.length;
    for(let i=0;i<length;i++){
        minus[i].onclick = ()=>{
            if(inputNumber[i].value>1)
            inputNumber[i].value--;
        }
        plus[i].onclick = ()=>{
            
            if(inputNumber[i].value <parseInt(inputNumber[i].getAttribute("max")))
            inputNumber[i].value++;
        }
    }
}


function getData(){
    let url ="http://192.168.172.128:8080/carts"
    fetch(url).then((Response)=>Response.json())
    .then(async Response=>{await loadData(Response);})

    
}
function loadData(datas){
let table = document.querySelector(".container .content .bill .table-bill")
console.log("rerender");
    datas.map((data)=>{
        estimate_total_money+=(data.price*data.quantity);
        var tr = document.createElement("tr");
        tr.classList.add(`bill-item-${data.id}`)
        tr.innerHTML=`
        <td><button data-id="${data.id}" class="close"><i class="fa-solid fa-xmark"></i></button></td>
        <td><img src="${data.thumbnail}" alt=""></td>
        <td>${data.title} - ${data.size}</td>
        <td>${fomartMoney(data.price)} &#8363</td>
        <td>
            <div class="input-quantity">
                <input type="button" value="-" class="minus">
                <input class="inputnumber" type="number" value="${data.quantity}" step="1" size="2" max="10" min="0">
                <input type="button" value="+" class="plus">
            </div>

        </td>
        <td>${fomartMoney(parseInt(data.price)*data.quantity)}&#8363</td>`

    table.appendChild(tr);
    })
    let estimate = document.querySelector(".total_money_cart .table_total_money td.estimate_total_money span")
    let total = document.querySelector(".total_money_cart .table_total_money td.total_money span")
    
    if(estimate_total_money>0){
        estimate.innerHTML= fomartMoney(estimate_total_money)

        total.innerHTML= fomartMoney(estimate_total_money+30000)
    }else{
        estimate.innerHTML=0
        total.innerHTML=0
    }
    
    
    
addactionbtn();
addactionbtnClose()
}


 let addactionbtnClose =()=>{
    let url ="http://192.168.172.128:8080/carts"
    let button_closes= document.querySelectorAll(".bill .table-bill tr td button.close");
    let option={
        method: 'DELETE',
        headers: {
            "Content-Type": "application/json"
          }

    };
    button_closes.forEach( function(button){
        button.onclick=function(){
            let id_delete=this.getAttribute("data-id")
            document.querySelector(`.container .content .bill .table-bill .bill-item-${id_delete}`).remove()

            fetch(url+"/"+id_delete ,option).then(
                (Response)=>{Response.json()})
                .then((Response)=>{console.log(Response);})
        }
        
    })
}
addactionbtnClose();

let btn_pay= document.querySelector(".container .content .bill .total_money_cart .check_out .btn_check_out");
btn_pay.onclick = ()=>{
    window.location.assign("pay.html")
}