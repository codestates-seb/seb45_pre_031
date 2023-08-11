import { styled } from "styled-components";
import NavBar from "../components/sharedlayout/NavBar"
import Aside from "../components/sharedlayout/Aside"

const Div_allContainer = styled.div`
  display: flex;
  justify-content: space-between;
`


function QuestionDetailPage() {
  return(
    <Div_allContainer>
      <NavBar />
        <main>
          QuestionDetailPage
        </main>
      <Aside />
    </Div_allContainer>
  )
}
export default QuestionDetailPage;
