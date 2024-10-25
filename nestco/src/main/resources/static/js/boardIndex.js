function newBoard() {
    axios.get('/board/check/userId')
    .then(function (response) {

        location.href = '/nestco/newBoard';
    })
    .catch(function (error) {
        console.log(error);
        alert('로그인을 진행하여야 합니다.');;
    });
}
