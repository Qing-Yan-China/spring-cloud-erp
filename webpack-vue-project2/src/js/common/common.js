import axios from 'axios'
import vuex from '@/js/vuex/store.js'
import router from '@/js/router.js'
const vuex_storage_name="vuex"
const sessionStorageOutTime=''
export default{
	getVuex_storage_name(){
		return vuex_storage_name
	},
	formData(data){
		let params=new URLSearchParams();
		for(let lt in data){
			if(!data[lt])continue;
			params.append(lt,data[lt])
		}
		return params;
	},getSort(sort,data){
		let responseData;
		axios.get('api/common/descSort',{params:sort})
			.then(response=>{
				data[sort.columnName]=response.data;
			})
	},setSessionStorage(key,value){
		sessionStorage.setItem(key,JSON.stringify(value))
	},getSessionStorage(key){
		let value = sessionStorage.getItem(key)
		if(value!=null&&value!=undefined&&value!=''){
			return JSON.parse(value)
		}
		return null;
	},removeSessionStorage(key){
		if(this.getSessionStorage(key)==null){
			return false;
		}
		sessionStorage.removeItem(key)
		return true;
	},logout(key){
		let loginUrl=vuex.getters.getLoginUrl;
		if(this.getSessionStorage(key)!=null){
			//删除存储在sessionStorage中的vuex数据
			sessionStorage.removeItem(key)
			sessionStorage.clear()
		}
		router.push({path:loginUrl})
	}
	// ,getSessionStorageOutTime(){
	// 	axios.get('/api/common/getSessionOutTime')
	// }
}