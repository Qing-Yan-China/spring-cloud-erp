import store from '@/js/vuex/store.js';
import router from '@/js/router.js'
import common from '@/js/common/common.js'
let socket = null; //连接通信实例

const lockReconnet = false; //防止重复连接

const webSocketUrl = "ws://localhost:8011/imserver/";

const global_callback = null;

//创建createSocket
let createSocket = () => {
	//try{/imserver/{userId}
	let url = webSocketUrl;
	let userId = store.getters.getActiveUser.user.uid;
	url += userId;
	console.log('url:' + url)
	if ('WebSocket' in window) {
		socket = new WebSocket(url)
	} else if ('MozWebSocket' in window) {
		socket = new MozWebSocket(url)

	}
	initSocket();
	// }catch(e){

	// }
}
let initSocket = () => {
	socket.onopen = onopen;
	socket.onmessage = onmessage;
	socket.onclose = onclose;
	socket.onerror = onerror;
}
let onopen = () => {
	console.log('连接成功!')
}
let onmessage = (responseData) => {
	console.log('获取到的服务器的数据:' + responseData.data);
	let response = JSON.parse(responseData.data);
	if (response.statusCord == 303) { //说明身份认证过期了
		let loginUrl = store.getters.getLoginUrl;
		if (router.currentRoute.path != loginUrl) { //说明身份过期而且不在登录页面
			// elementUI.message({
			// 	showClose: true,
			// 	message: '身份认证过期!即将跳入登录页面',
			// 	type: 'error',
			// 	duration: 2000
			// });
			alert('身份认证过期!即将跳入登录页面')
			//清楚sessionStorage中的数据
			common.removeSessionStorage(common.getVuex_storage_name())
			sessionStorage.clear()
			router.push({path:loginUrl})
		}
	}
}
let onclose = () => {
	console.log('调用onclose')
}
let onerror = (e) => {
	console.log('调用onerror:' + e);
}
let onMessageCallBack = () => {

}
export default {
	createSocket,

	// createSocket(){
	// 	createSocket1();
	// }
}
