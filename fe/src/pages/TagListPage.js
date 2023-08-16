import NavBar from "../components/sharedlayout/NavBar";
import Footer from "../components/sharedlayout/Footer";
import TagList from "../components/features/TagList";
import styled from "styled-components";

function TagListPage() {
  return (
    <StyledTagListPage>
      <div className="top">
        <NavBar></NavBar>
        <TagList></TagList>
      </div>
      <div className="bottom">
        <Footer></Footer>
      </div>
    </StyledTagListPage>
  );
}

export default TagListPage;

const StyledTagListPage = styled.div`
  display: flex;
  flex-direction: column;
  .top {
    display: flex;
    flex-direction: row;
  }
`;
