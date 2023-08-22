export const LOGIN_SUCCESS = 'LOGIN_SUCCESS';
export const LOGIN_FAILURE = 'LOGIN_FAILURE';

export const EMAIL_MISMATCH_ERROR = 'EMAIL_MISMATCH_ERROR';
export const PASSWORD_MISMATCH_ERROR = 'PASSWORD_MISMATCH_ERROR';

export const LOGIN_USER = 'LOGIN_USER';

export const SIGN_UP_SUCCESS = 'SIGN_UP_SUCCESS';
export const SIGN_UP_FAILURE = 'SIGN_UP_FAILURE';

export const LOGOUT = 'LOGOUT';



export const loginSuccess = ({ accessToken, displayName }) => ({
    type: LOGIN_SUCCESS,
    payload: { accessToken, displayName }
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

export const logoutAction = () => {
    return {
        type: LOGOUT,
    }
};

export const signUpSuccess = () => ({
    type: SIGN_UP_SUCCESS,
  });

  export const signUpFailure = (error) => ({
    type: SIGN_UP_FAILURE,
    payload: error,
  });
