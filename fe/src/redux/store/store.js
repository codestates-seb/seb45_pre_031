import { createStore, combineReducers } from 'redux';
import loginReducer from '../reducers/loginReducer';

// 여러 개의 리듀서를 합치기 위한 combineReducers 사용
const rootReducer = combineReducers({
  login: loginReducer,
});

// 스토어 생성
const store = createStore(rootReducer);

export default store;