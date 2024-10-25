import styled from 'styled-components';
import squirrelLogo from '../../images/squirrel.png';
import { useNavigate } from 'react-router-dom';
import {useState} from 'react';
import axios from 'axios';

const StyledJoinMain = styled.main`

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

    .container > h1 {
        position: absolute;
        top: 75px;
        left: 185px;
    }

    .container .joinBox {
        position: absolute;
        top: 160px;
        left: 80px
    }

    .container .joinBox .first-join input{
        width: 350px;
        height: 40px;
        margin-bottom: 10px;
    }

    .container .joinBox .second-join input{
        width: 350px;
        height: 40px;
        margin-bottom: 10px;
    }

    .container .joinBox .third-join input{
        width: 350px;
        height: 40px;
        margin-bottom: 10px;
    }

    .container .joinBox .fourth-join input{
        width: 350px;
        height: 40px;
        margin-bottom: 10px;
    }

    .container .joinBox .fiveth-join input{
        width: 350px;
        height: 40px;
        margin-bottom: 10px;
    }

    .container .joinBox .sixth-join input{
        width: 350px;
        height: 40px;
        margin-bottom: 10px;
    }

    .container .joinBox .seventh-join {
        display: flex;
        margin-bottom: 10px;
    }

    .container .joinBox .seventh-join button{
        width: 100px;
        height: 40px;
        color: white;
        background-color: rgb(77, 68, 68);
        margin-left: 10px;
        font-size: 15px;
        cursor: pointer;
    }

    .container .joinBox .seventh-join button:hover {
        background-color: #555;
    }

    .container .joinBox .seventh-join input{
        width: 200px;
        height: 40px;
    }

    .container .joinBox .eighth-join input{
        width: 350px;
        height: 40px;
        margin-bottom: 10px;
    }

    .container .joinBox .nineth-join input {
        width: 350px;
        height: 40px;
        margin-bottom: 10px;
    }

    .container .joinBox .tenth-join input {
        width: 350px;
        height: 40px;
    }

    .container .joinBox .join-button {
        width: 110px;
        height: 45px;
        color: white;
        background-color: rgb(77, 68, 68);
        font-size: 15px;
        cursor: pointer;
        position: absolute;
        margin-top: 30px;
        left: 120px;
    }

    .container .joinBox .join-button:hover {
        background-color: #555;
    }

    .squirrel {
        position: absolute;
        left: 200px;
        width: 200px;
        height: 150px;
    }
`;

