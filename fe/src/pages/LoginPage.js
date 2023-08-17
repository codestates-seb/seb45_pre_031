import React, { useState } from "react";
import { useNavigate } from "react-router";
import { styled } from "styled-components";
import { useDispatch } from "react-redux";
import axios from "axios";
import { loginSuccess, loginFailure, emailMismatchError, passwordMismatchError } from "../redux/actions/loginAction";
import PasswordModal from "../components/features/PasswordModal";

function LoginPage () {

  const navigate = useNavigate();
  const dispatch = useDispatch();

  const [ email, setEmail ] = useState("");
  const [ password, setPassword ] = useState("");
  const [ emailError, setEmailError ] = useState(false);
  const [ passwordError, setPasswordError ] = useState(false);
  const [ emailMismatchErrorText, setEmailMismatchErrorText] = useState("");
  const [ passwordMismatchErrorText, setPasswordMismatchErrorText] = useState("");
  const [ isModalOpen, setIsModalOpen ] = useState(false);

  const modalHandler = () => {
    setIsModalOpen(!isModalOpen);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  }


  const onEmailHandler = (e) => {
    setEmail(e.currentTarget.value);
    if (emailError) {
      setEmailError(false); // 값 입력 시 에러 스타일 clear
    }
  }

  const onPasswordHandler = (e) => {
    setPassword(e.currentTarget.value);
    if (passwordError) {
      setPasswordError(false); // 값 입력 시 에러 스타일 clear
    }
  }

  const validateEmail = (email) => {
    const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}$/;
    return emailRegex.test(email);
  }

  const validatePassword = (password) => {
    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/;
    return passwordRegex.test(password);
  }

  const loginHanler = async (e) => {
    // page refresh 방지
    e.preventDefault();

    // 이메일 input 값이 비어있거나 형식에 맞지 않게 입력 받을 경우 error 메시지 표출
    if (email === "") {
      setEmailError("Email cannot be empty.");
    } else if (!validateEmail(email)) {
      setEmailError("The email is not a valid email address.");
    }

    // 비밀번호 input 값이 비어있거나 형식에 맞지 않게 입력 받을 경우 error 메시지 표출
    if (password === "") {
      setPasswordError("Password cannot be empty.");
    } else if (!validatePassword(password)) {
      setPasswordError("The password is not a valid password.");
    }

    // 유효한 이메일과 비밀번호를 입력할 경우 서버로 전송
    try {
      const response = await axios.post("/v1/accounts/authenticate", { email, password });
      if (response.data.success) {
        // 서버에서 토큰을 받음
        const accessToken = response.data.accessToken;
        const refreshToken = response.data.refreshToken;

        // 토큰을 로컬 스토리지에 저장
        localStorage.setItem("access_token", accessToken);
        localStorage.setItem("refresh_token", refreshToken);

        // 토큰을 헤더에 포함시켜서 요청
        axios.defaults.headers.common["Authorization"] = `Bearer ${response.data.accessToken}`;

         // 로그인 성공 처리
         dispatch(loginSuccess(accessToken));

         // 로그인 성공 후 리다이렉션 처리
         navigate("/");

      } else if (response.data.errorType === "email_not_found") {
        dispatch(emailMismatchError("No user found with matching email."));
        setPasswordMismatchErrorText("");
      } else if (response.data.errorType === "password_mismatch") {
        dispatch(passwordMismatchError("Password dose not match."));
        setEmailMismatchErrorText("");
      } else {
        dispatch(loginFailure(response.data.message));
      }
    } catch (error) {
      console.error("Error during login:", error);
    }

  };

  return (
    <LoginPageContainer>
      <LoginContentContainet>
        <LoginLogoContiner>
          <img src="https://media.discordapp.net/attachments/1138344984454631504/1138711197278015569/image.png?width=612&height=708" alt="" />
        </LoginLogoContiner>
        <LoginBtnContainer>
          <GoogleLoginBtn>
            <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/53/Google_%22G%22_Logo.svg/1024px-Google_%22G%22_Logo.svg.png" alt="" />
            Log in with Google</GoogleLoginBtn>
        </LoginBtnContainer>
        <LoginFormContainer>
          <LoginForm>
            <LoginInputForm className="login-email-form">
              <div>
                <LoginLabel>Email</LoginLabel>
              </div>
              <LoginInput
                className={`login-email-input ${emailError || emailMismatchErrorText ? "error" : ""}`}
                type="email"
                value={email}
                onChange={onEmailHandler}
                />
                {emailError &&
                  <ErrorText>{emailError}</ErrorText>}
                {emailMismatchErrorText &&
                  <ErrorText>{emailMismatchErrorText}</ErrorText>}
            </LoginInputForm>
            <LoginInputForm className="login-password-form">
              <div>
                <LoginLabel>Password</LoginLabel>
                <p onClick={modalHandler}>Forgot password?</p>
                {isModalOpen ?
                  <PasswordModal
                    isModalOpen={isModalOpen}
                    closeModal={closeModal}
                    /> : null}
              </div>
              <LoginInput
                className={`login-password-input ${passwordError || passwordMismatchErrorText ? "error" : ""}`}
                type="password"
                value={password}
                onChange={onPasswordHandler}
                />
                {passwordError &&
                  <ErrorText>{passwordError}</ErrorText>}
                {passwordMismatchErrorText &&
                  <ErrorText>{passwordMismatchErrorText}</ErrorText>}
            </LoginInputForm>
            <LoginBtnContainer>
              <LoginBtn
                onClick={loginHanler}>Log in</LoginBtn>
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

  > div {
    margin-bottom: 4px;
  }

  &.login-password-form {

    > div {
      display: flex;
      align-items: center;
      justify-content: space-between;

      > p {
        font-size: 12px;
        color: rgb(0, 116, 204);
        cursor: pointer;
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

  &.error {
    border: 1px solid hsl(358,68%,59%);

    &:focus {
      box-shadow: 0 0 0 4px hsla(358,62%,47%,0.15);
    }
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

const ErrorText = styled.p`
  font-size: 12px;
  color: hsl(358, 68%, 59%);
  margin-top: 4px;
`;

export default LoginPage;