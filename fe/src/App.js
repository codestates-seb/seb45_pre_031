import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import Header from "./components/sharedlayout/Header";
import QuestionListPage from "./pages/QuestionListPage";
import LoginPage from "./pages/LoginPage";
import MembershipPage from "./pages/MembershipPage";
import QuestionlistPage from "./pages/QuestionListPage";
import QuestionDetailPage from "./pages/QuestionDetailPage";
import QuestionFormPage from "./pages/QuestionFormPage";
import TagListPage from "./pages/TagListPage";
import { useDispatch } from "react-redux";
import { useEffect } from "react";
import { loginSuccess, logoutAction } from "./redux/actions/loginAction";

function App() {

  const dispatch = useDispatch();

  useEffect(() => {
    const accessToken = localStorage.getItem("access_token");

    if (accessToken) {
      dispatch(loginSuccess(accessToken));
    } else {
      dispatch(logoutAction());
    }
  }, [dispatch]);

  return (
    <BrowserRouter>
      <div className="App">
        <Header />
        <Routes>
          <Route path="/" element={<QuestionListPage />}></Route>
          <Route path="/login" element={<LoginPage />}></Route>
          <Route path="/membership" element={<MembershipPage />}></Route>
          <Route path="/questionlist" element={<QuestionlistPage />}></Route>
          <Route path="/questiondetail/:questionId" element={<QuestionDetailPage />}></Route>
          <Route path="/questionform" element={<QuestionFormPage />}></Route>
          <Route path="/taglist" element={<TagListPage />}></Route>
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
