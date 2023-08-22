import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { styled } from "styled-components";

import NavBar from "../components/sharedlayout/NavBar";
import Aside from "../components/sharedlayout/Aside";

import AskQuestionBtn from "../components/atoms/AskQuestionBtn";

import Answer from "../components/features/Answer";
import Footer from "../components/sharedlayout/Footer";

import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";
import axios from "axios";

const DivAllContainer = styled.div`
  display: flex;
  justify-content: center;
`
const DivTitleMainAside = styled.div`
  border-left: solid 1px rgb(214, 217, 220);
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
  margin-right: 4px;
  color: rgb(106, 115, 124);
  font-size: 13px;
`
const SpanSubTitleRight = styled.span`
  color: rgb(35, 38, 41);
  font-size: 13px;
`
const DivMainAside = styled.div`

  /* display: flex;
  justify-content: space-between; */
  display: grid;
  grid-template-columns: 2fr 1fr;
  @media(max-width: 979px){
    &{
      display: block !important;
    }
  }
`
const MainMain = styled.main`
  margin: 12px 0;
  width: 100%;
`
const ArticleQ = styled.article`
  display: grid;
  grid-template-columns: max-content 1fr;
`
const SpanVoteContainer = styled.span`
  margin-right: 16px;
`
const ButtonUpDown = styled.button`
  width: 45px;  height: 45px;
  border-radius: 50%;
  border: 1px solid rgb(214, 217, 220);
  padding: 10px;
  background-color: transparent;
  font-size: 13px;
  cursor: pointer;
`
const DivVote = styled.div`
  width: 45px; height: 45px;
  padding: 10px;
  text-align: center;
  font-size: 19px;
`
const SpanComment = styled.span`
  padding: 0 3px 2px;
  font-size: 13px;
  color: rgb(131, 140, 149);
  cursor: pointer;
`
const SpanQContainer = styled.span`
  margin-bottom: 16px;
`
const DivQText = styled.div`
`
const UlTags = styled.ul`
  margin: 24px 0;
  list-style: none;
`
const LiTag = styled.li`
  display: inline !important;
  font-size: 12px;
  border-radius: 5px;
  margin: 4px 4px 4px 0;
  background-color: rgb(225, 236, 244);
  padding: 6px;
  color: rgb(57, 115, 157);
`
const DivShareEditProfile = styled.div`
  margin: 16px 0;
  display: flex;
  justify-content: space-between;
`
const SpanShare = styled.span`
  color: rgb(106, 115, 124);
  font-size: 13px;
  cursor: pointer;
  & span{
    margin-right: 8px;
  }
`
const SpanEdit = styled.span`
`
const SpanProfile = styled.span`
  width: 207px; height: 67px;
  border-radius: 5px;
  padding: 7px;
  background-color: rgb(217, 234, 247);
  font-size: 13px;
  >:first-child{
    margin-bottom: 4px;
    color: rgb(106, 115, 124);
  }
`
const SpanProfileUser = styled.span`
  display: flex;
  >:first-child{
    margin-right: 8px;
  }
`
const DivUserName = styled.div`
  color: rgb(0, 116, 204);
`
const DivFollow = styled.div`
  color: rgb(106, 115, 124);
`
const DivAnswerContainer = styled.div`
  display: flex;
  justify-content: space-between;
  margin: 24px 0;
`
const SpanAnswerLength = styled.span`
  font-size: 19px;
`
const LabelAnswerFilter = styled.label`
  margin-right: 8px;
  font-size: 12px;
  color: rgb(35, 38, 41);
`
const SelectAnswerFilter = styled.select`
  width: 250px;
  border: solid 1px rgb(186, 191, 196);
  border-radius: 5px;
  padding: 5px;
  font-size: 13px;
`
const ArticleA = styled.article`
  display: grid;
  grid-template-columns: max-content 1fr;
  margin-bottom: 24px;
`
const ArticleNewA = styled.article`
`
const H2YourAnswer = styled.h2`
  padding: 20px 0;
  border-top: 1px solid rgb(227, 230, 232);
`
const DivQuill = styled.div`
  padding-bottom: 45px;
  height: 200px;
`
const SubmitPost = styled.input`
  margin-top: 30px;
  height: 38px;
  padding: 10.5px;
  border-radius: 6px;
  border: none;
  cursor: pointer;
  color: white;
  background-color: hsl(206, 100%, 52%);
`

