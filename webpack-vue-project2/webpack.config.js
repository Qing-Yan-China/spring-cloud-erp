
const path=require('path')

const htmlWebpackPlugin=require('html-webpack-plugin')

const vueLoaderPlugin=require('vue-loader/lib/plugin')
//引入错误友好提示的插件
const FriendlyErrorsWebpackPlugin=require('friendly-errors-webpack-plugin')
module.exports={
	entry:path.join(__dirname,'./src'),//出口js文件 在src文件夹下  默认的名称index.js
	output:{//输出的js文件路径
		path:path.join(__dirname,'./dist'),
		filename:'bundle.js'
	}, devtool: 'inline-source-map',
	plugins:[
		new htmlWebpackPlugin({
			//指定模板路径
			template:path.join(__dirname,'./src/index.html'),
			//默认会去找dist文件夹
			filename:'index.html'
		}),new vueLoaderPlugin(),
		//new FriendlyErrorsWebpackPlugin(
		/*{
			// 运行成功
			compilationSuccessInfo:{
			message:['你的应用程序在这里运行http:// localhost:3000'],
			notes:['有些附加说明要在成功编辑时显示']
			},
			// 运行错误
			onErrors:function(severity,errors){
			//您可以收听插件转换和优先级的错误
			//严重性可以是'错误'或'警告'
			console.log("errors:"+errors);
			console.log("severity:"+severity)
			},
			//是否每次编译之间清除控制台
			//默认为true
			clearConsole:true,
		}*/
		//)
	],devServer:{
		open:true,
		port:8080,
		hot:true,
		proxy:{//axios异步请求代理配置
			'/api':{//这个是要替换的位置
				//将/api替换为目标target
				target: 'http://localhost:8011/api/',
				changeOrigin: true,//默认是false,如果需要代理需要改成true
				pathRewrite:{
					'^/api': ''//在这里 http://localhost:8080/index/xxx 已经被替换成 http://localhost:8011/api/
				}
			}
		},
		/*,quiet:true //webpack-dev-server，webpack的devServer选项中有一个设置
		,overlay: {
			errors: true
		},*/
	},module:{
		rules:[
			{test:/\.vue$/,use:['vue-loader']},
			{//处理字体文件的loader
				test: /\.(woff|woff2|eot|ttf|otf)$/,
				use: 'file-loader'
			},
			{//处理图片的loader
				test:/\.((png|svg|jpg|gif))$/,use:'file-loader',
			},
			{//处理样式的loader 执行顺序为 从后向前
				test:/\.css$/,use:['style-loader','css-loader'],
			},{
			  test: /\.styl(us)?$/,
			  use: [
				'vue-style-loader',
				'css-loader',
				'stylus-loader'
			  ]},{
				  test:/\.sass$/,
				  use:[
					  'style-loader','css-loader','sass-loader'
				  ]
			  }
		]
	},resolve:{//解析
		extensions: ['.js','.vue'],
		alias:{//别名匹配
			"vue$":"vue/dist/vue.js",//以vue结尾的
			"@":path.join(__dirname,"src"),
			"@vue":path.join(__dirname,"src","template"),
			"@vue_system":path.join(__dirname,"src","template","system")
		}
	},
}