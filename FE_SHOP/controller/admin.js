var txtTitle = document.querySelector("#inputTitle");
var txtFirstImage = document.querySelector("#inputFirstImage");
var txtSecondImage = document.querySelector("#inputSecondImage");
var txtCategory = document.querySelector("#inputCategory");
var txtPrice = document.querySelector("#inputPrice");
let id;
 
 
 const tr =document.querySelectorAll("tr");
 let quantity= tr.length;
 
 

getDataSelectRow();

function getDataSelectRow(){
 
 for(let i=1;i<quantity;i++){
    tr[i].onclick=()=>{
        const td=tr[i].querySelectorAll("td");
        let th=tr[i].querySelector("th");
        id= parseInt(th.innerHTML);
        console.log(id)
        txtTitle.value=td[0].innerHTML;
        txtCategory.value=td[3].innerHTML;
        txtPrice.value=td[4].innerHTML;
       
    }
 }
}


 
