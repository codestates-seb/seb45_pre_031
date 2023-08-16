export const LOGIN_SUCCESS = 'LOGIN_SUCCESS';
export const LOGIN_FAILURE = 'LOGIN_FAILURE';

export const loginSuccess = (token) => ({
    type: LOGIN_SUCCESS,
    payload: token,
});

export const loginFailure = (error) => ({
    type: LOGIN_FAILURE,
    payload: error,
});