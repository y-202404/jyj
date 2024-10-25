import styled from 'styled-components';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import {useState} from 'react';
import axios from 'axios';
import nestCoLogo from '../../images/nestCo.png'
import googleLogo from '../../images/google.png';
import kakaoLogo from '../../images/kakao.png';
import naverLogo from '../../images/naver.png';

const StyledLoginMain = styled.main`
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

    .loginBox {
        position: relative;
        top: 310px;
    }

    .loginBox h1{
        position: absolute;
        left: 210px;
    }

    .loginBox .text {
        display: flex;
        position: absolute;
        left: 160px;
        top: 50px;
    }

    .loginBox .text p{
        margin-right: 5px;
    }

    .local-login {
        position: absolute;
        top: 90px;
        left: 110px;
    }

    .local-login .insert-id {
        width: 300px;
        height: 50px;
        margin-bottom: 10px;
    }

    .local-login .insert-password {
        width: 300px;
        height: 50px;
    }

    .local-login .login-button {
        position: absolute;
        width: 90px;
        height: 40px;
        left: 100px;
        margin-top: 20px;
    }

    .local-login .search-id {
        position: absolute;
        font-size: 14px;
        margin-top: 30px;
    }

    .local-login .search-password {
        position: absolute;
        left: 210px;
        font-size: 14px;
        margin-top: 30px;
    }

    .line {
     display: flex;
     position: relative;
     top: 270px;
     left: 10px;
    }

    .line p:first-child {
        margin-right: 30px;
        height: 20px;
        position: absolute;
        top: 4px;
    }

    .line p:nth-child(2) {
        font-weight: 900px;
        color: black;
        height: 20px;
        font-size: 20px;
        font-weight: 900px;
        position: absolute;
        left: 240px;
    }

    .line p:last-child {
        margin-left: 30px;
        height: 20px;
        position: absolute;
        right: 1px;
        top: 4px;
    }

    .social-login {
        position: absolute;
        top: 320px;
        left: 160px;
    }

    .social-login .first-social {
        margin-bottom: 10px;
    }

    .social-login .second-social {
        margin-bottom: 10px;
    }

    .social-login .third-social {

    }

    .naverLogo {
        width: 189px;
        height: 40px;
    }
`;

    function LoginForm() {

    const navigate = useNavigate();
    const location = useLocation();

    const pathname = location.state;

    const[userId, setUserId] = useState("");
    const[userPassword, setUserPassword] = useState("");

    const reactLogin = () => {
        if(userId == "") {
            alert('아이디를 입력해주십시오.');
        } else {
            if(userPassword == "") {
                alert('비밀번호를 입력해주십시오.');
            } else {
                axios.post("http://localhost:9000/react/login", {userId, userPassword})
                .then(function (response) {
                    console.log("data: ", response.data);
                    alert(response.data.message);
                    if(response.data.message == "로그인 성공") {
                        sessionStorage.setItem("userData", JSON.stringify(response.data.member));
                        if(pathname != "/board/show") {
                            navigate(pathname);
                        } else {
                            navigate("/board");
                        }
                    }
                })
                .catch(function(error) {

                });
            }
        }
    };

    return(
    <StyledLoginMain className="Box">
        <div className="container">
            <img className="nestCo" src={nestCoLogo} alt="NestCo" />
            <div className="loginBox">
                <h1>로그인</h1>
                <div className="text">
                    <p>계정이 없으십니까?</p>
                    <Link to="/joinForm">회원가입</Link>
                </div>
                <div className="local-login">
                    <div>
                        <input className="insert-id" type="text" name="userId" onChange={(e) => setUserId(e.target.value)} onKeyUp={(e) => e.keyCode === 13 ? reactLogin() : null} />
                    </div>
                    <div>
                        <input className="insert-password" type="password" name="userPassword" onChange={(e) => setUserPassword(e.target.value)} onKeyUp={(e) => e.keyCode === 13 ? reactLogin() : null} />
                    </div>
                    <button className="login-button" onClick={reactLogin}>로그인</button>
                    <div className="search-id">
                        <Link to="/sms/search/id">아이디 찾기</Link>
                    </div>
                    <div className="search-password">
                        <Link to="/search/password">비밀번호 찾기</Link>
                    </div>
                </div>
                <div className="line">
                    <p>--------------------------------</p>
                    <p>or</p>
                    <p>--------------------------------</p>
                </div>
                <div className="social-login">
                    <div className="first-social">
                        <Link to="/oauth2/authorization/google"><img src={googleLogo} alt="구글" /></Link>
                    </div>
                    <div className="second-social">
                        <Link to="/oauth2/authorization/naver"><img className="naverLogo" src={naverLogo} alt="네이버" /></Link>
                    </div>
                    <div className="third-social">
                        <Link to="/oauth2/authorization/kakao"><img src={kakaoLogo} alt="카카오" /></Link>
                    </div>
                </div>
            </div>
        </div>
    </StyledLoginMain>
    )
}

export default LoginForm;