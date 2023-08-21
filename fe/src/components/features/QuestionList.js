import styled from "styled-components";
import AskQuestionBtn from "../atoms/AskQuestionBtn";
import logo from "../../assets/images/logo.png";
import Spinner from "../../assets/images/Spinner.gif";
import { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

import ReactPaginate from "react-paginate";

function QuestionList() {
  const [questionData, setQuestionData] = useState(null);
  const [pageInfoData, setPageInfoData] = useState({});
  const [tab, setTab] = useState("newest");
  const [pageNumber, setPageNumber] = useState(1);

  const [isPending, setIsPending] = useState(false);

  const filterOptions = ["Newest", "Active", "Unanswered", "Score", "Pop(week)", "Pop(month)"];

  const navigate = useNavigate();
  const goToDetail = (questionId) => {
    navigate(`/questiondetail/${questionId}`);
  };

  useEffect(() => {
    const fetchData = async () => {
      setIsPending(true);
      try {
        const response = await axios.get(
          `http://ec2-3-36-128-133.ap-northeast-2.compute.amazonaws.com//v1/questions/tabQuestion?page=${pageNumber}&tab=${tab}`
        );
        if (response.status >= 200 && response.status < 300) {
          setQuestionData(response.data.data);
          setPageInfoData(response.data.pageInfo);
        } else {
          console.error("Server responded with an error:", response.data.message || "Unknown server error");
        }
      } catch (error) {
        console.error("Error while trying to fetch questions:", error);
      } finally {
        setIsPending(false);
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

  function formatRelativeTime(dateString) {
    const now = new Date();
    const inputDate = new Date(dateString);

    const seconds = Math.floor((now - inputDate) / 1000);
    const minutes = Math.floor(seconds / 60);
    const hours = Math.floor(minutes / 60);
    const days = Math.floor(hours / 24);
    const months = Math.floor(days / 30);
    const years = Math.floor(days / 365);

    if (years > 1) return `${years} years ago`;
    if (years === 1) return "1 year ago";

    if (months > 1) return `${months} months ago`;
    if (months === 1) return "1 month ago";

    if (days > 1) return `${days} days ago`;
    if (days === 1) return "1 day ago";

    if (hours > 1) return `${hours} hours ago`;
    if (hours === 1) return "1 hour ago";

    if (minutes > 1) return `${minutes} minutes ago`;

    return "1 minute ago";
  }
  useEffect(() => console.log("data:", questionData), [questionData]);
  useEffect(() => console.log("pageInfo:", pageInfoData), [pageInfoData]);
  return (
    <StyledQuestionList>
      <HeaderContainer>
        <h1 className="headerTitle">All Questions</h1>
        <AskQuestionBtn>Ask Question</AskQuestionBtn>
      </HeaderContainer>
      <FiterContainer>
        <span className="questionCount">{pageInfoData.totalElements || 0} quesitons</span>
        <Fiter>
          {filterOptions.map((option, index) => (
            <FilterOption key={index} onClick={() => handleTab(option)} selected={tab === option}>
              {option}
            </FilterOption>
          ))}
        </Fiter>
      </FiterContainer>
      <QuestionListContainer>
        {isPending ? (
          <div className="load">
            <img src={Spinner} alt="spinner" />
          </div>
        ) : questionData ? (
          questionData.map((question) => (
            <Question key={question.questionId}>
              <div className="leftSide">
                <LeftSideInfo>
                  <span className="votes">{question.voteUp.length - question.voteDown.length} votes</span>
                </LeftSideInfo>
                <LeftSideInfo>
                  <span className="answersAndViews">{question.answersCount} answers</span>
                </LeftSideInfo>
                <LeftSideInfo>
                  <span className="answersAndViews">{question.views} views</span>
                </LeftSideInfo>
              </div>
              <div className="rightSide">
                <QuestionTitle onClick={() => goToDetail(question.questionId)}> {question.title} </QuestionTitle>
                <QuestionSummury>{question.body}</QuestionSummury>
                <TagAndUserInfoContainer>
                  <TagContainer>
                    {question.tags && question.tags.map((tag, index) => <Tag key={index}>{tag}</Tag>)}
                  </TagContainer>
                  <UserInfoContainer>
                    <img className="userAvatar" alt="userAvatar" src={logo} />
                    <div className="userName">
                      <span>{question.displayName}</span>
                    </div>
                    <div className="createdAt">
                      <span>
                        {tab === "Active"
                          ? formatRelativeTime(question.modified_at)
                          : formatRelativeTime(question.created_at)}
                      </span>
                    </div>
                  </UserInfoContainer>
                </TagAndUserInfoContainer>
              </div>
            </Question>
          ))
        ) : (
          <div className="load">
            <div className="prompt">
              <span>Sorry, we're having trouble loading the data. Please try again later.</span>
            </div>
          </div>
        )}
      </QuestionListContainer>
      <PaginationContainer>
        <StyledReactPaginate
          pageCount={pageInfoData.totalPages || 20}
          pageRangeDisplayed={5}
          marginPagesDisplayed={1}
          previousLabel={"Prev"}
          nextLabel={"Next"}
          onPageChange={({ selected }) => handlePage(selected + 1)}
        />
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
  width: 726px;
  min-width: 726px;
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

const FilterOption = styled.div`
  display: flex;
  font-size: 12px;
  align-items: center;
  padding: 9.6px;
  background-color: ${(props) => (props.selected ? "rgb(219, 219, 219)" : "transparent")};
  border-right: 1px solid rgb(214, 217, 220);
  &:last-of-type {
    border-right: none;
  }
  &:hover {
    background-color: hsl(210, 8%, 97.5%);
  }
`;

const QuestionListContainer = styled.ul`
  border-top: 1px solid rgb(214, 217, 220);
  margin-left: -24px;
  list-style: none;
  margin-bottom: 40px;
  display: flex;
  flex-direction: column;
  align-items: center;
  .load {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 40px;
    height: 200px;
  }
  .prompt {
    min-height: 80px;
    margin-top: 16px;
    margin-bottom: 24px;
    width: 100%;
    display: flex;
    align-items: center;
    background-color: rgb(253, 247, 226);
    border: 1px solid rgb(221, 180, 34);
    padding: 16px;
    max-width: 815px;
    font-weight: 600;
  }
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
  max-width: 565px;
  padding-right: 24px;
  margin-bottom: 2px;
  font-weight: 400;
  font-size: 100%;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
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
  width: 565px;
`;
const TagContainer = styled.ul`
  display: flex;
  flex-direction: row;
  max-width: 340px;
  overflow: hidden;
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

const StyledReactPaginate = styled(ReactPaginate)`
  display: flex;
  flex-direction: row;
  list-style: none;

  a {
    padding: 6px 8px 6px 8px;
    border-radius: 6px;
    margin-right: 6px;
    border: 1px solid rgb(214, 217, 220);
    font-size: 12px;

    &:hover {
      background-color: rgb(189, 190, 191);
    }
  }
  li.selected {
    a {
      color: white;
      background-color: rgb(244, 130, 37);
    }
  }
`;
