function boardMenu() {
    location.href='/nestco/board';
}

function boardEdit() {
    const nicknameEl = document.querySelector('#nickname');
    let nickname = nicknameEl.innerText;

    const boardIdEl = document.querySelector('#boardId');
    let boardId = boardIdEl.value;

    axios.post('/board/check/nickname', {nickname})
    .then(function (response) {
        let editBoard = confirm('글을 수정하시겠습니까?');
        if(editBoard) {
            location.href='/nestco/board/edit?boardId=' + boardId;
        }
    })
    .catch(function (error) {
        alert('수정할 수 있는 권한이 없습니다.');
    });
}


function boardDelete() {
    const nicknameEl = document.querySelector('#nickname');
    let nickname = nicknameEl.innerText;

    const boardIdEl = document.querySelector('#boardId');
    let boardId = boardIdEl.value;

    axios.post('/board/check/nickname', {nickname})
    .then(function (response) {
        let deleteBoard = confirm('정말로 삭제하시겠습니까?');
        if(deleteBoard) {
            axios.post('/board/delete/' + boardId)
            .then(function (response) {
                alert('삭제에 성공하였습니다.');
                location.href='/nestco/board';
            })
            .catch(function (error) {
                alert('삭제에 실패하였습니다.')
            });
        }
    })
    .catch(function (error) {
        alert('삭제할 수 있는 권한이 없습니다.');
    });
}