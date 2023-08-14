import React from "react";
import { Link } from "react-router-dom";
import { styled } from "styled-components";
import logo from "../../assets/images/logo.png";
import search from "../../assets/images/search.png";
import { useState } from "react";
import SearchInfo from "../features/SearchInfo";

const HeaderContainer = styled.header`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  position: sticky;
  min-width: auto;
  width: 100%;
  height: 56px;
  border-top: 3px solid #f48024;
  border-bottom: 1px solid #bbc0c4;
  z-index: 999;
  margin: 0 auto;
  gap: 12px;
  padding: 10px;
`;

const HeaderLogo = styled.div`
  display: flex;
  align-items: center;
  flex-grow: 0;

  img {
    width: 150px;
    height: 30px;
  }
`;

const SearchContainer = styled.div`
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-grow: 1;
  max-width: 980px;

  img {
    position: absolute;
    left: 0.5em;
    width: 18px;
    height: 18px;
  }
`;

const SearchInput = styled.input`
  padding-left: 2.5em;
  height: 30px;
  width: 100%;
  border-radius: 6px;
  font-size: 13px;
  border: 1px solid #bbc0c4;

  &:focus {
    border-color: hsl(206, 90%, 69.5%);
    box-shadow: 0 0 0 4px hsla(206, 100%, 40%, 0.15);
    outline: none;
  }
`;

const HeaderBtn = styled.button`
  padding: 8px 0.8em;
  border-radius: 6px;
  border: none;
  white-space: nowrap;
  cursor: pointer;

  color: ${(props) => (props.skyblue ? "hsl(205,47%,42%)" : "white")};
  background-color: ${(props) => (props.skyblue ? "hsl(205,46%,92%)" : "hsl(206,100%,52%)")};
`;

const BtnContatiner = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  flex-grow: 0;
  gap: 6px;
`;

function Header () {
  const [ isFocus, setIsFocus ] = useState(false);

  const focusHandler = () => {
    setIsFocus(!isFocus);
  };

  return (
    <>
    <HeaderContainer>
      <HeaderLogo>
        <Link to="/">
          <img className="logoImg" src={logo} alt="" />
        </Link>
      </HeaderLogo>
      <SearchContainer>
        <img className="searchImg" src={search} alt="" />
        <SearchInput
          type="text"
          placeholder="Search..."
          onFocus={focusHandler} />
          {isFocus && <SearchInfo />}
      </SearchContainer>
      <BtnContatiner>
        <Link to="/login">
          <HeaderBtn skyblue>Log in</HeaderBtn>
        </Link>
        <Link to="/membership">
          <HeaderBtn>Sign up</HeaderBtn>
        </Link>
      </BtnContatiner>
    </HeaderContainer>
  </>
  );
}
export default Header;
