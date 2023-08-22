import { LOGIN_SUCCESS, LOGIN_FAILURE, EMAIL_MISMATCH_ERROR, PASSWORD_MISMATCH_ERROR, LOGIN_USER, SIGN_UP_SUCCESS, SIGN_UP_FAILURE, LOGOUT } from '../actions/loginAction';

const initialState = {
    isLoggedIn: false,
    accessToken: null,
    error: null,
    DisplayName: null,
  };

  const loginReducer = (state = initialState, action) => {
    switch (action.type) {
      case LOGIN_SUCCESS:
        return {
          ...state,
          isLoggedIn: true,
          accessToken: action.payload.accessToken,
          error: null,
          DisplayName: action.payload.DisplayName,
        };

      case LOGIN_FAILURE:
        return {
          ...state,
          isLoggedIn: false,
          accessToken: null,
          error: null,
          DisplayName: null,
        };

      case EMAIL_MISMATCH_ERROR:
        return {
          ...state,
          isLoggedIn: false,
          accessToken: null,
          error: null,
          DisplayName: null,
        };

      case PASSWORD_MISMATCH_ERROR:
        return {
          ...state,
          isLoggedIn: false,
          accessToken: null,
          error: null,
          DisplayName: null,
      };

      case LOGIN_USER:
        return {
          ...state,
          loginSuccess: action.payload.loginSuccess
        }

      case LOGOUT:
        return {
          ...state,
          isLoggedIn: false,
          accessToken: null,
          error: null,
          DisplayName: null,
        };

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