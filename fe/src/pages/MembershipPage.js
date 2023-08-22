import { styled } from "styled-components";
import MsFirstIcon from "../assets/icons/MsFirstIcon";
import MsSecondIcon from "../assets/icons/MsSecondIcon";
import MsThirdIcon from "../assets/icons/MsThirdIcon";
import MsFourthIcon from "../assets/icons/MsFourthIcon";
import { useNavigate } from "react-router";
import { useDispatch } from "react-redux";
import { useState } from "react";
import axios from "axios";
import { signUpFailure, signUpSuccess } from "../redux/actions/loginAction";



function MembershipPage (props) {

  const navigate = useNavigate();
  const dispatch = useDispatch();

  const [ displayName, setDisplayName ] = useState('');
  const [ email, setEmail ] = useState('');
  const [ password, setPassword ] = useState('');
  const [ emailError, setEmailError ] = useState(false);
  const [ passwordError, setPasswordError ] = useState(false);
  const [ isUserExist, setIsUserExist ] = useState(false);

  const onDisplayNameHandler = (e) => {
    setDisplayName(e.currentTarget.value);
  };

  const onEmailHandler = (e) => {
    setEmail(e.currentTarget.value);
  };

  const onPasswordHandler = (e) => {
    setPassword(e.currentTarget.value);
  };


  const validateEmail = (email) => {
    const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}$/;
    return emailRegex.test(email);
  };

  const validatePassword = (password) => {
    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/;
    return passwordRegex.test(password);
  };

  const onSignUpHandler = async (e) => {
    // 페이지 리로드 방지
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

    try {
      const response = await axios.post("http://ec2-3-36-128-133.ap-northeast-2.compute.amazonaws.com/v1/accounts/signup", { displayName, email, password })
      console.log(response)
      console.log({ displayName: displayName, email: email, password: password});

      if (response.status === 201) {
        dispatch(signUpSuccess("회원가입 성공"));
        navigate("/login");
      } else if (response.status === 409) {
        dispatch(signUpFailure("회원가입 실패"));
      }
    } catch (error) {
      console.error("Error during login:", error);
      setIsUserExist("User already exist.");
      dispatch(signUpFailure());
    }

  };

  return (
    <MembershipPageContainer>
      <MembershipContentContainer>
        <MsTextContentParts>
          <MsTextTitle>
            <h1>Join the Stack Overflow community</h1>
          </MsTextTitle>
          <MsTextContentbox>
            <div><MsFirstIcon /></div>
            <div>Get unstuck — ask a question</div>
          </MsTextContentbox>
          <MsTextContentbox>
            <div><MsSecondIcon /></div>
            <div>Unlock new privileges like voting and commenting</div>
          </MsTextContentbox>
          <MsTextContentbox>
            <div><MsThirdIcon /></div>
            <div>Save your favorite questions, answers, watch tags, and more</div>
          </MsTextContentbox>
          <MsTextContentbox>
            <div><MsFourthIcon /></div>
            <div>Earn reputation and badges</div>
          </MsTextContentbox>
          <MsTextContentbox className="ms-textbox-below">
            <div>
              Collaborate and share knowledge with a private group for FREE.
              <a href="#none"> Get Stack Overflow for Teams free for up to 50 users.</a>
            </div>
          </MsTextContentbox>
        </MsTextContentParts>
        <MsSignupContentParts>
          <MsBtnContainer>
            <GoogleSignUpBtn>
            <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/5/53/Google_%22G%22_Logo.svg/1024px-Google_%22G%22_Logo.svg.png" alt="" />
            Sign up with Google</GoogleSignUpBtn>
          </MsBtnContainer>
          <SignUpFormContainer>
            <SignUpForm>
              <SignUpInputForm>
                <MsLabel>Display name</MsLabel>
                <SignUpInput className="signup-displyname-input"
                  type="text"
                  value={displayName}
                  onChange={onDisplayNameHandler} />
              </SignUpInputForm>
              <SignUpInputForm>
                <MsLabel>Email</MsLabel>
                <SignUpInput
                  className={`signup-email-input ${emailError || isUserExist ? "error" : ""}`}
                  type="email"
                  value={email}
                  onChange={onEmailHandler} />
                  {emailError && !isUserExist &&
                    <ErrorText>{emailError}</ErrorText>}
                  {isUserExist &&
                  <ErrorText>{isUserExist}</ErrorText>}
              </SignUpInputForm>
              <SignUpInputForm className="signup-password-form">
                <MsLabel>Password</MsLabel>
                <SignUpInput
                  className={`signup-password-input ${passwordError ? "error" : ""}`}
                  type="password"
                  value={password}
                  onChange={onPasswordHandler}/>
                  {passwordError &&
                  <ErrorText>{passwordError}</ErrorText>}
              </SignUpInputForm>
              <MsCreatePwRuleTextBox>
                <p>Passwords must contain at least six characters, including at least 1 letter and 1 number.</p>
              </MsCreatePwRuleTextBox>
              <MsBtnContainer>
                <SignUpBtn
                  onClick={onSignUpHandler}>Sign up</SignUpBtn>
              </MsBtnContainer>
              <MsTermsTextBox>
                By clicking “Sign up”, you agree to our
                <a href="#none"> terms of service </a>
                and acknowledge that you have read and understand our <a href="#none">privacy policy </a>
                and
                <a href="#none"> code of conduct.</a>
              </MsTermsTextBox>
            </SignUpForm>
          </SignUpFormContainer>
          <SignUpTextBelowContainer>
            Already have an account?
            <a href="/login">Log in</a>
          </SignUpTextBelowContainer>
        </MsSignupContentParts>
      </MembershipContentContainer>
    </MembershipPageContainer>
  );
}

