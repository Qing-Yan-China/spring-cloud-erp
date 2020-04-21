<template>
	<div id="permission">
		<router-view></router-view>
		<el-row type="flex" class="row-bg" justify="space-around">
			<el-form :inline="true" class="demo-form-inline">
				<el-form-item label="权限名称">
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
					
					<el-button native-type="button" v-on:click.prevent="searchPermission"  type="primary" icon="el-icon-search">搜索</el-button>
				</el-form-item>
			</el-form>
			<div>
				<el-button type="danger" v-on:click="$router.push({path:'/index/permission.html/insert'})">新增权限</el-button>
			</div>
		</el-row>
		<!-- 
		 default-sort  默认的排序 是一个对象
		 属性:prop:指定的排序列名 order:排序方式
		 有 ascending or descending
		 -->
		 <!-- :default-sort="sort_regulation" -->
		<el-table :border="true"
			:highlight-current-row="true"
		    style="width: 100%" :data="permissionPage.list" 
			:default-sort = "{prop: 'sort', order: 'ascending'}"
			 @sort-change="sortChange">
			<el-table-column label="权限名称" prop="pName" :sortable="custom">
				<template slot-scope="scope">
					{{scope.row.pName}}
				</template>
			</el-table-column>
			<el-table-column label="权限父类名称">
				<template slot-scope="scope">
					{{scope.row.parentPermission|whetherShow('pName')}}
				</template>
			</el-table-column>

			<el-table-column label="权限类型" prop="pType" :sortable="custom">
				<template slot-scope="scope">
					{{scope.row.pType}}
				</template>
			</el-table-column>

			<el-table-column align="center" style="font-size: 30px;" label="图标">
				<template slot-scope="scope">
					<template v-if="scope.row.icon!=''&&scope.row.icon!=null">
						<i v-bind:class="scope.row.icon"></i>
					</template>
					<template v-else>
						暂无图标信息
					</template>
				</template>
			</el-table-column>

			<el-table-column label="权限资源" >
				<template slot-scope="scope">
					{{scope.row|whetherShow('resource','暂无资源数据')}}
				</template>
			</el-table-column>

			<el-table-column label="资源路径" >
				<template slot-scope="scope">
					{{scope.row|whetherShow('url','暂无路径数据')}}
				</template>
			</el-table-column>
			<el-table-column label="状态" prop="permissionStatus" :sortable="custom">
				<template slot-scope="scope">
					<el-tooltip v-bind:content="scope.row.permissionStatus==1?'可用状态':'禁用状态'" placement="top">
						<el-switch @change="permissionStatusChange(scope.row)" v-model="scope.row.permissionStatus" active-color="#13ce66" inactive-color="red" 
						  :active-value="1"
						  :inactive-value="2">
						</el-switch>
					</el-tooltip>
				</template>
			</el-table-column>
			<el-table-column label="排序号" prop="sort" :sortable="custom">
				<template slot-scope="scope">
					{{scope.row.sort}}
				</template>
			</el-table-column>
			<el-table-column label="说明" >
				<template slot-scope="scope">
					{{scope.row|whetherShow('describe','暂无说明')}}
				</template>
			</el-table-column>
			<el-table-column label="操作" width="120px">
			  <template slot-scope="scope">
				  <!-- circle是否为圆形按钮  false -->
			    <el-button type="primary" title="编辑" icon="el-icon-edit" circle @click="compilePermission(scope.row.pId)"></el-button>
				<el-button type="danger" @click="deletePermission(scope.row.pId)" title="删除" icon="el-icon-delete" circle></el-button>
			  </template>
			</el-table-column>
		</el-table>
		<br />
		<div class="block">
			<el-pagination style="text-align: right;"
			  background
			  :current-page.sync="params.pageNum"
			  layout="total,prev, pager, next,jumper,slot"
			  :total="permissionPage.total"
			  :page-size="permissionPage.pageSize"
			  @current-change="currentPageNumChange">
			  <span>&nbsp;&nbsp;总页数:{{permissionPage.pages}}</span>
			</el-pagination>
		</div>
	</div>
</template>

<script>
	export default {
		name: 'permission',
		data() {
			return {
				custom:'custom',
				permissionPage:{},
				params:{
					pageNum:1,
					pageSize:5,
					keyWord:'',
					orderingRule:{
						prop:'sort',
						order:'ascending'
					}
				}
			}
		},
		methods: {
			sortChange(sort){
				this.params.orderingRule.prop=sort.prop;
				this.params.orderingRule.order=sort.order;
				this.searchPermission();
			},searchPermission(){
				// this.$post('/api/permission/search',this.params)
				// 	.then(response=>{
				// 		this.permissionPage=response.data;
				// 	})
				let params=this.$Qs.stringify(this.params);
				this.$axios.post('/api/permission/search',this.params)
					.then(response=>{
						this.permissionPage=response.data;
					})
			},compilePermission(pId){//编辑权限
				this.$router.push({path:'/index/permission.html/update/'+pId})
			},permissionStatusChange(permission){//改变状态
				let pId=permission.pId;
				let newPermissionStatus=permission.permissionStatus;
				this.$axios.get('/api/permission/updateStatus/'+pId+'/'+newPermissionStatus)
					.then(response=>{
						if(response.statusCord==200){
							//permission.permissionStatus=response.data
							this.$message({
								message:'设置成功!',
								type:'success'
							})
						}else{
							this.$message({
								type:'error',
								message:'设置失败!'
							})
						}
					})
			},deletePermission(pId){
				this.$confirm('是否删除该条数据?','提示',{
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
					center: true
				}).then(()=>{
					this.$axios.get('/api/permission/delete/'+pId)
						.then(response=>{
							if(response.statusCord==200){
								this.$message({
									type:'success',
									message:response.message?'删除成功':response.message
								})
								this.searchPermission();
							}else{
								this.$message({
									type:'error',
									message:'删除失败!'
								})
							}
						})
				}).catch(()=>{
					this.$message({
						type:'info',
						message:'已取消删除!'
					})
				})
			},currentPageNumChange(pageNum){//当前页改变时触发
				this.params.pageNum=pageNum;
				this.searchPermission();
			}
		},
		created() {
			this.searchPermission();
		},computed:{
			pageSizes(){
				return this.$store.getters.getPageSizes;
			}
		},filters:{
			whetherShow(obj,property,message){
				if(message==undefined||message=='')
					message="无"
				if(obj==''||obj==null){
					return message;
				}
				if(obj[property]==''||obj[property]==null)
					return message;
				return obj[property];
			}
		}
	}
</script>

<style>
</style>
