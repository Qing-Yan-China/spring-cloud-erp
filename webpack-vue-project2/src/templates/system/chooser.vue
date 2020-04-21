<template>
	<!-- 选择器 -->
	<div id="chooser">
		<!-- indeterminate:  默认false  为true的时候就展示一个-号  -->
		<el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAll">全选</el-checkbox>
		<div style="margin: 15px 0;"></div>
		<el-checkbox-group v-model="checkedRoles" @change="checkedRoleChange">
			<el-checkbox border v-for="(role,key,index) in roles" :key="index" :label="role.rid" :title="'角色描述:'+role.describe">{{role.rname}}</el-checkbox>
		</el-checkbox-group>
		<br />
		<div style="text-align: center;">
			<el-button type="success" title="保存" @click="setUserOwnRole" icon="el-icon-check" circle></el-button>
			<el-button type="danger" title="取消" @click="recoverCheckedKeys" icon="el-icon-close" circle></el-button>
		</div>
	</div>
</template>

<script>
	export default {
		name: 'chooser',
		props: {
			roles: {
				type: Array,
				default: () => ([])
			},
			id: {
				type: Number,
				required: true
			}
		},
		data() {
			return {
				checkAll: false,
				checkedRoles: [],
				isIndeterminate: false,
				isChange: false,
				default_checked_roles: [],
				total_rids:[]
			}
		},
		methods: {
			set_total_rids(){
				if(this.total_rids.length>0)return;
				let total_rids=this.total_rids;
				this.roles.forEach(role=>{
					total_rids.push(role.rid)
				})
			},
			setIsChange(fals) {
				this.isChange = fals
			},
			handleCheckAll(checkAll) { //是否全选
				this.set_total_rids()
				//当点击全选的时候  会有一个它绑定v-model的参数的值
				//如果为true 则把所有的roles赋值给checkedRoles
				this.checkedRoles = checkAll ? this.total_rids : [];
				this.isIndeterminate = false;
				this.setIsChange(true); //修改了
			},
			checkedRoleChange(value) { //复选框改变时发生
				this.set_total_rids()
				//当el-checkbox-group复选框组 下的复选框的状态值发生变化的时候调用此方法
				//此方法有一个 参数为 更新后的值
				//获取选中的长度
				this.setIsChange(true); //修改了
				let checkCount = value.length;
				this.checkAll = checkCount == this.total_rids.length;
				this.isIndeterminate = checkCount > 0 && !this.checkAll
				this.checkedRoles=value;
			},
			getUserOwnRole() {
				let id = this.id;
				this.$axios.get(`api/user/getUserOwnRole/${id}`)
					.then(response => {
						this.default_checked_roles = response.data;
						this.recoverCheckedKeys();
					})
			},
			setUserOwnRole() { //保存操作
				if (this.isChange) {
					let rids = this.checkedRoles;
					let default_checked_roles=this.default_checked_roles
					this.$axios.post('api/user/setUserOwnRole',{
						newRids: rids,
						id: this.id,
						oldRids:default_checked_roles
					}).then(response => {
						if (response.statusCord == 200) {
							this.$message({
								type: 'success',
								message: '设置成功!'
							})
						} else {
							this.$message({
								type: 'error',
								message: response.message
							})
						}
					})
				}
			},
			recoverCheckedKeys() { //取消操作
				//因为checkedRoles会被改变  而default_checked_roles是它的初始状态  不希望被改变
			    //this.checkedRoles=JSON.parse(JSON.stringify(this.default_checked_roles));
				this.checkedRoleChange(this.default_checked_roles);
				this.setIsChange(false);
			}
		},
		created() { 
			this.set_total_rids();
			this.getUserOwnRole();
		},
		beforeDestroy() { //当该组件要被销毁的之前  判断是否修改了 如果修改则给出提示
			if (this.isChange) {
				if (confirm('是否保存修改操作?')) {
					this.setUserOwnRole();
				}else{
					this.$message({
						type:'info',
						message:'你取消了保存操作'
					})
				}
			}
		}
	};
</script>

<style scoped="scoped">
	.el-checkbox {
		margin: 10px 30px 10px 0px;
	}

	.el-checkbox-group {
		width: 60%;
	}
</style>
