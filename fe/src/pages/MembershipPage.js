import { styled } from "styled-components";

const MsContainer = styled.div`
  background-color: hsl(210,8%,95%);
  display: flex;
  justify-content: center;
  max-width: 100%;
`

const MsContentContainer = styled.div`
  width: 100%;
  max-width: 1264px;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
`

const MsContentPartsContainer = styled.div`
  display: flex;
  align-items: center;
  font-size: 15px;
`

const JoinInfoBox = styled.div`
  font-size: 1.1rem;
  display: block;
  margin-bottom: 128px;
  margin-right: 48px;

  > h1 {
    line-height: 1;
    font-size: 2rem;
    margin-bottom: 32px;
    margin: 0 0 1em;
  }

`

const JoinInfoElement = styled.div`
`

const SignupContentContainer = styled.div``

const SocialSignUpContainer = styled.div`
`

const SignUpFormContainer = styled.div`
  background-color: white;
  border-radius: 6px;
  padding: 8px;
`

const SignUpForm = styled.form`
`

const SignupInputContainer = styled.div`
`

function MembershipPage() {
  return (
    <MsContainer>
      <MsContentContainer>
        <MsContentPartsContainer>
          <JoinInfoBox>
            <h1>Join the Stack Overflow community</h1>
            <JoinInfoElement>
              <div>{/* 로고 */}</div>
              <div>Get unstuck — ask a question</div>
            </JoinInfoElement>
            <JoinInfoElement>
              <div>{/* 로고 */}</div>
              <div>Unlock new privileges like voting and commenting</div>
            </JoinInfoElement>
            <JoinInfoElement>
              <div>{/* 로고 */}</div>
              <div>Save your favorite questions, answers, watch tags, and more</div>
            </JoinInfoElement>
            <JoinInfoElement>
              <div>{/* 로고 */}</div>
              <div>Earn reputation and badges</div>
            </JoinInfoElement>
            <JoinInfoElement>
            <p>Collaborate and share knowledge with a private group for FREE.</p>
            <a href="">Get Stack Overflow for Teams free for up to 50 users.</a>
            </JoinInfoElement>
          </JoinInfoBox>
          <SignupContentContainer>
            <SocialSignUpContainer>

            </SocialSignUpContainer>
            <SignUpFormContainer>
              <SignUpForm>
                <SignupInputContainer>
                  <label>Display name</label>
                  <input></input>
                </SignupInputContainer>
                <SignupInputContainer>
                  <label>Display name</label>
                  <input></input>
                </SignupInputContainer>
                <SignupInputContainer>
                  <label>Display name</label>
                  <input></input>
                </SignupInputContainer>
                <button>Sign up</button>
              </SignUpForm>
            </SignUpFormContainer>
            <div>
              <p>Already have an account?</p>
              <a href="/login">Log in</a>
            </div>
          </SignupContentContainer>
        </MsContentPartsContainer>
      </MsContentContainer>
    </MsContainer>
  );
}

export default MembershipPage;
