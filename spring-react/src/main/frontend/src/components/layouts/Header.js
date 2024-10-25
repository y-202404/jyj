import styled from 'styled-components';
import logo from '../../images/logo3.png';
import {Link, useNavigate, useLocation} from 'react-router-dom';

const StyledHeader = styled.header`

    .header-wrapper * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }

    .header-wrapper body {
        font-family: 'Noto Sans', sans-serif;
    }

    .header-wrapper .header-container {
        width: 100%;
        background-color: #4C7263;
        color: white;
        border-bottom: 1px solid #e0e0e0;
    }

    .header-wrapper .header-content {
        max-width: 1700px;
        margin: 0 auto;
        padding: 30px 20px;
    }

    .header-wrapper .header-inner {
        display: flex;
        align-items: center;
        justify-content: space-between;
        flex-wrap: nowrap; /* 화면이 작아져도 가로로 유지 */
    }

    .header-wrapper .logo {
        display: flex;
        align-items: center;
        margin-right: 20px;
    }

    .header-wrapper .logo img {
        height: 5rem;
    }

    .header-wrapper .logo-text {
        font-size: 24px;
        font-weight: bold;
        color: white;
    }

    .header-wrapper .category {
        position: relative;
        display: inline-block;
    }

    /* 사이드바와 하위 카테고리 스타일 */
    .header-wrapper .side-bar {
        display: none;
        position: absolute;
        top: 100%; /* 버튼 바로 아래에 위치 */
        left: 0;
        width: 250px;
        background-color: white;
        border: 1px solid #ccc;
        text-decoration:none;
        z-index: 100;
        box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1); /* 그림자 효과 추가 */
        padding: 10px;
    }

    .header-wrapper .side-bar ul {
        list-style: none;
        padding: 0;
        margin: 0;
    }

    /* 대분류 카테고리 스타일 */
    .header-wrapper .side-bar ul li {
        position: relative;
        padding: 10px 15px;
        color: black;
        cursor: pointer;
        margin: 0 -10px 0 0;
        border-bottom: none;
    }

    .header-wrapper .side-bar ul li ul.middle-category,
    .header-wrapper .side-bar ul li ul.child-category {
        display: none;
        position: absolute;
        left: 100%; /* 상위 카테고리의 오른쪽에 위치 */
        top: 0; /* 상위 카테고리와 수평을 맞춤 */
        width: 200px;
        background-color: white;
        z-index: 1000;
        padding: 0;
        margin: 0;
    }

    .header-wrapper .side-bar ul li ul.middle-category li,
    .header-wrapper .side-bar ul li ul.child-category li {
        padding: 8px 10px;
        margin: 0;
        border: none;
    }

    /* hover 시 중분류 및 소분류 표시 */
    .header-wrapper .side-bar ul li:hover > ul.middle-category,
    .header-wrapper .side-bar ul li ul.middle-category li:hover > ul.child-category {
        display: block;
    }

    /* hover 시 배경색 */
    .header-wrapper .side-bar ul li:hover {
        background-color: #f0f0f0;
    }

    /* 중분류 및 소분류 hover 시 배경색 */
    .header-wrapper .side-bar ul li ul.middle-category li:hover,
    .header-wrapper .side-bar ul li ul.child-category li:hover {
        background-color: #f0f0f0;
    }


    /* 카테고리 버튼 */
    .header-wrapper .category-btn {
        background-color: #5D4037;
        border: none;
        color: white;
        padding: 10px 15px;
        font-size: 18px;
        cursor: pointer;
        border-radius: 4px;
        margin-right: 10px;
        white-space: nowrap; /* 텍스트가 줄 바꿈되지 않도록 설정 */
        flex-shrink: 0;      /* 버튼의 크기가 작아지지 않도록 설정 */
    }

    .header-wrapper .category-btn:hover {
        background-color: #3E2723;
    }

    /* 검색창 */
    .header-wrapper .search-form {
        position: relative;
        flex-grow: 1;
        margin-right: 20px;
    }

    .header-wrapper .search-form input[type="search"] {
        width: 100%;
        padding: 10px 50px 10px 20px;
        border: 1px solid #ccc;
        border-radius: 4px;
        outline: none;
        font-size: 18px;
    }

    .header-wrapper .search-form button {
        position: absolute;
        right: 10px;
        top: 50%;
        transform: translateY(-50%);
        border: none;
        color: white;
        padding: 5px 10px;
        border-radius: 4px;
        cursor: pointer;
        font-size: 18px;
    }

    /* 아이콘 메뉴들 */
    .header-wrapper .icon-menus {
        display: flex;
        align-items: center;
        flex-shrink: 0;
        flex-wrap: nowrap; /* 메뉴들이 세로로 쌓이지 않도록 설정 */
    }

    .header-wrapper .icon-menus button {
        background-color: transparent;
        border: none;
        color: white;
        margin: 0 10px;
        font-size: 18px;
        cursor: pointer;
    }

    .header-wrapper .icon-menus .notification-badge {
        background-color: red;
        color: white;
        border-radius: 50%;
        padding: 2px 6px;
        font-size: 12px;
    }

    /* 드롭다운 메뉴 */
    .header-wrapper .dropdown {
        position: relative;
        display: inline-block;
    }

    .header-wrapper .dropdown-content {
        display: none;
        position: absolute;
        background-color: #f9f9f9;
        box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
        min-width: 160px;
        z-index: 1;
    }

    .header-wrapper .dropdown-content div {
        color: black;
        padding: 12px 16px;
        text-decoration: none;
        display: block;
        cursor: pointer;
    }

    .header-wrapper .dropdown-content div:hover {
        background-color: #4aa8d8;
    }

    .header-wrapper .dropdown:hover .dropdown-content {
        display: block;
        display: flex;
        flex-direction: column;
    }

    .header-wrapper .dropdown:hover .dropbtn {
        background-color: #3e8e41;
    }

    /* 로그인 버튼 */
    .header-wrapper .login {
        background-color: #5D4037;
        border: none;
        color: white;
        padding: 10px 20px;
        font-size: 18px;
        cursor: pointer;
        border-radius: 4px;
    }

    .header-wrapper .login:hover {
        background-color: #3E2723;
    }

    /* 하단 서브메뉴 */
    .header-wrapper .header-bottom {
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 30px 20px 10px 20px;
        color: white;
    }

    .header-wrapper .header-location {
        font-size: 18px;
        display: flex;
        align-items: center;
    }

    .header-wrapper .header-location i {
        margin-right: 5px;
    }

    .header-wrapper .sub-menu {
        display: flex;
    }

    .header-wrapper .sub-menu li {
        margin-right: 20px;
    }

    .header-wrapper .sub-menu li Link {
        color: white;
        text-decoration: none;
        font-size: 18px;
        padding: 0 10px;
    }

    .header-wrapper .sub-menu li Link:hover {
    }

    /* 반응형 레이아웃 설정 */
    @media (max-width: 768px) {
        .header-wrapper .header-inner {
            flex-wrap: nowrap; /* 화면이 작아져도 가로로 유지 */
        }

        .header-wrapper .category-btn {
            font-size: 18px;
            padding: 8px 12px;
            white-space: nowrap; /* 버튼 텍스트가 줄 바꿈되지 않도록 설정 */
        }

        .header-wrapper .icon-menus {
            flex-wrap: nowrap; /* 세로로 변경되지 않게 유지 */
        }
    }

    .link {
        color: white;
        text-decoration: none;
        cursor: pointer;
    }
`;

