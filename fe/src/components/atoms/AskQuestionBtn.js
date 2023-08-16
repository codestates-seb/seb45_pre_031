import styled from "styled-components";
import { Link } from "react-router-dom";

function AskQuestionBtn() {
  return (
    <Link to="/questionform">
      <Btn>Ask Question</Btn>
    </Link>
  );
}

export default AskQuestionBtn;

const Btn = styled.button`
  height: 38px;
  padding: 10.5px;
  border-radius: 6px;
  border: none;
  white-space: nowrap;
  cursor: pointer;
  color: white;
  background-color: hsl(206, 100%, 52%);
`;