<template>

	<el-container style="height: 100%;">
		<el-aside width="240px" style="height: 100%;">
			<!-- 左侧导航菜单 -->
			<tree-node style="height: 100%;"></tree-node>
		</el-aside>
		<el-container style="margin-left: 2px;">
			<!-- 头部 -->
			<el-header class="d-flex align-items-center" style="background: #545c64;">
				<!-- 头部组件 -->
				<header-component></header-component>
			</el-header>
			<!-- 主体main -->
			<el-main v-bind:xs="18" height="80%" style="background-color: #f5f6fa;">
				<div style="background-color: #FFFFFF;padding: 10px;">
					<router-view></router-view>
				</div>
			</el-main>
		</el-container>
	</el-container>
</template>
<script>
	import header from '@/components/header.vue'
	import treeNode from '@/components/treeNode.vue'
	export default {
		name: 'index',
		components: {
			"header-component": header,
			'tree-node': treeNode
		}
		/*
		<el-container direction="horizontal">
			<!-- 左侧树状菜单 -->
			<el-aside v-bind:xs="6">
				<tree-node></tree-node>
			</el-aside>
			<!-- 主体main -->
			<el-main v-bind:xs="18" height="80%" style="background-color: #f5f6fa;">
				<div style="background-color: #FFFFFF;padding: 10px;">
					<router-view></router-view>
				</div>
			</el-main>
		</el-container>
		*/
	}
</script>

<style>
	.el-header {
		background-color: #B3C0D1;
		color: #333;
	}

	.el-aside {
		color: #333;
	}
	 .el-dropdown-link {
	    cursor: pointer;
	    color: #409EFF;
	  }
</style>
