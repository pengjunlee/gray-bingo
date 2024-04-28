import { CommandType } from "../../../command";
import { useUserStore } from "../userStore";
import { userLogin, userRegister } from "../userApi";
import UserType = User.UserType;

/**
 * 用户登录命令
 */
const loginCommand: CommandType = {
  func: "login",
  name: "用户登录",
  options: [
    {
      key: "username",
      desc: "用户名",
      alias: ["u"],
      type: "string",
      required: true,
    },
    {
      key: "password",
      desc: "密码",
      alias: ["p"],
      type: "string",
      required: true,
    },
  ],
  async action(options, terminal) {
    const { username, password } = options;
    if (!username) {
      terminal.writeTextErrorResult("请输入用户名");
      return;
    }
    if (!password) {
      terminal.writeTextErrorResult("请输入密码");
      return;
    }
    const res: any = await userLogin(username, password);
    const { setLoginUser } = useUserStore();
    if (res?.username) {
      setLoginUser(res.username);
      terminal.writeTextSuccessResult("登录成功");
    } else {
      terminal.writeTextErrorResult(res?.message ?? "登录失败");
    }
  },
};

export default loginCommand;
