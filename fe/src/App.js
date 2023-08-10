import "./App.css";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import Header from "./components/Header";
import QuestionListPage from "./pages/QuestionListPage";
import LoginPage from "./pages/LoginPage";
import MembershipPage from "./pages/MembershipPage";
import QuestionlistPage from "./pages/QuestionlistPage";
import QuestionDetailPage from "./pages/QuestionDetailPage";
import TagListPage from "./pages/TagListPage";

function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <Header />
        <Routes>
          <Route path="/" element={<QuestionListPage />}></Route>
          <Route path="/login" element={<LoginPage />}></Route>
          <Route path="/membership" element={<MembershipPage />}></Route>
          <Route path="/questionlist" element={<QuestionlistPage />}></Route>
          <Route path="/questiondetail" element={<QuestionDetailPage />}></Route>
          <Route path="/taglist" element={<TagListPage />}></Route>
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;
