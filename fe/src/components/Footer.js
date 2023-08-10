import { styled } from "styled-components";

const Footer_footer = styled.footer`
  position: static;
  bottom: 0;
  height: 322px;
  background-color: rgb(35, 38, 41);
`
const Div_Container = styled.div`
  display: flex;
  flex-flow: row wrap;
  margin: 0 88px;
  padding: 32px 12px 12px;
  font-size: 13px;
  color: rgb(145, 153, 161);
`

const Div_LogoContainer = styled.div`
  flex: 0 0 64px;
  margin: -12px 0 32px 0;
`
const Img_Logo = styled.img`
`

const Nav_MainContainer = styled.nav`
  display: flex;
  flex: 2 1 auto;
  flex-wrap: wrap;
`
const Div_MainBox = styled.div`
  flex: 1 0 auto;
  padding: 0 12px 24px 0;
`
const H5_MainTitle = styled.div`
  margin-bottom: 12px;
  font-weight: 700;
  color: rgb(186, 191, 196);
`
const Ul_MainBodyBox = styled.ul`
  list-style: none;
  vertical-align: baseline;
  font-weight: 400;
`
const Li_MainBody = styled.li`
  padding: 4px 0;
`
const Li_MainBodyAPI = styled(Li_MainBody)`
  margin-top: 16px !important;
`
const Div_MainBoxLast = styled(Div_MainBox)`
  
`

const Div_SubContainer = styled.div`
  flex: 1 1 150px;
  display: flex;
  flex-direction: column;
  font-size: 11px;
  font-weight: 400;
`
const Ul_SubTitleBox = styled.ul`
  display: flex;
  list-style: none;
`
const Li_SubTitle = styled.li`
  padding: 4px 0;
  margin-right: 12px;
`
const P_SubBottom = styled.p`
  margin-top: auto;
  margin-bottom: 24px;
`

function Footer() {
  return(
    <Footer_footer>
      <Div_Container>

        <Div_LogoContainer>
          <Img_Logo
            src="https://media.discordapp.net/attachments/1138344984454631504/1138711197278015569/image.png?width=612&height=708"
            alt="Stack Overflow Logo"
            width="32px" height="37px"
          />
        </Div_LogoContainer>

        <Nav_MainContainer>
          <Div_MainBox>
            <H5_MainTitle>
              STACK OVERFLOW
            </H5_MainTitle>
            <Ul_MainBodyBox>
              <Li_MainBody>
                Questions
              </Li_MainBody>
              <Li_MainBody>
                Help
              </Li_MainBody>
            </Ul_MainBodyBox>
          </Div_MainBox>
          <Div_MainBox>
            <H5_MainTitle>
              PRODUCTS
            </H5_MainTitle>
            <Ul_MainBodyBox>
              <Li_MainBody>
                Teams
              </Li_MainBody>
              <Li_MainBody>
                Advertising
              </Li_MainBody>
              <Li_MainBody>
                Collectives
              </Li_MainBody>
              <Li_MainBody>
                Talent
              </Li_MainBody>
            </Ul_MainBodyBox>
          </Div_MainBox>
          <Div_MainBox>
            <H5_MainTitle>
              COMPANY
            </H5_MainTitle>
            <Ul_MainBodyBox>
              <Li_MainBody>
                About
              </Li_MainBody>
              <Li_MainBody>
                Press
              </Li_MainBody>
              <Li_MainBody>
                Work Here
              </Li_MainBody>
              <Li_MainBody>
                Legal
              </Li_MainBody>
              <Li_MainBody>
                Privacy Policy
              </Li_MainBody>
              <Li_MainBody>
                Terms of Service
              </Li_MainBody>
              <Li_MainBody>
                Contact Us
              </Li_MainBody>
              <Li_MainBody>
                Cookie Settings
              </Li_MainBody>
              <Li_MainBody>
                Cookie Policy
              </Li_MainBody>
            </Ul_MainBodyBox>
          </Div_MainBox>
          <Div_MainBoxLast>
            <H5_MainTitle>
              STACK EXCHANGE NETWORK
            </H5_MainTitle>
            <Ul_MainBodyBox>
              <Li_MainBody>
                Technology
              </Li_MainBody>
              <Li_MainBody>
                Culture & recreation
              </Li_MainBody>
              <Li_MainBody>
                Life & arts
              </Li_MainBody>
              <Li_MainBody>
                Science
              </Li_MainBody>
              <Li_MainBody>
                Professional
              </Li_MainBody>
              <Li_MainBody>
                Business
              </Li_MainBody>
              <Li_MainBodyAPI>
                API
              </Li_MainBodyAPI>
              <Li_MainBody>
                Data
              </Li_MainBody>
            </Ul_MainBodyBox>
          </Div_MainBoxLast>
        </Nav_MainContainer>

        <Div_SubContainer>
          <Ul_SubTitleBox>
            <Li_SubTitle>
              Blog
            </Li_SubTitle>
            <Li_SubTitle>
              Facebook
            </Li_SubTitle>
            <Li_SubTitle>
              Twitter
            </Li_SubTitle>
            <Li_SubTitle>
              LinkedIn
            </Li_SubTitle>
            <Li_SubTitle>
              Instagram
            </Li_SubTitle>
          </Ul_SubTitleBox>
          <P_SubBottom>
            "
            Site design / logo © 2023 Stack Exchange Inc; user contributions licensed under "
            <span>CC BY-SA</span>
            ".                    "
            <span>rev&nbsp;2023.8.9.43572</span>
          </P_SubBottom>
        </Div_SubContainer>

      </Div_Container>
    </Footer_footer>
  )
}

export default Footer;
