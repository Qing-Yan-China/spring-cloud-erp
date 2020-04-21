
setRouter(data,routers){
	let router={path:'',name:'',children:[]};
	router['path']=data.url
	router['name']=data.pname
	let componentName=data.componentName
	if (componentName.indexOf('/')) {
		componentName = componentName.substring(1, componentName.length);
	}
	if (componentName.lastIndexOf('/')) {
		componentName = componentName.substring(0, componentName - 1);
	}
	router['component']=this.setView(componentName)
	routers.push(router)
	if(data.children&&data.children.length>0){
		data.children.forEach((item)=>{
			this.setRouter(item,router.children)
		})
	}
}
setView(componentName){
	return resolve=>{
		require.ensure([],(require)=>{
			resolve(require('@/path/'+componentName+'.vue'))
		})
	}
}
export default{
	getRouters(data){
		let routers=[];
		data.forEach((item)=>{
			setRouter(item,routers)
		})
		return routers;
	}
}