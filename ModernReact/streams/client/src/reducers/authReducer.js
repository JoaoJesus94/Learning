import { SIGN_IN, SIGN_OUT } from "../actions/types";

const INITIAL_STATE = {
  isSignedIn: null,
  userUd: null
};

export default (state = INITIAL_STATE, action) => {
  switch (action.type) {
    case SIGN_IN:
      return { ...state, isSignedIn: true, userUd: action.payload };
    case SIGN_OUT:
      return { ...state, isSignedIn: false, userUd: null };
    default:
      return state;
  }
};
