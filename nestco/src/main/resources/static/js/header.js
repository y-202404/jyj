// 물품 등록화면으로 이동
const saveReq = () => {
    location.href = "/items/upload";
};

// 로그인화면으로 이동
const loginReq = () => {
    location.href = "/loginForm";
};



// DOM이 모두 로드된 후에 실행
$(document).ready(function () {

    // 카테고리 버튼 클릭 시 사이드바 토글
       $('#category-btn').on('click', function (event) {
           event.stopPropagation(); // 클릭 이벤트 전파 방지
           $('#side-bar').toggle();
       });

       // 페이지 외부 클릭 시 카테고리 사이드바 닫기
       $(window).on('click', function (event) {
           if (!$(event.target).closest('#category-btn').length && $('#side-bar').is(':visible')) {
               $('#side-bar').hide();
           }
       });

       // 대분류 hover 시 중분류 표시
           $('.parent-category').hover(function () {
               clearTimeout(hoverTimeout);  // 타이머 초기화
               $(this).find('.middle-category').css('display', 'block');
           }, function () {
               hoverTimeout = setTimeout(() => {
                   $(this).find('.middle-category').css('display', 'none');
               }, 10);  // 딜레이를 줘서 사라지지 않도록 설정
           });

           // 중분류 hover 시 소분류 표시
           $('.middle-category-item').hover(function () {
               clearTimeout(hoverTimeout);  // 타이머 초기화
               $(this).find('.child-category').css('display', 'block');
           }, function () {
               hoverTimeout = setTimeout(() => {
                   $(this).find('.child-category').css('display', 'none');
               }, 10);  // 딜레이 추가
           });

       // 카테고리 클릭 시 아이템 목록 보기
              $('.parent-category, .middle-category-item, .child-category-item').on('click', function () {
                 const categoryId = $(this).data('id');
                    $('#headerSearchForm').append('<input type="hidden" name="categoryId" value="' + categoryId + '">'); // 폼에 카테고리 ID 추가
                    $('#headerSearchForm').submit(); // 폼 제출
                 });

});


// 알림 배지 요소 가져오기
const notificationBadge = document.querySelector('.notification-badge');

// 새로운 알림 수신
socket.on('newNotification', function (message) {
    // 알림 배지 숫자 증가
    const currentBadgeCount = parseInt(notificationBadge.textContent) || 0;
    notificationBadge.textContent = currentBadgeCount + 1;

    // 알림 배지가 보이도록 설정
    if (currentBadgeCount === 0) {
        notificationBadge.style.display = 'inline';
    }

    console.log("새로운 알림:", message);
    alert("새로운 알림이 있습니다: " + message);
});

// 알림 버튼 클릭 시
document.getElementById("notificationBtn").addEventListener("click", function() {
    // 알림 목록 표시
    const notifications = document.getElementById("notificationList");
    notifications.style.display = notifications.style.display === "block" ? "none" : "block";
});

// 알림 항목 클릭 시
function openNotification(notificationId) {
    // 알림에 맞는 페이지 이동 처리
    window.location.href = `/notification/${notificationId}`;
};
