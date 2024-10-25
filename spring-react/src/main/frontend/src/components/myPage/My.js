import axios from 'axios';
import styled from 'styled-components';
import {useEffect, useState } from 'react';
import {Link, useNavigate} from 'react-router-dom';
import nestCoLogo from '../../images/nestCo.png';

const styledDiv = styled.div`

    position: relative;

    h1, h2, h3, h4, h5, p{
        margin: 0px;
    }

    #profileImg {
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .btn-primary {
        background-color : #4A2C2A;
        border: 0px;
    }

    .card-body {
        padding: 15px;
        overflow: hidden;
        max-height: 450px;
        overflow-y: auto;
    }

    .title {
        text-align: center;
        background-color: #4C7263;
        color: #fff;
        padding: 15px;
    }

    .item-list {
        height: 150px;
    }

    .item-desc {
        flex:3;
    }

    .item-list-img {
        width: 150px;
        height: 100%;
        display: block; /* 또는 inline-block */
        margin: 0 auto; /* 가로 중앙 정렬 */
        flex:1;
        margin-right: 10px;
    }

    .buttons {
        display: flex;                /* Flexbox 사용 */
    }
    .moving-button {
        margin-left: auto;
    }
    .moving-button .btn {
        margin-left: 10px;           /* 버튼 간의 간격 */
    }

    #goToTop {
        position: fixed; /* 화면의 고정 위치 */
        bottom: 20px; /* 아래에서 20px */
        right: 20px; /* 오른쪽에서 20px */
        display: none; /* 기본적으로 숨김 */
        border: none; /* 테두리 없음 */
        cursor: pointer; /* 커서 포인터 */
        z-index: 1000; /* 다른 요소 위에 표시 */
    }

    .inline {
        display: inline;
    }

    #sample6_postcode {
        width: 150px;
    }

    div input.btn {
        width:100px;
        margin-left: 50px;
    }

    #clone {
        height: 270px;
    }

    #cloneNumber {
        height: 334px;
    }

    #cloneImg {
        object-fit: cover;
        width: 100%;
        height: auto;
        max-height: 100%;
    }
`;

