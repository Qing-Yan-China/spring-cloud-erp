<template>
	<router-view></router-view>
</template>

<script>
	export default{
		name:'app',
		data(){
			return{
				vuex_storage_name:''
			}
		},methods:{
			init(){
				this.getPageSizes();
			},getPageSizes(){
				this.$store.dispatch({type:"searchPageSizes"})
			},setSessionStorage(){
				//将vuex中的数据存储到sessionStorage 
				//因为只要浏览器一刷新 vuex中的数据就会被销毁
				//所以需要把数据存储到sessionStorage中去
				//有因为 sessionStorage中只能存放字符串
				//判断是否登录了  登录了才存储数据
				if(this.$store.getters.isLogin){
					console.log('登录了 并且刷新页面了  需要将vuex中的数据存储到sessionStorage中去')
					
					this.$common.setSessionStorage(this.vuex_storage_name,this.$store.state)
					//sessionStorage.setItem(vuex_storage_name,JSON.stringify(this.$store.state));
				}
				
			},getSessionStorage(){
				//获取存储在sessionStorage中的数据
				let state=this.$common.getSessionStorage(this.vuex_storage_name)//sessionStorage.getItem(vuex_storage_name)
				console.log("state:"+state)
				if(state!=null){
					console.log('数据获取成功!')
					this.$store.replaceState(Object.assign({},this.$store.state,state))
					console.log(this.$store.state)
					this.$store.dispatch("init_user_resource")
						.then(response=>{
							this.$router.addRoutes([{
								path: '/index',
								component: () => import('@/templates/system/index.vue'),
								children: response
							}])
						})
					this.$router.addRoutes(this.$error_router);
				}
			},delSessionStorage(){
				this.$common.removeSessionStorage(this.vuex_storage_name);
				//sessionStorage.removeItem(vuex_storage_name);
			}
		},computed:{
		},created() {
			this.vuex_storage_name=this.$common.getVuex_storage_name();
			this.getSessionStorage();
			window.addEventListener('beforeunload',this.setSessionStorage)
		}
	}
</script>

<style>
</style>
