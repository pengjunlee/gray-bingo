<template>
    <yu-terminal
      ref="terminalRef"
      :user="loginUser"
      full-screen
      :on-submit-command="onSubmitCommand"
    />
  </template>
  
  <script setup lang="ts">
  import { doCommandExecute } from "../core/commandExecutor";
  import { onMounted, shallowRef} from "vue";
  import { useUserStore } from "../core/commands/user/userStore";
  import { storeToRefs } from "pinia";
  import { NOT_LOGIN } from "../core/commands/user/userConstant";

  const terminalRef = shallowRef();
  
  const onSubmitCommand = async (inputText: string) => {
    if (!inputText) {
      return;
    }
    const terminal = terminalRef.value.terminal;
    if(needLogin(inputText) && loginUser.value.username == NOT_LOGIN.username){
      terminal.writeTextErrorResult("please login!");
    }else{
      await doCommandExecute(inputText, terminal);
    }
  };

  const needLogin = (inputText:string):Boolean => {
    return !inputText.startsWith("login") && !inputText.startsWith("help");
  }
  
  const userStore = useUserStore();
  const { loginUser } = storeToRefs(userStore);
  
  onMounted(() => {
    userStore.setDefaultUser();
  });
  </script>
  
  <style></style>
  