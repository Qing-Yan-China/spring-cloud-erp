<template>
	<el-dialog id="updateUser" :before-close="close" width="50%" title="修改用户" :visible.sync="update_user_dialog_visible">
		<el-form :model="user" :rules="rules" ref="insertUser">
			<el-form-item label="用户账号:" prop="uid" :label-width="formLabelWidth">
				<el-input v-model="user.uid" autocomplete="off"></el-input>
			</el-form-item>
			<el-form-item label="用户名称:" prop="uname" :label-width="formLabelWidth">
				<el-input autocomplete="off" v-model="user.uname"></el-input>
			</el-form-item>
			<el-form-item label="用户地址:" :label-width="formLabelWidth">
				<el-input autocomplete="off" v-model="user.address"></el-input>
			</el-form-item>
			<el-form-item label="用户性别:" :label-width="formLabelWidth">
				<el-radio-group v-model="user.sex">
					<el-radio :label="0">男</el-radio>
					<el-radio :label="1">女</el-radio>
				</el-radio-group>
			</el-form-item>
			<el-form-item label="用户类型:" :label-width="formLabelWidth">
				<el-select v-model="user.isadmin">
					<template v-if="isadmin==3">
						<el-option label="3" value="普通用户"></el-option>
					</template>
					<template v-else>
						<el-option label="3" value="普通用户"></el-option>
						<el-option label="2" value="管理用户"></el-option>
					</template>
				</el-select>
			</el-form-item>
		</el-form>
		<div slot="footer" class="dialog-footer">
			<el-button @click="close()">取 消</el-button>
			<el-button type="button" v-on:click="updateUser()">确 定</el-button>
		</div>
	</el-dialog>
</template>

<script>
	export default {
		name:'updateUser',
		//props:['uid'],
		data() {
			var validatorCheckuid = (role, value, callback) => {
				if (value === '') {
					callback(new Error('用户账号不能为空!'))
				} else {
					this.$axios.get('api/user/checkuid', {
							params: {
								uid: value
							}
						})
						.then(response => {
							if (response.statusCord == 200) {
								callback()
							} else {
								callback(new Error('该用户名已存在!'))
							}
						})
				}
			};
			return {
				uid:null,
				formLabelWidth: '120px',
				update_user_dialog_visible: false,
				user: {},
				isadmin: 3,
				rules: {
					uid: [{
						validator: validatorCheckuid,
						trigger: 'blur'
					}],
					uname: [{
						required: true,
						message: '用户名不能为空',
						trigger: 'blur'
					}]
				}
			}
		},
		created() {
			this.uid=this.$route.params[Object.getkeys(this.$route.params)[0]]
			this.update_user_dialog_visible = true;
			this.getUser();
		},
		methods: {
			close() {
				this.update_user_dialog_visible = false;
				this.$router.go(-1)
			},
			updateUser() {
				this.$refs.updateUser.validate((valid) => {
					if (valid) {
						this.$axios.post('/api/user/update', this.user)
							.then(response => {
								let responseData = response.data;
								if (response.statusCord == 200) {
									this.$message({
										showClose: true,
										message: '修改成功!',
										type: 'success',
										duration: 2000
									});
									this.close();
								} else {
									this.$message({
										showClose: true,
										message: responseData.message,
										type: 'error',
										duration: 2000
									});
								}
							})
					}
				})
			},getUser(){
				this.$axios.get('api/user/byId/'+this.uid)
					.then(response=>{
						this.user=response.data
					})
			}
		},
		mounted() {
			//获取当前用户的等级 this.$store.getActiveUser this.$store
			let activeUser = this.$store.getters.getActiveUser
			this.isadmin = activeUser.user.isadmin;
		}
	}
</script>

<style>
</style>
