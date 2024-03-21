import { RouteRecordRaw } from "vue-router";
import IndexPage from "./pages/Index.vue";
import { markRaw } from 'vue'


const routes: RouteRecordRaw[] = [
  { path: "/", component: markRaw(IndexPage) },
];

export default routes;
