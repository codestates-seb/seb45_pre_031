import { styled } from "styled-components";

const AsideAside = styled.aside`
  margin: 12px 24px;
  width: 300px;
  @media (max-width: 979px) {
    & {
      margin: 96px 0;
      width: 100% !important;
    }
  }
`;
//
const Div1 = styled.div`
  margin-bottom: 16px;
  border: 1px solid rgb(241, 229, 188);
  background-color: rgb(253, 247, 226);
`;
const Ul1 = styled.ul`
  list-style: none;
`;
const Li1Title = styled.li`
  border: rgb(241, 229, 188) !important;
  padding: 12px 15px;
  background-color: rgb(251, 243, 213) !important;
  font-size: 12px;
`;
const Li1Container = styled.li`
  display: flex !important;
  margin: 12px 0;
  padding: 0 16px;
  font-size: 13px;
`;
const Div1BoxSvg = styled.div`
  flex-shrink: 0 !important;
  flex-basis: calc(1% / 3);
  margin-right: 10px;
`;
const Div1BoxSvgMsg = styled(Div1BoxSvg)`
  color: rgb(0, 149, 255) !important;
`;
const Div1BoxText = styled.div`
  overflow-wrap: break-word;
`;
//
const Div2 = styled.div`
  margin-bottom: 16px;
  border: 1px solid rgb(214, 217, 220);
`;
const Div2TitleBox = styled.div`
  background-color: rgb(248, 249, 249);
  padding: 12px 15px;
  font-size: 15px;
`;
const Div2SeeAll = styled.div`
  float: right;
  font-size: 11px;
  color: blue;
  margin: 0 0 4px 8px;
`;
const Div2Container = styled.div`
  padding: 16px 15px;
  border: 1px solid rgb(214, 217, 220);
`;
const Div2Box = styled.div`
  display: flex !important;
  align-items: center !important;
  flex: 1 auto !important;
  height: 43.19px;
  margin-bottom: 7px;
`;
const Div2Img = styled.div`
  margin-right: 12px;
`;
const Div2TextBox = styled.div`
  flex: 1 auto !important;
`;
const Div2AppName = styled.div``;
const Div2Members = styled.div`
  font-size: 12px;
  margin-bottom: 8px;
`;
const DivJoin = styled.div`
  border: 1px solid rgb(55, 159, 239);
  border-radius: 10px;
  padding: 9.6px;
`;
const SpanDecript = styled.span`
  height: 41px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
`;
//
const Ad = styled.a`
  min-height: 300px;
  margin-bottom: 1.5em;
`;
//
const Div3 = styled.div`
  margin-top: 1.5em;
`;
const Div3Title = styled.div`
  font-size: 19px;
  margin: 0 0 1em;
`;
const Div3BodyBox = styled.div`
  display: flex;
  margin-bottom: 12px;
  font-size: 13px;
`;
const Span3BodyNumber = styled.span`
  display: inline-flex;
  justify-content: center;
  width: 38px;
  height: auto;
  padding: 4px 0;
  margin: 0 10px 0 0;
  border-radius: 2px;
  background-color: rgb(241, 242, 243);
`;
const Span3BodyNumberGreen = styled(Span3BodyNumber)`
  background-color: rgb(94, 186, 125);
  color: rgb(255, 255, 255);
`;
const Span3BodyText = styled.span`
  width: 70%;
  color: rgb(0, 116, 204);
  display: -webkit-box;
  overflow-wrap: break-word;
  -webkit-box-orient: vertical;
  white-space: break-spaces;
`;

