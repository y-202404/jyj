//// 스크롤 이벤트 리스너 추가
//window.addEventListener('scroll', function() {
//    const scrollY = window.scrollY; // 현재 세로 스크롤 위치
//    console.log(`현재 스크롤 위치: ${scrollY}`); // 스크롤 위치를 콘솔에 출력
//});
//관심목록 460
//내 게시물 1020
//교내역 1394.5

let loadedProfileImg = null;

// 클릭 이벤트 리스너 추가
function scrollToPosition(location) {
    window.scrollTo({
        top: location, // 이동할 픽셀 위치
        behavior: 'smooth' // 부드럽게 스크롤
    });
}

const goToTopButton = document.getElementById('goToTop');
const boundary = 300;
// 스크롤 위치에 따라 버튼 표시/숨기기
window.onscroll = function() {
    const scrollTop = window.scrollY;

    if (scrollTop > boundary) {
        goToTopButton.style.display = "block"; // 위로 스크롤 시 버튼 보이기
    } else {
        goToTopButton.style.display = "none"; // 아래로 스크롤 시 버튼 숨기기
    }
};

// 다음 우편번호 검색 api를 사용하기 위해 구현
//new daum.Postcode({
//        oncomplete: function(data) {
//            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분입니다.
//            // 예제를 참고하여 다양한 활용법을 확인해 보세요.
//        }
//    }).open();

function sample6_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                    if(extraAddr !== ''){
                        extraAddr = ' (' + extraAddr + ')';
                    }
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    document.getElementById("sample6_extraAddress").value = extraAddr;

                } else {
                    document.getElementById("sample6_extraAddress").value = '';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample6_postcode').value = data.zonecode;
                document.getElementById("sample6_address").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("sample6_detailAddress").focus();
            }
        }).open();
    }

// LoginService.java에서 중복가입을 방지하기 위해 알림창을 띄워주는 스크립트
const join_button = document.getElementById("social_join");
if (join_button) {
    join_button.addEventListener("click", function(event) {

        //    console.log("On Clicked");
        var userInfoBody = {
            "userId": document.getElementById("userId").value,
            "userPassword": document.getElementById("userPassword").value ,
            "username": document.getElementById("userName").value ,
            "provider": document.getElementById("provider").value ,
            "nickname": document.getElementById("nickName").value ,
            "phoneNumber": document.getElementById("phoneNumber").value ,
            "email": document.getElementById("email").value ,
            "postalAddress": document.getElementById("sample6_postcode").value ,
            "address": document.getElementById("sample6_address").value ,
            "detailedAddress": document.getElementById("sample6_detailAddress").value ,
        };

        if(!userInfoBody.nickname) {
            alert("닉네임을 입력해 주세요.");
            return;
        }
        if (userInfoBody.phoneNumber.length != 11) {
            alert("양식에 맞는 전화번호를 입력해 주세요.");
            return;
        }
        if(!userInfoBody.postalAddress) {
            alert("주소검색을 눌러 주소를 입력해 주세요.");
            return;
        }
        if(!userInfoBody.address) {
            alert("주소검색을 눌러 주소를 입력해 주세요.");
            return;
        }
        if(!userInfoBody.detailedAddress) {
            alert("자세한 주소를 입력해 주세요.");
            return;
        }

        fetch("/join/social", {
            method:"post",
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            },
            body: JSON.stringify(userInfoBody) // 객체를 JSON 문자열로 변환
        })
        .then(response => {
            console.log("response : ", response);
            if (!response.ok) {
                return response.text().then(errordata => {
                console.error("Error_1 : " + errordata);
                throw new Error("회원가입에 실패 하였습니다 : " + errordata);
                })
            }
            return;
        })
        .then(data => {
            alert("회원가입에 성공 하였습니다.")
            window.location.href = "/myPage"
        })
        .catch(error => {
            alert("Error_2 :" + error.message);
        });
    });
}

