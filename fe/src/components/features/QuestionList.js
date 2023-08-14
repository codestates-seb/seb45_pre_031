import styled from "styled-components";

function QuestionList() {
  return (
    <StyledQuestionList>
      <HeaderContainer>
        <h1>All Questions</h1>
        <button>Ask Question</button>
      </HeaderContainer>
      <FiterContainer>
        <span>25,533,92 quesitons</span>
        <div>필터/정렬</div>
      </FiterContainer>
      <QuestionListContainer>
        <span>Question List</span>
      </QuestionListContainer>
      <PaginationContainer>
        <span>pagination</span>
      </PaginationContainer>
    </StyledQuestionList>
  );
}

export default QuestionList;

const StyledQuestionList = styled.div`
  display: flex;
  flex-direction: column;
  border-left: 1px solid rgb(214, 217, 220);
  padding: 24px;
  width: 100vw;
  max-width: 727px;
  min-width: 428px;
`;

const HeaderContainer = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  min-height: 49.781px;
  margin-bottom: 12px;
`;
const FiterContainer = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  min-height: 47.844px;
`;
const QuestionListContainer = styled.div``;
const PaginationContainer = styled.div``;
