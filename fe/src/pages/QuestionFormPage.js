import Footer from "../components/sharedlayout/Footer";
import QuestionForm from "../components/features/QuestionForm";
import styled from "styled-components";

import backgroundimg from "../assets/images/background.svg";

function QuestionFormPage() {
  return (
    <div>
      <StyledQuestionFormPage>
        <div className="top">
          <QuestionForm></QuestionForm>
        </div>
        <div className="bottom"></div>
      </StyledQuestionFormPage>
      <Footer></Footer>
    </div>
  );
}

export default QuestionFormPage;

const StyledQuestionFormPage = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: rgb(248, 249, 249);
  width: 100vw;

  background-image: url(${backgroundimg});
  background-repeat: no-repeat;
  background-position: top right 300px;
  background-size: 600px 130px;
`;
