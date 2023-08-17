import styled from "styled-components";
import AskQuestionBtn from "../atoms/AskQuestionBtn";
import logo from "../../assets/images/logo.png";
import { useEffect, useState } from "react";
import axios from "axios";

function QuestionList() {
  const [data, setData] = useState("");
  const [tab, setTab] = useState("newest");
  const [pageNumber, setPageNumber] = useState(1);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/question?tab=${tab}&page=${pageNumber}`, {
          headers: {
            // 필요 헤더
          },
        });

        if (response.data.success) {
          setData(response.data);
        } else {
          console.error("Failed to fetch questions:", response.data.message);
        }
      } catch (error) {
        console.error("Error fetching questions:", error);
      }
    };
    fetchData();
  }, [tab, pageNumber]);

  const handleTab = (selectedTab) => {
    setPageNumber(1);
    setTab(selectedTab);
  };

  const handlePage = (selectedPage) => {
    setPageNumber(selectedPage);
  };

  const handlePageInput = (e) => {
    if (e.key === "Enter") {
      const inputValue = parseInt(e.target.value);
      if (!isNaN(inputValue)) {
        setPageNumber(inputValue);
      }
    }
  };

  useEffect(() => console.log("tab:", tab, "page:", pageNumber), [tab, pageNumber]);

  return (
    <StyledQuestionList>
      <HeaderContainer>
        <h1 className="headerTitle">All Questions</h1>
        <AskQuestionBtn>Ask Question</AskQuestionBtn>
      </HeaderContainer>
      <FiterContainer>
        <span className="questionCount">{data.questions_count} quesitons</span>
        <Fiter>
          <FiterOption onClick={() => handleTab("newest")}>Newest</FiterOption>
          <FiterOption onClick={() => handleTab("active")}>Active</FiterOption>
          <FiterOption onClick={() => handleTab("unanswered")}>Unanswered</FiterOption>
          <FiterOption onClick={() => handleTab("score")}>Score</FiterOption>

          <FiterOption onClick={() => handleTab("popular")}>Pop(week)</FiterOption>
          <FiterOption onClick={() => handleTab("popular")}>Pop(month)</FiterOption>
        </Fiter>
      </FiterContainer>
      <QuestionListContainer>
        {/* {data.question_data.map((question) => (
          <Question>
            <div className="leftSide">
              <LeftSideInfo>
                <span className="votes">{question.vote_up.length - question.vote_down.length} votes</span>
              </LeftSideInfo>
              <LeftSideInfo>
                <span className="answersAndViews">{question.answers_count} asnswers</span>
              </LeftSideInfo>
              <LeftSideInfo>
                <span className="answersAndViews">{question.views} views</span>
              </LeftSideInfo>
            </div>
            <div className="rightSide">
              <QuestionTitle> {question.title} </QuestionTitle>
              <QuestionSummury>{question.bodyHTML}</QuestionSummury>
              <TagAndUserInfoContainer>
                <TagContainer>
                  {question.tags.map((tag) => (
                    <>
                      <Tag>{tag}</Tag>
                      <Tag>{tag}</Tag>
                    </>
                  ))}
                </TagContainer>
                <UserInfoContainer>
                  <img className="userAvatar" alt="userAvatar" src={logo} />
                  <div className="userName">
                    <span>{question.avatarUrl}</span>
                  </div>
                  <div className="createdAt">
                    <span>5 mins ago</span>
                  </div>
                </UserInfoContainer>
              </TagAndUserInfoContainer>
            </div>
          </Question>
        ))} */}
        <Question>
          <div className="leftSide">
            <LeftSideInfo>
              <span className="votes">0 votes</span>
            </LeftSideInfo>
            <LeftSideInfo>
              <span className="answersAndViews">0 asnswers</span>
            </LeftSideInfo>
            <LeftSideInfo>
              <span className="answersAndViews">0 views</span>
            </LeftSideInfo>
          </div>
          <div className="rightSide">
            <QuestionTitle> What is JavaScript? </QuestionTitle>
            <QuestionSummury>
              JavaScript is a scripting or programming language that allows you to implement complex features on web
              pages — every time a web page does more than just sit there and display static information for you to look
              at — displaying timely content updates, interactive maps, animated 2D/3D graphics, scrolling video
              jukeboxes, etc. — you can bet that JavaScript is probably involved. It is the third layer of the layer
              cake of standard web technologies, two of which (HTML and CSS) we have covered in much more detail in
              other parts of the Learning Area.
            </QuestionSummury>
            <TagAndUserInfoContainer>
              <TagContainer>
                <Tag>javascript</Tag>
                <Tag>about</Tag>
              </TagContainer>
              <UserInfoContainer>
                <img className="userAvatar" alt="userAvatar" src={logo} />
                <div className="userName">
                  <span>HongGilDong</span>
                </div>
                <div className="createdAt">
                  <span>5 mins ago</span>
                </div>
              </UserInfoContainer>
            </TagAndUserInfoContainer>
          </div>
        </Question>
        <Question>Question2</Question>
        <Question>Question3</Question>
        <Question>Question4</Question>
        <Question>Question5</Question>
        <Question>Question6</Question>
      </QuestionListContainer>
      <PaginationContainer>
        <Paginator>Prev</Paginator>
        <Paginator onClick={() => handlePage(1)}>1</Paginator>
        <Paginator onClick={() => handlePage(2)}>2</Paginator>
        <Paginator onClick={() => handlePage(3)}>3</Paginator>
        <Paginator onClick={() => handlePage(4)}>4</Paginator>
        <Paginator onClick={() => handlePage(5)}>5</Paginator>
        <PageInput onKeyUp={handlePageInput} />
        <Paginator onClick={() => handlePage(1592)}>1592</Paginator>
        <Paginator>Next</Paginator>
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
  padding-right: 0px;
  width: 100vw;
  max-width: 727px;
  min-width: 428px;
`;

const HeaderContainer = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  min-height: 50px;
  margin-bottom: 12px;

  .headerTitle {
    font-size: 26px;
    font-weight: 400;
  }
`;

const FiterContainer = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  min-height: 48px;
  .questionCount {
    display: flex;
    align-items: center;
  }
`;

const Fiter = styled.div`
  display: flex;
  flex-direction: row;
  border: 1px solid rgb(214, 217, 220);
  border-radius: 6px;
  height: 36px;
`;

const FiterOption = styled.div`
  display: flex;
  font-size: 12px;
  align-items: center;
  padding: 9.6px;

  border-right: 1px solid rgb(214, 217, 220);
  &:last-of-type {
    border-right: none;
  }
  &:hover {
    background-color: hsl(210, 8%, 97.5%);
  }
  //selected: hwb(210deg 89.2% 9.2%);
`;

const QuestionListContainer = styled.ul`
  border-top: 1px solid rgb(214, 217, 220);
  margin-left: -24px;
  list-style: none;
  margin-bottom: 40px;
`;

const Question = styled.li`
  display: flex;
  flex-direction: row;
  padding: 16px;
  height: 124px;

  border-bottom: 1px solid rgb(214, 217, 220);
  .leftSide {
    display: flex;
    flex-direction: column;
    min-width: 108px;
    min-height: 87px;
    margin-right: 16px;
    margin-bottom: 4px;
    font-size: 13px;
  }
  .rightSide {
    display: flex;
    flex-direction: column;
  }
`;
const LeftSideInfo = styled.div`
  display: flex;
  justify-content: flex-end;
  margin-bottom: 8px;
  span.votes {
    color: hwb(0deg 4.64% 95.36%);
  }
  span.answersAndViews {
    color: hwb(210deg 41.4% 51.4%);
  }
`;
const QuestionTitle = styled.h3`
  padding-right: 24px;
  margin-bottom: 2px;
  font-weight: 400;
  font-size: 100%;
  color: hsl(206, 100%, 40%);
  &:hover {
    color: hsl(206, 100%, 52%);
  }
`;
const QuestionSummury = styled.div`
  font-size: 13px;
  height: 34px;
  max-width: 565px;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  margin-top: -2px;
  margin-bottom: 8px;
`;

const TagAndUserInfoContainer = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
`;
const TagContainer = styled.ul`
  display: flex;
  flex-direction: row;
  height: 26px;
  list-style: none;
`;
const Tag = styled.li`
  margin-right: 4px;
  font-size: 13px;
  color: hsl(206, 100%, 40%);
  background-color: hwb(205deg 88.32% 4.32%);
  border-radius: 6px;
  padding: 4px 8px 4px 8px;
  &:hover {
    color: hsl(205, 46%, 32%);
    background-color: hsl(205deg 53% 88%);
  }
`;

const UserInfoContainer = styled.div`
  display: flex;
  flex-direction: row;

  .userAvatar {
    width: 16px;
    height: 16px;
    margin-right: 6px;
  }
  .userName {
    height: 16px;
    font-size: 12px;
    margin-right: 6px;
    color: hsl(206, 100%, 40%);
  }
  .createdAt {
    height: 16px;
    font-size: 12px;
    color: hwb(210deg 41.4% 51.4%);
  }
`;

const PaginationContainer = styled.div`
  display: flex;
  flex-direction: row;
  margin-top: 20px;
  margin-bottom: 20px;
`;

const Paginator = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 6px 10px 6px 10px;
  border-radius: 6px;

  margin-right: 6px;
  border: 1px solid rgb(214, 217, 220);
  font-size: 12px;
  &:hover {
    background-color: hsl(210, 8%, 97.5%);
  }
`;

const PageInput = styled.input`
  display: flex;
  padding: 6px 10px 6px 10px;
  border-radius: 6px;
  width: 48px;
  margin-right: 6px;
  border: 1px solid rgb(214, 217, 220);
  font-size: 12px;
`;
