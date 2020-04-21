<template>
		<el-autocomplete @change="handleSelect(icon)" v-model="icon" :label="label" style="width: 400px;" @select="handleSelect" :popper-class="'iconselect'" 
		 :fetch-suggestions="querySearch" placeholder="请输入内容">
			<i :class="value" slot="suffix"></i>
			<template slot-scope="{ item }">
				<div class="name" :title="item"><i style="font-size: 30px;" :class="item"></i></div>
			</template>
		</el-autocomplete>
</template>
<script>
	import iconJson from '@/json/icon_className.json';
	export default {
		name: 'icon',
		model: {
			prop: 'value',
			event: 'selected',
		},
		props: {
		    // 接收绑定参数 - 图标类名
		    value: {
		      type: String,
		      required: true,
		    },
		    // 选项数据，图标类名数组
		    label:{
				type:String,
				default:()=>('菜单图标')
			}
		  },
		data() {
			return {
				icons: [],
				icon: '',
			}
		},
		methods: {
			getIcons() {
				this.icons = iconJson;
			},
			querySearch(query, callback) {
				//query:查询的名字
				//callback:回调函数  放入数据
				if (query == '') {
					callback(this.icons);
				} else {
					let results = this.icons.filter(name => {
						return name.includes(query);
					})
					callback(results);
				}
			},
			handleSelect(item) {
				this.icon=item;
				this.$emit('selected',item);
			}
		},
		created() {
			this.getIcons();
			
		}
	}
</script>
<style scoped>
	.iconselect {
		li {
			display: inline-block;
			border: 1px solid red;
		}
	}
</style>
