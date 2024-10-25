import axios from 'axios';
import styled from 'styled-components';
import {useEffect, useState } from 'react';
import {Link, useNavigate} from 'react-router-dom';

const StyledDiv = styled.div`
    /* 전체 페이지 설정 */
    background-color: #4A2C2A; /* White Chocolate */
    font-family: 'Arial', sans-serif;
    margin: 0;
    padding: 30px 0 0 0;
    color: #EFE7DA;

    /* 컨테이너 설정 */
    .container {
        max-width: 900px;
        margin: 0 auto;
        padding: 20px;
    }

    /* 공지사항 제목 */
    .title {
        color: #EFE7DA;
        font-size: 28px;
        font-weight: bold;
        text-align: center;
    }

    /* 공지사항 테이블 */
    .board-container {
        background-color: #4C7263; /* Hooker Green */
        padding: 0;
        border-radius: 8px;
        border: 1px solid #EFE7DA; /* 외곽 테두리 */
        overflow: hidden;
    }

    /* 제목 행 스타일 */
    .board-container .title {
        display: flex;
        justify-content: space-between;
        align-items: center;
        font-weight: bold;
        border-bottom: 2px solid #EFE7DA; /* 가로줄 */
        padding: 10px;
        text-align: center;
    }

    /* 내용 행 스타일 */
    .board-container .content {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 10px;
        border-bottom: 1px solid #EFE7DA; /* 가로줄 */
        text-align: center;
    }

    /* 마지막 행의 테두리 제거 */
    .board-container .content:last-child {
        border-bottom: none;
    }

    /* 열 비율 설정 */
    .board-container .title p,
    .board-container .content p {
        flex: 1;
        margin: 0;
        padding: 5px;
        text-align: center;
    }

    /* 링크 스타일 */
    .board-container a {
        color: #EFE7DA;
        text-decoration: none;
        font-weight: 900;
    }

    .board-container a:hover {
        text-decoration: underline;
    }

    /* 버튼 그룹 스타일 - 왼쪽 정렬 및 여백 조정 */
    .button-group {
        display: flex;
        justify-content: flex-start; /* 왼쪽 정렬 */
        align-items: center;
        width: 100%; /* 그룹의 전체 너비 */
        margin-left: 10px;
        margin-top: 30px; /* 위쪽 여백 */
    }

    /* 뒤로가기 버튼 스타일 */
    .button-group .btn {
        padding: 12px 24px;
        color: #EFE7DA;
        background-color: #4C7263; /* Hooker Green */
        font-size: 15px;
        transition: background-color 0.3s ease; /* 호버 애니메이션 */
    }

    /* 호버 시 버튼 색상 변화 */
    .button-group .btn:hover {
        cursor: pointer;
        background-color: #3B5C54; /* 약간 어두운 색으로 변경 */
    }

    .page-bar {
        margin: 30px 0 0 450px;
    }
`;

function CenterNotice() {

    const navigate = useNavigate();

    const[notices, setNotices] = useState([]);
    const[firstPage, setFirstPage] = useState(0);
    const[totalPages, setTotalPages] = useState();
    const[pageNumber, setPageNumber] = useState();
    const[currentPageList, setCurrentPageList] = useState([]);

    useEffect(() => {
        axios.get("http://localhost:9000/noticeData")
        .then(function(response) {
            setNotices(response.data.content);
            setTotalPages(response.data.totalPages);
            setPageNumber(response.data.pageable.pageNumber);
        })
        .catch(function(error) {

        });
    }, [])

    useEffect(() => {
        const pages = [];

        for(let i = 1; i <= totalPages; i++) {
            pages.push(i);
        }

        setCurrentPageList(pages);
    }, [totalPages])

    function first() {
        axios.get("http://localhost:9000/noticeData?page=" + firstPage)
        .then(function(response) {
            setNotices(response.data.content);
            setPageNumber(response.data.pageable.pageNumber);
        })
        .catch(function(error) {
            alert('데이터 조회 실패');
        });
    }

    function last() {
        axios.get("http://localhost:9000/noticeData?page=" + (totalPages - 1))
        .then(function(response) {
            setNotices(response.data.content);
            setPageNumber(response.data.pageable.pageNumber);
        })
        .catch(function(error) {
            alert('데이터 조회 실패');
        });
    }

    function prev() {
        axios.get("http://localhost:9000/noticeData?page=" + (pageNumber - 1))
        .then(function(response) {
            setNotices(response.data.content);
            setPageNumber(response.data.pageable.pageNumber);
        })
        .catch(function(error) {
            alert('데이터 조회 실패');
        });
    }

    function next() {
        axios.get("http://localhost:9000/noticeData?page=" + (pageNumber + 1))
        .then(function(response) {
            setNotices(response.data.content);
            setPageNumber(response.data.pageable.pageNumber);
        })
        .catch(function(error) {
            alert('데이터 조회 실패');
        });
    }

    function current(currentPage) {
        axios.get("http://localhost:9000/noticeData?page=" + (currentPage - 1))
        .then(function(response) {
            setNotices(response.data.content);
            setPageNumber(response.data.pageable.pageNumber);
        })
        .catch(function(error) {
            alert('데이터 조회 실패');
        });
    }

    function moveBack() {
        navigate("/center");
    }

    return(
        <StyledDiv>
            <div class="service-center">
                <h1 class="text-center mb-4 title">공지사항</h1>
                <div class="board-container">
                    <div class="title">
                        <p>번호</p>
                        <p>제목</p>
                        <p>작성자</p>
                        <p>작성일</p>
                        <p>조회수</p>
                    </div>
                    {notices.map((notice) => (
                    <div class="content">
                        <p >{notice.id}</p>
                        <p><Link to="/center/notices/detail" state={notice} class="boxTitle">{notice.title}</Link></p>
                        <p>{notice.uploader.nickname}</p>
                        <p>{notice.createDate}</p>
                        <p>{notice.readCount}</p>
                    </div>
                    ))}
                </div>
                 <nav className="page-bar" aria-label="...">
                      <ul className="pagination">
                        {pageNumber != firstPage ? (
                        <>
                        <li className="page-item">
                            <p className="page-link" onClick={first}>First</p>
                        </li>
                        <li className="page-item">
                          <p className="page-link" onClick={prev}>Previous</p>
                        </li>
                        </>
                        ): null}
                        {currentPageList.map((currentPage) => (
                        <li className={pageNumber == (currentPage -1) ? "page-item active" : "page-item"}>
                          <p className="page-link" onClick={() => current(currentPage)}>{currentPage}</p>
                        </li>
                        ))}
                        {pageNumber != (totalPages -1) ? (
                        <>
                        <li className="page-item">
                          <p className="page-link" onClick={next}>Next</p>
                        </li>
                        <li className="page-item">
                          <p className="page-link" onClick={last}>Last</p>
                        </li>
                        </>
                        ): null}
                      </ul>
                    </nav>
                <div class="box">
                    <div class="button-group text-center mt-4">
                        <button class="btn btn-outline-light" onClick={moveBack}>
                            뒤로가기
                        </button>
                    </div>
                </div>
            </div>
            <br></br>
        </StyledDiv>
    )
}

export default CenterNotice;