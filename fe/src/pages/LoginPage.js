import React, { useState } from "react";
import axios from "axios";
import { useDispatch } from "react-redux";
import { styled } from "styled-components";
import GoogleLoginBtn from "../components/features/GoogleLoginBtn";
import { loginSuccess, loginFailure } from "../redux/actions/loginAction";



const FormContainer = styled.div`

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


  return (<div>LoginPage
    <GoogleLoginBtn />
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
  </div>)
}

export default LoginPage;
