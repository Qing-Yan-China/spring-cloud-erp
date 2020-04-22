
/*
导入基本依赖
*/
import Vue from 'vue'//引入vue
import VueRouter from 'vue-router'
import axios,{fetch,post} from '@/js/axios.js'
import Vuex from 'vuex'
import elementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
//
/*引入自定义的路由和vuex的js对象*/
import store from '@/js/vuex/store.js'
import router,{error_router} from '@/js/router.js'
//
/*
公共的js
*/
import common from '@/js/common/common.js'
import socket from '@/js/common/webSocket.js'
//引入格式化数据工具
import qs from 'qs'
//vue注入全局对象
Vue.prototype.$Qs=qs
Vue.prototype.$axios=axios
Vue.prototype.$fetch=fetch
Vue.prototype.$post=post
Vue.prototype.$common=common
Vue.prototype.$error_router=error_router
Vue.use(elementUI)
Vue.use(Vuex)
Vue.use(VueRouter)
Vue.prototype.$socket=socket
//

import app from '@/app.vue'
const vm=new Vue({
	el:"#app",
	data(){
		return{
			message:"123"
		}
	},
	router:router,//注入路由对象 是在该组件的所有子组件都会得到该实例
	store:store,//注入store对象
	render(createElement){
		return createElement(app)
	}
})
