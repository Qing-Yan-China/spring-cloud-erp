<template>
	<div id="role">
		<router-view></router-view>
		<el-row type="flex" class="row-bg" justify="space-around">
			<el-form :inline="true" class="demo-form-inline">
				<el-form-item label="角色名称">
					<el-input v-model="params.keyWord" placeholder="权限名称"></el-input>
				</el-form-item>
				<el-form-item label="页面大小">
					<el-select v-model="params.pageSize" placeholder="页面大小">
						<el-option v-for="(pageSize,index) in pageSizes" v-bind:key="index" v-bind:value="pageSize">
							{{pageSize}}
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<el-button native-type="button" v-on:click.prevent="searchRole" type="primary" icon="el-icon-search">搜索</el-button>
				</el-form-item>
			</el-form>
			<div>
				<el-button type="danger" v-on:click="insertRole">新增角色</el-button>
			</div>
		</el-row>
		<!-- 
			 default-sort  默认的排序 是一个对象
			 属性:prop:指定的排序列名 order:排序方式
			 有 ascending or descending
			 -->
		<!-- :default-sort="sort_regulation" -->
		<!-- expand-change: 树状表格的状态改变时的回调函数-->
		<el-table ref="role_table" :border="true" :highlight-current-row="true" style="width: 100%" :data="rolePage.list"
		 :default-sort="{prop: params.orderingRule.prop, order: params.orderingRule.order}" @sort-change="sortChange"
		 @expand-change="expandChange" >
			<!-- type:expand 显示一个可展开的按钮 -->
			<el-table-column type="expand" label="权限">
				<template slot-scope="{ row, column, $index }">
					<tree-control ref="tree_control" :tree_menu="treeMenu" :rid="row.rid"></tree-control>
				</template>
			</el-table-column>
			<el-table-column label="权限名称" prop="rname" :sortable="custom">
				<template slot-scope="scope">
					{{scope.row.rname}}
				</template>
			</el-table-column>
			<el-table-column label="状态" prop="roleStatus" :sortable="custom">
				<template slot-scope="scope">
					<el-tooltip v-bind:content="scope.row.roleStatus==1?'可用状态':'禁用状态'" placement="top">
						<el-switch @change="roleStatusChange(scope.row)" v-model="scope.row.roleStatus" active-color="#13ce66"
						 inactive-color="red" :active-value="1" :inactive-value="2">
						</el-switch>
					</el-tooltip>
				</template>
			</el-table-column>
			<!-- 设置 sortable="custom" 表示远程排序 -->
			<el-table-column label="排序号" prop="sort" :sortable="custom">
				<template slot-scope="scope">
					{{scope.row.sort}}
				</template>
			</el-table-column>
			<el-table-column label="创建日期" prop="createTime" :sortable="custom">
				<template slot-scope="scope">
					<i class="el-icon-time"></i>
					<span style="margin-left: 10px" :title="scope.row.createTime">{{ scope.row.createTime }}</span>
				</template>
			</el-table-column>
			<el-table-column label="修改日期" prop="updateTime" :sortable="custom">
				<template slot-scope="scope">
					<template v-if="scope.row.updateTime!=''&&scope.row.updateTime!=null">
						<i class="el-icon-time"></i>
						<span style="margin-left: 10px">{{ scope.row.updateTime }}</span>
					</template>
					<template v-else>暂无数据</template>
				</template>
			</el-table-column>
			<el-table-column label="说明">
				<template slot-scope="scope">
					{{scope.row|whetherShow('describe','暂无说明')}}
				</template>
			</el-table-column>

			<el-table-column label="操作" width="120px">
				<template slot-scope="scope">
					<!-- circle是否为圆形按钮  false -->
					<el-button type="primary" title="编辑" icon="el-icon-edit" circle @click="compileRole(scope.row.rid)"></el-button>
					<el-button type="danger" @click="deleteRole(scope.row.rid)" title="删除" icon="el-icon-delete" circle></el-button>
				</template>
			</el-table-column>
		</el-table>
		<br />
		<div class="block">
			<el-pagination style="text-align: right;"
			 background
			  :current-page.sync="params.pageNum"
			   layout="total,prev, pager, next,jumper,slot"
			 :total="rolePage.total"
			 :page-size="rolePage.pageSize"
			 @current-change="currentPageNumChange">
			 <span>&nbsp;&nbsp;&nbsp;总页数:<span>{{rolePage.pages}}</span></span>
			</el-pagination>
		</div>
	</div>
</template>

<script>
	import treeControl from '@/templates/system/treeControl.vue'
	export default {
		name: 'role',
		data() {
			return {
				params: {
					pageNum: 1,
					pageSize: 5,
					keyWord: '',
					orderingRule: {
						prop: 'sort',
						order: 'ascending'
					}
				},
				rolePage: {},
				custom: 'custom',
				prefixRole: '',
				treeMenu: [
					{
						title:'全选',
						id:0,
						children:[]
					}
				]
			}
		},
		methods: {
			insertRole(){
				this.$router.push({path:'/index/role.html/insert'})
			},
			sortChange(order) {
				this.params.orderingRule = order;
				this.searchRole()
			},
			searchRole() {
				this.$axios.post('/api/role/search', this.params)
					.then(response => {
						this.rolePage = response.data;
					})
			},currentPageNumChange(currentPageNum){
				this.params.pageNum=currentPageNum;
				this.searchRole();
			},compileRole(rid) {
				this.$router.push({
					path: `/index/role.html/update/${rid}`
				});
			},deleteRole(rid) {
				this.$confirm('是否删除该角色?','提示',{
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(()=>{
					this.$axios.get('api/role/delete/'+rid)
						.then(response=>{
							if(response.statusCord==200){
								this.$message({
									type:'success',
									message:response.message
								})
								this.searchRole();
							}else{
								this.$message({
									type:'error',
									message:response.message
								})
							}
						})
				}).catch(()=>{
					this.$message({
						type:'info',
						message:'你点击了取消删除!'
					})
				})
			},
			roleStatusChange(currentRole) {
				let rid=currentRole.rid;
				let status=currentRole.roleStatus;
				this.$axios.get(`api/role/setRoleState/${rid}/${status}`)
					.then(response=>{
						if(response.statusCord==200){
							this.$message({
								type:'success',
								message:response.message
							})
						}else{
							this.$message({
								type:'error',
								message:response.message
							})
						}
					})
			},
			expandChange(row, expandedRows) {
				if (this.treeMenu[0].children.length == 0)
					this.getTreeMenu();
				//expand-change: 树状表格的状态改变时的回调函数
				if (this.prefixRole != '' && this.prefixRole.rid == row.rid) {
					this.prefixRole='';
				} else {
					//说明打开了多个树状表格  关闭之前哪一个
					if (this.prefixRole != '')
						this.$refs.role_table.toggleRowExpansion(this.prefixRole, false)
					this.prefixRole = row;
				}
			},
			getTreeMenu() {
				this.$axios.get('api/common/getTreeMenu')
					.then(response => {
						console.log('treeMenu:'+this.treeMenu)
						this.treeMenu[0].children=response.data;
						console.log('treeMenu:'+this.treeMenu);
						
					})
			},
		},
		computed: {
			pageSizes() {
				return this.$store.getters.getPageSizes;
			}
		},
		filters: {
			whetherShow(data, property, message) {
				if (message == '')
					message = "暂无数据"
				if (data == '' || data == undefined)
					return message;
				if (data[property] == '' || data[property] == null)
					return message;
				return data[property];
			}
		},
		created() {
			this.searchRole();
		},
		components: {
			"tree-control": treeControl
		}
	}
</script>

<style>
</style>
