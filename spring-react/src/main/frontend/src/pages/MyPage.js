import My from '../components/myPage/My.js';
import Header from '../components/layouts/Header.js';
import Footer from '../components/layouts/Footer.js';

function MyPage() {
    return(
        <div>
            <Header />
            <My />
            <Footer />
        </div>
    )
}

export default MyPage;