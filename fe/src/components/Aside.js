import { styled } from "styled-components"

const Aside_aside = styled.aside`
  width: 298px;
`
const Div_1 = styled.div`
  margin-bottom: 16px;
  border: 1px solid rgb(241, 229, 188);
  background-color: rgb(253, 247, 226);
`
const Ul_1 = styled.ul`
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
const Div_1_boxText = styled.div`
  overflow-wrap: break-word;
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
        </Ul_1>
      </Div_1>
      <a href="https://www.codestates.com/" >
        <img
          src="https://ditoday.com/wp-content/uploads/2023/01/codestates-1536x864.jpg"
          width="300px" height="250px"
          alt="코드스테이츠 광고"
        />
      </a>
    </Aside_aside>
  )
}
export default Aside