import { createApp } from 'vue'
import './style.css'
import App from './App.vue'

import * as VueRouter from "vue-router";
import routes from "./routes";

import { createPinia } from "pinia";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
// 路由
const router = VueRouter.createRouter({
    history: VueRouter.createWebHashHistory(),
    routes,
  });
const app = createApp(App);

  app.use(router);

// 状态管理
const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);
app.use(pinia);


app.mount('#app');
