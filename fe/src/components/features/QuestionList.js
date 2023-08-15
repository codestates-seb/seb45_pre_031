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
          setData(response.data); // 필요한 경우 response.data 내부 구조에 맞게 접근 수정
        } else {
          // 실패한 경우
        }
      } catch (error) {
        console.error("Error fetching questions:", error);
      }
    };
    fetchData();
  }, [tab, pageNumber]);

  return (
    <StyledQuestionList>
      <HeaderContainer>
        <h1 className="headerTitle">All Questions</h1>
        <AskQuestionBtn>Ask Question</AskQuestionBtn>
      </HeaderContainer>
      <FiterContainer>
        <span className="questionCount">25,343,781 quesitons</span>
        <Fiter>
          <FiterOption>Newest</FiterOption>
          <FiterOption>Active</FiterOption>
          <FiterOption>Unanswered</FiterOption>
          <FiterOption>Score</FiterOption>
          <FiterOption>Popular</FiterOption>
        </Fiter>
      </FiterContainer>
      <QuestionListContainer>
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
      </QuestionListContainer>
      <PaginationContainer>
        <span>pagination</span>
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

const PaginationContainer = styled.div``;
// {
//   questions_count: 1513531,//필터 후 총 질문 개수
//   question_data:[
//     {
//       question_id: 84gdfgsrkg4wltg92zls15145,//고유한 id 생성해서 전달
//       created_at: "Mon Aug 14 2023 11:11:22 GMT+0900 (한국 표준시)",//date 기본값
//       updated_at: "Mon Aug 14 2023 11:11:22 GMT+0900 (한국 표준시)",//date 기본값
//       views: 252,
//       title: "koans 과제 진행 중 npm install 오류로 인해 정상 작동 되지 않습니다",
//       user_id: "dubipy",
//       vote_up: ['hongsik','jang'],
//       vote_down: ['honggildong','boby','james'],
//       tags: ['javascript', 'react', 'discord'],
//       bodyHTML:'<p dir="auto">--------------- 여기서부터 복사하세요 ---------------</p>\n<p dir="auto">운영 체제: 예) macOS</p>\n<p dir="auto">현재 어떤 챕터/연습문제/과제를 진행 중이고, 어떤 문제에 부딪혔나요?<br>\nPair 과제 / JavaScript Koans</p>\n<p dir="auto">npm install 명령어 입력 시 env: node: No such file or directory 라고 뜹니다</p>\n<p dir="auto">에러 발생하여 아래 명령어 실행 했는데도 불구하고 똑같은 에러가 발생했습니다<br>\nnpm cache clean --force</p>\n<p dir="auto">rm package-lock.json</p>\n<p dir="auto">rm -rf ./node_modules/</p>\n<p dir="auto">npm --verbose install</p>\n<p dir="auto">폴더 자체가 문제가 있다고 생각하여 github에서 다시 fork 후 진행했는데도 같은 에러가 발생했습니다<br>\n리눅스 기초 챕터 때 npm 설치해서 마지막 submit까지는 잘 됐는데 현재 짝수 생성기 폴더도 똑같이 npm install 시 no such file or directory가 발생합니다</p>\n<p dir="auto">에러가 출력된 곳에서, 이유라고 생각하는 부분을 열 줄 이내로 붙여넣기 해 주세요. (잘 모르겠으면 에러라고 생각하는 곳을 넣어주세요)</p>\n<div class="highlight highlight-source-js position-relative overflow-auto" data-snippet-clipboard-copy-content="minjun@dubi fe-sprint-javascript-koans-main % pwd \n/Users/minjun/Documents/fe_frontand_39/fe-sprint-javascript-koans-main\nminjun@dubi fe-sprint-javascript-koans-main % npm install \nenv: node: No such file or directory"><pre><span class="pl-s1">minjun</span>@<span class="pl-s1">dubi</span> <span class="pl-s1">fe</span><span class="pl-c1">-</span><span class="pl-s1">sprint</span><span class="pl-c1">-</span><span class="pl-s1">javascript</span><span class="pl-c1">-</span><span class="pl-s1">koans</span><span class="pl-c1">-</span><span class="pl-s1">main</span> <span class="pl-c1">%</span> <span class="pl-s1">pwd</span> \n<span class="pl-c1">/</span><span class="pl-v">Users</span><span class="pl-c1">/</span><span class="pl-s1">minjun</span><span class="pl-c1">/</span><span class="pl-v">Documents</span><span class="pl-c1">/</span><span class="pl-s1">fe_frontand_39</span><span class="pl-c1">/</span><span class="pl-s1">fe</span><span class="pl-c1">-</span><span class="pl-s1">sprint</span><span class="pl-c1">-</span><span class="pl-s1">javascript</span><span class="pl-c1">-</span><span class="pl-s1">koans</span><span class="pl-c1">-</span><span class="pl-s1">main</span>\n<span class="pl-s1">minjun</span><span class="pl-kos"></span>@<span class="pl-s1">dubi</span> <span class="pl-s1">fe</span><span class="pl-c1">-</span><span class="pl-s1">sprint</span><span class="pl-c1">-</span><span class="pl-s1">javascript</span><span class="pl-c1">-</span><span class="pl-s1">koans</span><span class="pl-c1">-</span><span class="pl-s1">main</span> <span class="pl-c1">%</span> <span class="pl-s1">npm</span> <span class="pl-s1">install</span> \nenv: node: <span class="pl-v">No</span> <span class="pl-s1">such</span> <span class="pl-s1">file</span> <span class="pl-s1">or</span> <span class="pl-s1">directory</span></pre></div>\n<p dir="auto">검색했던 링크가 있다면 첨부해 주세요.<br>\n<a href="https://mia-dahae.tistory.com/89" rel="nofollow">https://mia-dahae.tistory.com/89</a></p>\n<p dir="auto"><a href="https://stackoverflow.com/questions/38143558/npm-install-resulting-in-enoent-no-such-file-or-directory" rel="nofollow">https://stackoverflow.com/questions/38143558/npm-install-resulting-in-enoent-no-such-file-or-directory</a></p>\n<p dir="auto"><a href="https://velog.io/@hn04147/npm-install-%ED%95%A0-%EB%95%8C-tar-ENOENT-no-such-file-or-directory-lstat-%EC%97%90%EB%9F%AC%EB%82%A0-%EA%B2%BD%EC%9A%B0" rel="nofollow">https://velog.io/@hn04147/npm-install-%ED%95%A0-%EB%95%8C-tar-ENOENT-no-such-file-or-directory-lstat-%EC%97%90%EB%9F%AC%EB%82%A0-%EA%B2%BD%EC%9A%B0</a></p>\n<p dir="auto"><a href="https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&amp;blogId=chandong83&amp;logNo=221064506346" rel="nofollow">https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&amp;blogId=chandong83&amp;logNo=221064506346</a></p>\n<p dir="auto"><a href="https://webisfree.com/2021-07-15/npm-install-%EC%97%90%EB%9F%AC-%EB%B0%9C%EC%83%9D-rename-no-such-file-or-directory-%ED%95%B4%EA%B2%B0%ED%95%98%EA%B0%80" rel="nofollow">https://webisfree.com/2021-07-15/npm-install-%EC%97%90%EB%9F%AC-%EB%B0%9C%EC%83%9D-rename-no-such-file-or-directory-%ED%95%B4%EA%B2%B0%ED%95%98%EA%B0%80</a></p>\n<p dir="auto"><a href="https://hellowworlds.tistory.com/57" rel="nofollow">https://hellowworlds.tistory.com/57</a></p>',
//       avatarUrl:"https://avatars.githubusercontent.com/u/97888923?s=64&u=12b18768cdeebcf358b70051283a3ef57be6a20f&v=4",}
//     }, ///이렇게 15개의 데이터
//   ]
// }
