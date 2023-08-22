import { LOGIN_SUCCESS, LOGIN_FAILURE, EMAIL_MISMATCH_ERROR, PASSWORD_MISMATCH_ERROR, SIGN_UP_SUCCESS, SIGN_UP_FAILURE, LOGOUT } from '../actions/loginAction';

const initialState = {
    isLoggedIn: false,
    accessToken: null,
    error: null,
    displayName: null,
  };

  const loginReducer = (state = initialState, action) => {
    switch (action.type) {
      case LOGIN_SUCCESS:
        return {
          ...state,
          isLoggedIn: true,
          accessToken: action.payload.accessToken,
          error: null,
          displayName: action.payload.displayName,
        };

      case LOGIN_FAILURE:
        return {
          ...state,
          isLoggedIn: false,
          accessToken: null,
          error: null,
          displayName: null,
        };

      case EMAIL_MISMATCH_ERROR:
        return {
          ...state,
          isLoggedIn: false,
          accessToken: null,
          error: null,
          displayName: null,
        };

      case PASSWORD_MISMATCH_ERROR:
        return {
          ...state,
          isLoggedIn: false,
          accessToken: null,
          error: null,
          displayName: null,
      };

      case LOGOUT:
        return {
          ...state,
          isLoggedIn: false,
          accessToken: null,
          error: null,
          displayName: null,
        };

      case SIGN_UP_SUCCESS:
        return {
          ...state,
          isLoggedIn: false,
          accessToken: null,
          error: null,
          displayName: null,
        };

      case SIGN_UP_FAILURE:
        return {
          ...state,
          isLoggedIn: false,
          accessToken: null,
          error: null,
          displayName: null,
        };

      default:
        return state;
    }
  };

  export default loginReducer;