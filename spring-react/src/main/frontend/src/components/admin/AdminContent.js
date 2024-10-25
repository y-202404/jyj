import {Link, useNavigate} from 'react-router-dom';
import styled from 'styled-components'
import {useState, useEffect} from 'react';
import axios from 'axios';

const StyledAdminDiv = styled.div`
    #logout-btn {
        position: relative;
        top: 10px;
        cursor: pointer;
    }
`;

function AdminContent() {

    const navigate = useNavigate();

    const[contents, setContents] = useState([]);
    const[totalPages, setTotalPages] = useState();
    const[pageNumber, setPageNumber] = useState();
    const[firstPage, setFirstPage] = useState(0);
    const[currentPageList, setCurrentPageList] = useState([]);

    function logout() {
        sessionStorage.removeItem("userData");
        navigate("/");
    }

    useEffect(() => {
        axios.get("http://localhost:9000/nestCoData")
        .then(function(response) {
            console.log(response)
            setContents(response.data.content);
            setTotalPages(response.data.totalPages);
            setPageNumber(response.data.pageable.pageNumber);
        })
        .catch(function(error) {
            alert('데이터 조회 실패');
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
        axios.get("http://localhost:9000/nestCoData?page=" + firstPage)
        .then(function(response) {
            setContents(response.data.content);
            setPageNumber(response.data.pageable.pageNumber);
        })
        .catch(function(error) {
            alert('데이터 조회 실패');
        })
    }

    function last() {
        axios.get("http://localhost:9000/nestCoData?page=" + (totalPages - 1))
        .then(function(response) {
            setContents(response.data.content);
            setPageNumber(response.data.pageable.pageNumber);
        })
        .catch(function(error) {
            alert('데이터 조회 실패');
        })
    }

    function prev() {
        axios.get("http://localhost:9000/nestCoData?page=" + (pageNumber - 1))
        .then(function(response) {
            setContents(response.data.content);
            setPageNumber(response.data.pageable.pageNumber);
        })
        .catch(function(error) {
            alert('데이터 조회 실패');
        })
    }

    function next() {
        axios.get("http://localhost:9000/nestCoData?page=" + (pageNumber + 1))
        .then(function(response) {
            setContents(response.data.content);
            setPageNumber(response.data.pageable.pageNumber);
        })
        .catch(function(error) {
            alert('데이터 조회 실패');
        })
    }

    function current(currentPage) {
        axios.get("http://localhost:9000/nestCoData?page=" + (currentPage - 1))
        .then(function(response) {
            setContents(response.data.content);
            setPageNumber(response.data.pageable.pageNumber);
        })
        .catch(function(error) {
            alert('데이터 조회 실패');
        })
    }

    return(
        <StyledAdminDiv>
            <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
                <div className="container-fluid">
                    <a className="navbar-brand" href="#">Admin Dashboard</a>
                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarNav">
                        <ul className="navbar-nav ms-auto">
                            <li className="nav-item">
                                <p id="logout-btn" onClick={logout} className="nav-link">로그아웃</p>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            <div className="container-fluid">
                <div className="row">
                    <nav id="sidebarMenu" className="col-md-3 col-lg-2 d-md-block bg-light sidebar collapse">
                        <div className="position-sticky pt-3">
                            <ul className="nav flex-column">
                                <li className="nav-item">
                                    <Link to="/admin" className="nav-link active">대시보드</Link>
                                </li>
                                <li className="nav-item">
                                    <Link to="/admin/members" className="nav-link">회원 관리</Link>
                                </li>
                                <li className="nav-item">
                                    <Link to="/admin/contents" className="nav-link">콘텐츠 관리</Link>
                                </li>
                                <li className="nav-item">
                                    <Link to="/admin/boards" className="nav-link">게시글 관리</Link>
                                </li>
                                <li className="nav-item">
                                    <Link to="/admin/notices" className="nav-link">공지사항 관리</Link>
                                </li>
                                <li className="nav-item">
                                    <Link to="/admin/categories" className="nav-link">카테고리 관리</Link>
                                </li>
                            </ul>
                        </div>
                    </nav>
                    <main className="col-md-9 ms-sm-auto col-lg-10 px-md-4">
                        <br></br><br></br>
                        <h1>콘텐츠 관리</h1>
                        <form action="/admin/contents" method="GET" className="input-group mt-3 mb-3">
                            <input type="text" name="searchKey" className="form-control" placeholder="검색어를 입력하세요..." aria-label="검색어 입력" aria-describedby="button-addon2" />
                            <button className="btn btn-outline-secondary" type="submit" id="button-addon2">검색</button>
                        </form>
                        <table className="table">
                            <thead>
                            <tr>
                                <th>콘텐츠 ID</th>
                                <th>제목</th>
                                <th>카테고리</th>
                                <th>회원 정보</th>
                                <th>등록일</th>
                                <th>액션</th>
                            </tr>
                            </thead>
                            <tbody>
                                {contents.map((content) => (
                                <tr>
                                    <td>{content.id}</td>
                                    <td>{content.title}</td>
                                    <td>{content.category.name}</td>
                                    <td><a href="/admin/users/{{ uploader }}" className="btn btn-info btn-sm">{content.uploader.nickname}</a></td>
                                    <td>{content.createDate}</td>
                                    <td>
                                        <form action="/admin/contents/delete/{{ id }}" method="POST">
                                            <button type="submit" class="btn btn-danger btn-sm">삭제</button>
                                        </form>
                                    </td>
                                </tr>
                                ))}
                            </tbody>
                        </table>
                        <nav aria-label="...">
                          <ul className="pagination">
                            {pageNumber != firstPage ? (
                            <>
                            <li className="page-item">
                                <p className="page-link" onClick={first}>First</p>
                            </li>
                            <li className="page-item">
                              <p className="page-link" onClick={prev}>Prev</p>
                            </li>
                            </>
                            ): null}
                            {currentPageList.map((currentPage) => (
                            <li className={pageNumber == (currentPage - 1) ? "page-item active" : "page-item"}>
                              <p className="page-link" onClick={() => current(currentPage)}>{currentPage}</p>
                            </li>
                            ))}
                            {pageNumber != (totalPages - 1) ? (
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
                    </main>
                </div>
            </div>
        </StyledAdminDiv>
    )
}

export default AdminContent;
