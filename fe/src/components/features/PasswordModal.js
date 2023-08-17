import React from "react";
import { styled } from "styled-components";

function PasswordModal ({ closeModal }) {

    return (
    <ModalContainer onClick={closeModal}>
      <ModalContent>
        <ModalTextContainer>
          <p>Forgot your account’s password? Enter your email address and we’ll send you a recovery link.</p>
        </ModalTextContainer>
        <ModalInputContainer>
          <ModalLabelContainer>
            <label>Email</label>
          </ModalLabelContainer>
          <ModalPasswordInput />
        </ModalInputContainer>
        <ModalBtnContainer>
          <ModalPasswordBtn>Send recovery email</ModalPasswordBtn>
        </ModalBtnContainer>
      </ModalContent>
    </ModalContainer>
  )
};

const ModalContainer = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  box-shadow: 0 10px 24px hsla(0,0%,0%,0.05), 0 20px 48px hsla(0, 0%, 0%, 0.05), 0 1px 4px hsla(0, 0%, 0%, 0.1);
  padding: 24px;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 24px;
  background: rgba(0, 0, 0, 0.40);
  border-radius: 6px;
`;

const ModalContent = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: white;
  padding: 24px;
  border-radius: 6px;
  width: 340px;
  gap: 12px;
`;

const ModalTextContainer = styled.div`
  display: flex;
  flex-direction: column;
  font-size: 13px;
  width: 100%;
`

const ModalInputContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  gap: 4px;
`
const ModalLabelContainer = styled.div`
  > label {
    font-size: 15px;
    font-family: inherit;
    font-weight: 600;
  }
`

const ModalPasswordInput = styled.input`
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

const ModalBtnContainer = styled.div`
  width: 100%;
`

const ModalPasswordBtn = styled.button`
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

export default PasswordModal;
