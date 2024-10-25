import styled from 'styled-components';
import {useState} from 'react';
import {Link, useNavigate} from 'react-router-dom';
import axios from 'axios';

const StyledDiv = styled.div`
    /* Global Styles */
    background-color: #4A2C2A;
    color: #EFE7DA;
    font-family: 'Arial', sans-serif;

    /* Title and Header */
    .title {
        color: #EFE7DA;
        font-size: 2rem;
        margin-bottom: 10px;
        text-align: center; /* 제목 중앙 정렬 */
    }

    /* Service Center Container */
    .service-center {
        background-color: #4A2C2A;
        padding: 20px;
        border-radius: 10px;
    }

    /* Form Section 중앙 정렬 */
    .one-on-one-section {
        background-color: #EFE7DA;
        padding: 20px;
        border-radius: 10px;
        max-width: 600px;
        margin: 0 auto;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        min-height: 400px;
    }

    /* Form Styles */
    form {
        width: 100%;
        max-width: 500px;
        display: flex;
        flex-direction: column;
        align-items: center;
    }

    form .form-group {
        margin-bottom: 10px;
        width: 100%;
    }

    /* 문의 내용 텍스트 필드 확장 */
    .expanded-textarea {
        width: 100%;
        height: 300px;
        padding: 10px;
        border: 1px solid #4A2C2A;
        border-radius: 5px;
    }

    /* 더 컴팩트한 드롭다운 */
    .compact-select {
        height: 35px;
        padding: 5px;
        width: 100%;
    }

    /* Button Styles */
    form button {
        background-color: #4C7263;
        color: #EFE7DA;
        padding: 10px 15px;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        margin-top: 10px;
        width: 150px;
        text-align: center;
        align-self: center;
    }

    form button:hover {
        background-color: #3B5C54;
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
        background-color: #4C7263;
        margin-left: 0;
        box-shadow: none; /* 그림자 제거 */
        border: none; /* 테두리가 필요 없으면 제거 */
    }

    /* 호버 시 버튼 색상 변화 */
    .button-group .btn:hover {
        cursor: pointer;
        background-color: #3B5C54; /* 약간 어두운 색으로 변경 */
    }
`;

function CenterOneOnOne() {

    const userData = JSON.parse(sessionStorage.getItem("userData"));

    const navigate = useNavigate();

    const[content, setContent] = useState();
    const[type, setType] = useState("purchase");


    function moveOne() {
        axios.post("http://localhost:9000/oneAdd", {memberDTO: userData, oneOnOneDTO: {content, type}})
        .then(function(response) {
            alert('문의하기 성공');
            setTimeout(function() {
                navigate("/center");
            }, 100)
        })
        .catch(function(error) {
            alert('문의하기 실패');
        });
    }

    function moveCenter() {
        navigate("center");
    }

    return(
        <StyledDiv>
            <div class="container mt-5 service-center">
                <h1 class="text-center mb-4 title">1:1문의</h1>
                <hr class="divider"></hr>
                <br></br>

                <section class="one-on-one-section">
                    <form id="oneOnOneForm" action="/nestco/oneOneOnCreate" method="POST" class="text-center" onsubmit="submitForm(event)">
                        <div class="form-group mb-3">
                            <select name="type" onChange={(e) => setType(e.target.value)} required class="form-control limited-width compact-select">
                                <option value="purchase">구매문의</option>
                                <option value="sale">판매문의</option>
                            </select>
                        </div>

                        <div class="form-group mb-3">
                            <textarea name="content" onChange={(e) => setContent(e.target.value)} class="form-control limited-width expanded-textarea"
                                      placeholder="문의 내용을 입력하세요" required></textarea>
                        </div>

                        <button type="button" class="btn btn-primary" onClick={moveOne}>문의하기</button>
                    </form>

                    <div class="button-group mt-4">
                        <button class="btn btn-outline-light" onClick={moveCenter}>
                            뒤로가기
                        </button>
                    </div>
                </section>
                <br></br>
                <hr class="divider"></hr>
            </div>
        </StyledDiv>
    )
}

export default CenterOneOnOne;