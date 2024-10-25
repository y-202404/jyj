import styled from 'styled-components';
import nestCoLogo from '../../images/nestCo.png';

const StyledSearchPasswordMain = styled.main`
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

    .container .search-box {
        position: absolute;
        top: 340px;
        left: 75px;
    }

    .container .search-box .first {
        width: 350px;
        height: 40px;
        margin-bottom: 10px;
    }

    .container .search-box .second {
        width: 150px;
        height: 40px;
        margin-bottom: 10px;
    }

    .container .search-box .number-button {
        width: 80px;
        height: 35px;
        background-color: rgb(77, 68, 68);
        font-size: 12px;
        margin-left: 10px;
    }

    .container .search-box .number-button:hover {
        background-color: #555;
    }

    .container .search-box .search-button {
        width: 110px;
        height: 45px;
        background-color: rgb(77, 68, 68);
        position: absolute;
        top: 135px;
        left: 130px;
    }

    .container .search-box .search-button:hover {
        background-color: #555;
    }
`;

function SearchPassword() {
    return(
        <StyledSearchPasswordMain>
            <div className="container">
                <img className="nestCo" src={nestCoLogo} alt="로고" />
                <div className="search-box">
                    <div>
                        <input className="first" name="email" type="email" placeholder="이메일" />
                    </div>
                    <div>
                        <input id="number" className="second" type="text" placeholder="인증번호" />
                        <span><button id="transmitButton" className="number-button">인증번호 전송</button></span>
                    </div>
                    <button  onClick="search()" className="search-button">비밀번호 찾기</button>
                </div>
            </div>
        </StyledSearchPasswordMain>
    )
}

export default SearchPassword;
