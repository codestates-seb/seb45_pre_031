import { styled } from "styled-components"

const Aside_aside = styled.aside`
  margin: 24px;
  width: 300px;
`
//
const Div_1 = styled.div`
  margin-bottom: 16px;
  border: 1px solid rgb(241, 229, 188);
  background-color: rgb(253, 247, 226);
`
const Ul_1 = styled.ul`
  list-style: none;
`
const Li_1_title = styled.li`
  border: rgb(241, 229, 188) !important;
  padding: 12px 15px;
  background-color: rgb(251, 243, 213) !important;
  font-size: 12px;
`
const Li_1_container = styled.li`
  display: flex !important;
  margin: 12px 0;
  padding: 0 16px;
  font-size: 13px;
`
const Div_1_boxSVG = styled.div`
  flex-shrink: 0 !important;
  flex-basis: calc(1%/3);
  margin-right: 10px;
`
const Div_1_boxSVG_msg = styled(Div_1_boxSVG)`
  color: rgb(0,149,255) !important;
`
const Div_1_boxText = styled.div`
  overflow-wrap: break-word;
`
//
const Div_2 = styled.div`
  margin-bottom: 16px;
  border: 1px solid rgb(214, 217, 220);
`
const Div_2_titleBox = styled.div`
  background-color: rgb(248, 249, 249);
  padding: 12px 15px;
  font-size: 15px;
`
const Div_2_seeAll = styled.div`
  float: right;
  font-size: 11px;
  color: blue;
  margin: 0 0 4px 8px;
`
const Div_2_container = styled.div`
  padding: 16px 15px;
  border: 1px solid rgb(214, 217, 220);
`
const Div_2_box = styled.div`
  display: flex !important;
  align-items: center !important;
  flex: 1 auto !important;
  height: 43.19px;
  margin-bottom: 7px;
`
const Div_2_img = styled.div`
  margin-right: 12px;
`
const Div_2_textBox = styled.div`
  flex: 1 auto !important;
`
const Div_2_appName = styled.div`
`
const Div_2_members = styled.div`
  font-size: 12px;
  margin-bottom: 8px;
`
const Div_join = styled.div`
  border: 1px solid rgb(55, 159, 239);
  border-radius: 10px;
  padding: 9.6px;
`
const Span_decript = styled.span`
  height: 41px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;  
`
//
const A_ad = styled.a`
  min-height: 300px;
  margin-bottom: 1.5em;
`
//
const Div_3 = styled.div`
  margin-bottom: 1.5em;
`
const Div_3_title = styled.div`
  font-size: 19px;
  margin: 0 0 1em;
`


