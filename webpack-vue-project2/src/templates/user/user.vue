<template>
	<div id="user">
		<router-view></router-view>
		<el-row type="flex" class="row-bg" justify="space-around">
			<el-form :inline="true" class="demo-form-inline">
				<el-form-item label="用户账号或名称">
					<el-input v-model="params.keyWord" placeholder="用户账号或名称"></el-input>
				</el-form-item>
				<el-form-item label="页面大小">
					<el-select v-model="params.pageSize" placeholder="页面大小">
						<el-option v-for="(pageSize,index) in pageSizes" v-bind:key="index" v-bind:value="pageSize">
							{{pageSize}}
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<el-button native-type="button" @click="searchUser" type="primary" icon="el-icon-search">搜索</el-button>
				</el-form-item>
			</el-form>
			<div>
				<el-button type="danger" v-on:click="insertUser">新增用户</el-button>
			</div>
		</el-row>
		<el-table :data="userPage.list" :border="true" :highlight-current-row="true" style="width: 100%" ref="user_table"
		 :default-sort="{prop: params.orderingRule.prop, order: params.orderingRule.order}" @sort-change="sortChange"
		 @expand-change="expandChange">
			<!-- type=expand=>可展开的 -->
			<el-table-column type="expand">
				<template slot-scope="{ row,column,$index }">
					<chooser-component :roles="usableRoleChooser" :id="row.id"></chooser-component>
				</template>
			</el-table-column>
			<el-table-column label="账号" prop="uid" :sortable="custom">
				<template slot-scope="scope">
					<span>
						{{ scope.row.uid }}</span>
				</template>
			</el-table-column>
			<el-table-column label="姓名">
				<template slot-scope="scope">
					<span>
						{{ scope.row.uname }}</span>
				</template>
			</el-table-column>
			<el-table-column label="头像">
				<template slot-scope="scope">
					<!-- shape 设置图片的形状 square正方形  -->
					<el-avatar shape="square" :alt="scope.row.uname" :size="50" :src="'http://localhost:8011/upload/showImage/'+scope.row.imgPath" @error="true"></el-avatar>
				</template>
			</el-table-column>
			<el-table-column label="地址">
				<template slot-scope="scope">
					<span title="scope.row.address">
						{{ scope.row.address }}</span>
				</template>
			</el-table-column>
			<el-table-column label="状态" prop="userStatus" :sortable="custom">
				<template slot-scope="scope">
					<el-tooltip v-bind:content="scope.row.userStatus==1?'可用状态':'禁用状态'" placement="top">
						<el-switch v-model="scope.row.userStatus" active-color="#13ce66" inactive-color="red" active-text="可用"
						 inactive-text="禁用" :active-value="1" :inactive-value="2">
						</el-switch>
					</el-tooltip>
				</template>
			</el-table-column>
			<el-table-column label="创建时间" prop="createTime" :sortable="custom">
				<template slot-scope="scope">
					<i class="el-icon-time"></i>
					<span>{{ scope.row.createTime }}</span>
				</template>
			</el-table-column>
			<el-table-column label="操作">
				<template slot-scope="scope">
					<!-- circle是否为圆形按钮  false -->
					<el-button type="primary" title="编辑" icon="el-icon-edit" @click="updateUser(scope.row.uid)" circle></el-button>
					<el-button type="danger" title="删除" icon="el-icon-delete" circle></el-button>
				</template>
			</el-table-column>
		</el-table>
		<br />
		<div class="block">
			<el-pagination style="text-align: right;" background :current-page.sync="params.pageNum" layout="total,prev, pager, next,jumper,slot"
			 :total="userPage.total" :page-size="userPage.pageSize" @current-change="currentPageNumChange">
				<span>&nbsp;&nbsp;&nbsp;总页数:<span>{{userPage.pages}}</span></span>
			</el-pagination>
		</div>
	</div>
</template>

<script>
	import chooser from '@/templates/system/chooser.vue'
	export default {
		data() {
			return {
				custom: 'custom', //custom表示远程排序
				userPage: {},
				params: {
					pageNum: 1,
					pageSize: 5,
					keyWord: '',
					orderingRule: {
						prop: 'createTime',
						order: 'ascending'
					}
				},
				usableRoleChooser: [],
				prefixUser: ''
			}
		},
		methods: {
			updateUser(uid){
				this.$router.push({path:'/index/user.html/update/'+uid})
			},
			insertUser(){
				this.$router.push({path:'/index/user.html/insert'})
			},
			searchUser() {  
				this.$axios.post('api/user/search', this.params)
					.then(response => {
						this.userPage = response.data;
					})
			},
			currentPageNumChange(currentPageNum) {//页数发生改变
				this.params.pageNum = currentPageNum;
				this.searchUser();
			},
			getUsableRoleChooser() { //获取可用的角色选择器
				this.$axios.get('api/role/usableChooser')
					.then(response => {
						this.usableRoleChooser = response.data;
					})
			},
			sortChange(sort) { //当排序发生改变时发生
				this.params.orderingRule.prop = sort.prop;
				this.params.orderingrule.order = sort.order;
				this.searchUser();
			},
			expandChange(row) { //当点击展开或者关闭的时候触发
				if(this.usableRoleChooser==''||this.usableRoleChooser.length==0)
					this.getUsableRoleChooser()
				let prefixUser = this.prefixUser;
				if (prefixUser != '' && prefixUser.id == row.id) {
					//说明它点击的是同一个展开行
					prefixUser = '';
				} else {
					//关闭之前的展开行
					if(prefixUser != '')
						this.$refs.user_table.toggleRowExpansion(prefixUser,false)
					prefixUser=row;
				}
			}
		},
		created() {
			this.searchUser();
		},
		components: {
			"chooser-component": chooser
		},
		computed: {
			pageSizes() {
				return this.$store.getters.getPageSizes;
			}
		},
		filters: {

		}
	}
</script>

<style>
</style>
