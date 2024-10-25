import styled from 'styled-components'
import axios from 'axios';
import {useState, useEffect} from 'react';
import {useNavigate, useLocation} from 'react-router-dom';

const StyledBoardShowMain = styled.main`
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
        margin: 30px 0 0 600px;
    }

    .board-btn {
        width: 80px;
        height: 30px;
        background-color: #333;
        color: white;
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
`;

function BoardShow() {

    const navigate = useNavigate();
    const location = useLocation();

    const userData = JSON.parse(sessionStorage.getItem("userData"));

    const board = location.state.board;

    sessionStorage.setItem("board", board);

    function boardMenu() {
        navigate("/board");
    }

    function boardEdit() {
        if(userData != null) {
            if(userData.nickname == board.uploader.nickname) {
                    navigate('/board/edit', { state: { board: board}});
            } else {
                alert("수정할 수 있는 권한이 없습니다.");
            }
        }   else {
            alert("수정할 수 있는 권한이 없습니다.");
        }
    }

    function boardDelete() {
        if(userData != null) {
            if(userData.nickname == board.uploader.nickname) {
                if(window.confirm("정말로 삭제하시겠습니까?")) {
                    axios.get("http://localhost:9000/deleteBoard?boardId=" + board.id)
                    .then(function (response) {
                        alert("게시글 삭제 성공");

                        setTimeout(function() {
                            navigate('/board');
                            sessionStorage.removeItem("board");
                        }, 100);
                    })
                    .catch(function (error) {
                        alert("게시글 삭제 실패");
                    });
                }
            } else {
                alert("삭제할 수 있는 권한이 없습니다.");
            }
        }   else {
            alert("삭제할 수 있는 권한이 없습니다.");
        }
    }

    useEffect(() => {
        axios.get("http://localhost:9000/boardReadCount?boardId=" + board.id)
        .then(function (response) {
            console.log(userData);
        })
        .catch(function (error){
        });
    });

    return (
        <StyledBoardShowMain>
            <div className="container">
                <div className="board-box">
                    <div className="title">
                        <div className="title-menu">
                            <h1>제목:</h1>
                            <h1>{board.title}</h1>
                        </div>
                        <div className="writer-menu">
                            <h1>작성자:</h1>
                            <h1 id="nickname">{board.uploader.nickname}</h1>
                        </div>
                    </div>
                    <div className="content">
                        <p>{board.content}</p>
                    </div>
                </div>
                <div className="buttons">
                    <button class="board-btn" onClick={boardMenu}>목록으로</button>
                    <button class="edit-btn" onClick={boardEdit} >수정</button>
                    <button class="delete-btn" onClick={boardDelete}>삭제</button>
                </div>
            </div>
        </StyledBoardShowMain>
    )
}

export default BoardShow;