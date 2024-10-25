import styled from 'styled-components';
import {Link} from 'react-router-dom';

const StyledFooter = styled.footer`

    background-color: #4C7263;
    padding: 40px 20px;
    color: white;


    .footer-container {
        display: flex;
        justify-content: space-between;
        padding: 0 40px;
        align-items: center;
        flex-wrap: wrap;
        max-width: 1700px;
        margin: 2rem auto;

    }

    /* 로고 스타일 */
    .footer-container .logo {
        font-size: 1.5em;
        font-weight: bold;
        display: flex;
        align-items: center;
    }

    .footer-container .logo img {
        height: 40px;
        margin-right: 10px;
    }

    /* 링크 리스트 스타일 */
    .footer-container ul {
        list-style-type: none;
        display: flex;
        padding: 0;
        margin: 0;
    }

    .footer-container ul li {
        margin-right: 30px;
    }

    .footer-container ul li Link {
        color: white;
        text-decoration: none;
        font-size: 0.9em;
        padding: 0 20px;
    }

    .footer-container ul li a:hover {
        text-decoration: underline;
    }

    /* 소셜 아이콘 스타일 */
    .social-icons {
        display: flex;
        justify-content: flex-end;
        align-items: center;
    }

    .social-icons Link {
        color: white;
        margin-left: 15px;
        font-size: 1.2em;
        text-decoration: none;
    }

    /* 하단 문구 */
    .footer-bottom {
        text-align: center;
        margin-top: 30px;
        font-size: 0.85em;
        color: #ccc;
    }

    hr {
        border: none;
        height: 1px;
        background-color: #ffffff50;
        margin: 20px 0;
    }

    @media (max-width: 768px) {
        .footer-container {
            flex-direction: column;
            align-items: center;
            text-align: center;
        }

        .footer-container ul {
            flex-direction: column;
            align-items: center;
        }

        .footer-container ul li {
            margin-bottom: 10px;
        }

        .social-icons {
            margin-top: 20px;
        }
    }

    .link {
        color: white;
    }
`;

function Footer() {
    return(
        <StyledFooter>
            <div className="footers">
                <div className="footer-container">
                    <div className="logo">
                    </div>
                    <ul className="info">
                        <li><Link to="#" className="link">개인정보처리방침</Link></li>
                        <li><Link to="#" className="link">홈페이지이용약관</Link></li>
                        <li><Link to="#" className="link">위치정보이용약관</Link></li>
                        <li><Link to="#" className="link">고객지원</Link></li>
                    </ul>
                    <div className="social-icons">
                        <Link href="#"><i className="fab fa-twitter"></i></Link>
                        <Link href="#"><i className="fab fa-facebook"></i></Link>
                        <Link href="#"><i className="fab fa-instagram"></i></Link>
                        <Link href="#"><i className="fab fa-pinterest"></i></Link>
                    </div>
                </div>
                <hr />
                <div className="footer-bottom">
                    <p>ⓒ Copyright 2024 NestCo. All rights reserved</p>
                </div>
            </div>
        </StyledFooter>
    )
}

export default Footer;