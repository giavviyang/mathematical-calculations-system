import { constantRoutes } from '@/router'
import { getRouters } from '@/api/menu'
import Layout from '@/layout/index'
import vi from "element-ui/src/locale/lang/vi";

const permission = {
  state: {
    routes: [],
    addRoutes: []
  },
  mutations: {
    SET_ROUTES: (state, routes) => {
      state.addRoutes = routes
      state.routes = constantRoutes.concat(routes)
    }
  },
  actions: {
    // 生成路由
    GenerateRoutes({ commit }) {
      return new Promise(resolve => {
        // 向后端请求路由数据
        getRouters().then(res => {
          const accessedRoutes = filterAsyncRouter(res.data);
          //console.log(accessedRoutes);
          commit('SET_ROUTES', accessedRoutes);
          accessedRoutes.push({ path: '*', redirect: '/404', hidden: true });
          resolve(accessedRoutes)
        })
      })
    }
  }
}

// 遍历后台传来的路由字符串，转换为组件对象
function filterAsyncRouter(asyncRouterMap) {
  asyncRouterMap.map(item=>{
    if(item.hidden == "false"){
      item.hidden = false;
    }else if(item.hidden == "true"){
      item.hidden = true;
    }
  })
  // console.log(asyncRouterMap);
  return asyncRouterMap.filter(route => {



    // console.log(route);
    if (route.component) {
      // Layout组件特殊处理
      if (route.component === 'Layout') {
        route.component = Layout
      } else {

        route.component = loadView(route.component);
        // console.log(route.component);

      }
    }
    if (route.children != null && route.children && route.children.length) {
      route.children = filterAsyncRouter(route.children);
      // console.log(route.children);
    }
    return true
  })
}

/*export const loadView = (view) => { // 路由懒加载
  // console.log(view);
  return () => import(`@/views/${view}`)
};*/
export const loadView = (view) => {
  return (resolve) => require([`@/views/${view}`], resolve)
};

export default permission
