function declaration() {
    const uploaderEl = document.querySelector('#uploader-name');
    let nickname = uploaderEl.innerText
    console.log(nickname);

    axios.post('/items/check/nickname', {nickname})
    .then(function (response) {
        alert('신고가 접수되었습니다.');
    })
    .catch(function (error) {
        alert('신고가 실패하였습니다.');
    });
}

const saveLike = (itemId) => {
        axios.post(`/items/like/${itemId}`)
            .then(response => {
                if (response.data.success) {
                    alert("찜하기 성공!");
                } else {
                    alert(response.data.message);
                }
            })
            .catch(error => {
                if (error.response.status === 401) {
                    alert("로그인이 필요합니다.");
                    window.location.href = "/loginForm";
                } else {
                    alert("찜하기 처리 중 오류가 발생했습니다.");
                }
            });

};

$(document).ready(function () {
    $('.exchange-btn').on('click', function () {
        const itemId = $(this).data('item-id');  // 클릭한 버튼의 아이템 ID

        // Ajax로 교환할 아이템 데이터를 가져와 모달에 표시
        $.ajax({
            url: '/getExchangeRequestData',
            type: 'GET',
            data: { itemId: itemId },
            success: function (response) {
                if (response.uploaderItem && response.myItems.length > 0) {
                    $('#uploaderItemId').val(response.uploaderItem.uploaderItemId);  // 값 설정
                    $('#exchangeItemId').empty();
                    response.myItems.forEach(function (item) {
                        $('#exchangeItemId').append('<option value="' + item.exchangeItemId + '">' + item.exchangeItemTitle + '</option>');
                    });
                } else {
                    alert('교환 가능한 아이템이 없습니다.');
                }
            },
            error: function (xhr) {
                if (xhr.status === 401) {
                    alert('로그인이 필요합니다.');
                    window.location.href = '/loginForm';
                } else {
                    alert('교환 신청 정보를 가져오는 중 오류가 발생했습니다.');
                }
            }
        });
    });

    // 교환 신청 버튼 클릭 시
    $('#submitExchangeRequest').on('click', function (event) {
        event.preventDefault();

        const uploaderItemId = $('#uploaderItem').val();  // 수정된 부분
        const exchangeItemId = $('#exchangeItem').val();  // 수정된 부분
        const message = $('#message').val();

        console.log("uploaderItemId: ", uploaderItem);  // 값 출력 확인
        console.log("exchangeItemId: ", exchangeItem);  // 값 출력 확인

        // 필수 항목 체크
        if (!uploaderItem || !exchangeItem) {
            alert('교환할 아이템과 메시지를 입력해주세요.');
            return;
        }

        // 교환 신청 데이터 전송
        $.ajax({
            url: '/submitExchangeRequest',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ uploaderItem, exchangeItem, message }),  // 객체 간단하게
            success: function () {
                alert('교환 신청이 성공적으로 전송되었습니다.');
                $('#exchangeRequestModal').modal('hide');  // 모달 닫기
            },
            error: function (xhr) {
                if (xhr.status === 401) {
                    alert('로그인이 필요합니다.');
                    window.location.href = '/loginForm';
                } else {
                    alert('교환 신청 중 오류가 발생했습니다.');
                }
            }
        });
    });
});