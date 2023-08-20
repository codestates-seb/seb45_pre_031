import { styled } from "styled-components";
import NavBar from "../components/sharedlayout/NavBar";
import Aside from "../components/sharedlayout/Aside";

import AskQuestionBtn from "../components/atoms/AskQuestionBtn";

import Answer from "../components/features/Answer";

import Footer from "../components/sharedlayout/Footer";

import React, { useState } from "react";
import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";

import { useParams } from "react-router-dom";

const DivAllContainer = styled.div`
  display: flex;
  justify-content: center;
`;
const DivTitleMainAside = styled.div`
  border-left: solid 1px rgb(214, 217, 220);
  padding: 24px;
`;
const DivMainTitleContainer = styled.div``;
const DivMainTitleBox = styled.div`
  display: flex;
  justify-content: space-between;
`;
const H1MainTitle = styled.h1`
  margin-bottom: 8px;
  font-size: 27px;
  color: rgb(59, 64, 69);
`;
const DivSubTitleContainer = styled.div`
  margin-bottom: 16px;
  border-bottom: 1px solid rgb(227, 230, 232);
  padding-bottom: 8px;
`;
const SpanSubTitleBox = styled.span`
  margin: 0 16px 8px 0;
`;
const SpanSubTitleLeft = styled.span`
  margin-right: 4px;
  color: rgb(106, 115, 124);
  font-size: 13px;
`;
const SpanSubTitleRight = styled.span`
  color: rgb(35, 38, 41);
  font-size: 13px;
`;
const DivMainAside = styled.div`
  display: flex;
  justify-content: space-between;
  width: 1080px;
`;
const MainMain = styled.main`
  width: 100%;
`;
const ArticleQ = styled.article`
  display: grid;
  grid-template-columns: max-content 1fr;
`;
const SpanVoteContainer = styled.span`
  margin-right: 16px;
`;
const ButtonUpDown = styled.button`
  width: 45px;
  height: 45px;
  border-radius: 50%;
  border: 1px solid rgb(214, 217, 220);
  padding: 10px;
  background-color: transparent;
  font-size: 13px;
`;
const DivVote = styled.div`
  width: 45px;
  height: 45px;
  padding: 10px;
  text-align: center;
  font-size: 19px;
`;
const SpanComment = styled.span`
  padding: 0 3px 2px;
  font-size: 13px;
  color: rgb(131, 140, 149);
`;
const SpanQContainer = styled.span`
  margin-bottom: 16px;
`;
const DivQText = styled.div``;
const UlTags = styled.ul`
  margin: 24px 0 24px 0;
  list-style: none;
`;
const LiTag = styled.li`
  display: inline !important;
  font-size: 12px;
  border-radius: 5px;
  margin: 4px 4px 4px 0;
  background-color: rgb(225, 236, 244);
  padding: 6px;
  color: rgb(57, 115, 157);
`;
const DivShareEditProfile = styled.div`
  margin: 16px 0;
  display: flex;
  justify-content: space-between;
`;
const SpanShare = styled.span`
  color: rgb(106, 115, 124);
  font-size: 13px;
  & span {
    margin-right: 8px;
  }
`;
const SpanEdit = styled.span``;
const SpanProfile = styled.span`
  width: 207px;
  height: 67px;
  border-radius: 5px;
  padding: 7px;
  background-color: rgb(217, 234, 247);
  font-size: 13px;
  > :first-child {
    margin-bottom: 4px;
    color: rgb(106, 115, 124);
  }
`;
const SpanProfileUser = styled.span`
  display: flex;
  > :first-child {
    margin-right: 8px;
  }
`;
const DivUserName = styled.div`
  color: rgb(0, 116, 204);
`;
const DivFollow = styled.div`
  color: rgb(106, 115, 124);
`;
const DivAnswerContainer = styled.div`
  display: flex;
  justify-content: space-between;
  margin: 24px 0;
`;
const SpanAnswerLength = styled.span`
  font-size: 19px;
`;
const LabelAnswerFilter = styled.label`
  margin-right: 8px;
  font-size: 12px;
  color: rgb(35, 38, 41);
`;
const SelectAnswerFilter = styled.select`
  width: 250px;
  border: solid 1px rgb(186, 191, 196);
  border-radius: 5px;
  padding: 5px;
  font-size: 13px;
`;
const ArticleA = styled.article`
  display: grid;
  grid-template-columns: max-content 1fr;
  margin-bottom: 24px;
`;
const ArticleNewA = styled.article``;
const H2YourAnswer = styled.h2`
  padding: 20px 0;
  border-top: 1px solid rgb(227, 230, 232);
`;
const DivQuill = styled.div`
  height: 200px;
`;
///// 임시 DB 시작 /////
const askAll = {
  question_id: "001",
  created_at: "Mon Aug 14 2023 11:11:22 GMT+0900 (한국 표준시)",
  updated_at: "Mon Aug 15 2023 11:11:22 GMT+0900 (한국 표준시)",
  views: 252,
  title: "koans 과제 진행 중 npm install 오류로 인해 정상 작동 되지 않습니다",
  user_id: "dubipy",
  vote_up: ["hongsik", "jang"],
  vote_down: ["honggildong", "boby", "james"],
  tags: ["javascript", "react", "discord"],
  bodyHTML: "<p>--------------- 질문 내용 ---------------</p>",
  avatarUrl: "https://avatars.githubusercontent.com/u/97888923?s=64&u=12b18768cdeebcf358b70051283a3ef57be6a20f&v=4",
  answer: [
    {
      answer_id: "DC_kwDOHOApLM4AKg6M",
      created_at: "Mon Aug 14 2023 11:11:22 GMT+0900 (한국 표준시)", //date 기본값,
      vote_up: ["hongsik", "jang"],
      vote_down: ["honggildong", "boby", "james"],
      user_id: "Kingsenal",
      bodyHTML:
        '<p dir="auto">안녕하세요. <a class="user-mention notranslate" data-hovercard-type="user" data-hovercard-url="/users/dubipy/hovercard" data-octo-click="hovercard-link-click" data-octo-dimensions="link_type:self" href="https://github.com/dubipy">@dubipy</a> 님!<br>\n코드스테이츠 교육 엔지니어 권준혁 입니다. <g-emoji class="g-emoji" alias="raised_hands" fallback-src="https://github.githubassets.com/images/icons/emoji/unicode/1f64c.png">🙌</g-emoji></p>\n<p dir="auto">질문 주신 내용은 노드 환경이 구성되어 있지 않기 때문에 발생되는 문제로 확인됩니다.</p>\n<p dir="auto"><code class="notranslate">brew unlink node &amp;&amp; brew link node</code></p>\n<p dir="auto">노드를 연결해 보시고 안된다면</p>\n<p dir="auto"><code class="notranslate">brew link --overwrite node</code></p>\n<p dir="auto">이 명령어를 그 다음에도 안된다면 접근권한 문제일 가능성이 큽니다.</p>\n<p dir="auto"><code class="notranslate">$ sudo chmod 776 /usr/local/lib</code> 접근 권한 변경 후<br>\n<code class="notranslate">$ brew link --overwrite node</code> 다시 연결을 해보세요 !</p>\n<p dir="auto">그럼에도 안된다면 다시 한 번 더 질문을 남겨주세요 !</p>\n<p dir="auto">답변이 되셨다면 내용을 간략하게 정리해서 코멘트를 남기고 answered를 마크해주세요 <g-emoji class="g-emoji" alias="white_check_mark" fallback-src="https://github.githubassets.com/images/icons/emoji/unicode/2705.png">✅</g-emoji><br>\n감사합니다.<g-emoji class="g-emoji" alias="rocket" fallback-src="https://github.githubassets.com/images/icons/emoji/unicode/1f680.png">🚀</g-emoji><br>\n코드스테이츠 교육 엔지니어 권준혁</p>',
      avatarUrl: "https://avatars.githubusercontent.com/u/79903256?s=64&v=4",
    },
    {
      answer_id: "DC_kwDOHOApLM4AKg6M",
      created_at: "Mon Aug 14 2023 11:11:22 GMT+0900 (한국 표준시)", //date 기본값,
      vote_up: ["hongsik", "jang"],
      vote_down: ["honggildong", "boby", "james"],
      user_id: "Kingsenal",
      bodyHTML:
        '<p dir="auto">안녕하세요. <a class="user-mention notranslate" data-hovercard-type="user" data-hovercard-url="/users/dubipy/hovercard" data-octo-click="hovercard-link-click" data-octo-dimensions="link_type:self" href="https://github.com/dubipy">@dubipy</a> 님!<br>\n코드스테이츠 교육 엔지니어 권준혁 입니다. <g-emoji class="g-emoji" alias="raised_hands" fallback-src="https://github.githubassets.com/images/icons/emoji/unicode/1f64c.png">🙌</g-emoji></p>\n<p dir="auto">질문 주신 내용은 노드 환경이 구성되어 있지 않기 때문에 발생되는 문제로 확인됩니다.</p>\n<p dir="auto"><code class="notranslate">brew unlink node &amp;&amp; brew link node</code></p>\n<p dir="auto">노드를 연결해 보시고 안된다면</p>\n<p dir="auto"><code class="notranslate">brew link --overwrite node</code></p>\n<p dir="auto">이 명령어를 그 다음에도 안된다면 접근권한 문제일 가능성이 큽니다.</p>\n<p dir="auto"><code class="notranslate">$ sudo chmod 776 /usr/local/lib</code> 접근 권한 변경 후<br>\n<code class="notranslate">$ brew link --overwrite node</code> 다시 연결을 해보세요 !</p>\n<p dir="auto">그럼에도 안된다면 다시 한 번 더 질문을 남겨주세요 !</p>\n<p dir="auto">답변이 되셨다면 내용을 간략하게 정리해서 코멘트를 남기고 answered를 마크해주세요 <g-emoji class="g-emoji" alias="white_check_mark" fallback-src="https://github.githubassets.com/images/icons/emoji/unicode/2705.png">✅</g-emoji><br>\n감사합니다.<g-emoji class="g-emoji" alias="rocket" fallback-src="https://github.githubassets.com/images/icons/emoji/unicode/1f680.png">🚀</g-emoji><br>\n코드스테이츠 교육 엔지니어 권준혁</p>',
      avatarUrl: "https://avatars.githubusercontent.com/u/79903256?s=64&v=4",
    },
  ],
};
///// 임시 DB 끝 /////

