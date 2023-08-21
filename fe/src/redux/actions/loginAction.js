import axios from "axios";

export const LOGIN_SUCCESS = 'LOGIN_SUCCESS';
export const LOGIN_FAILURE = 'LOGIN_FAILURE';

export const EMAIL_MISMATCH_ERROR = 'EMAIL_MISMATCH_ERROR';
export const PASSWORD_MISMATCH_ERROR = 'PASSWORD_MISMATCH_ERROR';

export const LOGIN_USER = 'LOGIN_USER';


export const loginSuccess = (token) => ({
    type: LOGIN_SUCCESS,
    payload: token,
});

export const loginFailure = (error) => ({
    type: LOGIN_FAILURE,
    payload: error,
});

export const emailMismatchError = (error) => ({
    type: EMAIL_MISMATCH_ERROR,
    payload: error,
  });

export const passwordMismatchError = (error) => ({
type: PASSWORD_MISMATCH_ERROR,
payload: error,
});

export const loginUser = (dataToSubmit) => {
    // dataToSubmit으로 받은 이메일, 비밀번호 정보를
    const request = axios.post('http://localhost:8080/v1/accounts/authenticate', dataToSubmit) // axios로 login 요청
        .then(res => res.data)

    return {
        type: LOGIN_USER,
        payload: request,
    }; // 반환하여 request에 넣어줌
};
