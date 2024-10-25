import styled from 'styled-components';
import {Link, useNavigate} from 'react-router-dom';
import {useState, useEffect} from 'react';

const StyledAdminDiv = styled.div`
    #logout-btn {
        position: relative;
        top: 10px;
        cursor: pointer;
    }
`;

function Admin() {

    const navigate = useNavigate();

    function logout() {
        sessionStorage.removeItem("userData");
        navigate("/");
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
                                <p id="logout-btn" className="nav-link" onClick={logout}>로그아웃</p>
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
                        <div className="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                            <h1 className="h2">대시보드</h1>
                        </div>

                        <div className="row">
                            <div className="col-lg-3 col-md-6">
                                <div className="card text-white bg-primary mb-3">
                                    <div className="card-body">
                                        <h5 className="card-title">총 아이템 수</h5>
                                        <p className="card-text">   59개</p>
                                    </div>
                                </div>
                            </div>
                            <div className="col-lg-3 col-md-6">
                                <div className="card text-white bg-success mb-3">
                                    <div className="card-body">
                                        <h5 className="card-title">총 회원 수</h5>
                                        <p className="card-text">   50명</p>
                                    </div>
                                </div>
                            </div>
                            <div className="col-lg-3 col-md-6">
                                <div className="card text-white bg-warning mb-3">
                                    <div className="card-body">
                                        <h5 className="card-title">신규 등록 아이템</h5>
                                        <p className="card-text"> 25개</p>
                                    </div>
                                </div>
                            </div>
                            <div className="col-lg-3 col-md-6">
                                <div className="card text-white bg-danger mb-3">
                                    <div className="card-body">
                                        <h5 className="card-title">신고된 아이템</h5>
                                        <p className="card-text"> 30개</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </main>
                </div>
            </div>
        </StyledAdminDiv>
    )
}

export default Admin;