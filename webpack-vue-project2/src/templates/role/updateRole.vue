<template>
	<el-dialog  :before-close="close" title="修改角色" :visible.sync="update_role_dialog_visible">
	  <el-form :rules="rules" :model="role" ref="role">
	    <el-form-item label="角色名称:" prop="rname">
	      <el-input placeholder="角色名称" v-model="role.rname" autocomplete="off"></el-input>
	    </el-form-item>
		<el-form-item label="排序号:" prop="sort">
		  <el-input placeholder="排序号" v-model="role.sort" autocomplete="off"></el-input>
		</el-form-item>
		<el-form-item label="当前状态:">
		   <el-tooltip :content="role.roleStatus==1?'可用状态':'禁用状态'">
		     <el-switch
		       v-model="role.roleStatus"
		       active-color="#13ce66"
		       inactive-color="#ff4949"
		       :active-value="1"
		       :inactive-value="2">
		     </el-switch>
		   </el-tooltip>
		 </el-form-item>
		<el-form-item label="角色说明:" >
		  <el-input placeholder="角色的说明" type="textarea" v-model="role.describe"></el-input>
		</el-form-item>
	  </el-form>
	  <div slot="footer" class="dialog-footer">
	    <el-button @click="close">取 消</el-button>
	    <el-button type="primary" @click="updateRole">确 定</el-button>
	  </div>
	</el-dialog>
</template>

<script>
	export default{
		name:'updateRole',
		props:['rid'],
		data(){
			return{
				role:{},
				update_role_dialog_visible:false,
				rules:{
					rname:{
						required:true,message:'角色名称不能为空',trigger:'blur'
					},sort:[
						{ required: true, message: '排序号不能为空',trigger:'blur'},
						{ type: 'number', message: '排序号必须为数字值',trigger:'blur'}
					]
				}
			}
		},methods:{
			getRoleById(){
				let rid=this.rid;
				this.$axios.get(`api/role/getById/${rid}`)
					.then(response=>{
						this.role=response.data;
					})
			},close(){
				this.update_role_dialog_visible=false;
				this.$router.go(-1);
			},updateRole(){
				this.$axios.post('/api/role/update',this.role)
					.then(response=>{
						if(response.statusCord==200){
							this.$message({
							    showClose: true,
							    message: '修改成功!',
							    type: 'success',
								duration:2000
							});
							this.close();
						}else{
							this.$message({
							    showClose: true,
							    message: '修改失败!',
							    type: 'error',
								duration:2000
							});
						}
					})
			}
		},created(){
			this.getRoleById();
			this.update_role_dialog_visible=true;
		}
	}
</script>

<style>
</style>