function Aside(){
  return(
    <Aside_aside>

      <Div_1>
        <Ul_1>
          <Li_1_title>
            The Overflow Blog
          </Li_1_title>
          <Li_1_container>
            <Div_1_boxSVG>
              <i class="fa-solid fa-pen"></i>
            </Div_1_boxSVG>
            <Div_1_boxText>
              Speeding up the I/O-heavy app: Q&A with Malte Ubl of Vercel
            </Div_1_boxText>
          </Li_1_container>
          <Li_1_container>
            <Div_1_boxSVG>
              <i class="fa-solid fa-pen"></i>
            </Div_1_boxSVG>
            <Div_1_boxText>
              Understanding SRE (Ep. 597)
            </Div_1_boxText>
          </Li_1_container>
          <Li_1_title>
            Featured on Meta
          </Li_1_title>
          <Li_1_container>
            <Div_1_boxSVG_msg>
              <i class="fa-regular fa-message"></i>
            </Div_1_boxSVG_msg>
            <Div_1_boxText>
              Moderation strike: Results of negotiations
            </Div_1_boxText>
          </Li_1_container>
          <Li_1_container>
            <Div_1_boxSVG_msg>
              <i class="fa-regular fa-message"></i>
            </Div_1_boxSVG_msg>
            <Div_1_boxText>
              Our Design Vision for Stack Overflow and the Stack Exchange network
            </Div_1_boxText>
          </Li_1_container>
          <Li_1_container>
            <Div_1_boxSVG>
              <img
                src="https://static-00.iconduck.com/assets.00/stack-overflow-icon-427x512-cz0mn36a.png"
                width="16px" height="16px"
              />
            </Div_1_boxSVG>
            <Div_1_boxText>
              Temporary policy: Generative AI (e.g., ChatGPT) is banned
            </Div_1_boxText>
          </Li_1_container>
          <Li_1_container>
            <Div_1_boxSVG>
              <img
                src="https://static-00.iconduck.com/assets.00/stack-overflow-icon-427x512-cz0mn36a.png"
                width="16px" height="16px"
              />
            </Div_1_boxSVG>
            <Div_1_boxText>
              Preview of Search and Question-Asking Powered by GenAI
            </Div_1_boxText>
          </Li_1_container>
          <Li_1_container>
            <Div_1_boxSVG>
              <img
                src="https://static-00.iconduck.com/assets.00/stack-overflow-icon-427x512-cz0mn36a.png"
                width="16px" height="16px"
              />
            </Div_1_boxSVG>
            <Div_1_boxText>
              Collections: A New Feature for Collectives on Stack Overflow
            </Div_1_boxText>
          </Li_1_container>
        </Ul_1>
      </Div_1>

      <Div_2>
        <Div_2_titleBox>
          <Div_2_seeAll>
            see all
          </Div_2_seeAll>
        "  
        Collectives
        "
        </Div_2_titleBox>
        <Div_2_container>
          <Div_2_box>
            <Div_2_img>
              <img
                src="https://play-lh.googleusercontent.com/RyoQTmHnxsxPYabsETmWVXHtLorVh_yOO48hsdv2VmI-Uki4qt5c5vV1cicJODV56A4=w480-h960-rw"
                width="32px" height="32px"
              />
            </Div_2_img>
            <Div_2_textBox>
              <Div_2_appName>
                Google Cloud
              </Div_2_appName>
              <Div_2_members>
                45k Members
              </Div_2_members>
            </Div_2_textBox>
            <Div_join>
              Join
            </Div_join>
          </Div_2_box>
          <Span_decript>
            Google Cloud provides organizations with leading infrastructure, platform capabilities and industry solutions to help them solve their most critical business problems.
          </Span_decript>
        </Div_2_container>
        <Div_2_container>
          <Div_2_box>
            <Div_2_img>
              <img
                src="https://visualstudio.microsoft.com/wp-content/uploads/2023/01/msft-logo.png"
                width="32px" height="32px"
              />
            </Div_2_img>
            <Div_2_textBox>
              <Div_2_appName>
                Microsoft Azure
              </Div_2_appName>
              <Div_2_members>
                11k Members
              </Div_2_members>
            </Div_2_textBox>
            <Div_join>
              Join
            </Div_join>
          </Div_2_box>
          <Span_decript>
            On-premises, hybrid, multicloud, or at the edge—build on your terms with best-in-class tools, your favorite open-source frameworks and languages, and a platform that supports continuous collaboration and delivery with Azure.
          </Span_decript>
        </Div_2_container>
        <Div_2_container>
          <Div_2_box>
            <Div_2_img>
              <img
                src="https://cdn.icon-icons.com/icons2/2699/PNG/512/twilio_logo_icon_168416.png"
                width="32px" height="32px"
              />
            </Div_2_img>
            <Div_2_textBox>
              <Div_2_appName>
                Twilio
              </Div_2_appName>
              <Div_2_members>
                9k Members
              </Div_2_members>
            </Div_2_textBox>
            <Div_join>
              Join
            </Div_join>
          </Div_2_box>
          <Span_decript>
            Twilio has democratized channels like voice, text, chat, video, and email by virtualizing the world’s communications infrastructure through APIs that are simple enough for any developer, yet robust enough to power the world’s most demanding applications.
          </Span_decript>
        </Div_2_container>
      </Div_2>

      <A_ad href="https://www.codestates.com/" >
        <img
          src="https://ditoday.com/wp-content/uploads/2023/01/codestates-1536x864.jpg"
          width="300px" height="250px"
          alt="코드스테이츠 광고"
        />
      </A_ad>

      <Div_3>
        <Div_3_title>
          Related Tags
        </Div_3_title>
      </Div_3>

    </Aside_aside>
  )
}
export default Aside