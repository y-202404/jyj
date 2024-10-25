import axios from 'axios';
import styled from 'styled-components';
import {useEffect, useState } from 'react';
import {Link, useNavigate} from 'react-router-dom';
import OneOnOneLogo from '../../images/oneonone.png';
import NoticeLogo from '../../images/notice.png';
import EventLogo from '../../images/event.png';

const StyledDiv = styled.div`

    .service-center {
      background-color: #4A2C2A; /* 배경색을 브랜드 색상으로 설정 */
      padding: 20px;
    }

    .title {
      color: #EFE7DA; /* 제목 텍스트 색상을 #EFE7DA으로 설정 */
      margin-bottom: 10px;
      text-align: center; /* 제목 중앙 정렬 */
    }

    /* 구분선 스타일 */
    .divider {
      border: 1px solid #EFE7DA; /* #EFE7DA의 경계선 */
      margin-bottom: 30px;
    }

    /* 섹션 묶음을 가로 정렬 */
    .section {
      display: flex; /* Flexbox 레이아웃 적용 */
      justify-content: space-between; /* 섹션 간의 간격을 균등하게 설정 */
      flex-wrap: wrap; /* 섹션이 화면 크기에 맞춰 자동으로 줄 바꿈됨 */
    }

    /* 각 개별 섹션의 기본 스타일 */
    .section > div {
      flex: 1 1 calc(25% - 20px); /* 섹션을 동일한 비율로 배치하며, 좌우 마진 20px을 계산하여 너비 설정 */
      margin: 10px;
      text-align: center; /* 각 섹션 내용을 중앙 정렬 */
      background-color: #EFE7DA; /* 섹션 배경색 설정 */
      padding: 15px;
      border-radius: 10px;

      cursor: pointer;
    }

    .icon img {
      max-width: 100px; /* 아이콘 크기 조정 */
      height: auto;
    }

    /* 버튼 스타일 */
    .btn {
      background-color: #4C7263; /* 버튼 색상을 설정 */
      color: #EFE7DA; /* 텍스트 색상을 설정 */
      padding: 10px 20px;
      border: none;
      border-radius: 5px;
      cursor: pointer;
    }

    /* 링크를 한 줄로 정렬하고 스타일링 */
    .links {
      display: flex; /* Flexbox 레이아웃 사용 */
      justify-content: center; /* 자식 요소를 가로로 중앙 정렬 */
      align-items: center; /* 자식 요소를 세로로 중앙 정렬 */
      gap: 15px; /* 링크 사이에 15픽셀의 간격을 추가 */
    }

    .link {
      color: #EFE7DA; /* 링크 텍스트 색상을 #EFE7DA으로 설정 */
      text-decoration: none; /* 링크의 기본 밑줄 제거 */
      padding: 10px; /* 링크에 패딩 추가하여 클릭 가능 영역 확대 */
    }

    .link:hover {
      cursor: pointer; /* 마우스 오버 시 커서를 손 모양으로 변경 */
      text-decoration: none; /* 마우스 오버 시에도 밑줄 제거 */
    }

    /* 세로 구분선 스타일 */
    .vertical-divider {
      border-left: 1px solid #EFE7DA; /* 색상을 #EFE7DA 로 세로 구분선 추가 (왼쪽 경계선) */
      height: 24px; /* 세로 구분선의 높이 설정 */
    }

    .event-image {
        max-width: 500px; /* 이벤트 이미지의 최대 너비 */
        height: auto; /* 비율 유지 */


    /* ------------------------------------------------------------------------------------
    /* 반응형 디자인 */
    @media (max-width: 768px) {
    .section {
        flex-direction: column; /* 작은 화면에서는 섹션을 세로로 정렬 */
      }

      .section > div {
        flex: 1 1 100%; /* 각 섹션을 100% 너비로 설정 */
        margin-bottom: 20px; /* 섹션 간의 간격을 추가 */
      }
      .service-center {
        padding: 15px; /* 작은 화면에서 패딩을 줄여 공간 확보 */
      }

      .title {
        font-size: 1.5rem; /* 제목 폰트 크기를 줄임 */
      }

      .link {
        font-size: 1rem; /* 링크 폰트 크기를 줄임 */
      }

      .links {
        flex-direction: column; /* 작은 화면에서는 링크를 세로로 정렬 */
      }

      .vertical-divider {
        display: none; /* 세로 구분선 숨김 (작은 화면에서는 필요 없음) */
      }

      .link {
        margin-bottom: 10px; /* 링크 아래에 마진을 추가하여 요소 간 간격 조정 */
      }
    }
`;

function Center() {

    const userData = JSON.parse(sessionStorage.getItem("userData"));

    const navigate = useNavigate();

    function moveNotice() {
        navigate("/center/notices")
    }

    function moveOneOnOne() {
        if(userData == null) {
            alert('로그인이 필요합니다.');
        } else {
            navigate("/center/oneonone");
        }
    }

    function moveEvent() {
        navigate("/center/event");
    }


    return(
        <StyledDiv>
            <div className="service-center">
                <h1 className="text-center mb-4 title">고객센터</h1>
                <hr className="divider"></hr>
                <div className="section">
                <div className="section1">
                    <div className="icon">
                        <img src={OneOnOneLogo} alt="1:1문의 아이콘" />
                    </div>
                    <h2>1:1문의</h2>
                    <p>회원 여러분에게 답변해 드립니다.</p>
                    <button className="btn" onClick={moveOneOnOne} >1:1문의 ></button>
                </div>

                <div className="section2">
                    <div className="icon">
                        <img src={NoticeLogo} alt="공지사항 아이콘" />
                    </div>
                    <h2>공지사항</h2>
                    <p>회원 여러분에게 알려드립니다.</p>
                    <button className="btn" onClick={moveNotice}>공지사항 ></button>
                </div>

                <div className="section3">
                    <div className="icon">
                        <img src={EventLogo} alt="이벤트 아이콘" />
                    </div>
                    <h2>이벤트</h2>
                    <p>회원 여러분에게 드립니다.</p>
                    <button className="btn" onClick={moveEvent}>이벤트 ></button>
                </div>
            </div>
               <br></br>
                <hr className="divider"></hr>
                <div className="text-center links">
                    <span className="vertical-divider"></span>
                    <span className="link">1:1문의</span>
                    <span className="vertical-divider"></span>
                    <span className="link">공지사항</span>
                    <span className="vertical-divider"></span>
                    <span className="link">이벤트</span>
                    <span className="vertical-divider"></span>
                </div>
            <br></br>
            <br></br>
            <br></br>
            </div>
        </StyledDiv>
    )
}

export default Center;