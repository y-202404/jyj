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

    .search {
        display: flex;
    }

    .search button {
        margin-left: 10px;
    }

    .sort-btn {
        display: flex;
        margin: 20px 0 20px 0;
    }

    .sort-btn button {
        margin-right: 10px;
    }

    #select {
        display: inline;
        height: 30px;
    }

    .declaration {
        display: flex;
    }

    .page-link {
        cursor: pointer;
    }
`;

function AdminMember() {

    const navigate = useNavigate();

    const[members, setMembers] = useState([]);
    const[totalPages, setTotalPages] = useState(0);
    const[pageNumber, setPageNumber]= useState(0);
    const[firstPage, setFirstPage] = useState(0);
    const[currentPageList, setCurrentPageList] = useState([]);
    const[idx, setIdx] = useState();
    const[searchKey, setSearchKey] = useState("");
    const[searching, setSearching] = useState();

    useEffect(() => {
        axios.get('http://localhost:9000/memberData')
        .then(function(response) {
            console.log(response.data);
            setMembers(response.data.content);
            setPageNumber(response.data.pageable.pageNumber);
            setTotalPages(response.data.totalPages);
        })
        .catch(function(error) {
            alert('데이터 조회 실패');
        });
    }, []);

    useEffect(() => {
        const pages= [];

        for(let i = 1; i <= totalPages; i++) {
            pages.push(i)
        }
        setCurrentPageList(pages);

    }, [totalPages])

    function logout() {
        sessionStorage.removeItem("userData");
        navigate("/");
    }

    function first() {
        if(idx == null) {
            if(searchKey == null) {
                axios.get("http://localhost:9000/memberData?page=" + firstPage)
                .then(function(response) {
                    setMembers(response.data.content);
                    setPageNumber(response.data.pageable.pageNumber);
                })
                .catch(function(error) {
                    alert('페이지 이동 실패');
                });
            } else {
                axios.get("http://localhost:9000/memberData?page=" + firstPage + "&searchKey=" + searchKey)
                .then(function(response) {
                    setMembers(response.data.content);
                    setPageNumber(response.data.pageable.pageNumber);
                })
                .catch(function(error) {
                    alert('페이지 이동 실패');
                });
            }
        } else {
            if(searchKey == null) {
                axios.get("http://localhost:9000/memberData?page=" + firstPage + "&idx=" + idx)
                .then(function(response) {
                    setMembers(response.data.content);
                    setPageNumber(response.data.pageable.pageNumber);
                })
                .catch(function(error) {
                    alert('페이지 이동 실패');
                });
            } else {
                axios.get("http://localhost:9000/memberData?page=" + firstPage + "&idx=" + idx + "searchKey=" + searchKey)
                .then(function(response) {
                    setMembers(response.data.content);
                    setPageNumber(response.data.pageable.pageNumber);
                })
                .catch(function(error) {
                    alert('페이지 이동 실패');
                });
            }
        }
    }

    function last() {
        if(idx == null) {
            if(searchKey == null) {
                axios.get("http://localhost:9000/memberData?page=" + (totalPages -1))
                .then(function(response) {
                    setMembers(response.data.content);
                    setPageNumber(response.data.pageable.pageNumber);
                })
                .catch(function(error) {
                    alert('페이지 이동 실패');
                });
            } else {
                axios.get("http://localhost:9000/memberData?page=" + (totalPages -1) + "&searchKey=" + searchKey)
                .then(function(response) {
                    setMembers(response.data.content);
                    setPageNumber(response.data.pageable.pageNumber);
                })
                .catch(function(error) {
                    alert('페이지 이동 실패');
                });
            }
        } else {
            if(searchKey == null) {
                axios.get("http://localhost:9000/memberData?page=" + (totalPages -1) + "&idx=" + idx)
                .then(function(response) {
                    setMembers(response.data.content);
                    setPageNumber(response.data.pageable.pageNumber);
                })
                .catch(function(error) {
                    alert('페이지 이동 실패');
                });
            } else {
                axios.get("http://localhost:9000/memberData?page=" + (totalPages -1) + "&idx=" + idx + "&searchKey=" + searchKey)
                .then(function(response) {
                    setMembers(response.data.content);
                    setPageNumber(response.data.pageable.pageNumber);
                })
                .catch(function(error) {
                    alert('페이지 이동 실패');
                });
            }
        }
    }

    function prev() {
        if(idx == null) {
            if(searchKey == null) {
                axios.get("http://localhost:9000/memberData?page=" + (pageNumber - 1))
                .then(function(response) {
                    setMembers(response.data.content);
                    setPageNumber(response.data.pageable.pageNumber);
                })
                .catch(function(error) {
                    alert('페이지 이동 실패');
                });
            } else {
                axios.get("http://localhost:9000/memberData?page=" + (pageNumber - 1) + "&searchKey=" + searchKey)
                .then(function(response) {
                    setMembers(response.data.content);
                    setPageNumber(response.data.pageable.pageNumber);
                })
                .catch(function(error) {
                    alert('페이지 이동 실패');
                });
            }
        } else {
            if(searchKey == null) {
                axios.get("http://localhost:9000/memberData?page=" + (pageNumber - 1) + "&idx=" + idx)
                .then(function(response) {
                    setMembers(response.data.content);
                    setPageNumber(response.data.pageable.pageNumber);
                })
                .catch(function(error) {
                    alert('페이지 이동 실패')
                });
            } else {
                axios.get("http://localhost:9000/memberData?page=" + (pageNumber - 1) + "&idx=" + idx + "&searchKey=" + searchKey)
                .then(function(response) {
                    setMembers(response.data.content);
                    setPageNumber(response.data.pageable.pageNumber);
                })
                .catch(function(error) {
                    alert('페이지 이동 실패')
                });
            }
        }
    }

    function next() {
        if(idx == null) {
            if(searchKey == null) {
                axios.get("http://localhost:9000/memberData?page=" + (pageNumber + 1))
                .then(function(response) {
                    setMembers(response.data.content);
                    setPageNumber(response.data.pageable.pageNumber);
                })
                .catch(function(error) {
                    alert('페이지 이동 실패');
                });
            } else {
                axios.get("http://localhost:9000/memberData?page=" + (pageNumber + 1) + "&searchKey=" + searchKey)
                .then(function(response) {
                    setMembers(response.data.content);
                    setPageNumber(response.data.pageable.pageNumber);
                })
                .catch(function(error) {
                    alert('페이지 이동 실패');
                });
            }
        } else {
            if(searchKey == null) {
                axios.get("http://localhost:9000/memberData?page=" + (pageNumber + 1) + "&idx=" + idx)
                .then(function(response) {
                    setMembers(response.data.content);
                    setPageNumber(response.data.pageable.pageNumber);
                })
                .catch(function(error) {
                    alert('페이지 이동 실패');
                });
            } else {
                axios.get("http://localhost:9000/memberData?page=" + (pageNumber + 1) + "&idx=" + idx + "&searchKey=" + searchKey)
                .then(function(response) {
                    setMembers(response.data.content);
                    setPageNumber(response.data.pageable.pageNumber);
                })
                .catch(function(error) {
                    alert('페이지 이동 실패');
                });
            }
        }
    }

    function current(currentPage) {
        if(idx == null) {
            if(searchKey == null) {
                axios.get("http://localhost:9000/memberData?page=" + (currentPage -1))
                .then(function(response) {
                    setMembers(response.data.content);
                    setPageNumber(response.data.pageable.pageNumber);
                })
                .catch(function(error) {
                    alert('페이지 이동 실패');
                });
            } else {
                axios.get("http://localhost:9000/memberData?page=" + (currentPage -1) + "&searchKey=" + searchKey)
                .then(function(response) {
                    setMembers(response.data.content);
                    setPageNumber(response.data.pageable.pageNumber);
                })
                .catch(function(error) {
                    alert('페이지 이동 실패');
                });
            }
        } else {
            if(searchKey == null) {
                axios.get("http://localhost:9000/memberData?page=" + (currentPage -1) + "&idx=" + idx)
                .then(function(response) {
                    setMembers(response.data.content);
                    setPageNumber(response.data.pageable.pageNumber);
                })
                .catch(function(error) {
                    alert('페이지 이동 실패');
                });
            } else {
                axios.get("http://localhost:9000/memberData?page=" + (currentPage -1) + "&idx=" + idx + "&searchKey=" + searchKey)
                .then(function(response) {
                    setMembers(response.data.content);
                    setPageNumber(response.data.pageable.pageNumber);
                })
                .catch(function(error) {
                    alert('페이지 이동 실패');
                });
            }
        }
    }

    function oldIdx() {
        setIdx("ascending");
        axios.get("http://localhost:9000/memberData?idx=" + idx)
        .then(function(response) {
            setMembers(response.data.content);
        })
        .catch(function(error) {
            alert('데이터 조회 실패');
        })
    }

    function newIdx() {
        setIdx();
        axios.get("http://localhost:9000/memberData")
        .then(function(response) {
            setMembers(response.data.content);
        })
        .catch(function(error) {
            alert('데이터 조회 실패');
        })
    }

    function search() {
        if(searchKey == "") {
            alert('검색어를 입력해 주십시오.');
        } else {
            axios.get("http://localhost:9000/searchMemberData?searchKey=" + searchKey)
            .then(function(response) {
                setMembers(response.data.content)
                setPageNumber(response.data.pageable.pageNumber)
                setTotalPages(response.data.totalPages);

                const pages= [];
                for(let i = 1; i <= response.data.totalPages; i++) {
                    pages.push(i)
                }
                setCurrentPageList(pages);
            })
            .catch(function(error) {
                alert('데이터 조회 실패');
            });
        }
    }

    function memberBlock(member) {
        axios.post("http://localhost:9000/memberBlock", member)
        .then(function(response) {
            alert(response.data);
            axios.get("http://localhost:9000/memberData")
            .then(function(response) {
                setMembers(response.data.content);
                setPageNumber(response.data.pageable.pageNumber);
            })
            .catch(function(error) {

            });
        })
        .catch(function(error) {
            alert('에러');
        });
    }


    return(
        <StyledAdminDiv>
            <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
                <div className="container-fluid">
                    <Link to="/" className="navbar-brand">Admin Dashboard</Link>
                    <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarNav">
                        <ul className="navbar-nav ms-auto">
                            <li className="nav-item">
                                <p id="logout-btn" onClick={logout} class="nav-link">로그아웃</p>
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
                                <li class="nav-item">
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
                                <li class="nav-item">
                                    <Link to="/admin/categories" className="nav-link">카테고리 관리</Link>
                                </li>
                            </ul>
                        </div>
                    </nav>
                    <main className="col-md-9 ms-sm-auto col-lg-10 px-md-4">
                        <br></br><br></br>
                        <h1>회원 관리</h1>
                        <div className="search">
                            <input type="text" className="form-control" name="searchKey" onChange={(e) => setSearchKey(e.target.value)} placeholder="검색어를 입력하세요..." aria-label="검색어 입력" aria-describedby="button-addon2" />
                            <button className="btn btn-outline-secondary" onClick={search} type="button" id="button-addon2">검색</button>
                        </div>
                        <div className="sort-btn">
                            <div>
                                <button type="button"  className="btn btn-secondary btn-lg" onClick={newIdx}>가입일 최신순</button>
                            </div>
                            <div>
                                <button type="button" className="btn btn-secondary btn-lg" onClick={oldIdx}>가입일 순서로 조회</button>
                            </div>
                        </div>
                        <table className="table">
                            <thead>
                            <tr>
                                <th>회원 ID</th>
                                <th>이름</th>
                                <th>이메일</th>
                                <th>가입일</th>
                                <th>액션</th>
                            </tr>
                            </thead>
                            <tbody>
                                {members.map((member) => (
                                <tr>
                                    <td> {member.userId} </td>
                                    <td> {member.username} </td>
                                    <td> {member.email} </td>
                                    <td> {member.createDate} </td>
                                    <td>
                                        <div className="declaration">
                                            <button id="select" onClick={() => memberBlock(member)} type="button" className="btn btn-danger btn-sm">{member.block == "0" ? "차단" : "차단해제"}</button>
                                            <p>신고 수: {member.declaration}</p>
                                        </div>
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
                            <li className={currentPage == (pageNumber + 1) ? "page-item active" : "page-item"}>
                              <p className="page-link" onClick={() => current(currentPage)}>{currentPage}</p>
                            </li>
                            ))}
                            {(pageNumber + 1) != totalPages ? (
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

export default AdminMember;