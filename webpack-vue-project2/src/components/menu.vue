<template>
	<!--这个表示有子菜单的情况下，才会显示-->
	<el-submenu :index="tree_menu.id+''" v-if="tree_menu.children && tree_menu.children.length>0">
	  <template slot="title">
	    <i  :class="tree_menu.icon"></i>
	    <span slot="title">{{tree_menu.title}}</span>
	  </template>
	  <template v-for="(routeurl,key,index) in tree_menu.children">
	  <!-- **注意**这个表示判断子菜单下面是否还有三级和四级菜单 -->
	    <sub_menu :tree_menu="routeurl" :key="index" v-if="routeurl.children && routeurl.children.length>0"></sub_menu>
	    <el-menu-item v-else  :index="'/index/'+routeurl.url" :key="index">
	      <i  :class="routeurl.icon"></i>
	      <span slot="title">{{routeurl.title}}----------</span>
	    </el-menu-item>
	  </template>
	</el-submenu>
	
	<!--表示一级菜单-->
	<el-menu-item v-else :index="tree_menu.url" >
	  <i  :class="tree_menu.icon"></i>
	  <span slot="title">{{tree_menu.title}}</span>
	</el-menu-item>
	<!-- <el-submenu :index="tree_menu.url==null?'null':tree_menu.url">
		<template slot="title">
			<i :class="tree_menu.icon"></i>
			<span>{{tree_menu.title}}--{{tree_menu.url}}</span>
		</template>
		<template v-if="tree_menu.children!=null && tree_menu.children.length>0">
			<template v-for="(menutree_menu,key,index) in tree_menu.children">
				<template v-if="menutree_menu.children!=null && menutree_menu.children.length>0">
					<menu-tree_menu-children :menu_tree_menu="menutree_menu"></menu-tree_menu-children>
				</template>
				<template v-else>
					<el-menu-tree_menu :index="menutree_menu.url==null?'null':menutree_menu.url" >
						<template slot="title">
							<i :class="menutree_menu.icon"></i>
							<span>{{menutree_menu.title}}--{{tree_menu.url}}</span>
						</template>
					</el-menu-tree_menu>
				</template>
			</template>
		</template> 
	</el-submenu> -->
</template>

<script>
	//import menutree_menuChildren from '@/components/menuChildren.vue'
	export default{
		name:'sub_menu',
		props:['tree_menu'],
		data(){
			return{
				
			}
		},methods:{
			
		},components:{
			//'menu-tree_menu-children':menutree_menuChildren
		},filters:{
			
		},created() {
		}
	}
</script>

<style>
</style>
