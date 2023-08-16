import React, { useState } from "react";
import axios from "axios";
import { useDispatch } from "react-redux";
import { styled } from "styled-components";
import GoogleLoginBtn from "../components/features/GoogleLoginBtn";
import { loginSuccess, loginFailure } from "../redux/actions/loginAction";


const LoginPageContainer = styled.div`
  display: flex;
  justify-content: center;
  max-width: 100%;
  width: 100%;
  height: 100vh;
  margin: 0;
  background-color: hsl(210,8%,95%);
`

const ContentContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  padding: 24px;
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

  > form {
    display: flex;
    flex-direction: column;
    width: 100%;
    flex: 1 0 auto;
    margin: 0 auto;
    text-align: left;
  }
`

const InputEmail = styled.input`
  width: 100%;
`

const InputPassword = styled.input`
  width: 100%;
`

const BtnContainer = styled.div`
`

const LoginBtn = styled.button`
`


function LoginPage (props) {

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
      const response = await axios.post("http://localhost:8080/accounts/authenticate", {
        username,
        password,
      }, {
        headers: {
          "Access-Control-Allow-Origin": "http://localhost:3000", // 서버 도메인 설정
        },
      });

      if (response.data.success) {
        // 서버에서 받은 jwt 토큰
        const token = response.data.token;
        // redux 액션 호출로 토큰 저장
        dispatch(loginSuccess(token));
        // 로그인 성공 처리 : main 화면으로 이동

      } else {
        dispatch(loginFailure(response.data.message));
      }
    } catch (error) {
      console.error("Error during login:", error);
    }
  };


  return (
    <LoginPageContainer>
      <ContentContainer>
        <ItemsContainer>
          <LogoContainer>
            <img src="https://media.discordapp.net/attachments/1138344984454631504/1138711197278015569/image.png?width=612&height=708" alt="" />
          </LogoContainer>
          <SocialLoginContainer>
            <GoogleLoginBtn />
          </SocialLoginContainer>
          <FormContainer>
            <form>
              <InputEmail
                value={username}
                onChange={onUsernameHandler}/>
              <InputPassword
                value={password}
                onChange={onPasswordHandler}/>
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
