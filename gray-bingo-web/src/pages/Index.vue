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
  import { onMounted, ref ,shallowRef} from "vue";
  import { useUserStore } from "../core/commands/user/userStore";
  import { storeToRefs } from "pinia";
  import myAxios from "../plugins/myAxios";

  const terminalRef = shallowRef();
  
  const onSubmitCommand = async (inputText: string) => {
    if (!inputText) {
      return;
    }
    const terminal = terminalRef.value.terminal;
    if(loginUser.value.username == 'noLogin' && !inputText.startsWith("login")){
      terminal.writeTextErrorResult("please login!");
    }else{
      await doCommandExecute(inputText, terminal);
    }
  };
  
  const userStore = useUserStore();
  const { loginUser } = storeToRefs(userStore);
  
  onMounted(() => {
    myAxios.defaults.baseURL = window.location.origin;
    userStore.getAndSetLoginUser();
  });
  </script>
  
  <style></style>
  