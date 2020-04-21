<template>
	<main id="login">
		<el-form :model="user" :rules="fieldRules" ref="login" label-position="left" label-width="0px" class="demo-ruleForm login-container">
			<h3 class="title">系统登录</h3>
			{{activeUser}}
			<el-form-item prop="uid">
				<el-input type="text" v-model="user.uid" auto-complete="off" placeholder="账号"></el-input>
			</el-form-item>
			<el-form-item prop="upwd">
				<el-input type="password" v-model="user.upwd" auto-complete="off" placeholder="密码"></el-input>
			</el-form-item>
			<el-form-item prop="verificationCode">
				<el-row :gutter="20" >
					<el-col :span="10">
						<el-input type="text" v-model="user.verificationCode" auto-complete="off" placeholder="验证码"></el-input>
					</el-col>
					<el-col :span="10">
						<img title="看不清!换一张" @click="changeImage" :src="imgPath" />
						<!-- <el-image style="width: 80px; height: 40px" url="http://localhost:8011/api/common/verificationImg">
							<div slot="error" class="image-slot">
								<i class="el-icon-picture-outline"></i>
							</div>
						</el-image> -->
					</el-col>
				</el-row>
				
			</el-form-item>
			<!-- <el-checkbox v-model="checked" checked class="remember">记住密码</el-checkbox> -->
			<el-form-item style="width:100%;">
				<el-button type="primary" style="width:45%;" @click.native.prevent="reset">重 置</el-button>
				<el-button type="primary" style="width:45%;" @click.native.prevent="login" :loading="logining">登 录</el-button>
			</el-form-item>
		</el-form>
	</main>
</template>

<script>
	export default {
		name: 'login',
		data() {
			return {
				imgPath:'http://localhost:8011/verificationImg',
				user: {
					uid: '123',
					upwd: '123',
					verificationCode: '1',
					
				},
				fieldRules: {
					uid: [{
						required: true,
						message: '请输入账号!',
						trigger: 'blur'
					}],
					upwd: [{
						required: true,
						message: '请输入密码!',
						trigger: 'blur'
					}],
					verificationCode:[{
						required:true,
						message:'请输入验证码!',
						trigger:'blur'
					}]
				},logining:false
			}
		},
		methods: {
			init_user_resource(){
				this.$socket.createSocket()
				this.$store.dispatch("init_user_resource")
					.then(response=>{
						console.log(JSON.stringify(response))
						console.log(JSON.stringify(this.$store.getters.getDynamicRouters))
						// this.$router.addRoutes([{
						// 	path: '/index',
						// 	component: () => import('@/templates/system/index.vue'),
						// 	children: response
						// }])
						let hostname=this.$route.query.redirect;
						if(hostname!=undefined){
							this.$router.go(-1)
						}else{
							this.$router.push({path:this.$store.getters.getIndexUrl})
						}
					})
				
			},
			login() {
				
				this.logining=true;
				this.$refs.login.validate((validate)=>{
					if(validate){
						let params=this.$Qs.stringify(this.user);
						this.$axios.post('/api/login',params)
							.then(response=>{
								if(response.statusCord==200){
									//保存用户信息
									this.$message({
										type:'success',
										message:response.message
									})
									this.$store.commit({
										type:'setActiveUser',
										activeUser:response.data
									})
									this.init_user_resource()
									
								}else{
									this.$message({
										type:'error',
										message:response.message
									})
								}
							})
					}else{
						this.$message({
							type:'error',
							message:'验证失败!'
						})
					}
				})
				this.logining=false;
			},
			reset() {
				this.$refs.login.resetFields();
			},changeImage(){
				//换一张图片
				let timeStamp=(new Date()).valueOf();
				console.log('timeStamp:'+timeStamp)
				//获取图片路径的请求地址
				let newImgPath=this.imgPath;
				console.log('newImgPath:'+newImgPath)
				let index=newImgPath.indexOf('?')
				//如果有带参数
				if(index>0){//去除问号后面的参数
					newImgPath=newImgPath.substring(0,index);
				}
				//在加上时间  因为时间肯定是唯一的  所以服务器会根据这个参数  知道 他是一次
				//新的请求 所以会去请求这个路径  这个时间参数就是为了让浏览器以为是一次全新的请求而已
				newImgPath+="?timeStamp="+timeStamp;
				this.imgPath=newImgPath;
			}
		},computed:{
			activeUser(){
				return this.$store.getters.getActiveUser;
			}
		}
	}
</script>

<style scoped>
	.login-container {
		-webkit-border-radius: 5px;
		border-radius: 5px;
		-moz-border-radius: 5px;
		background-clip: padding-box;
		margin: 180px auto;
		width: 350px;
		padding: 35px 35px 15px 35px;
		background: #fff;
		border: 1px solid #eaeaea;
		box-shadow: 0 0 25px #cac6c6;

		.title {
			margin: 0px auto 40px auto;
			text-align: center;
			color: #505458;
		}

		.remember {
			margin: 0px 0px 35px 0px;
		}
	}
</style>
