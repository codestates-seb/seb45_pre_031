import { styled } from "styled-components";
import { Link } from "react-router-dom";

const FooterFooter = styled.footer`
  position: static;
  bottom: 0;
  height: 322px;
  background-color: rgb(35, 38, 41);
`
const DivContainer = styled.div`
  display: flex;
  flex-flow: row wrap;
  margin: 0 88px;
  padding: 32px 12px 12px;
  font-size: 13px;
  color: rgb(145, 153, 161);
`

const DivLogoContainer = styled.div`
  flex: 0 0 64px;
  margin: -12px 0 32px 0;
`
const ImgLogo = styled.img`
`

const NavMainContainer = styled.nav`
  display: flex;
  flex: 2 1 auto;
  flex-wrap: wrap;
`
const DivMainBox = styled.div`
  flex: 1 0 auto;
  padding: 0 12px 24px 0;
`
const H5MainTitle = styled.div`
  margin-bottom: 12px;
  font-weight: 700;
  color: rgb(186, 191, 196);
`
const UlMainBodyBox = styled.ul`
  list-style: none;
  vertical-align: baseline;
  font-weight: 400;
`
const LiMainBody = styled.li`
  padding: 4px 0;
`
const LiMainBodyAPI = styled(LiMainBody)`
  margin-top: 16px !important;
`
const DivMainBoxLast = styled(DivMainBox)`
  
`

const DivSubContainer = styled.div`
  flex: 1 1 150px;
  display: flex;
  flex-direction: column;
  font-size: 11px;
  font-weight: 400;
`
const UlSubTitleBox = styled.ul`
  display: flex;
  list-style: none;
`
const LiSubTitle = styled.li`
  padding: 4px 0;
  margin-right: 12px;
`
const PSubBottom = styled.p`
  margin-top: auto;
  margin-bottom: 24px;
`

function Footer() {
  return(
    <FooterFooter>
      <DivContainer>

        <DivLogoContainer>
          <Link to="/">
            <ImgLogo
              src="https://media.discordapp.net/attachments/1138344984454631504/1138711197278015569/image.png?width=612&height=708"
              alt="Stack Overflow Logo"
              width="32px" height="37px"
            />
          </Link>
        </DivLogoContainer>

        <NavMainContainer>
          <DivMainBox>
            <H5MainTitle>
              STACK OVERFLOW
            </H5MainTitle>
            <UlMainBodyBox>
              <LiMainBody>
                Questions
              </LiMainBody>
              <LiMainBody>
                Help
              </LiMainBody>
            </UlMainBodyBox>
          </DivMainBox>
          <DivMainBox>
            <H5MainTitle>
              PRODUCTS
            </H5MainTitle>
            <UlMainBodyBox>
              <LiMainBody>
                Teams
              </LiMainBody>
              <LiMainBody>
                Advertising
              </LiMainBody>
              <LiMainBody>
                Collectives
              </LiMainBody>
              <LiMainBody>
                Talent
              </LiMainBody>
            </UlMainBodyBox>
          </DivMainBox>
          <DivMainBox>
            <H5MainTitle>
              COMPANY
            </H5MainTitle>
            <UlMainBodyBox>
              <LiMainBody>
                About
              </LiMainBody>
              <LiMainBody>
                Press
              </LiMainBody>
              <LiMainBody>
                Work Here
              </LiMainBody>
              <LiMainBody>
                Legal
              </LiMainBody>
              <LiMainBody>
                Privacy Policy
              </LiMainBody>
              <LiMainBody>
                Terms of Service
              </LiMainBody>
              <LiMainBody>
                Contact Us
              </LiMainBody>
              <LiMainBody>
                Cookie Settings
              </LiMainBody>
              <LiMainBody>
                Cookie Policy
              </LiMainBody>
            </UlMainBodyBox>
          </DivMainBox>
          <DivMainBoxLast>
            <H5MainTitle>
              STACK EXCHANGE NETWORK
            </H5MainTitle>
            <UlMainBodyBox>
              <LiMainBody>
                Technology
              </LiMainBody>
              <LiMainBody>
                Culture & recreation
              </LiMainBody>
              <LiMainBody>
                Life & arts
              </LiMainBody>
              <LiMainBody>
                Science
              </LiMainBody>
              <LiMainBody>
                Professional
              </LiMainBody>
              <LiMainBody>
                Business
              </LiMainBody>
              <LiMainBodyAPI>
                API
              </LiMainBodyAPI>
              <LiMainBody>
                Data
              </LiMainBody>
            </UlMainBodyBox>
          </DivMainBoxLast>
        </NavMainContainer>

        <DivSubContainer>
          <UlSubTitleBox>
            <LiSubTitle>
              Blog
            </LiSubTitle>
            <LiSubTitle>
              Facebook
            </LiSubTitle>
            <LiSubTitle>
              Twitter
            </LiSubTitle>
            <LiSubTitle>
              LinkedIn
            </LiSubTitle>
            <LiSubTitle>
              Instagram
            </LiSubTitle>
          </UlSubTitleBox>
          <PSubBottom>
            "
            Site design / logo © 2023 Stack Exchange Inc; user contributions licensed under "
            <span>CC BY-SA</span>
            ".                    "
            <span>rev&nbsp;2023.8.9.43572</span>
          </PSubBottom>
        </DivSubContainer>

      </DivContainer>
    </FooterFooter>
  )
}

export default Footer;