function Aside() {
  return (
    <AsideAside>
      <Div1>
        <Ul1>
          <Li1Title>The Overflow Blog</Li1Title>
          <Li1Container>
            <Div1BoxSvg>
              <i className="fa-solid fa-pen"></i>
            </Div1BoxSvg>
            <Div1BoxText>Speeding up the I/O-heavy app: Q&A with Malte Ubl of Vercel</Div1BoxText>
          </Li1Container>
          <Li1Container>
            <Div1BoxSvg>
              <i className="fa-solid fa-pen"></i>
            </Div1BoxSvg>
            <Div1BoxText>Understanding SRE (Ep. 597)</Div1BoxText>
          </Li1Container>
          <Li1Title>Featured on Meta</Li1Title>
          <Li1Container>
            <Div1BoxSvgMsg>
              <i className="fa-regular fa-message"></i>
            </Div1BoxSvgMsg>
            <Div1BoxText>Moderation strike: Results of negotiations</Div1BoxText>
          </Li1Container>
          <Li1Container>
            <Div1BoxSvgMsg>
              <i className="fa-regular fa-message"></i>
            </Div1BoxSvgMsg>
            <Div1BoxText>Our Design Vision for Stack Overflow and the Stack Exchange network</Div1BoxText>
          </Li1Container>
          <Li1Container>
            <Div1BoxSvg>
              <img
                src="https://static-00.iconduck.com/assets.00/stack-overflow-icon-427x512-cz0mn36a.png"
                width="16px"
                height="16px"
                alt=""
              />
            </Div1BoxSvg>
            <Div1BoxText>Temporary policy: Generative AI (e.g., ChatGPT) is banned</Div1BoxText>
          </Li1Container>
          <Li1Container>
            <Div1BoxSvg>
              <img
                src="https://static-00.iconduck.com/assets.00/stack-overflow-icon-427x512-cz0mn36a.png"
                width="16px"
                height="16px"
                alt=""
              />
            </Div1BoxSvg>
            <Div1BoxText>Preview of Search and Question-Asking Powered by GenAI</Div1BoxText>
          </Li1Container>
          <Li1Container>
            <Div1BoxSvg>
              <img
                src="https://static-00.iconduck.com/assets.00/stack-overflow-icon-427x512-cz0mn36a.png"
                width="16px"
                height="16px"
                alt=""
              />
            </Div1BoxSvg>
            <Div1BoxText>Collections: A New Feature for Collectives on Stack Overflow</Div1BoxText>
          </Li1Container>
        </Ul1>
      </Div1>

      <Div2>
        <Div2TitleBox>
          <Div2SeeAll>see all</Div2SeeAll>" Collectives "
        </Div2TitleBox>
        <Div2Container>
          <Div2Box>
            <Div2Img>
              <img
                src="https://play-lh.googleusercontent.com/RyoQTmHnxsxPYabsETmWVXHtLorVh_yOO48hsdv2VmI-Uki4qt5c5vV1cicJODV56A4=w480-h960-rw"
                width="32px"
                height="32px"
                alt=""
              />
            </Div2Img>
            <Div2TextBox>
              <Div2AppName>Google Cloud</Div2AppName>
              <Div2Members>45k Members</Div2Members>
            </Div2TextBox>
            <DivJoin>Join</DivJoin>
          </Div2Box>
          <SpanDecript>
            Google Cloud provides organizations with leading infrastructure, platform capabilities and industry
            solutions to help them solve their most critical business problems.
          </SpanDecript>
        </Div2Container>
        <Div2Container>
          <Div2Box>
            <Div2Img>
              <img
                src="https://visualstudio.microsoft.com/wp-content/uploads/2023/01/msft-logo.png"
                width="32px"
                height="32px"
                alt=""
              />
            </Div2Img>
            <Div2TextBox>
              <Div2AppName>Microsoft Azure</Div2AppName>
              <Div2Members>11k Members</Div2Members>
            </Div2TextBox>
            <DivJoin>Join</DivJoin>
          </Div2Box>
          <SpanDecript>
            On-premises, hybrid, multicloud, or at the edge—build on your terms with best-in-className tools, your
            favorite open-source frameworks and languages, and a platform that supports continuous collaboration and
            delivery with Azure.
          </SpanDecript>
        </Div2Container>
        <Div2Container>
          <Div2Box>
            <Div2Img>
              <img
                src="https://cdn.icon-icons.com/icons2/2699/PNG/512/twilio_logo_icon_168416.png"
                width="32px"
                height="32px"
                alt=""
              />
            </Div2Img>
            <Div2TextBox>
              <Div2AppName>Twilio</Div2AppName>
              <Div2Members>9k Members</Div2Members>
            </Div2TextBox>
            <DivJoin>Join</DivJoin>
          </Div2Box>
          <SpanDecript>
            Twilio has democratized channels like voice, text, chat, video, and email by virtualizing the world’s
            communications infrastructure through APIs that are simple enough for any developer, yet robust enough to
            power the world’s most demanding applications.
          </SpanDecript>
        </Div2Container>
      </Div2>

      <Ad href="https://www.codestates.com/">
        <img
          src="https://ditoday.com/wp-content/uploads/2023/01/codestates-1536x864.jpg"
          width="300px"
          height="250px"
          alt="코드스테이츠 광고"
        />
      </Ad>

      <Div3>
        <Div3Title>Related</Div3Title>
        <Div3BodyBox>
          <span>
            <Span3BodyNumberGreen>40</Span3BodyNumberGreen>
          </span>
          <Span3BodyText>Expand and Collapse tableview cells</Span3BodyText>
        </Div3BodyBox>
        <Div3BodyBox>
          <span>
            <Span3BodyNumber>9</Span3BodyNumber>
          </span>
          <Span3BodyText>Swift vertical UICollectionView inside UITableView</Span3BodyText>
        </Div3BodyBox>
        <Div3BodyBox>
          <span>
            <Span3BodyNumberGreen>20</Span3BodyNumberGreen>
          </span>
          <Span3BodyText>TableView not displaying text with JSON data from API call</Span3BodyText>
        </Div3BodyBox>
        <Div3BodyBox>
          <span>
            <Span3BodyNumberGreen>19</Span3BodyNumberGreen>
          </span>
          <Span3BodyText>Update or reload UITableView after completion of delete action on detail view</Span3BodyText>
        </Div3BodyBox>
        <Div3BodyBox>
          <span>
            <Span3BodyNumber>3</Span3BodyNumber>
          </span>
          <Span3BodyText>Search Bar Not Displaying Search Results</Span3BodyText>
        </Div3BodyBox>
        <Div3BodyBox>
          <span>
            <Span3BodyNumberGreen>4</Span3BodyNumberGreen>
          </span>
          <Span3BodyText>thread 1: exc_bad_instruction(code=exc_1386_invop,subcode=0x0)</Span3BodyText>
        </Div3BodyBox>
        <Div3BodyBox>
          <span>
            <Span3BodyNumberGreen>1</Span3BodyNumberGreen>
          </span>
          <Span3BodyText>UITableView willDisplay cell is drawing all cells before I scroll down</Span3BodyText>
        </Div3BodyBox>
      </Div3>
    </AsideAside>
  );
}
export default Aside;
