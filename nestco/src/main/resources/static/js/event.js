function newContainer() {
    axios.get("/event/check")
    .then(function (response) {
        alert(response.data);
    })
    .catch(function (error) {
        alert('이벤트 참여 실패');
    });
}