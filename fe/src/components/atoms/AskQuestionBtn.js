import styled from "styled-components";
import { useNavigate } from "react-router-dom";

function AskQuestionBtn() {
  const navigate = useNavigate();
  const accessToken = localStorage.getItem("access_token");

  const handleClick = () => {
    if (accessToken) {
      navigate("/questionform");
    } else {
      alert("You must be logged in to ask a question on Stack Overflow");
      navigate("/login");
    }
  };
  return <Btn onClick={handleClick}>Ask Question</Btn>;
}

/////////////////////로그인 없이 formPage 테스트시 위에 것 주석 처리, 아래 것 주석 해제
// import styled from "styled-components";
// import { Link } from "react-router-dom";
// function AskQuestionBtn() {
//   return (
//     <Link to="/questionform">
//       <Btn>Ask Question</Btn>
//     </Link>
//   );
// }
////////////////////////////////////
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