const update_button = document.getElementById("update_identity");
if(update_button) {
    update_button.addEventListener("click", function(event) {
        const currentImage = document.getElementById("profile_img").src; // 현재 이미지 URL

        const formData = new FormData();

//        var userInfoBody = {
//            "userId": document.getElementById("userId").value,
//            "username": document.getElementById("userName").value ,
//            "nickname": document.getElementById("nickName").value ,
//            "phoneNumber": document.getElementById("phoneNumber").value ,
//            "email": document.getElementById("email").value ,
//            "postalAddress": document.getElementById("sample6_postcode").value ,
//            "address": document.getElementById("sample6_address").value ,
//            "detailedAddress": document.getElementById("sample6_detailAddress").value ,
//        };

        formData.append('userId', document.getElementById("userId").value);
        formData.append('username', document.getElementById("userName").value);
        formData.append('nickname', document.getElementById("nickName").value);
        formData.append('phoneNumber', document.getElementById("phoneNumber").value);
        formData.append('email', document.getElementById("email").value);
        formData.append('postalAddress', document.getElementById("sample6_postcode").value);
        formData.append('address', document.getElementById("sample6_address").value);
        formData.append('detailedAddress', document.getElementById("sample6_detailAddress").value);
        formData.append('profileImage', null);
        if(loadedProfileImg != currentImage){
            const fileInput = document.getElementById("image_input");
            const file = fileInput.files[0];

            if(file) {
                formData.append('profileImage', file);
            }
        }


        if (!formData.get('nickname')) {
            alert("닉네임을 입력해 주세요.");
            return;
        }
        if (formData.get('phoneNumber').length != 11) {
            alert("양식에 맞는 전화번호를 입력해 주세요.");
            return;
        }
        if(!formData.get('postalAddress')) {
            alert("주소검색을 눌러 주소를 입력해 주세요.");
            return;
        }
        if(!formData.get('address')) {
            alert("주소검색을 눌러 주소를 입력해 주세요.");
            return;
        }
        if(!formData.get('detailedAddress')) {
            alert("자세한 주소를 입력해 주세요.");
            return;
        }

        fetch("/updateIdentity", {
            method:'POST',
            body: formData
        })
        .then(response => {
            console.log("response : ", response);
            if (!response.ok) {
                return response.text().then(errordata => {
                console.error("Error_1 : " + errordata);
                throw new Error(errordata);
                })
            }
            return;
        })
        .then(data => {
            alert("정보수정에 성공 하였습니다.")
            window.location.href = "/myPage"
        })
        .catch(error => {
            alert("Error_2 :" + error.message);
        });

    });
}

const profileImg = document.getElementById("profile_img");
if(profileImg){
    profileImg.addEventListener('click', function() {
        document.getElementById("image_input").click();
    });

    document.getElementById("image_input").addEventListener('change', function(event) {
        const file = event.target.files[0];
        if(file) {
            const reader = new FileReader();

            reader.onload= function(e) {
                const imgElement = document.getElementById("profile_img");
                imgElement.src = e.target.result;
            };
            reader.readAsDataURL(file);

        }
    })
}

document.addEventListener('DOMContentLoaded', function() {
    loadedProfileImg = document.getElementById("profile_img"); //페이지 로드시 프로필 업데이트에서 이미지 변경여부를 확인하기 위해 등록
    const profileImageViews = document.getElementsByClassName('profileImg');
    if (profileImageViews.length > 0) { // 요소가 존재하는지 확인
        // HTMLCollection을 배열로 변환 후 forEach 사용
        Array.from(profileImageViews).forEach(function(profileImageView) {
            const userId = profileImageView.getAttribute('data-userId'); // 각 요소의 data-userId 속성 가져오기
            console.log(userId);

            fetch('/searchUserId', {
                method: 'POST', // 대문자로 수정
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    userId: userId,
                })
            })
            .then(response => response.json())
            .then(data => {
                if (data != null) {
                    const dataPath = data.profileImagePath;
                    if (dataPath) { // dataPath가 null 또는 빈 문자열이 아닐 경우
                        // profileImageView에 src 할당
                        profileImageView.src = dataPath.replace("/static", "");
//                        profileImageView.src = dataPath;

                        // 이미지 로드 실패 시 대체 URL 설정
                        profileImageView.onerror = function() {
                            profileImageView.src = "/images/NestCoWhite.svg";
                        };
                    } else { profileImageView.src = "/images/NestCoWhite.svg"; }
                } else {
                    // dataPath가 null이거나 유효하지 않을 경우 대체 이미지 설정
                    profileImageView.src = "/images/NestCoWhite.svg";
                }
            })
            .catch(error => {
                console.log('get img error: ', error);
            });
        });
    }
});
