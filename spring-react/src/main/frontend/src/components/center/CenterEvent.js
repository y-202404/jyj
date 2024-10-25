import styled from 'styled-components';
import eventLogo from '../../images/event.jpg';
import {useNavigate} from 'react-router-dom';
import axios from 'axios';

const StyledDiv = styled.div`

    background-color: #4A2C2A; /* 배경 색상 설정 */
    color: #EFE7DA; /* 글씨 색상 설정 */
    font-family: 'Arial', sans-serif; /* 기본 글꼴 설정 */

    h1 {
        margin-bottom: 20px; /* 제목과 다른 요소 간의 간격 설정 */
    }

    .event-image {
        max-width: 500px ;
        height: auto; /* 이미지 비율 유지 */
        border: 5px solid #4C7263;; /* 이미지 테두리 색상 설정 */
        border-radius: 10px; /* 이미지 모서리 둥글게 설정 */
    }

    .btn-primary {
        background-color: #4C7263; /* 버튼 배경 색상 설정 */
        border-color: #4C7263; /* 버튼 테두리 색상 설정 */
    }

    .btn-primary:hover {
        background-color: #3B5C54; /* 버튼 호버 시 배경 색상 설정 */
        border-color: #3B5C54; /* 버튼 호버 시 테두리 색상 설정 */
    }

    /* 구분선 스타일 */
    .divider {
      border: 1px solid #EFE7DA; /* #EFE7DA의 1픽셀 두께의 경계선으로 구분선 설정 */
      margin-bottom: 30px; /* 구분선 아래에 마진을 추가하여 요소 간 간격 조정 */
    }

    /* introduce 스타일 추가 */
    .introduce {
        text-align: center; /* 글씨 중앙 정렬 */
    }

    #eventButton {
        margin-right: 20px;
    }
`;

function CenterEvent() {

    const userData = JSON.parse(sessionStorage.getItem("userData"));

    const navigate = useNavigate();



    function moveBack() {
        navigate("/center");
    }

    function eventJoin() {
        if(userData == null) {
            alert('로그인이 필요합니다.');
        } else {
            axios.post("http://localhost:9000/event/join", {memberDTO: userData})
            .then(function(response) {
                alert(response.data);
            })
            .catch(function(error) {

            });
        }
    }

    return(
        <StyledDiv>
            <div class="container text-center">
                <h1>이벤트 참여하기</h1>
                <h3>무선 이어폰 2명</h3>
                <hr class="divider"></hr>
                <div>
                <img src={eventLogo} alt="이벤트 이미지" class="event-image" />
                </div>
                <br></br>
                <button id="eventButton" class="btn btn-primary" onClick={eventJoin}>
                    클릭하여 참여하기
                </button>
                <button class="btn btn-primary" onClick={moveBack}>
                    뒤로가기
                </button>
            </div>
            <div>
                <br></br>
                <h4 class="introduce">당첨자는 2025-01-01 에 발표합니다</h4>
            </div>
            <br></br>
        </StyledDiv>
    )
}

export default CenterEvent;