function QuestionDetailPage() {
  const [newAnswerValue, setNewAnswerValue] = useState("");
  const { question_id } = useParams();
  return (
    <div>
      <DivAllContainer>
        <NavBar />
        <DivTitleMainAside>
          <DivMainTitleContainer>
            <DivMainTitleBox>
              <H1MainTitle>MainTitle</H1MainTitle>
              <AskQuestionBtn />
            </DivMainTitleBox>
            <DivSubTitleContainer>
              <SpanSubTitleBox>
                <SpanSubTitleLeft>Asked</SpanSubTitleLeft>
                <SpanSubTitleRight>
                  {new Date().getDate() - new Date(askAll.created_at).getDate()} days ago
                </SpanSubTitleRight>
              </SpanSubTitleBox>
              <SpanSubTitleBox>
                <SpanSubTitleLeft>Modified</SpanSubTitleLeft>
                <SpanSubTitleRight>
                  {new Date().getDate() - new Date(askAll.updated_at).getDate()} days ago
                </SpanSubTitleRight>
              </SpanSubTitleBox>
              <SpanSubTitleBox>
                <SpanSubTitleLeft>Viewed</SpanSubTitleLeft>
                <SpanSubTitleRight>{askAll.views} times</SpanSubTitleRight>
              </SpanSubTitleBox>
            </DivSubTitleContainer>
          </DivMainTitleContainer>
          <DivMainAside>
            <MainMain>
              <ArticleQ>
                <SpanVoteContainer>
                  <ButtonUpDown>▲</ButtonUpDown>
                  <DivVote>{askAll.vote_up.length - askAll.vote_down.length}</DivVote>
                  <ButtonUpDown>▼</ButtonUpDown>
                </SpanVoteContainer>
                <SpanQContainer>
                  <DivQText>{askAll.bodyHTML}</DivQText>
                  <UlTags>
                    {askAll.tags.map((tag) => {
                      return <LiTag>{tag.toLowerCase()}</LiTag>;
                    })}
                  </UlTags>
                  <DivShareEditProfile>
                    <SpanShare>
                      <span>Share</span>
                      <span>Improve this question</span>
                      <span>Follow</span>
                    </SpanShare>
                    <SpanProfile>
                      <div>
                        {"asked " +
                          new Intl.DateTimeFormat("en-GB", { month: "short", day: "numeric" }).format(
                            new Date(askAll.created_at)
                          )}
                      </div>
                      <SpanProfileUser>
                        <img src={askAll.avatarUrl} alt="" width="32px" height="32px" />
                        <span>
                          <DivUserName>name</DivUserName>
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
                <SpanAnswerLength>{askAll.answer.length + " Answers"}</SpanAnswerLength>
                <form>
                  <LabelAnswerFilter>Sorted by:</LabelAnswerFilter>
                  <SelectAnswerFilter>
                    <option>Highest score (default)</option>
                    <option>Trending (recent votes count more)</option>
                    <option>Date modified (newest first)</option>
                    <option>Date created (oldest first)</option>
                  </SelectAnswerFilter>
                </form>
              </DivAnswerContainer>
              <ArticleA>
                <SpanVoteContainer>
                  <ButtonUpDown>▲</ButtonUpDown>
                  <DivVote>{askAll.vote_up.length - askAll.vote_down.length}</DivVote>
                  <ButtonUpDown>▼</ButtonUpDown>
                </SpanVoteContainer>
                <SpanQContainer>
                  <DivQText>{askAll.bodyHTML}</DivQText>
                  <DivShareEditProfile>
                    <SpanShare>
                      <span>Share</span>
                      <span>Improve this question</span>
                      <span>Follow</span>
                    </SpanShare>
                    <SpanProfile>
                      <div>
                        {"asked " +
                          new Intl.DateTimeFormat("en-GB", { month: "short", day: "numeric" }).format(
                            new Date(askAll.created_at)
                          )}
                      </div>
                      <SpanProfileUser>
                        <img src={askAll.avatarUrl} alt="" width="32px" height="32px" />
                        <span>
                          <DivUserName>name</DivUserName>
                          <DivFollow>follow</DivFollow>
                        </span>
                      </SpanProfileUser>
                    </SpanProfile>
                  </DivShareEditProfile>
                </SpanQContainer>
                <span></span>
                <SpanComment>Add a comment</SpanComment>
              </ArticleA>
              <ArticleNewA>
                <H2YourAnswer>Your Answer</H2YourAnswer>
                <DivQuill>
                  <ReactQuill theme="snow" value={newAnswerValue} onChange={setNewAnswerValue} />
                </DivQuill>
              </ArticleNewA>
            </MainMain>
            <Aside />
          </DivMainAside>
        </DivTitleMainAside>
      </DivAllContainer>
      <Footer />
    </div>
  );
}
export default QuestionDetailPage;
