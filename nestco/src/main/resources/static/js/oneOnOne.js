function submitForm(event, isAsync = false) {
    event.preventDefault(); // 기본 동작 중단
    alert('문의가 제출되었습니다.'); // 간단한 알림

    if (isAsync) {
        // 비동기 처리
        fetch(event.target.action, {
            method: 'POST',
            body: new FormData(event.target),
        })
        .then(() => alert('비동기로 제출 완료!'))
        .catch(() => alert('제출 실패!'));
    } else {
        // 동기 처리
        event.target.submit();
    }
}