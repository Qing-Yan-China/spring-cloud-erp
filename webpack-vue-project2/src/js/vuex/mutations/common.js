//import Vuex from 'vuex'
import axios from 'axios'

import pageSizesJson from '@/json/pageSizes.json'
// import Vue from 'vue'
// Vue.use(Vuex)
const common={
	state:{
		pageSizes:pageSizesJson,
		routerNames:[]
	},getters:{
		getPageSizes(state,getters){
			return state.pageSizes
		},getRouterNames(state,getters){
			return state.routerNames
		}
	},mutations: {
		setRouterNames(state,payload){
			let routerNames=payload.routerNames
			if(routerNames&&routerNames.length>0)
				state.routerNames=routerNames
		}
	},actions:{
		// searchPageSizes(context){
		// 	axios.get('src/json/pageSizes.json')
		// 		.then(response=>{
		// 			context.state.pageSizes=response;
		// 		})
		// }
	}
}
export default common