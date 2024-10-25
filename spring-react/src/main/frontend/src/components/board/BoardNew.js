import styled from 'styled-components'
import axios from 'axios';
import {useState} from 'react';
import {useNavigate} from 'react-router-dom';

const StyledBoardMain = styled.main`
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

   .container {
       display: flex;
       justify-content: center;
       align-items: center;
       flex-direction: column;
       margin-top: 20px;
   }

   .container .form-box {
       margin-top: 20px;
       display: flex;
       justify-content: center;
       align-items: center;
       flex-direction: column;
   }

   .container .title{
       display: flex;
       justify-content: center;
       align-items: center;
       margin-bottom: 15px;
   }

   .container .title h1 {
       font-size: 25px;
   }

   .container .title .first {
       width: 350px;
       height: 40px;
   }

   .container .content{
       display: flex;
       justify-content: center;
       align-items: center;
   }

   .container .content .second{
       width: 350px;
       height: 100px;
   }

   .container .content h1 {
       font-size: 25px;
   }

   .registration-btn {
       width: 90px;
       height: 40px;
       margin: 20px 0 20px 0;
       background-color: #333;
       color: white;

    .container {
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
        margin-top: 20px;
    }

    .container .form-box {
        margin-top: 20px;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
    }

    .container .title{
        display: flex;
        justify-content: center;
        align-items: center;
        margin-bottom: 15px;
    }

    .container .title h1 {
        font-size: 25px;
    }

    .container .title .first {
        width: 350px;
        height: 40px;
    }

    .container .content{
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .container .content .second{
        width: 350px;
        height: 100px;
    }

    .container .content h1 {
        font-size: 25px;
    }

    .registration-btn {
        width: 90px;
        height: 40px;
        margin: 20px 0 20px 0;
        background-color: #333;
        color: white;
    }
`;

function BoardNew() {

    const navigate = useNavigate();

    const[title, setTitle] = useState("");
    const[content, setContent] = useState("");

    const userData = JSON.parse(sessionStorage.getItem("userData"));

    function newBoard() {
        axios.post('http://localhost:9000/newBoard', {memberDTO: userData, boardDTO: {title, content}})
        .then(function (response) {
            alert(response.data);
            setTimeout(function() {
                navigate("/board")
            }, 100);
        })
        .catch(function (error) {
            alert('게시글 등록실패');
        });
    }



    return(
        <StyledBoardMain>
            <div className="container">
                <h1>게시글 등록하기</h1>
                <div className="form-box">
                    <div class="title">
                        <h1>제목</h1>
                        <input className="first" type="text" name="title" onChange={(e) => setTitle(e.target.value)} />
                    </div>
                    <div class="content">
                        <h1>내용</h1>
                        <textarea className="second" name="content" onChange={(e) => setContent(e.target.value)}></textarea>
                    </div>
                    <button className="registration-btn" type="submit" onClick={newBoard}>등록하기</button>
                </div>
            </div>
        </StyledBoardMain>
    )
}

export default BoardNew;

