<template>
	<el-dialog title="新增权限" :before-close="close" :visible.sync="update_permission_dialog_visible">
		<el-form :model="permission" :rules="rules" ref="permission" label-width="100px" class="demo-permission">
			<el-form-item   label="权限名称:" prop="pName">
				<el-input placeholder="权限名称" v-model="permission.pName"></el-input>
			</el-form-item>
			<el-form-item label="权限父类" >
				<el-cascader :disabled="parentPermissionDisabled" v-model="permission.parentId" :options="parentPermissions" placeholder="请选择"
				:props="propsParams">
				  <template slot-scope="{ node, data }">
				    <span>{{ data.title }}</span>
				    <span v-if="!node.isLeaf"> ({{ data.children.length }}) </span>
				  </template>
				</el-cascader>
			</el-form-item>
			 <el-form-item label="当前状态:">
			    <el-tooltip :content="permission.permissionStatus==1?'可用状态':'禁用状态'">
			      <el-switch
			        v-model="permission.permissionStatus"
			        active-color="#13ce66"
			        inactive-color="#ff4949"
			        :active-value="1"
			        :inactive-value="2">
			      </el-switch>
			    </el-tooltip>
			  </el-form-item>
			<el-form-item label="权限类型:" >
				<el-radio-group   v-model="permission.pType">
					<el-radio :disabled="pTypeDisabled" :label="1">资源权限类型</el-radio>
					<el-radio :disabled="pTypeDisabled" :label="2">菜单权限类型</el-radio>
				</el-radio-group>
			</el-form-item>
			<template v-if="permission.pType==2"  >
				<!-- <el-form-item label="菜单左侧图标:" v-show="permission.pType==2" >
					<el-input placeholder="菜单左侧图标" v-model="permission.icon"></el-input>
				</el-form-item> -->
				<el-form-item label="菜单左侧图标:">
					<select_icon v-model="permission.icon"></select_icon>
				</el-form-item>
				<el-form-item label="菜单url:" v-show="permission.pType==2">
					<el-input placeholder="菜单url" v-model="permission.url"></el-input>
				</el-form-item>
				<el-form-item label="菜单组件路径:" >
					<el-input placeholder="菜单组件路径" v-model="permission.componentName"></el-input>
				</el-form-item>
				<el-form-item>
					<el-radio-group v-model="permission.showMenu">
						<el-radio :label="true">显示到侧边栏</el-radio>
						<el-radio :label="false">不显示到侧边栏</el-radio>
					</el-radio-group>
				</el-form-item>
			</template>
			<template v-else  >
				<el-form-item label="权限资源:" v-show="permission.pType==1" >
					<el-input placeholder="权限资源" v-model="permission.resource"></el-input>
				</el-form-item>
			</template>
			<el-form-item label="排列顺序:" prop="sort">
				<el-input placeholder="排列顺序" v-model="permission.sort"></el-input>
			</el-form-item>
			
			<el-form-item label="权限说明:" >
				<el-input placeholder="权限的说明" type="textarea" v-model="permission.describe"></el-input>
			</el-form-item>
		</el-form>
		<div slot="footer" class="dialog-footer">
			<el-button @click="close">取 消</el-button>
			<el-button type="primary" @click="updatePermission('permission')">确 定</el-button>
		</div>
	</el-dialog>
</template>
<script>
	import select_icon from '@/templates/system/icon.vue'
	export default{
		name:'updatePermission',
		data(){
			return{
				//checkStrictly是否严格的遵守父子节点不互相关联
				//expandTrigger次级菜单的展开方式
				propsParams:{
					checkStrictly: true,
					expandTrigger:'hover',
					label:'title',
					value:'value'
				},
				permission:{},
				update_permission_dialog_visible:false,
				parentPermissions:[],
				parentPermissionDisabled:false,
				pTypeDisabled:false,
				rules: {
					pName: {
							required: true,
							message: '请输入权限名称',
							trigger: 'blur',
					},sort:[
						{ required: true, message: '排序号不能为空',trigger:'blur'},
						{ type: 'number', message: '排序号必须为数字值',trigger:'blur'}
					]
				}
			}
		},methods:{
			getPermission(){
				this.$axios.get('/api/permission/getById',{params:{pId:this.pId}})
					.then(response=>{
						this.permission=response.data;
					})
			},close(){
				this.$router.go(-1);
				this.update_permission_dialog_visible=false;
			},getCascadePermission(){//获取级联选择器中的数据
				this.$axios.get('/api/cascaded/permission')
					.then(response=>{
						this.parentPermissions=response.data;
					})
			},updatePermission(formName){
				this.$refs[formName].validate((valid)=>{
					if(valid){//成功!
						let params=this.$common.formData(this.permission);
						this.$axios.post('/api/permission/update',params)
							.then(response=>{
								if(response.statusCord==200){
										this.$message({
										    showClose: true,
										    message: '修改权限成功!',
										    type: 'success',
											duration:2000
										});
										this.close();
									}else{
										this.$message({
											showClose: true,
											message: response.message,
											type: 'error',
											duration:2000
										})
									}
							})
					}else{
						this.$message({
							showClose: true,
							message: "请填写必填项",
							type: 'error',
							duration:2000
						})
					}
				})
			},init(){
				this.getCascadePermission();
				this.update_permission_dialog_visible=true
				this.getPermission();
				let permission=this.permission;
				if(permission.pType==2){
					this.parentPermissionDisabled = this.parentPermissions.some(permission1=>{
						return permission.pId==permission1.value&&permission1.children&&permission1.children.elgnth>0
					})
					this.pTypeDisabled=this.parentPermissionDisabled;
				}
			}
		},created() {
			this.init();
		},props:[
			'pId'
		],components:{
			select_icon
		}
	}
</script>
<style scoped>


</style>
