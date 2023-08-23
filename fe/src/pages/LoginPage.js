import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router";
import { styled } from "styled-components";
import { useDispatch } from "react-redux";
import axios from "axios";
import { loginSuccess, loginFailure } from "../redux/actions/loginAction";
import PasswordModal from "../components/features/PasswordModal";

function LoginPage () {

  const navigate = useNavigate();
  const dispatch = useDispatch();

  const [ email, setEmail ] = useState("");
  const [ password, setPassword ] = useState("");
  const [ emailError, setEmailError ] = useState(false);
  const [ passwordError, setPasswordError ] = useState(false);
  const [ isModalOpen, setIsModalOpen ] = useState(false);

  const [ googleLoginError, setGoogleLoginError ] = useState(false);

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
      const response = await axios.post("http://ec2-3-36-128-133.ap-northeast-2.compute.amazonaws.com/v1/accounts/authenticate", { email, password });


      if (response.status === 200) {
        // 서버에서 토큰과 유저 이름을 받음
        const accessToken = response.headers.getAuthorization();
        const refreshToken = response.headers.get("Refresh");
        const displayName = response.data.DisplayName;

        // 토큰과 유저이름을 로컬 스토리지에 저장
        localStorage.setItem("access_token", accessToken);
        localStorage.setItem("refresh_token", refreshToken);
        localStorage.setItem("display_name", displayName);

        // 쿠키 설정
        document.cookie = `access_token=${accessToken}; path=/;`;
        document.cookie = `display_name=${displayName}; path=/;`;

        // 토큰을 헤더에 포함시켜서 요청
        axios.defaults.headers.common["Authorization"] = `${accessToken}`;

         // 로그인 성공 처리
        dispatch(loginSuccess({ accessToken, displayName }));

         // 로그인 성공 후 리다이렉션 처리
        navigate("/");



      }
    } catch (error) {
      console.error("Error during login:", error);
    }
  };

  const googleLoginHandler = async () => {
    try {
      window.location.href = "http://ec2-3-36-128-133.ap-northeast-2.compute.amazonaws.com/oauth2/authorization/google";

    } catch (error) {
      console.error("Google 로그인 중 에러:", error);
      dispatch(loginFailure("Google 로그인 중 에러가 발생했습니다."));
    }
  };

  useEffect(() => {
    const urlSearchParams = new URLSearchParams(window.location.search);
    const accessToken = urlSearchParams.get("access_token");
    const refreshToken = urlSearchParams.get("refresh_token");


    if (accessToken && refreshToken) {
      try {
        // 로컬 스토리지에 토큰 저장
        localStorage.setItem("access_token", accessToken);
        localStorage.setItem("refresh_token", refreshToken);


        // 토큰을 헤더에 포함시켜서 요청
        axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;

        // displayName 불러오기
        axios.get("http://ec2-3-36-128-133.ap-northeast-2.compute.amazonaws.com/v1/auth/oauth")
          .then(response => {
            console.log(response);
            const displayName = response.data.displayName;

            console.log(displayName);

            // 로그인 성공 처리
            dispatch(loginSuccess({ accessToken, displayName }));
          })

        // 로그인 성공 후 리다이렉션 처리
       navigate("/");

      } catch (error) {
        console.error("로그인 에러:", error);
        // 로그인 에러 처리
        dispatch(loginFailure("로그인 중 에러가 발생했습니다."));
        // 오류 메시지 표출
        setGoogleLoginError(true);
        // 로그인 페이지로 리다이렉션
       navigate("/login");
      }
    }
  }, [dispatch, navigate]);

  return (
    <LoginPageContainer>
      <LoginContentContainet>
        <LoginLogoContiner>
          <img src="https://media.discordapp.net/attachments/1138344984454631504/1138711197278015569/image.png?width=612&height=708" alt="" />
        </LoginLogoContiner>
        <LoginBtnContainer>
          <GoogleLoginBtn
            onClick={googleLoginHandler}
            >
            <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/53/Google_%22G%22_Logo.svg/1024px-Google_%22G%22_Logo.svg.png" alt="" />
            Log in with Google</GoogleLoginBtn>
            {googleLoginError && <p>Google 로그인 오류가 발생했습니다.</p>}
        </LoginBtnContainer>
        <LoginFormContainer>
          <LoginForm>
            <LoginInputForm className="login-email-form">
              <div>
                <LoginLabel>Email</LoginLabel>
              </div>
              <LoginInput
                className={`login-email-input ${emailError? "error" : ""}`}
                type="email"
                value={email}
                onChange={onEmailHandler}
                />
                {emailError &&
                  <ErrorText>{emailError}</ErrorText>}
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
                className={`login-password-input ${passwordError ? "error" : ""}`}
                type="password"
                value={password}
                onChange={onPasswordHandler}
                />
                {passwordError &&
                  <ErrorText>{passwordError}</ErrorText>}
            </LoginInputForm>
            <LoginBtnContainer>
              <LoginBtn
                onClick={loginHanler}>Log in</LoginBtn>
            </LoginBtnContainer>
          </LoginForm>
        </LoginFormContainer>
        <LoginTextBelowContainer>
            Don't have an accout?
            <a href="/membership">Sign up</a>
        </LoginTextBelowContainer>
      </LoginContentContainet>
    </LoginPageContainer>
  );
};

export default LoginPage;

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
    text-decoration: none;

    &:hover {
      color: hsl(206,100%,52%);
    }
  }
`;

const ErrorText = styled.p`
  font-size: 12px;
  color: hsl(358, 68%, 59%);
  margin-top: 4px;
`;