function JoinForm() {

    const[userId, setUserId] = useState();
    const[userPassword, setUserPassword] = useState();
    const[username, setUsername] = useState();
    const[nickname, setNickname] = useState();
    const[email, setEmail] = useState();
    const[phoneNumber, setPhoneNumber] = useState();
    const[postalAddress, setPostalAddress] = useState();
    const[address, setAddress] = useState();
    const[cloneAddress, setCloneAddress] =useState();
    const[detailedAddress, setDetailedAddress] = useState();

    const navigate = useNavigate();

    function handleChange(e) {
        let sample = e.target.value;
        let regExp =  /[~!@#$%";'^,&*()_+|</>=>`?:{[}]/g;

        if(regExp.test(sample)) {
            alert("아이디에 특수문자는 포함시킬 수 없습니다.");
            sample = sample.replace(regExp, "");
            e.target.value = sample;
            setUserId(sample);
        } else {
            setUserId(sample);
        }

        console.log(sample);
    }

    function handleChangeClone(e) {
        let sample = e.target.value;
        let regExp = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣|a-zA-Z~!@#$%^&*()_+|<>?:{}]/g;

        if(regExp.test(sample)) {
            alert("휴대폰 번호는 숫자로만 입력할 수 있습니다.");
            sample = sample.replace(regExp, "");
            e.target.value = sample;
            setPhoneNumber(sample);
        } else {
            setPhoneNumber(sample);
        }

        console.log(sample);
    }

    function join() {
           if(!userId) {
                alert('아이디를 입력해 주세요.');
                return;
           }

            if(!userPassword) {
               alert('비밀번호를 입력해 주세요.');
               return;
            }

            if(!username) {
               alert('이름을 입력해 주세요.');
               return;
            }

            if(!nickname) {
               alert('닉네임을 입력해 주세요.');
               return;
            }

            if(!email) {
               alert('이메일을 입력해 주세요.');
                return;
            }

            if(!phoneNumber) {
                alert('휴대폰 번호를 입력해 주세요.');
                return;
            }

            if(phoneNumber.length > 11) {
                alert('휴대폰 번호를 입력해 주세요.');
                return;
            }

            if(!postalAddress) {
                alert('우편번호를 입력해 주세요.');
                return;
            }

            if(!address) {
                alert('주소를 입력해 주세요.');
                return;
            }

            if(!detailedAddress) {
                alert('상세주소를 입력해 주세요.');
                return;
            }


        axios({
            method: "post",
            url: "http://localhost:9000/react/join",
            data: {userId, userPassword, username, nickname, email, phoneNumber, postalAddress, address, detailedAddress},
            responseType: "json",
        })
        .then(function(response) {
            console.log(response);
            alert(response.data);
            if(response.data == "회원가입에 성공하였습니다.") {
                navigate('/loginForm');
            }
        })
        .catch(function(error) {
            console.log(error);
            alert('회원가입 실패');
        });
    };

    function postalOpen() {
        new window.daum.Postcode({
              oncomplete: function (data) {
                console.log(data);
                setPostalAddress(data.zonecode);
                setAddress(data.address);
                setCloneAddress(data.jibunAddress);
          },
        }).open();
    }

    return(
        <StyledJoinMain>
            <div className="container">
                <h1>회원가입</h1>
                <div className="joinBox">
                    <div className="first-join">
                        <input type="text" id="userId" name="userId" placeholder="아이디" onChange={handleChange} required />
                    </div>
                    <div className="second-join">
                        <input type="password" id="userPassword" name="userPassword" placeholder="비밀번호" onChange={(e) => setUserPassword(e.target.value)} required />
                    </div>
                    <div className="third-join">
                        <input type="text" id="username" name="username" placeholder="이 름" onChange={(e) => setUsername(e.target.value)} required />
                    </div>
                    <div className="fourth-join">
                        <input type="text" id="nickname" name="nickname" placeholder="닉네임" onChange={(e) => setNickname(e.target.value)} required />
                    </div>
                    <div className="fiveth-join">
                        <input type="email" id="email" name="email" placeholder="이메일" onChange={(e) => setEmail(e.target.value)} required />
                    </div>
                    <div className="sixth-join">
                        <input type="type" id="phoneNumber" name="phoneNumber" placeholder="휴대폰 번호 입력" onChange={handleChangeClone} required />
                    </div>
                    <div className="seventh-join">
                        <input type="number" id="sample6_postcode" value={postalAddress} name="postalAddress" placeholder="우편번호" onChange={(e) => setPostalAddress(e.target.value)} required />
                        <span><button onClick={postalOpen}>검색</button></span>
                    </div>
                    <div className="eighth-join">
                        <input type="text" id="sample6_address" value={address} name="address" placeholder="주소" onChange={(e) => setAddress(e.target.value)} required />
                    </div>
                    <div className="nineth-join">
                        <input type="text" id="sample6_extraAddress" value={cloneAddress} onChange={(e) => setCloneAddress(e.target.value)} placeholder="참고항목" />
                    </div>
                    <div className="tenth-join">
                        <input type="text" id="sample6_detailAddress" name="detailedAddress" placeholder="상세주소" onChange={(e) => setDetailedAddress(e.target.value)} required />
                    </div>
                    <button id="join-btn" className="join-button" onClick={join}>회원가입</button>
                    <img className="squirrel" src={squirrelLogo} alt="다람쥐" />
                </div>
            </div>
        </StyledJoinMain>
    )
}

export default JoinForm;