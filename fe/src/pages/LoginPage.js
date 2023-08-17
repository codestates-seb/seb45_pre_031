import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { useDispatch } from "react-redux";
import { styled } from "styled-components";
import { loginSuccess, loginFailure } from "../redux/actions/loginAction";


const LoginPageContainer = styled.div`
  display: flex;
  justify-content: center;
  flex-basis: auto;
  flex-shrink: 0;
  flex-grow: 1;
  position: relative;
  max-width: 100%;
  width: 100%;
  height: 100vh;
  margin: 0;
  background-color: hsl(210,8%,95%);
  font-family: -apple-system,BlinkMacSystemFont,"Segoe UI Adjusted","Segoe UI","Liberation Sans",sans-serif;
`

const ContentContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  max-width: 1264px;
  margin: 0;
  background-color: transparent;
  padding: 24px;

  &::before {
    content: "";
    display: table;
  }
`

const ItemsContainer = styled.div`
  display: block;
  margin: 0;
  padding: 0;
`

const LogoContainer = styled.div`
  display: block;
  text-align: center;
  margin-bottom: 24px;

  > img {
    width: 32px;
    height: 37px;
  }
`

const SocialLoginContainer = styled.div`
  display: flex;
  flex-direction: column;
  flex-basis: auto;
  flex-grow: 1;
  flex-shrink: 1;
  margin-bottom: 16px;
`

const FormContainer = styled.div`
  display: block;
  margin-bottom: 24px;
  margin-left: auto;
  margin-right: auto;
  padding: 24px;
  background-color: hsl(0,0%,100%);
  border-radius: 8px;
  box-shadow: 0 10px 24px hsla(0,0%,0%,0.05), 0 20px 48px hsla(0, 0%, 0%, 0.05), 0 1px 4px hsla(0, 0%, 0%, 0.1);
  max-width: 24rem;
  width: 230px;

  > form {
    display: flex;
    flex-direction: column;
    width: 100%;
    flex: 1 0 auto;
    margin: calc(12 / 2 * -1);
    text-align: left;
  }
`

const Input = styled.input`
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
`

const InputContainer = styled.div`
  display: flex;
  flex-direction: column;
  padding: 0;
  margin: 6px 0px;

  &.input-password {

    > div {
      display: flex;
      align-items: center;
      justify-content: space-between;
    }
  }

  > div > span {
    font-size: 10px;
  }


`

const BtnContainer = styled.div`
`

const GoogleLoginButton = styled.button`
  width: 100%;
  padding: 8px 0.8em;
  border-radius: 6px;
  border: none;
  white-space: nowrap;
  cursor: pointer;
  color: black;
  background-color: #ffffff; /* Google 로그인 색상 */
`;

const LoginBtn = styled.button`
  width: 100%;
  padding: 8px 0.8em;
  border-radius: 6px;
  border: none;
  white-space: nowrap;
  cursor: pointer;
  color: white;
  background-color: hsl(206,100%,52%);
`


function LoginPage (props) {
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
      <ContentContainer>
        <ItemsContainer>
          <LogoContainer>
            <img src="https://media.discordapp.net/attachments/1138344984454631504/1138711197278015569/image.png?width=612&height=708" alt="" />
          </LogoContainer>
          <SocialLoginContainer>
            <GoogleLoginButton
              onClick={onGoogleLoginHandler}>
                구글로 로그인
            </GoogleLoginButton>
          </SocialLoginContainer>
          <FormContainer>
            <form id="login-form">
               <InputContainer className="input-email">
                <label>Email</label>
                <Input
                type="email"
                value={username}
                onChange={onUsernameHandler}/>
              </InputContainer>
              <InputContainer className="input-password">
                <div>
                  <label>Password</label>
                  <span>Forgot password?</span>
                </div>
                <Input
                  type="password"
                  value={password}
                  onChange={onPasswordHandler}/>
              </InputContainer>
              <BtnContainer>
                <LoginBtn
                  onClick={onLoginHadler}>Log in</LoginBtn>
              </BtnContainer>
            </form>
          </FormContainer>
        </ItemsContainer>
      </ContentContainer>
    </LoginPageContainer>
  )
}

export default LoginPage;