function QuestionDetailPage() {

  const [question, setQuestion] = useState({
    questionId: "001",
    created_at: "Mon Aug 14 2023 11:11:22 GMT+0900 (한국 표준시)",
    modified_at: "Mon Aug 15 2023 11:11:22 GMT+0900 (한국 표준시)",
    views: "views",
    title: "제목",
    displayName: "displayName",
    voteUp: ["onePerson"],
    voteDown: ["onePerson","twoPerson"],
    tags: ["javascript", "react"],
    body:
    "<p> 질문 내용 </p>",
  })
  const [answers, setAnswers] = useState([{
    answer_id: "007",
    created_at: "Mon Aug 14 2023 11:11:22 GMT+0900 (한국 표준시)",
    modified_at: "Mon Aug 14 2023 11:11:22 GMT+0900 (한국 표준시)",
    voteUp: ["onePerson"],
    voteDown: ["onePerson","twoPerson"],
    displayName: "displayName",
    body:
      "<p>안녕하세요.<p>",
  }])

  const { questionId } = useParams();

  const navigate = useNavigate();

  const [newAnswerValue, setNewAnswerValue] = useState('');

  function loadQnA(){
    axios.get("http://ec2-3-36-128-133.ap-northeast-2.compute.amazonaws.com/v1/questions/"+questionId)
    .then(question=>setQuestion(question.data.data))
    .catch(err=>console.log(err+"질문을 가져오지 못했습니다."))
    axios.get("http://ec2-3-36-128-133.ap-northeast-2.compute.amazonaws.com/v1/answer?questionId="+questionId)
    .then(answers=>setAnswers(answers.data.data))
    .catch(err=>console.log(err+"답변을 가져오지 못했습니다."))
  }

  useEffect(loadQnA,[])

  function shareClick(){
    navigator.clipboard.writeText(window.location.href)
    .then(res=>alert('링크가 복사되었습니다.'))
  }

  const token = localStorage.getItem("access_token");
  const postSend = (e) => {
    console.log("llllllllll")
    axios.post(
      "http://ec2-3-36-128-133.ap-northeast-2.compute.amazonaws.com/v1/answer/questionId=?"+questionId+"&body="+newAnswerValue,
      {headers:{"Authorization": token}},
      {body: {data: newAnswerValue}}
    )
    .then(res=>console.log(res+"포스팅에 성공했습니다."))
    .catch(err=>console.log(err+"포스팅에 실패했습니다."))
  }

  return(
    <div>
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
                {new Date().getDate() - new Date(question.created_at).getDate()} days ago
              </SpanSubTitleRight>
            </SpanSubTitleBox>
            <SpanSubTitleBox>
              <SpanSubTitleLeft>
                Modified
              </SpanSubTitleLeft>
              <SpanSubTitleRight>
                {new Date().getDate() - new Date(question.updated_at).getDate()} days ago
              </SpanSubTitleRight>
            </SpanSubTitleBox>
            <SpanSubTitleBox>
              <SpanSubTitleLeft>
                Viewed
              </SpanSubTitleLeft>
              <SpanSubTitleRight>
                {question.views} times
              </SpanSubTitleRight>
            </SpanSubTitleBox>
            </DivSubTitleContainer>
          </DivMainTitleContainer>
          <DivMainAside>
            <MainMain>
              <ArticleQ>
                <SpanVoteContainer>
                  <ButtonUpDown>
                    ▲
                  </ButtonUpDown>
                  <DivVote>
                    {question.voteUp.length - question.voteDown.length}
                  </DivVote>
                  <ButtonUpDown>
                    ▼
                  </ButtonUpDown>
                </SpanVoteContainer>
                <SpanQContainer>
                  <DivQText>
                    {question.body}
                  </DivQText>
                  <UlTags>
                    {question.tags.map((tag)=>{
                      return <LiTag>{tag.toLowerCase()}</LiTag>
                    })}
                  </UlTags>
                  <DivShareEditProfile>
                    <SpanShare>
                      <span onClick={shareClick}>
                        Share
                      </span>
                      <span>
                        Improve this question
                      </span>
                      <span>
                        Follow
                      </span>
                    </SpanShare>
                    <SpanProfile>
                      <div>{"asked "+new Intl.DateTimeFormat("en-GB",{month: 'short', day: 'numeric'}).format(new Date(question.created_at))}</div>
                      <SpanProfileUser>
                        <img
                          src={question.avatarUrl}
                          alt=""
                          width="32px" height="32px"
                        />
                        <span>
                          <DivUserName>{question.displayName}</DivUserName>
                          <DivFollow>follow</DivFollow>
                        </span>
                      </SpanProfileUser>
                    </SpanProfile>
                  </DivShareEditProfile>
                </SpanQContainer>
                <span></span>
                <SpanComment>Add a comment</SpanComment>
              </ArticleQ>
              <DivAnswerContainer>
                <SpanAnswerLength>
                  {answers.length+" Answers"}
                </SpanAnswerLength>
                <LabelAnswerFilter>
                  Sorted by:
                </LabelAnswerFilter>
                <SelectAnswerFilter>
                  <option>Highest score (default)</option>
                  <option>Trending (recent votes count more)</option>
                  <option>Date modified (newest first)</option>
                  <option>Date created (oldest first)</option>
                </SelectAnswerFilter>
              </DivAnswerContainer>
              {answers.map(answer=><Answer answer={answer} shareClick={shareClick} />)}
              <ArticleNewA>
                <form
                  // name="newAnswerPost"
                  // method="POST"
                  // action={"http://ec2-3-36-128-133.ap-northeast-2.compute.amazonaws.com/v1/answer?questionId="+questionId+"&body="+newAnswerValue}
                  // onsubmit={postSend}
                  onsubmit="return false"
                >
                  <H2YourAnswer>
                    Your Answer
                  </H2YourAnswer>
                  <DivQuill>
                    <ReactQuill
                      name="data"
                      theme="snow"
                      value={newAnswerValue}
                      onChange={()=>setNewAnswerValue(newAnswerValue)}
                    />
                  </DivQuill>
                  <SubmitPost
                    type="submit"
                    value="Post Your Answer"
                    onClick={postSend}
                   />
                </form>
              </ArticleNewA>
            </MainMain>
            <Aside />
          </DivMainAside>
        </DivTitleMainAside>
      </DivAllContainer>
      <Footer />
    </div>
  )
}
export default QuestionDetailPage;