import { RouteRecordRaw } from "vue-router";
import IndexPage from "./pages/Index.vue";

const routes: RouteRecordRaw[] = [
  { path: "/", component: IndexPage },
];

export default routes;
