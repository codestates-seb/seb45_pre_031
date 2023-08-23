import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { styled } from "styled-components";
import logo from "../../assets/images/logo.png";
import search from "../../assets/images/search.png";
import { useState } from "react";
import SearchInfo from "../features/SearchInfo";
import { logoutAction } from '../../redux/actions/loginAction';
import { useDispatch, useSelector } from "react-redux";

function Header() {

  const dispatch = useDispatch();
  const navigate = useNavigate();

  const isLoggedIn = useSelector(state => state.login.isLoggedIn);
  const displayName = useSelector(state => state.login.displayName);

  const [ isFocus, setIsFocus ] = useState(false);

  const focusHandler = () => {
    setIsFocus(!isFocus);
  };

  const logoutHandler = () => {
    localStorage.removeItem("access_token");
    localStorage.removeItem("refresh_token");
    localStorage.removeItem("display_name");

    // 로그아웃 액션 호출
    dispatch(logoutAction());

    // 홈 페이지로 이동
    navigate("/login");

  };

  return (
    <>
    <HeaderContainer>
      <HeaderElementContainer>
        <HeaderLogo>
          <Link to="/">
            <img className="logoImg" src={logo} alt="" />
          </Link>
        </HeaderLogo>
        <SearchContainer> {/* form */}
          <SearchbarContainer>
            <img className="searchImg" src={search} alt="" />
            <SearchInput
              type="text"
              placeholder="Search..."
              onFocus={focusHandler} />
          </SearchbarContainer>
            {isFocus && <SearchInfo />}
        </SearchContainer>
        <BtnContatiner>
          {isLoggedIn ? (
            <>
              <UserDisplayName>
                <a href="#none">{displayName}</a> 님
              </UserDisplayName>
              <LogoutBtn onClick={logoutHandler}>Log out</LogoutBtn>
            </>
          ) : (
            <>
            <Link to="/login">
              <LoginBtn>Log in</LoginBtn>
            </Link>
            <Link to="/membership">
              <SignupBtn>Sign up</SignupBtn>
            </Link>
            </>
          )}
        </BtnContatiner>
      </HeaderElementContainer>
    </HeaderContainer>
  </>
  );
}
export default Header;

const HeaderContainer = styled.header`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  position: sticky;
  min-width: auto;
  width: 100%;
  height: 56px;
  border-top: 3px solid hsl(27,90%,55% );
  border-bottom: 1px solid hsl(210,8%,85%);
  background-color: hsl(0,0%,100%);
  left: 0;
  top: 0;
  z-index: 5050;
  margin: 0 auto;
  gap: 12px;
  padding: 10px;
`;

const HeaderElementContainer = styled.div`
  width: 98rem;
  max-width: 100%;
  height: 100%;
  display: flex;
  margin: 0 auto;
  align-items: center;
`

const HeaderLogo = styled.div`
  display: flex;
  align-items: center;
  padding: 0 8px;
  height: 100%;
  background-color: transparent;
  cursor: pointer;

  img {
    width: 150px;
    height: 30px;
  }
`;

const SearchContainer = styled.form`
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 10000;
  flex-grow: 1;
  padding: 0 8px;
  font-size: 13px;
  font-stretch: 100%;
  vertical-align: baseline;
`;

const SearchbarContainer = styled.div`
  display: block;
  flex-grow: 1;
  position: relative;
  margin: 0;
  padding: 0;
  border: 0;
  font-size: 100%;

  img {
    vertical-align: bottom;
    right: auto;
    left: 0.7em;
    margin-top: calc(9px + 1px * -1);
    position: absolute;
    width: 18px;
    height: 18px;
  }
`

const SearchInput = styled.input`
  padding: 0.6em 0.7em 0.6em 2.4em;
  margin: 0;
  width: 100%;
  border-radius: 6px;
  border: 1px solid hsl(210,8%,75%);
  background-color: hsl(0,0%,100%);
  color: hsl(210,8%,25%);
  font-size: 13px;
  font-stretch: 100%;

  &:focus {
    border-color: hsl(206, 90%, 69.5%);
    box-shadow: 0 0 0 4px hsla(206, 100%, 40%, 0.15);
    outline: none;
  }
`;

const LoginBtn = styled.button`
  padding: 8px 0.8em;
  border-radius: 6px;
  border: none;
  white-space: nowrap;
  cursor: pointer;
  color: hsl(205,47%,42%);
  background-color: hsl(205,46%,92%);

  &:hover {
    background-color: hsl(205,57%,81%);
  }

  &:active {
    background-color: hsl(205,56%,76%);
  }

  &:focus {
    box-shadow: 0 0 0 4px hsla(206, 100%, 40%, 0.15);
    outline: none;
  }
`;

const SignupBtn = styled.button`
  padding: 8px 0.8em;
  border-radius: 6px;
  border: none;
  white-space: nowrap;
  cursor: pointer;
  color: white;
  background-color: hsl(206,100%,52%);

  &:hover {
    background-color: hsl(206,100%,40%);
  }

  &:active {
    background-color: hsl(209,100%,37.5%);
  }

  &:focus {
    box-shadow: 0 0 0 4px hsla(206, 100%, 40%, 0.15);
    outline: none;
  }
`

const BtnContatiner = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  flex-grow: 0;
  gap: 6px;
`;

const UserDisplayName = styled.p`
  font-family: inherit;
  font-size: 14px;
  margin-left: 10px;
  margin-right: 10px;
  text-align: center;

  > a {
    font-weight: 600;
    text-decoration: none;
    color: black;
  }
`

const LogoutBtn = styled.button`
  padding: 8px 0.8em;
  border-radius: 6px;
  border: none;
  white-space: nowrap;
  cursor: pointer;
  color: hsl(205,47%,42%);
  background-color: hsl(205,46%,92%);

  &:hover {
    background-color: hsl(205,57%,81%);
  }

  &:active {
    background-color: hsl(205,56%,76%);
  }

  &:focus {
    box-shadow: 0 0 0 4px hsla(206, 100%, 40%, 0.15);
    outline: none;
  }
`