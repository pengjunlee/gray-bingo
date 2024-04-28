import { defineStore } from "pinia";
import { NOT_LOGIN } from "./userConstant";
import UserType = User.UserType;

/**
 * 用户系统
 */
export const useUserStore = defineStore("user", {
  state: () => ({
    loginUser: {
      ...NOT_LOGIN,
    },
  }),
  getters: {},
  // 持久化
  persist: {
    key: "user-store",
    storage: window.localStorage,
    beforeRestore: (context) => {
      console.log("before user-store restore!");
    },
    afterRestore: (context) => {
      console.log("after user-store restore!");
    },
  },
  actions: {
    setDefaultUser() {
      this.loginUser = NOT_LOGIN;
    },
    setLoginUser(user: UserType) {
      this.loginUser = user;
    },
  },
});
