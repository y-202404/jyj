import styled from 'styled-components';
import nestCoLogo from '../../images/nestCo.png';

const StyledSearchIdMain = styled.main`
    input[type="number"]::-webkit-outer-spin-button,
    input[type="number"]::-webkit-inner-spin-button {
        -webkit-appearance: none;
        margin: 0;
    }

    * {
        margin: 0px;
        padding: 0px;
    }

    ul, ol {
        list-style: none;
    }

    a {
        text-decoration: none
    }

    width: 100%;
    height: 800px;
    background-color: rgb(239, 231, 218);

    .container {
        width: 500px;
        height: 800px;
        margin: 0 auto;
        position: relative;
    }

    .nestCo {
        width: 400px;
        height: 300px;
        position: absolute;
        top: 30px;
        left: 60px;
    }

    h1 {
        position: absolute;
        top: 330px;
        left: 165px;
    }

    .search-box {
        position: absolute;
        top: 400px;
        left: 150px;
    }

    .first {
        width: 200px;
        height: 30px;
        margin-bottom: 10px;
    }


    .second {
        width: 200px;
        height: 30px;
        margin-bottom: 10px;
    }

    .first-button {
        margin: 0px 0px 10px 55px;
        background-color: rgb(77, 68, 68);
        color: white;
        height: 30px;
    }

    .second-button {
        background-color: rgb(77, 68, 68);
        color: white;
        height: 30px;
        margin: 0px 0px 10px 55px;
    }
`;

function SearchId() {
    return(
        <StyledSearchIdMain>
            <div class="container">
                <img src={nestCoLogo} alt="" class="nestCo" />
                <h1>아이디 찾기</h1>
                <div class="search-box">
                    <div>
                        <input class="first" id="phoneNumber" type="number" name="phoneNum" placeholder="휴대폰 번호 입력" />
                    </div>
                    <button class="first-button" id="transmit-number">인증번호 전송</button>
                    <div>
                        <input class="second" id="number" type="number" placeholder="인증번호 입력" />
                    </div>
                    <button class="second-button" id="check-number">인증번호 확인</button>
                </div>
            </div>
        </StyledSearchIdMain>
    )
}

export default SearchId;