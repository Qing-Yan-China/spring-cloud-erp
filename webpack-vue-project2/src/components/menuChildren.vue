<template>
	<el-menu-item :index="menu_item.url==null?'null':menu_item.url">
		<template slot="title">
			<i :class="menu_item.icon"></i>
			<span>{{menu_item.title}}</span>
		</template>
		<template v-if="menu_item.children!=null && menu_item.children.length>0">
			<template v-for="(menuItem,key,index) in menu_item.children">
				<template v-if="menuItem.children!=null && menuItem.children.length>0">
					<menu-item-children :menu_item="menuItem"></menu-item-children>
				</template>
				<template v-else>
					<el-menu-item :index="menuItem.url==null?'null':menuItem.url">
						<template slot="title">
							<i :class="menu_item.icon"></i>
							<span>{{menuItem.title}}</span>
						</template>
					</el-menu-item>
				</template>
			</template>
		</template>
	</el-menu-item>
</template>

<script>
	export default{
		name:'menu-item-children',
		props:['menu_item'],
		data(){
			return{
				
			}
		}
	}
</script>

<style>
</style>
