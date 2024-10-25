import styled from 'styled-components'
import axios from 'axios';
import {useState, useEffect} from 'react';
import {useNavigate, useLocation} from 'react-router-dom';

const StyledBoardEditMain = styled.main`
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

    margin: 30px 0 30px 0;

    .container {
        width: 900px;
        margin: 0 auto;
        display: flex;
        justify-content: center;
        align-items: center;
        flex-direction: column;
    }

    .title {
        width: 900px;
        border: 1px solid black;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .title .title-menu {
        display: flex;
        justify-content: center;
        align-items: center;
        margin-right: 40px;
    }

    .title .writer-menu {
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .content {
        width: 900px;
        border: 1px solid black;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .content p {
        margin-top: 20px;
        white-space: normal;
    }

    .buttons {
        margin: 30px 0 0 710px;
    }

    .edit-btn {
        width: 80px;
        height: 30px;
        background-color: #333;
        color: white;
    }

    .delete-btn {
        width: 80px;
        height: 30px;
        background-color: #333;
        color: white;
    }

    input {
        border: none;
        outline: none;
        font-size: 20px;
    }

    textarea {
        font-size: 20px;
    }
`;

function BoardEdit() {

  const navigate = useNavigate();
  const location = useLocation();

  const boardTarget = location.state.board;

  const[board, setBoard] = useState(boardTarget);

  const[title, setTitle] = useState(boardTarget.title);
  const[content, setContent] = useState(boardTarget.content);

    function editBoard() {
        axios.post("http://localhost:9000/editBoard",{boardDTO: board, subBoardDTO: {title, content}})
        .then(function (response) {
            alert(response.data);
            setTimeout(function() {
                navigate('/board')
                sessionStorage.removeItem("board");
            }, 100);
        })
        .catch(function (error) {
            alert('게시글 수정에 실패하였습니다.');
        });
    }

    return(
         <StyledBoardEditMain>
            <div className="container">
                <div className="board-box">
                    <div className="title">
                        <div className="title-menu">
                            <h1>제목:</h1>
                            <input type="text" name="title" value={title} onChange={(e) => setTitle(e.target.value)}/>
                        </div>
                        <div className="writer-menu">
                            <h1>작성자:</h1>
                            <h1 id="nickname">{board.uploader.nickname}</h1>
                        </div>
                    </div>
                    <textarea className="content" name="content" value={content} onChange={(e) => setContent(e.target.value)}></textarea>
                </div>
                <div className="buttons">
                    <button className="edit-btn" onClick={editBoard} type="submit">수정</button>
                </div>
            </div>
        </StyledBoardEditMain>
    )
}

export default BoardEdit;