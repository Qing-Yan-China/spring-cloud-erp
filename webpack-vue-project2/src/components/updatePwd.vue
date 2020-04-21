<template>
	<el-dialog :before-close="close" title="修改密码" :visible.sync="updateUpwdVisible">
		<el-form :model="pwd" status-icon :rules="rules" ref="updatePwd" label-width="100px" class="demo-ruleForm">
			<el-form-item label="密码" prop="newPwd">
				<el-input type="password" v-model="pwd.newPwd" autocomplete="off"></el-input>
			</el-form-item>
			<el-form-item label="确认密码" prop="checkPwd">
				<el-input type="password" v-model="pwd.checkPwd" autocomplete="off"></el-input>
			</el-form-item>
		</el-form>
		<div slot="footer" class="dialog-footer">
			<el-button @click="close">取 消</el-button>
			<el-button type="primary" @click="updateUpwd">确 定</el-button>
		</div>
	</el-dialog>
</template>

<script>
	export default {
		name: 'updatePwd',
		data() {
			var validatePass = (rule, value, callback) => {
				if (value === '') {
					callback(new Error('请输入密码'));
				} else {
					if (this.pwd.checkPwd !== '') {
						this.$refs.updatePwd.validateField('checkPwd');
					}
					callback();
				}
			};
			var validatePass2 = (rule, value, callback) => {
				if (value === '') {
					callback(new Error('请再次输入密码'));
				} else if (value !== this.pwd.newPwd) {
					callback(new Error('两次输入密码不一致!'));
				} else {
					callback();
				}
			};
			return {
				pwd: {
					newPwd: '',
					checkPwd: ''
				},
				updateUpwdVisible: false,
				rules: {
					newPwd: [{
						validator: validatePass,
						trigger: 'blur'
					}],
					checkPwd: [{
						validator: validatePass2,
						trigger: 'blur'
					}]
				}
			};
		},
		mounted() {
			this.checkPwd();
		},
		methods: {
			close() {
				this.$router.go(-1)
			},
			updateUpwd() {
				this.$refs.updatePwd.validate((valid) => {
					if (valid) {
						let params="newUpwd="+this.pwd.newPwd;
						this.$axios.post('/api/user/updateUpwd', params)
							.then(response => {
								if (response.statusCord == 200) {
									this.$message.success('修改密码成功!即将进入登录页面...')
									this.$axios.get('api/logout')
									this.$common.logout(this.$common.getVuex_storage_name())
									//this.close()
								} else {
									this.$message.error(response.message)
								}
							})
					} else {
						alert('哈哈哈')
					}
				});
			},
			checkPwd() { //验证当前用户输入的密码是否正确
				this.$prompt('请输入密码', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					inputType: 'password',
				}).then(({
					value
				}) => {
					let params="upwd="+value;
					this.$axios.post('/api/user/equalsPwd',params)
						.then(response => {
							if (response.statusCord == 200) {
								this.updateUpwdVisible = true;
							} else {
								this.$message.error('输入的密码有误')
								this.close()
							}
						})
				}).catch(() => {
					this.$message({
						type: 'info',
						message: '取消输入'
					});
					this.close()
				});
			}
		},created() {
			
			
		}
		

	}
</script>

<style>
</style>
