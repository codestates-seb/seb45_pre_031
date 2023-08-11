import { NavLink } from "react-router-dom";
import styled from "styled-components";
import GlobeIcon from "../../assets/icons/GlobeIcon";

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
    </StyledNavBar>
  );
}

export default NavBar;

const StyledNavBar = styled.nav`
  min-width: 164px;
  height: 100vh;
  display: flex;
  flex-direction: column;
  padding-top: 20px;

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

