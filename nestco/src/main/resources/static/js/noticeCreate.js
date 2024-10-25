function submitForm(event, isAsync = false) {
    event.preventDefault(); // 기본 제출 중단
    alert('공지사항이 등록되었습니다.');

    if (isAsync) {
        fetch(event.target.action, {
            method: 'POST',
            body: new FormData(event.target)
        })
        .then(res => alert(res.ok ? '공지 등록이 완료되었습니다!' : '등록에 실패했습니다.'))
        .catch(() => alert('서버 오류가 발생했습니다.'));
    } else {
        event.target.submit(); // 동기 제출
    }
}