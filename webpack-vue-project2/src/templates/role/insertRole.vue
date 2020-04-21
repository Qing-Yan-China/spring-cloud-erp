<template>
	<el-dialog  :before-close="close" title="新增角色" :visible.sync="insert_role_dialog_visible">
	  <el-form :rules="rules" :model="role" ref="role">
	    <el-form-item label="角色名称:" prop="rname">
	      <el-input placeholder="角色名称" v-model="role.rname" autocomplete="off"></el-input>
	    </el-form-item>
		<el-form-item label="排序号:" prop="sort">
		  <el-input placeholder="排序号" v-model="role.sort" autocomplete="off"></el-input>
		</el-form-item>
		<el-form-item label="角色说明:" >
		  <el-input placeholder="角色的说明" type="textarea" v-model="role.describe"></el-input>
		</el-form-item>
	  </el-form>
	  <div slot="footer" class="dialog-footer">
	    <el-button @click="close">取 消</el-button>
	    <el-button type="button" @click="insertRole" >确 定</el-button>
	  </div>
	</el-dialog>
</template>

<script>
	export default{
		name:'insertRole',
		data(){
			return{
				role:{
					rname:'',
					sort:1,
					describe:''
				},rules:{
					rname:{
						required:true,message:'角色名称不能为空',trigger:'blur'
					},sort:[
						{ required: true, message: '排序号不能为空',trigger:'blur'},
						{ type: 'number', message: '排序号必须为数字值',trigger:'blur'}
					]
				},insert_role_dialog_visible:false
			}
		},methods:{
			getSort(){
				let sort = this.$common.getSort({tableName:'sys_role',columnName:'sort'},this.role);
			},insertRole(){
				this.$axios.post('api/role/insert',this.role)
					.then(response=>{
						if(response.statusCord==200){
							this.$message({
							    showClose: true,
							    message: '新增成功!',
							    type: 'success',
								duration:2000
							});
							this.close();
						}else{
							this.$message({
							    showClose: true,
							    message: '新增失败!',
							    type: 'error',
								duration:2000
							});
						}
					})
			},close(){
				this.insert_role_dialog_visible=false;
				this.$router.go(-1);
			}
		},created() {
			this.getSort();
			this.insert_role_dialog_visible=true;
		}
	}
</script>

<style>
</style>
