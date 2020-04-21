<template>
	<div id="treeControl">
		<!-- accordion:手风琴
		    show-checkbox:显示复选框
				    				   node-key:唯一的key
				    				   default-expanded-keys:默认展开的数据 数组
				    				   default-expanded-all:所有
				    				   default-checked-keys:默认选中
				    				   expand-on-click-node:点击节点是否选中
		highlight-current:是否高亮显示选中节点 
		-->		
		<!-- check-change:选中节点发生变化的回调函数
			check:只有当复选框被点击的时候才会触发
			而 check-change会在加载树状控件的时候如果有默认的选中项  也会触发
		 -->
		<el-tree accordion :data="tree_menu" :default-checked-keys='default_check_keys' show-checkbox node-key="id" ref="tree" :default-expanded-keys="default_check_keys"
		 :expand-on-click-node="false" @check="checkChange" :highlight-current="true">
			<!-- check-change:选中节点发生变化的回调函数 -->
			<span class="custom-tree-node" slot-scope="{ node, data }">
				<span>{{ data.title }}</span>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<template v-if="data.type==1">
					  <span class="el-link el-link--primary is-underline" >类型:资源权限</span>
				</template>
				<template v-else-if="data.type==2">
					<span class="el-link el-link--primary is-underline" >类型:菜单权限</span>
				</template>
			</span>
		</el-tree>

		<div style="text-align: center;">
			<el-button type="success" title="保存" @click="setOwnPermission" icon="el-icon-check" circle></el-button>
			<el-button type="danger" title="取消" @click="recoverCheckedKeys" icon="el-icon-close" circle></el-button>
		</div>
	</div>
</template>

<script>
	export default {
		name: 'treeControl',
		props: {
			rid: {
				type: Number,
				required: true
			},
			tree_menu: {
				type: Array,
				default: () => ([])
			}
		},
		data() {
			return {
				isChange: false,
				default_check_keys: [],
				props: {
					label: 'title',
					children: 'children'
				}
			}
		},
		methods: {
			// getTreeMenu() {
			// 	this.$axios.get('api/common/getTreeMenu')
			// 		.then(response => {
			// 			this.treeMenu = response.data;
			// 			console.log(this.treeMenu);
			// 		})
			// },
			setIsChange(fals){
				this.isChange=fals;
			},checkChange() {
				this.isChange = true;
			},
			recoverCheckedKeys() { //恢复初始状态
				this.$refs.tree.setCheckedKeys(this.default_check_keys, false);
				this.setIsChange(false);
			},
			setOwnPermission() {
				if (this.isChange) {
					let pids = this.getPitchOnKeys();
					console.log('pids:'+pids);
					if (pids.length > 0) {
						this.$axios.post('api/role/setOwnPermission', {
								pids: pids,
								rid: this.rid,
								oldPids:this.default_check_keys
							})
							.then(response => {
								if(response.statusCord==200){
									this.$message({
										type: 'success',
										message: '设置成功!'
									})
									this.setIsChange(false);
								}else{
									this.$message({
										type:'error',
										message:'设置失败!'
									})
								}
							})
					}
				}
			},getPitchOnKeys(){//获取选中的keys
				return this.$refs.tree.getCheckedKeys('true');
			},
			getOwnPermission() {
				//if(!this.isChange)return;
				this.$axios.get('api/role/getOwnPermission', {
						params: {
							rid: this.rid
						}
					})
					.then(response => {
						//获取该角色拥有的权限id  让它在treeControl上默认选中
						this.default_check_keys = response.data ? response.data : [];
						console.log('this.default_check_keys:'+this.default_check_keys)
						
						//this.default_check_keys.push(this.tree_menu[0].id);
					})
			},
			isChangeCheck() {
				return this.isChange;
			}
		},
		created() {
			//this.getTreeMenu();
			this.getOwnPermission();
		},
		beforeDestroy() {
			if (this.isChange&&this.getPitchOnKeys().length>0) {
				if (confirm('是否保存此操作?')) {
					this.setOwnPermission();
				} else {
					this.$message({
						type: 'info',
						message: '已取消保存'
					});
				}
			}
		}
	}
</script>

<style scoped="scoped">
</style>
