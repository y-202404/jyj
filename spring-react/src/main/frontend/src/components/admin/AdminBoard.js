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

function AdminBoard() {

    const navigate = useNavigate();

    function logout() {
        sessionStorage.removeItem("userData");
        navigate("/");
    }

    const[boards, setBoards] = useState([]);
    const[pageNumber, setPageNumber] = useState();
    const[totalPages, setTotalPages] = useState();
    const[firstPage, setFirstPage] = useState(0);
    const[currentPageList, setCurrentPageList] = useState([]);
    const[searchKey, setSearchKey] = useState("");

    useEffect(() => {
        axios.get("http://localhost:9000/boardData")
        .then(function(response) {
            console.log(response.data);
            setBoards(response.data.content);
            setTotalPages(response.data.totalPages);
            setPageNumber(response.data.pageable.pageNumber);
        })
        .catch(function(error) {
            alert('데이터 조회 실패')
        });
    }, []);

    useEffect(() => {
        const pages= [];

        for(let i = 1; i <= totalPages; i++) {
            pages.push(i)
        }
        setCurrentPageList(pages);

    }, [totalPages]);

    function first() {
        if(searchKey == null) {
            axios.get("http://localhost:9000/boardData?page=" + firstPage)
            .then(function(response) {
                setBoards(response.data.content);
                setPageNumber(response.data.pageable.pageNumber);
            })
            .catch(function(error) {
                alert('데이터 조회 실패');
            });
        } else {
            axios.get("http://localhost:9000/searchBoard?page=" + firstPage + "&searchKey=" + searchKey)
            .then(function(response) {
                setBoards(response.data.content);
                setPageNumber(response.data.pageable.pageNumber);
            })
            .catch(function(error) {
                alert('데이터 조회 실패');
            });
        }
    }

    function last() {
        if(searchKey == null) {
            axios.get("http://localhost:9000/boardData?page=" + (totalPages -1))
            .then(function(response) {
                setBoards(response.data.content);
                setPageNumber(response.data.pageable.pageNumber);
            })
            .catch(function(error) {
                alert('데이터 조회 실패');
            });
        } else {
            axios.get("http://localhost:9000/searchBoard?page=" + (totalPages -1) + "&searchKey=" + searchKey)
            .then(function(response) {
                setBoards(response.data.content);
                setPageNumber(response.data.pageable.pageNumber);
            })
            .catch(function(error) {
                alert('데이터 조회 실패');
            });
        }
    }

    function prev() {
        if(searchKey == null) {
            axios.get("http://localhost:9000/boardData?page=" + (pageNumber -1))
            .then(function(response) {
                setBoards(response.data.content);
                setPageNumber(response.data.pageable.pageNumber);
            })
            .catch(function(error) {
                alert('데이터 조회 실패');
            });
        } else {
            axios.get("http://localhost:9000/searchBoard?page=" + (pageNumber -1) + "&searchKey=" + searchKey)
            .then(function(response) {
                setBoards(response.data.content);
                setPageNumber(response.data.pageable.pageNumber);
            })
            .catch(function(error) {
                alert('데이터 조회 실패');
            });
        }
    }

    function next() {
        if(searchKey == null) {
            axios.get("http://localhost:9000/boardData?page=" + (pageNumber + 1))
            .then(function(response) {
                setBoards(response.data.content);
                setPageNumber(response.data.pageable.pageNumber);
            })
            .catch(function(error) {
                alert('데이터 조회 실패');
            });
        } else {
            axios.get("http://localhost:9000/searchBoard?page=" + (pageNumber + 1) + "&searchKey=" + searchKey)
            .then(function(response) {
                setBoards(response.data.content);
                setPageNumber(response.data.pageable.pageNumber);
            })
            .catch(function(error) {
                alert('데이터 조회 실패');
            });
        }
    }

    function current(currentPage) {
        if(searchKey == null) {
            axios.get("http://localhost:9000/boardData?page=" + (currentPage - 1))
            .then(function(response) {
                setBoards(response.data.content);
                setPageNumber(response.data.pageable.pageNumber);
            })
            .catch(function(error) {
                alert('데이터 조회 실패');
            });
        } else {
            axios.get("http://localhost:9000/searchBoard?page=" + (currentPage - 1) + "&searchKey=" + searchKey)
            .then(function(response) {
                setBoards(response.data.content);
                setPageNumber(response.data.pageable.pageNumber);
            })
            .catch(function(error) {
                alert('데이터 조회 실패');
            });
        }
    }

    function searchBoardData() {
        if(searchKey == "") {
            alert('검색어를 입력해주십시오.');
        } else {
            axios.get("http://localhost:9000/searchBoard?searchKey=" + searchKey)
            .then(function(response) {
                setBoards(response.data.content);
                setPageNumber(response.data.pageable.pageNumber);
                setTotalPages(response.data.totalPages);
            })
            .catch(function(error) {
                alert('데이터 조회 실패');
            });
        }
    }

    function deleteBoard(boardId) {
        axios.get("http://localhost:9000/deleteBoard?boardId=" + boardId)
        .then(function(response) {
            alert(response.data);
            axios.get("http://localhost:9000/boardData")
            .then(function(response) {
                setBoards(response.data.content);
                setTotalPages(response.data.totalPages);
                setPageNumber(response.data.pageable.pageNumber);
            })
            .catch(function(error) {
                alert('데이터 조회 실패');
            })
        })
        .catch(function(error) {
            alert('게시글 삭제 실패');
        });
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
                        <h1>게시글 관리</h1>
                        <div className="input-group mt-3 mb-3">
                            <input type="text" className="form-control" name="searchKey" onChange={(e) => setSearchKey(e.target.value)} placeholder="검색어를 입력하세요..." aria-label="검색어 입력" aria-describedby="button-addon2" />
                            <button className="btn btn-outline-secondary" onClick={searchBoardData} type="button" id="button-addon2">검색</button>
                        </div>
                        <table className="table">
                            <thead>
                            <tr>
                                <th>게시글 ID</th>
                                <th>제목</th>
                                <th>작성자 정보</th>
                                <th>작성일</th>
                                <th>액션</th>
                            </tr>
                            </thead>
                            <tbody>
                                {boards.map((board) => (
                                <tr>
                                    <td>{board.id}</td>
                                    <td>{board.title}</td>
                                    <td><Link to="#" className="btn btn-info btn-sm">{board.uploader.nickname}</Link></td>
                                    <td>{board.createDate}</td>
                                    <td>
                                        <button type="button" onClick={() => deleteBoard(board.id)} className="btn btn-danger btn-sm">삭제</button>
                                    </td>
                                </tr>
                                ))}
                            </tbody>
                        </table>
                        <nav aria-label="...">
                          <ul className="pagination">
                            {pageNumber != 0 ? (
                            <>
                            <li className="page-item">
                                <p className="page-link" onClick={first}>First</p>
                            </li>
                            <li className="page-item">
                              <p className="page-link" onClick={prev}>Prev</p>
                            </li>
                            </>
                            ) : null}
                            {currentPageList.map((currentPage) => (
                            <li className={pageNumber == (currentPage -1) ? "page-item active" : "page-item"}>
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
                            ) : null}
                          </ul>
                        </nav>
                    </main>
                </div>
            </div>
        </StyledAdminDiv>
    )
}

export default AdminBoard;