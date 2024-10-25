import axios from 'axios';
import styled from 'styled-components';
import {useEffect, useState } from 'react';
import {Link, useNavigate, useLocation} from 'react-router-dom';

const StyledDiv = styled.div`
    /* 전체 페이지 설정 */
    background-color: #4A2C2A; /* White Chocolate */
    font-family: 'Arial', sans-serif;
    margin: 0;
    padding: 20px 0 20px 0;
    color: #EFE7DA; /* 밝은 텍스트 색상 */

    /* 컨테이너 설정 */
    .container {
        max-width: 800px;
        margin: 0 auto;
        padding: 20px;
    }

    /* 공지사항 제목 스타일 */
    .title {
        color: #EFE7DA;
        font-size: 32px;
        font-weight: bold;
        text-align: center;
        margin-bottom: 20px;
        border-bottom: 2px solid #EFE7DA;
        padding-bottom: 10px;
    }

    /* 공지사항 정보 스타일 */
    .notice-info {
        display: flex;
        justify-content: space-between;
        margin-bottom: 15px;
        font-size: 18px;
    }

    /* 공지사항 내용 스타일 */
    .notice-content {
        background-color: white;
        color:black;
        padding: 20px;
        white-space: pre-wrap; /* 줄 바꿈 유지 */
        font-size: 16px;
    }

    /* 버튼 그룹 스타일 - 왼쪽 정렬 */
    .button-group {
        display: flex;
        justify-content: flex-start; /* 왼쪽 정렬 */
        width: 100%; /* 버튼 그룹의 전체 너비 */
    }

    .button-group .btn {
        margin-top: 10px;
        padding: 10px 20px;
        color: #EFE7DA;
        border-radius: 4px;
        background-color: #4C7263;
        margin-left: 0;
    }

    /* 호버 시 버튼 색상 변화 */
    .button-group .btn:hover {
        cursor: pointer;
        background-color: #3B5C54; /* 약간 어두운 색으로 변경 */
    }
`;

function CenterNoticeDetail() {

    const navigate = useNavigate();

    const location = useLocation();

    const notice = location.state;

    console.log(notice);

    function moveNotices() {
        navigate("/center/notices");
    }

    useEffect(() => {
        axios.post("http://localhost:9000/noticeData/readCount", {id: notice.id})
        .then(function(response) {

        })
        .catch(function(error) {

        });
    }, [notice])

    return(
        <StyledDiv>
            <div class="service-center">
                <h1 class="text-center mb-4 title">{notice.title}</h1>
                <div class="notice-info">
                    <p><strong>작성자:</strong> {notice.uploader.nickname}</p>
                    <p><strong>작성일:</strong> {notice.createDate}</p>
                    <p><strong>조회수:</strong> {notice.readCount}</p>
                </div>
                <div class="notice-content">
                    <pre>content</pre>
                </div>
                <div class="button-group text-center mt-4">
                    <button class="btn btn-outline-light" onClick={moveNotices}>
                        뒤로가기
                    </button>
                </div>
            </div>
        </StyledDiv>
    )
}

export default CenterNoticeDetail;