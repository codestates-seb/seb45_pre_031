import NavBar from "../components/sharedlayout/NavBar";
import Footer from "../components/sharedlayout/Footer";

import QuestionList from "../components/features/QuestionList";
import styled from "styled-components";

function QuestionListPage() {
  return (
    <StyledQuestionListPage>
      <div className="top">
        <NavBar></NavBar>
        <QuestionList></QuestionList>
      </div>
      <div className="bottom">
        <Footer></Footer>
      </div>
    </StyledQuestionListPage>
  );
}

export default QuestionListPage;

const StyledQuestionListPage = styled.div`
  display: flex;
  flex-direction: column;
  .top {
    display: flex;
    flex-direction: row;
  }
`;
