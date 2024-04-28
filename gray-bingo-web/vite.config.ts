import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
// @ts-ignore
import Components from "unplugin-vue-components/vite";
// @ts-ignore
import { AntDesignVueResolver } from "unplugin-vue-components/resolvers";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    // 按需加载 ant-design-vue
    Components({
      resolvers: [AntDesignVueResolver()],
    })
  ].filter(Boolean),
  base: '/bingo/console/', // 这里设置你的资源前缀
  build: {
    assetsDir: 'assets', // 指定生成静态文件目录
    outDir:'../gray-bingo-starter/src/main/resources/static'
  },
});
