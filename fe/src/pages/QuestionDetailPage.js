import { styled } from "styled-components";
import NavBar from "../components/sharedlayout/NavBar"
import Aside from "../components/sharedlayout/Aside"

import AskQuestionBtn from "../components/atoms/AskQuestionBtn"

const DivAllContainer = styled.div`
  display: flex;
  justify-content: center;
`
const DivTitleMainAside = styled.div`
  padding: 24px;
`
const DivMainTitleContainer = styled.div`

`
const DivMainTitleBox = styled.div`
  display: flex;
  justify-content: space-between;
`
const H1MainTitle = styled.h1`
  margin-bottom: 8px;
  font-size: 27px;
  color: rgb(59, 64, 69);
`
const DivSubTitleContainer = styled.div`
  margin-bottom: 16px;
  border-bottom: 1px solid rgb(227, 230, 232);
  padding-bottom: 8px;
`
const SpanSubTitleBox = styled.span`
  margin: 0 16px 8px 0;
`
const SpanSubTitleLeft = styled.span`
  margin-right: 2px;
  color: rgb(106, 115, 124);
  font-size: 13px;
`
const SpanSubTitleRight = styled.span`
  color: rgb(35, 38, 41);
  font-size: 13px;
`
const DivMainAside = styled.div`
`
const MainMain = styled.main`
`
const ArticleQ = styled.article`
`
const ArticleA = styled.article`
`

function QuestionDetailPage() {
  return(
    <DivAllContainer>
      <NavBar />
      <DivTitleMainAside>
        <DivMainTitleContainer>
          <DivMainTitleBox>
            <H1MainTitle>
              MainTitle
            </H1MainTitle>
            <AskQuestionBtn />
          </DivMainTitleBox>
          <DivSubTitleContainer>
          <SpanSubTitleBox>
            <SpanSubTitleLeft>
              Asked
            </SpanSubTitleLeft>
            <SpanSubTitleRight>
              n days ago
            </SpanSubTitleRight>
          </SpanSubTitleBox>
          <SpanSubTitleBox>
            <SpanSubTitleLeft>
              Modified
            </SpanSubTitleLeft>
            <SpanSubTitleRight>
              n days ago
            </SpanSubTitleRight>
          </SpanSubTitleBox>
          <SpanSubTitleBox>
            <SpanSubTitleLeft>
              Viewed
            </SpanSubTitleLeft>
            <SpanSubTitleRight>
              n times
            </SpanSubTitleRight>
          </SpanSubTitleBox>
          </DivSubTitleContainer>
        </DivMainTitleContainer>
        <DivMainAside>
          <MainMain>
            <ArticleQ>

            </ArticleQ>
            <ArticleA>

            </ArticleA>
          </MainMain>
          <Aside />
        </DivMainAside>
      </DivTitleMainAside>
    </DivAllContainer>
  )
}
export default QuestionDetailPage;
