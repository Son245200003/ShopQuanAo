let textFeedBack = document.querySelector(".container .content .feed_back .feed_back_content textarea");
let btnFeedBack = document.querySelector(".container .content .feed_back button");
let url = "http://192.168.172.130:8080/api-feedback/add_feedback";
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

// Call the function to update the cart number
getNumberCart();

// Event listener for feedback submission
btnFeedBack.onclick = () => {
    let content = textFeedBack.value;
    let firstname = document.getElementById("firstname").value;
    let lastname = document.getElementById("lastname").value;
    let email = document.getElementById("email").value;
    let phone = document.getElementById("phone").value;
    let subjectName = document.getElementById("subject_name").value;

    // Constructing the feedback object
    let feedbackData = {
        note: content,
        firstName: firstname,
        lastName: lastname,
        email: email,
        phone: phone,
        subjectName: subjectName
    };

    let options = {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(feedbackData)
    };

    // Sending feedback data to the server
    fetch(url, options)
        .then((response) => {
            return response.json();
        })
        .then((response) => {
            alert("Cảm ơn bạn đã gửi phản hồi, chúng tôi sẽ sớm khắc phục");
        });
};
