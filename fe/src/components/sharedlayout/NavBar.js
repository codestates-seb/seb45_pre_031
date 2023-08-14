import { NavLink } from "react-router-dom";
import styled from "styled-components";
import GlobeIcon from "../../assets/icons/GlobeIcon";
import ExclamationMarkIcon from "../../assets/icons/ExclamationMarkIcon";
import InStarIcon from "../../assets/icons/InStarIcon";
import BriefcaseIcon from "../../assets/icons/BriefcaseIcon";

function NavBar() {
  return (
    <StyledNavBar>
      <NavLink to="/" className="link" style={linkStyle}>
        <div className="icon">
          <GlobeIcon />
        </div>
        Questions
      </NavLink>
      <NavLink to="/taglist" className="link" style={linkStyle}>
        <div className="icon"></div>Tags
      </NavLink>
      <Dummy>
        <DummySection>
          <NonFunctionalLinkTitle>
            <span>COLLECTIVES</span>
            <ExclamationMarkIcon />
          </NonFunctionalLinkTitle>
          <NonFunctionalLinkContent>
            <InStarIcon />
            <span className="content">Explore Collectives </span>
          </NonFunctionalLinkContent>
        </DummySection>
        <DummySection>
          <NonFunctionalLinkTitle>
            <span>TEAMS</span>
            <ExclamationMarkIcon />
          </NonFunctionalLinkTitle>
          <NonFunctionalLinkContent>
            <div className="orange">
              <BriefcaseIcon />
            </div>
            <span className="content">Create free Team</span>
          </NonFunctionalLinkContent>
        </DummySection>
        <DummySection>
          <NonFunctionalLinkBox>Looking for your Teams?</NonFunctionalLinkBox>
        </DummySection>
      </Dummy>
    </StyledNavBar>
  );
}

export default NavBar;

const StyledNavBar = styled.nav`
  width: 164px;
  min-width: 164px;
  height: 100vh;
  display: flex;
  flex-direction: column;
  padding-top: 20px;

  .orange {
    display: flex;
    justify-content: center;
    border-radius: 4px;
    width: 16px;
    height: 16px;
    background-color: rgb(244, 130, 37);
  }

  .link {
    display: flex;
    align-items: center;
    padding-left: 10px;
    color: rgb(35, 38, 41);
    text-decoration: none;
    height: 33px;
    font-size: 100%;
    opacity: 0.6;

    &:hover {
      opacity: 0.9;
    }
  }

  .icon {
    width: 16px;
    height: 16px;
    margin-right: 8px;
  }
`;

const linkStyle = ({ isActive }) => {
  if (isActive) {
    return {
      color: "rgb(12, 13, 14)",
      fontWeight: "700",
      height: "33px",
      fontSize: "100%",
      textDecoration: "none",
      backgroundColor: "rgb(241, 242, 243)",
      borderRight: "4px solid rgb(244, 130, 37)",
      opacity: "1.0",
    };
  }
  return;
};
const Dummy = styled.div``;

const DummySection = styled.div`
  margin-top: 16px;
`;

const NonFunctionalLinkTitle = styled.div`
  display: flex;
  flex-direction: row;
  padding-left: 10px;
  padding-right: 10px;
  justify-content: space-between;
  padding-bottom: 8px;
  font-size: 11px;
  color: rgb(121, 126, 132);
`;

const NonFunctionalLinkContent = styled.div`
  display: flex;
  flex-direction: row;
  font-size: 13px;
  padding-left: 10px;
  padding-right: 10px;
  color: rgb(121, 126, 132);
  .content {
    margin-left: 6px;
  }
`;

const NonFunctionalLinkBox = styled.div`
  font-size: 12px;
  color: rgb(0, 99, 191);
  background-color: rgb(240, 248, 255);
  padding: 7px;
  border-radius: 6px;
  margin-left: 4px;
  margin-right: 6px;
`;
