import MainPage from './pages/MainPage.js';
import MyPage from './pages/MyPage.js';
import LoginPage from './pages/loginPages/LoginPage.js';
import JoinPage from './pages/loginPages/JoinPage.js';
import SearchIdPage from './pages/loginPages/SearchIdPage.js';
import SearchPasswordPage from './pages/loginPages/SearchPasswordPage.js';
import BoardIndexPage from './pages/boardPages/BoardIndexPage.js';
import BoardNewPage from './pages/boardPages/BoardNewPage.js';
import BoardShowPage from './pages/boardPages/BoardShowPage.js';
import BoardEditPage from './pages/boardPages/BoardEditPage.js';
import CenterPage from './pages/centerPages/CenterPage.js';
import CenterNoticePage from './pages/centerPages/CenterNoticePage.js';
import CenterNoticeDetailPage from './pages/centerPages/CenterNoticeDetailPage.js';
import CenterOneOnOnePage from './pages/centerPages/CenterOneOnOnePage.js';
import CenterEventPage from './pages/centerPages/CenterEventPage.js';
import AdminPage from './pages/adminPages/AdminPage.js';
import AdminMemberPage from './pages/adminPages/AdminMemberPage.js';
import AdminBoardPage from './pages/adminPages/AdminBoardPage.js';
import AdminNoticePage from './pages/adminPages/AdminNoticePage.js';
import AdminCategoryPage from './pages/adminPages/AdminCategoryPage.js';
import AdminContentPage from './pages/adminPages/AdminContentPage.js';
import {Route, Routes} from 'react-router-dom';

function App() {

  return (
    <Routes>
        <Route path="/" exact={true} element={<MainPage />} />
        <Route path="/myPage" exact={true} element={<MyPage />} />
        <Route path="/loginForm" exact={true} element={<LoginPage />} />
        <Route path="/joinForm" exact={true} element={<JoinPage />} />
        <Route path="/sms/search/id" exact={true} element={<SearchIdPage />} />
        <Route path="/search/password" exact={true} element={<SearchPasswordPage />} />
        <Route path="/board" exact={true} element={<BoardIndexPage />} />
        <Route path="/board/new" exact={true} element={<BoardNewPage />} />
        <Route path="/board/show" exact={true} element={<BoardShowPage />} />
        <Route path="/board/edit" exact={true} element={<BoardEditPage />} />
        <Route path="/center" exact={true} element={<CenterPage />} />
        <Route path="/center/notices" exact={true} element={<CenterNoticePage />} />
        <Route path="/center/notices/detail" exact={true} element={<CenterNoticeDetailPage />} />
        <Route path="/center/oneonone" exact={true} element={<CenterOneOnOnePage />} />
        <Route path="/center/event" exact={true} element={<CenterEventPage />} />
        <Route path="/admin" exact={true} element={<AdminPage />} />
        <Route path="/admin/members" exact={true} element={<AdminMemberPage />} />
        <Route path="/admin/boards" exact={true} element={<AdminBoardPage />} />
        <Route path="/admin/notices" exact={true} element={<AdminNoticePage />} />
        <Route path="/admin/categories" exact={true} element={<AdminCategoryPage />} />
        <Route path="/admin/contents" exact={true} element={<AdminContentPage />} />
    </Routes>
  );
}

export default App;
