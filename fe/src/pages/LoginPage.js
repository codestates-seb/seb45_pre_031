import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router";
import { styled } from "styled-components";
import { useDispatch } from "react-redux";
import axios from "axios";
import { loginSuccess, loginFailure } from "../redux/actions/loginAction";

function LoginPage () {
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const [ username, setUsername ] = useState('');
  const [ password, setPassword ] = useState('');

  // 입력 시, onChange 발생 -> state 변경 -> value 변경
  const onUsernameHandler = (event) => {
    setUsername(event.currentTarget.value);
  };

  const onPasswordHandler = (event) => {
    setPassword(event.currentTarget.value);
  };

  const onLoginHadler = async (event) => {
    // page refresh 방지
    event.preventDefault();

    // 서버 요청
    try {
      const response = await axios.post("http://localhost:8080/v1/accounts/authenticate", {
        username,
        password,
      });

      if (response.data.success) {
        // 서버에서 받은 토큰
        const accessToken = response.data.accessToken;
        const refreshToken = response.data.refreshToken;

        // 로컬 스토리지에 토큰 저장
        localStorage.setItem("access_token", accessToken);
        localStorage.setItem("refresh_token", refreshToken);

        // 로그인 성공 처리
        dispatch(loginSuccess(accessToken));

        // 로그인 성공 후 리다이렉션 처리
        navigate("/");

      } else {
        dispatch(loginFailure(response.data.message));
      }
    } catch (error) {
      console.error("Error during login:", error);
      dispatch(loginFailure("An error occurred during login."));
    }
  };

  const onGoogleLoginHandler = () => {
    // Google 로그인 URL
    const googleLoginUrl = "http://localhost:8080/oauth2/authorization/google";

    // Google 로그인 팝업
    window.open(googleLoginUrl, "_blank");
  };

  useEffect(() => {
    const urlSearchParams = new URLSearchParams(window.location.search);
    const accessToken = urlSearchParams.get("access_token");
    const refreshToken = urlSearchParams.get("refresh_token");

    if (accessToken && refreshToken) {
      // 로컬 스토리지에 토큰 저장
      localStorage.setItem("access_token", accessToken);
      localStorage.setItem("refresh_token", refreshToken);

      // 로그인 성공 처리
      dispatch(loginSuccess(accessToken));

      // 로그인 성공 후 리다이렉션 처리
      navigate("/");
    }
  });

  return (
    <LoginPageContainer>
      <LoginContentContainet>
        <LoginLogoContiner>
          <img src="https://media.discordapp.net/attachments/1138344984454631504/1138711197278015569/image.png?width=612&height=708" alt="" />
        </LoginLogoContiner>
        <LoginBtnContainer>
          <GoogleLoginBtn
            onClick={onGoogleLoginHandler}>
            <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/53/Google_%22G%22_Logo.svg/1024px-Google_%22G%22_Logo.svg.png" alt="" />
            Log in with Google</GoogleLoginBtn>
        </LoginBtnContainer>
        <LoginFormContainer>
          <LoginForm>
            <LoginInputForm className="login-email-form">
              <LoginLabel>Email</LoginLabel>
              <LoginInput
                className="login-email-input"
                onClick={onUsernameHandler} />
            </LoginInputForm>
            <LoginInputForm className="login-password-form">
              <div>
                <LoginLabel>Password</LoginLabel>
                <p>Forgot password?</p>
              </div>
              <LoginInput
                className="login-password-input"
                onChange={onPasswordHandler} />
            </LoginInputForm>
            <LoginBtnContainer>
              <LoginBtn
                onClick={onLoginHadler}>Log in</LoginBtn>
            </LoginBtnContainer>
          </LoginForm>
        </LoginFormContainer>
        <LoginTextBelowContainer>
            Don't have an accout?
            <a>Sign up</a>
        </LoginTextBelowContainer>
      </LoginContentContainet>
    </LoginPageContainer>

  );
};

const LoginPageContainer = styled.section`
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgb(241, 242, 243);
`;

const LoginContentContainet = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 6px;
`;

const LoginFormContainer = styled.div`
  width: 290px;
  box-shadow: 0 10px 24px hsla(0,0%,0%,0.05), 0 20px 48px hsla(0, 0%, 0%, 0.05), 0 1px 4px hsla(0, 0%, 0%, 0.1);
  padding: 24px;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 24px;
  background-color: white;
  border-radius: 6px;
  box-sizing: inherit;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const LoginForm = styled.form`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 18px;
  width: 100%;
`;

const LoginInputForm = styled.div`
  width: 100%;

  &.login-password-form {

    > div {
      display: flex;
      align-items: center;
      justify-content: space-between;

      > p {
        font-size: 12px;
        color: rgb(0, 116, 204);
      }
    }
  }
`;

const LoginLabel = styled.label`
  font-size: 15px;
  font-family: inherit;
  font-weight: 600;
`;

const LoginInput = styled.input`
  width: 100%;
  margin: 0;
  background-color: hsl(0,0%,100%);
  border: 1px solid hsl(210,8%,75%);
  border-radius: 6px;
  padding: 8px 9px;

  &:focus {
    border-color: hsl(206, 90%, 69.5%);
    box-shadow: 0 0 0 4px hsla(206, 100%, 40%, 0.15);
    outline: none;
  }
`;

const LoginBtnContainer = styled.div`
  width: 100%;
`;

const LoginBtn = styled.button`
  width: 100%;
  margin: 0;
  padding: 10px;
  border-radius: 6px;
  color: white;
  font-size: 13px;
  border: none;
  white-space: nowrap;
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
`;

const GoogleLoginBtn = styled.button`
  width: 100%;
  margin: 0;
  margin-top: 8px;
  margin-bottom: 10px;
  padding: 10px;
  border-radius: 6px;
  color: rgb(59, 64, 69);
  font-size: 13px;
  border: 1px solid hsl(210,8%,85%);
  white-space: nowrap;
  background-color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 6px;

  &:hover {
    background-color: hsl(210,8%,97.5%);
  }

  &:active {
    background-color: hsl(210,8%,95%);
  }

  &:focus {
    box-shadow: 0 0 0 4px hsla(210,8%,15%,0.1);
    outline: none;
  }

  > img {
    display: block;
    margin: 0;
    padding: 0;
    width: 18px;
    height: 18px;
  }
`;

const LoginLogoContiner = styled.div`
  display: block;
  text-align: center;

  > img {
    width: 32px;
    height: 37px;
  }
`;

const LoginTextBelowContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 6px;
  font-size: 13px;

  > a {
    color: rgb(0, 116, 204);
  }
`;

export default LoginPage;