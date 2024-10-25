import BoardEdit from '../../components/board/BoardEdit.js';
import Header from '../../components/layouts/Header.js';
import Footer from '../../components/layouts/Footer.js';

function BoardEditPage() {
    return(
        <div>
            <Header />
            <BoardEdit />
            <Footer />
        </div>
    )
}

export default BoardEditPage;