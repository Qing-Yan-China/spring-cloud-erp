<template>
	<div>
		<el-col :span="20">
			<!-- <el-page-header style="line-height: 60px;" content="详情页面">
			</el-page-header> -->
			<el-breadcrumb separator-class="el-icon-arrow-right">
				<el-breadcrumb-item  v-for="(item,index) in routerNames" :key="index">
					<span style="color: #FFFFFF;">{{item}}</span>
				</el-breadcrumb-item>
			</el-breadcrumb>
			&nbsp;
		</el-col>
		<el-col :span="4">
			<!-- 顶部导航栏 -->
			<el-menu class="el-menu-demo" mode="horizontal" background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
				<el-submenu index="100">
					<template slot="title">
						<!-- 头像 -->
						<el-avatar size="small" src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png">
						</el-avatar>
						{{activeUser.user.uname}}
					</template>
					<el-menu-item @click="setPersonalcenter">设置个人信息</el-menu-item>
					<el-menu-item @click="updatePwd">修改密码</el-menu-item>
					<el-menu-item @click="logout">退出</el-menu-item>
				</el-submenu>
			</el-menu>
		</el-col>
		<!-- <el-col :span="6">
			<el-page-header style="line-height: 60px;" content="详情页面">
			</el-page-header>
		</el-col>
		<el-col :span="17" style=" text-align: right; font-size: 12px">
			<div style="display: inline-block;">
				<el-avatar icon="el-icon-user-solid"></el-avatar>
			</div>
			<el-dropdown :hide-on-click="false"  @command="handleCommand">
				<span class="el-dropdown-link">
					{{activeUser.user.uname}}<i class="el-icon-arrow-down el-icon--right"></i>
				</span>
				<el-dropdown-menu slot="dropdown">
					<el-dropdown-item  command="logout">安全退出</el-dropdown-item>
				</el-dropdown-menu>
			</el-dropdown>
		</el-col> -->
	</div>
</template>

<script>
	export default {
		name: 'cephalosome', //也是头部的意思
		data() {
			return {

			}
		},
		methods: {
			setPersonalcenter(){
				this.$router.push({path:'/index/personalcenter.html'});
			},updatePwd(){
				this.$router.push({path:'/index/updatePwd'})
			},logout() {//用户退出
				this.$axios.get('api/logout')
					.then(response => {
						if (response.statusCord == 200) {
							//设置数据为
							this.$store.commit({
								type: 'setActiveUser',
								activeUser: {}
							})
							//删除存储在sessionStorage中的数据
							//封装了下面的所有
							this.$common.logout(this.$common.getVuex_storage_name())
							// this.$common.removeSessionStorage(this.$common.getVuex_storage_name());
							// this.$router.push({
							// 	path: this.$store.getters.getLoginUrl
							// })
						} else {
							this.$message({
								type: 'error',
								message: response.message
							})
						}
					})
			}
		},
		computed: {
			activeUser() {
				return this.$store.getters.getActiveUser;
			},
			routerNames() {
				return this.$store.getters.getRouterNames;
			}
		}
	}
</script>

<style scoped="scoped">
	.el-breadcrumb__inner {
		color: #FFFFFF;
		font-size: 18px;
	}
	.el-breadcrumb{
		line-height: 55px;
	}
</style>
