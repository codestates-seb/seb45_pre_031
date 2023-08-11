import styled from "styled-components";

function TagList() {
  return <StyledTagList>태그페이지(후순위구현)</StyledTagList>;
}

export default TagList;

const StyledTagList = styled.div`
  display: flex;
  flex-direction: row;
  border-left: 1px solid rgb(214, 217, 220);
  max-width: 1100px;
`;