function My() {

    const userData = JSON.parse(sessionStorage.getItem("userData"));

    return(
        <styledDiv>
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-md-6 col-12">
                        <div class="card mt-5">
                            <h3 class="text-left title">안녕하세요 {userData.username}님!</h3>
                            <div id="clone" class="card-body">
                                <div class="mb-3">
                                    <label class="form-label">나의 별명 : {userData.nickname}</label>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">전화번호 : {userData.phoneNumber}</label>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">E-Mail : {userData.email}</label>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Address : {userData.address} {userData.detailedAddress}</label>
                                </div>
                                <div id="buttons" class="buttons">
                                    <a href="/updateMyPage" class="btn btn-primary">내 정보 변경</a>
                                    <div class="moving-button">
                                        <button class="btn btn-primary" id="goToWatchlist">관심물품</button>
                                        <button class="btn btn-primary" id="goToMyPost">내 게시글</button>
                                        <button class="btn btn-primary" id="goToXchgItems">교환물품</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3 col-12">
                        <div id="cloneNumber" class="card mt-5">
                            <div class="card-body" id="profileImg">
                                <img
                                        id="cloneImg"
                                        class="profileImg Img"
                                        alt="프로필 사진"
                                />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-md-10">
                        <div class="card mt-5">
                            <h5 class="title"> 관심 목록 </h5>
                            <div class="card-body">
                                <div class="d-flex align-items-center item-list">
                                    <img class="item-list-img" src="{{thumbnailPath}}" alt="관심목록 사진1" />
                                    <div class="item-desc">
                                        <h5>itemName</h5>
                                        <p>업로더: uploaderName</p>
                                        <p>거래 상태: transactionStatus</p>
                                        <a href="/items/{{itemId}}" class="btn btn-primary">상세 페이지로 이동</a>
                                    </div>
                                </div>
                                <div class="d-flex align-items-center item-list">
                                    <img class="item-list-img" src="/images/NestCoWhite.svg" alt="관심목록 사진2" />
                                    <div class="item-desc">
                                        <h5>관심목록 2번 제목</h5>
                                        <p>관심목록 2번의 상세설명</p>
                                    </div>
                                </div>
                                <div class="d-flex align-items-center item-list">
                                    <img class="item-list-img" src="/images/NestCoGreen.svg" alt="관심목록 사진3" />
                                    <div class="item-desc">
                                        <h5>관심목록 3번 제목</h5>
                                        <p>관심목록 3번의 상세설명</p>
                                    </div>
                                </div>
                                <div class="d-flex align-items-center item-list">
                                    <img class="item-list-img" src="/images/NestCoWhite.svg" alt="관심목록 사진4" />
                                    <div class="item-desc">
                                        <h5>관심목록 4번 제목</h5>
                                        <p>관심목록 4번의 상세설명</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-md-10">
                        <div class="card mt-5">
                            <h5 class="title"> 나의 게시글 </h5>
                            <div class="card-body">
                                <div class="d-flex align-items-center item-list">
                                    <img class="item-list-img" src="/images/NestCoWhite.svg" alt="게시글 사진1" />
                                    <div class="item-desc">
                                        <h5>게시글 1번 제목</h5>
                                        <p>게시글 1번의 상세설명</p>
                                    </div>
                                </div>
                                <div class="d-flex align-items-center item-list">
                                    <img class="item-list-img" src="/images/NestCoGreen.svg" alt="게시글 사진2" />
                                    <div class="item-desc">
                                        <h5>게시글 2번 제목</h5>
                                        <p>게시글 2번의 상세설명</p>
                                    </div>
                                </div>
                                <div class="d-flex align-items-center item-list">
                                    <img class="item-list-img" src="/images/NestCoWhite.svg" alt="게시글 사진3" />
                                    <div class="item-desc">
                                        <h5>게시글 3번 제목</h5>
                                        <p>게시글 3번의 상세설명</p>
                                    </div>
                                </div>
                                <div class="d-flex align-items-center item-list">
                                    <img class="item-list-img" src="/images/NestCoGreen.svg" alt="게시글 사진4" />
                                    <div class="item-desc">
                                        <h5>게시글 4번 제목</h5>
                                        <p>게시글 4번의 상세설명</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-md-10">
                        <div class="card mt-5">
                            <h5 class="title"> 나의 교환 내역 </h5>
                            <div class="card-body">
                                <div class="d-flex align-items-center item-list">
                                    <img class="item-list-img" src="/images/NestCoWhite.svg" alt="교환물품 사진1" />
                                    <div class="item-desc">
                                        <h5>교환물품 1번 제목</h5>
                                        <p>교환물품 1번의 상세설명</p>
                                    </div>
                                </div>
                                <div class="d-flex align-items-center item-list">
                                    <img class="item-list-img" src="/images/NestCoGreen.svg" alt="교환물품 사진2" />
                                    <div class="item-desc">
                                        <h5>교환물품 2번 제목</h5>
                                        <p>교환물품 2번의 상세설명</p>
                                    </div>
                                </div>
                                <div class="d-flex align-items-center item-list">
                                    <img class="item-list-img" src="/images/NestCoWhite.svg" alt="교환물품 사진3" />
                                    <div class="item-desc">
                                        <h5>교환물품 3번 제목</h5>
                                        <p>교환물품 3번의 상세설명</p>
                                    </div>
                                </div>
                                <div class="d-flex align-items-center item-list">
                                    <img class="item-list-img" src="/images/NestCoGreen.svg" alt="교환물품 사진4" />
                                    <div class="item-desc">
                                        <h5>교환물품 4번 제목</h5>
                                        <p>교환물품 4번의 상세설명</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </styledDiv>
    );
}

export default My;