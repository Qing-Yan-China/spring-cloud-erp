<template>
	<div>
		<div class="edit-avatar">
			<img :src="headPortraiUrl" alt="..." style="width: 120px; height: 120px;" class="img-avatar">
			<div class="avatar-divider"></div>
			<div class="edit-avatar-content">
				<!-- action:上传地址 -->
				<!-- list-type:上传的类型 picture图片 -->
				<!-- data要传递的数据 
				before-upload 上传之前的钩子函数 -->
				<!-- show-file-list:上传成功后是否显示图片 -->
				<!-- limit:上传数量的限制 -->
				<el-upload :limit="1" :data="{userId:user.id}" class="upload-demo" :action="action" :before-upload="isProgress"
				 list-type="picture" :on-success="handleSuccess" :on-error="handleError" :show-file-list="false">
					<el-button size="small" type="primary">点击上传</el-button>
					<div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
				</el-upload>
			</div>
		</div>
		<el-form :model="user" :rules="rules" ref="activeUser" label-width="100px" class="demo-ruleForm">
			<el-form-item label="用户账号">
				<el-input :disabled="true" v-model="user.uid"></el-input>
			</el-form-item>
			<el-form-item label="用户呢称" prop="uname">
				<el-input v-model="user.uname"></el-input>
			</el-form-item>
			<el-form-item label="用户密码" >
				<el-input :disabled="true" type="password" v-model="user.upwd" ></el-input>
				<el-button type="danger" @click="updatePwd">修改密码</el-button>
			</el-form-item>
			<!-- 
		  <el-form-item label="请输入新密码" prop="uname">
		    <el-input placeholder="请输入新密码" v-model="input" show-password></el-input>
		  </el-form-item>
		  <el-form-item label="请确认新密码" prop="uname">
		    <el-input placeholder="请确认新密码" v-model="input" show-password></el-input>
		  </el-form-item> 
		   -->
			<el-form-item label="性别">
				<el-radio-group v-model="user.sex">
					<el-radio label="0">男</el-radio>
					<el-radio label="1">女</el-radio>
				</el-radio-group>
			</el-form-item>
			<el-form-item label="简介">
				<el-input type="textarea" v-model="user.signature"></el-input>
			</el-form-item>
			<el-form-item label="当前所在地址" >
				<el-input type="textarea" v-model="user.address"></el-input>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="updateActiveUser">保存</el-button>
			</el-form-item>
		</el-form>
		<router-view></router-view>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				urlPrefix: 'http://localhost:8011/upload/showImage/',
				action: 'http://localhost:8011/upload/headPortrai',
				rules: {
					uname: [{
						required: true,
						message: '用户呢称不能为空!'
					}]
				},
				user: {}
			};
		},
		methods: {
			isProgress(file) { //是否上传图片
				let isImage = file.type.includes("image")
				if (!isImage) {
					this.$message.error('上传文件类型必须为图片')
				}
				let isLt2M = file.size / 1024 / 1024 < 2;
				if (!isLt2M) {
					this.$message.error("上传图片大小不能超过 2MB!");
				}
				console.log('file:' + file)
				if (isImage && isLt2M) {
					if (!confirm('是否上传头像?')) {
						return false;
					}
				} else {
					return false;
				}
			},
			handleSuccess(response) { //上传成功的回调函数
				if (response.statusCord == 200) {
					this.$message.success(response.message)
					this.user.imgPath = response.data
					this.$store.commit({
						type: 'updateActiveUser',
						obj: 'user',
						property: 'imgPath',
						value: this.user.imgPath
					})
				} else {
					this.$message.error(response.message)
				}
			},
			handleError(error) { //上传失败的回调函数
				this.$message.error(error)
			},updatePwd() { //修改密码
				this.$router.push({path:'/index/personalcenter.html/updatePwd'})
			},updateActiveUser(){
				this.$refs.activeUser.validate((valid)=>{
					if(valid){
						let params = this.formData(this.user);
						this.$axios.post('api/user/updateActiveUser',params)
							.then(response=>{
								if(response.statusCord==200){
									this.$message.success('保存成功!');
								}else{
									this.$message.error('保存失败!');
								}
							})
					}else{
					}
				})
			},formData(data){
				let params=new URLSearchParams();
				for(let lt in data){
					if(data[lt]!=='')
						params.append(lt,data[lt])
				}
				return params;
			}
		},
		created() {
			//获取当前用户
			let user = this.$store.getters.getActiveUser.user;
			this.user = this.$Qs.parse(this.$Qs.stringify(user))
		},
		computed: {
			headPortraiUrl() {
				return this.urlPrefix + this.user.imgPath
			}
		}

	}
	// export default {
	// 	name:'personalcenter',
	// 	data() {
	// 		return {
	// 			imageUrl: '',

	// 		}
	// 	},methods :{
	// 		previewPicture(file){
	// 			alert(1)
	// 			console.log(file)
	// 		},fileChange(fileObj){
	// 			console.log(fileObj.target.files[0]);
	// 		},handleAvatarSuccess(file){
	// 			console.log(file)
	// 		}
	// 	}
	// }
</script>

<style scoped="scoped">
	.demo-form .demo-dynamic .el-input {
		margin-right: 10px;
		width: 270px;
		vertical-align: top;
	}

	.el-input,
	.el-textarea {
		width: 50%
	}

	.avatar {
		width: 178px;
		height: 178px;
		display: block;
	}

	.edit-avatar {
		display: -ms-flexbox;
		display: flex;
		-ms-flex-align: start;
		align-items: flex-start;
	}

	.avatar-divider {
		display: inline-block;
		border-left: 1px solid rgba(77, 82, 89, 0.07);
		height: 50px;
		align-self: center;
		margin: 0px 20px;
	}

	.edit-avatar-content {
		display: inline-block;
	}

	.m-0 {
		margin: 0px !important;
	}
</style>
