import { LOGIN_SUCCESS, LOGIN_FAILURE, EMAIL_MISMATCH_ERROR, PASSWORD_MISMATCH_ERROR, LOGIN_USER, SIGN_UP_SUCCESS, SIGN_UP_FAILURE } from '../actions/loginAction';

const initialState = {
    token: null,
    error: null,
  };

  const loginReducer = (state = initialState, action) => {
    switch (action.type) {
      case LOGIN_SUCCESS:
        return {
          ...state,
          token: action.payload,
          error: null,
        };

      case LOGIN_FAILURE:
        return {
          ...state,
          token: null,
          error: action.payload,
        };

      case EMAIL_MISMATCH_ERROR:
        return {
          ...state,
          token: null,
          error: action.payload,
        };

      case PASSWORD_MISMATCH_ERROR:
        return {
          ...state,
          token: null,
          error: action.payload,
      };

      case LOGIN_USER:
        return {
          ...state,
          loginSuccess: action.payload.loginSuccess
        }

      case SIGN_UP_SUCCESS:
        return {
          ...state,
          error: null,
        };

      case SIGN_UP_FAILURE:
        return {
          ...state,
          error: action.error,
        };

      default:
        return state;
    }
  };

  export default loginReducer;