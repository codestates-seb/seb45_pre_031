import React, { useState } from "react";
import { Link } from "react-router-dom";
import { styled } from "styled-components";
import logo from '../assets/images/logo.png';

const HeaderContainer = styled.header`
  display: flex;
  flex-direction: row;
  align-items: center;
  position: sticky;
  min-width: auto;
  width: 100%;
  height: 56px;
  border-top: 3px solid #f48024;
  border-bottom: 1px solid #BBC0C4;
`

const Logo = styled.div`
  display: flex;
  align-items: center;

  img {
    width: 150px;
    height: 30px;
  }
`

const SearchContainer = styled.div`
  position: relative;
  display: flex;
  align-items: center;
`

const SearchInput = styled.input`
  height: 30px;
  border-radius: 6px;
  border: 1px solid #BBC0C4 ;
`

function Header() {
  return (
    <HeaderContainer>
      <Logo>
        <Link to="/">
          <img className="logoImg" src={logo} />
        </Link>
      </Logo>
      <SearchContainer>
        <SearchInput
          type="text"
          placeholder="Search..." />
      </SearchContainer>
      <button>Log in</button>
      <button>Sign up</button>
    </HeaderContainer>
  );
}

export default Header;