export default MembershipPage;

const MembershipPageContainer = styled.section`
  width: 100%;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgb(241, 242, 243);
`;

const MembershipContentContainer = styled.div`
  display: flex;
  align-items: center;
  width: 785px;
  max-width: 1264px;
`

const MsTextContentParts = styled.div`
  display: flex;
  flex-direction: column;
  gap: 18px;
  width: 100%;
  padding-left: 20px;
`

const MsTextTitle = styled.div`

  > h1 {
    font-size: 27px;
    white-space: nowrap;
    line-height: 1;
    font-weight: 400;

  }
`

const MsTextContentbox = styled.div`
  display: flex;
  text-align: left;
  align-items: center;
  gap: 6px;
  font-size: 15px;
  white-space: normal;


  &.ms-textbox-below {

    > div {
      display: flex;
      flex-direction: column;
      font-size: 13px;
      color: hsl(210,8%,45%);

      > a {
      color: rgb(0, 116, 204);
      text-decoration: none;

      &:hover {
        color: hsl(206,100%,52%);
      }
    }
    }
  }
`

const MsSignupContentParts = styled.div`
`

const MsBtnContainer = styled.div`
  width: 100%;
`

const GoogleSignUpBtn = styled.button`
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
`

const SignUpFormContainer = styled.div`
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
`

const SignUpForm = styled.form`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
`

const MsLabel = styled.label`
  font-size: 15px;
  font-family: inherit;
  font-weight: 600;
`

const SignUpInput = styled.input`
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
`

const SignUpInputForm = styled.div`
  width: 100%;
  margin-bottom: 18px;

  > div {
    margin-bottom: 4px;
  }

  &.signup-password-form {
    margin-bottom: 6px;
  }
`

const SignUpBtn = styled.button`
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
`

const MsCreatePwRuleTextBox = styled.div`
  width: 100%;
  font-size: 12px;
  color: hsl(210,8%,45%);
  margin-bottom: 14px;
`

const MsTermsTextBox = styled.div`
  width: 100%;
  font-size: 12px;
  color: hsl(210,8%,45%);
  margin-top: 28px;

  > a {
    color: rgb(0, 116, 204);
    text-decoration: none;

    &:hover {
      color: hsl(206,100%,52%);
    }
  }
`

const SignUpTextBelowContainer = styled.div`
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