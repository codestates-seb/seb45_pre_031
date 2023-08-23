import NavBar from "../components/sharedlayout/NavBar";
import Footer from "../components/sharedlayout/Footer";
import TagList from "../components/features/TagList";
import styled from "styled-components";

function TagListPage() {
  return (
    <div>
      <StyledTagListPage>
        <div className="top">
          <NavBar></NavBar>
          <TagList></TagList>
        </div>
        <div className="bottom"></div>
      </StyledTagListPage>

      <Footer></Footer>
    </div>
  );
}

export default TagListPage;

const StyledTagListPage = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  .top {
    display: flex;
    flex-direction: row;
  }
`;
