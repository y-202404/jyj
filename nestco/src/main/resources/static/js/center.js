function checkLogin() {
    axios.get("/center/checkLogin")
    .then(function (response) {
        location.href='/nestco/oneonone';
    })
    .catch(function (error) {
        alert('로그인을 해야합니다.');
    });
}

function checkEvent() {
    location.href='/nestco/event';
}