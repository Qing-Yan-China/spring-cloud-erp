import Vuex from 'vuex'
import Vue from 'vue'
import axios from 'axios'
import {
	permission_router
} from '@/js/router.js'
Vue.use(Vuex)
// const store=Vuex.Store({
// 	mutations:{

// 	},actions:
// })
const permission = {
	state: {
		loginUrl: '/login.html',
		indexUrl: '/index',
		activeUser: {},
		treeMenu: [],
		dynamicRouters: [],
	},
	getters: {
		getLoginUrl(state, getters) {
			return state.loginUrl
		},
		getIndexUrl(state, getters) {
			return state.indexUrl
		},
		getActiveUser(state, getters) {
			return state.activeUser
		},
		isLogin(state, getters) {
			return Object.keys(state.activeUser).length > 0
		},
		getTreeMenu(state, getters) {
			return state.treeMenu
		},getDynamicRouters(state,getters){
			return state.dynamicRouters
		}
	},
	mutations: {
		updateActiveUser(state, payload) {
			let objName = payload.obj
			let propertyName = payload.property
			let value = payload.value
			console.log(objName)
			console.log(propertyName)
			console.log(value)
			let obj = state.activeUser[objName];
			obj[propertyName] = value
		},
		setActiveUser(state, payload) {
			let activeUser = payload.activeUser;
			if (activeUser == null || activeUser == '') {
				activeUser = {};
			}
			state.activeUser = activeUser;
		},
		setTreeMenu(state, payload) {
			let treeMenu = payload.treeMenu
			if (treeMenu == null || treeMenu == '') {
				treeMenu = [];
			}
			state.treeMenu = treeMenu;
		},
		generateRouters(state, payload) {
			//console.log('permission_router:' + permission_router)
			//state.dynamicRouters = permission_router
			let user = state.activeUser.user;
			let treeMenu = state.treeMenu;
			let dynamicRouters = [];
			treeMenu.forEach((treeMenu) => {
				this.commit({
					type: 'setRouter',
					data: treeMenu,
					routers: dynamicRouters
				});
			})
			state.dynamicRouters=dynamicRouters
		},
		setRouter(state, payload) {
			let data = payload.data;
			let router;
			let routers = payload.routers;
			if (data.componentName) {
				router = {
					path: '',
					name: '',
					component: {},
					children: []
				};
				router.path = data.url
				router.name = data.pname
				let componentName = data.componentName;
				componentName = componentName.replace(/\s/g, "");
				if (componentName.charAt(0) === '/') {
					componentName = componentName.substring(1, componentName.length);
				}
				if (componentName.charAt(componentName.length - 1) === '/') {
					componentName = componentName.substring(0, componentName.length - 1);
				}
				router.component = resolve => {
					require.ensure([], (require) => {
						resolve(require('@/templates/' + componentName + '.vue'))
					})
				}
				routers.push(router)
			}
			if (data.children && data.children.length > 0) {
				let fals = false; 
				data.children.forEach((treeMenu) => {
					if(treeMenu.showMenu){
						fals=true
					}
					this.commit({
						type: 'setRouter',
						data: treeMenu,
						routers: router ? router.children : routers
					});
				})
				data.showMenu=fals;
			}
		}
	},
	actions: {
		getActiveUser(context) {
			return new Promise((resolve, reject) => {
				if (!context.getters.isLogin) { //如果activeUser中没有数据  就去获取数据
					axios.get('api/activeUser')
						.then(response => {
							context.commit({
								type: 'setActiveUser',
								activeUser: response.data
							})
							resolve(context.getters.isLogin)
						})
				} else {
					resolve(context.getters.isLogin)
				}
			})
		},
		init_user_resource(context,to_obtain) {
			let treeMenu = context.state.treeMenu;
			let  dynamicRouters = context.state.dynamicRouters;
			return new Promise((resolve, reject) => {
				if(to_obtain!=undefined&&to_obtain&&treeMenu.length > 0 &&dynamicRouters.length > 0) {
					resolve(context.state.dynamicRouters)
				}
				axios.get('api/common/getTreeMenus')
					.then(response => {
						if (response.statusCord == 200) {
							context.commit({
								type: "setTreeMenu",
								treeMenu: response.data
							})
							context.commit({
								type: 'generateRouters'
							})
							context.state.dynamicRouters.forEach((item)=>{
								console.log(JSON.stringify(item))
							})
							resolve(context.state.dynamicRouters)
						} else {
							reject()
						}
					})
			})

		}
	}
}
export default permission