function Header() {

    const userData = JSON.parse(sessionStorage.getItem("userData"));

    const navigate = useNavigate();
    const location = useLocation();

    function login() {
        navigate("/loginForm", {state: location.pathname});
    }

    function logout() {
        sessionStorage.removeItem("userData");
        if(location.pathname != "/board/show") {
            navigate(location.pathname);
        } else {
            navigate("/board");
        }
    }

    function moveAdmin() {
        if(userData == null) {
            alert('접근할 수 있는 권한이 없습니다.');
        } else {
            if(userData.role != "ROLE_ADMIN") {
                alert('접근할 수 있는 권한이 없습니다.');
            } else {
                navigate('/admin');
            }
        }
    }

    function moveMyPage() {
        navigate("/myPage");
    }

    return(
        <StyledHeader>
            <div className="header-wrapper">
                <div className="header-container">
                    <div className="header-content">
                        <div className="header-inner">
                            <div className="logo">
                                <Link to="/">
                                    <img src={logo} alt="NestCo Logo" />
                                </Link>
                            </div>
                            <div className="category">
                                <button id="category-btn" className="category-btn">
                                    <i class="fas fa-bars"></i>카테고리
                                </button>
                                <aside id="side-bar" className="side-bar">
                                    <ul id="parentCategoryList">
                                        <li data-id="{{id}}" className="parent-category">
                                            <i className="{{icon}}"></i> name
                                            <ul className="middle-category">
                                                <li data-id="{{parentId}}" className="middle-category-item">name
                                                    <ul class="child-category" >
                                                        <li data-id="{{parentId}}" className="child-category-item">name</li>
                                                    </ul>
                                                </li>
                                            </ul>
                                        </li>
                                    </ul>
                                </aside>
                            </div>
                            <div className="search-form">
                                <form id="headerSearchForm" action="/nestco" method="get">
                                    <input id="search" type="search"  name="searchQuery" placeholder="검색어를 입력해주세요" />
                                    <button type="submit"><i className="fas fa-search"></i></button>
                                </form>
                            </div>
                            <div className="icon-menus">
                                <button className="item-register"><i className="fas fa-box-open"></i>물품 등록</button>
                                   <button className="like-items"><i class="fas fa-heart"></i>찜한 물품</button>-->
                                <button className="notice"><i className="fas fa-bell"></i>알림
                                    <span className="notification-badge">1</span>
                                </button>
                                {userData ? (
                                <div class="dropdown">
                                    <button id="userDropdown" className="dropbtn">{userData.nickname}</button>
                                    <div className="dropdown-content">
                                        <div onClick={moveMyPage}>마이페이지</div>
                                        <div onClick={logout}>로그아웃</div>
                                    </div>
                                </div>
                                ) : (
                                <button className="loginForm" onClick={login}>
                                    <i className="fas fa-user"></i>로그인
                                </button>
                                )}
                            </div>
                        </div>
                        <div className="header-bottom">
                            <div className="header-location">
                                <i class="fas fa-map-marker-alt">{userData != null ? userData.address : ""}</i>
                            </div>
                            <ul className="sub-menu">
                                <li><Link to="/board" className="link">자유게시판</Link></li>
                                <li><Link to="/center" className="link">고객센터</Link></li>
                                <li><p className="link" onClick={moveAdmin}>관리자 페이지</p></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </StyledHeader>
    )
}

export default Header;