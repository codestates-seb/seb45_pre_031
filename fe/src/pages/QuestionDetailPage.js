import { styled } from "styled-components";
import NavBar from "../components/sharedlayout/NavBar";
import Aside from "../components/sharedlayout/Aside";

const DivAllContainer = styled.div`
  display: flex;
  justify-content: space-between;
`;

function QuestionDetailPage() {
  return (
    <DivAllContainer>
      <NavBar />
      <main>QuestionDetailPage</main>
      <Aside />
    </DivAllContainer>
  );
}
export default QuestionDetailPage;
