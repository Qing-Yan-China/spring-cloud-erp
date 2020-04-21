import axios from 'axios'

import router from '@/js/router.js'
import common from '@/js/common/common.js'
import elementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import qs from 'qs'

import store from '@/js/vuex/store.js'

let loading;

//开始动画
function startLoading(){
	loading=elementUI.Loading.service({
		lock:true,
		text: '拼命加载中...',
		background: 'rgba(0,0,0,0.7)'
	})
	
}
//结束动画
function endLoading(){
    console.log('结束请求')
    loading.close()
}
// 默认超时时间
axios.defaults.timeout = 15000

//http request 拦截器
axios.interceptors.request.use((config) => {
	//config.headers = {}
	startLoading();
	console.log('http request 拦截器')
	return config
}, (error) => {
	return Promise.reject(error)
})

//http response 拦截器
axios.interceptors.response.use((response) => {
	endLoading();
	let responseData = response.data
	console.log('http response 拦截器')
	if (responseData.statusCord == 302) {
		//没有登录
		router.push({
			path:store.getters.getLoginUrl
		})
	}
	if(responseData.statusCord == 301) {
		
	}
	return responseData
}, (error) => {
	endLoading();
	//let message = error.response.data;
	//return Promise.reject(message || error)
	return Promise.reject(error)
})

export default axios

export function fetch(url, params = {}) {
	return new Promise((resolve,reject)=>{
		axios.get(url, {params:params})
			.then((response) => {
				console.log(response)
				resolve(response);
			}).catch(error => {
				reject(error)
			})
	}) 
}
function formData(data){
	let params=new URLSearchParams();
	for(let lt in data){
		params.append(lt,data[lt]);
	}
	return params;
}
// export function post(
// 			url,
// 			data
// 		){
// 			return new Promise((resolve,reject)=>{
// 				let params = qs.stringify(data);
// 				axios.post(url,params)
// 					.then(response=>{
// 						resolve(response)
// 					}).catch(error=>{
// 						reject(error)
// 					})
// 			})
// 		}
export function post(
			url,
			data ={},
			config={
				transformRequest:[function(fData,headers){
					headers['Content-Type'] = 'application/json'
					return JSON.stringify(fData)
				}]
			}
		){
			return new Promise((resolve,reject)=>{
				axios.post(url,data,config)
					.then(response=>{
						resolve(response)
					}).catch(error=>{
						reject(error)
					})
			})
		}