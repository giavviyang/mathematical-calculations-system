import request from '@/utils/request'


// 下载方法
export function download(fileName) {
	window.location.href = fileName;
}
//保存档案与档案接收记录
export function insertA01(params) {
  return request({
    url: '/a01/insertA01',
    method: 'post',
    params: params
  })
}

//获取字典表数据
export function queryDictionaryByType(params) {
  return request({
    url: '/sysDictionary/queryDictionaryByType',
    method: 'get',
    params: params
  })
}

//获取部门树
export function queryDeptTree(params) {
  return request({
    url: '/system/dept',
    method: 'get',
    params: params
  })
}

//获取简拼
export function queryPingYin(params) {
  return request({
    url: '/a01/queryPingYin',
    method: 'get',
    params: params
  })
}
//获取库房下拉数据
export function queryRoomList() {
  return request({
    url: '/store/room',
    method: 'get',
  })
}
//获取材料信息
export function queryE01z1Grid(params) {
  return request({
    url: '/e01z1/queryE01z1Grid',
    method: 'get',
    data: params
  })
}
//获取材料类型树结构
export function queryArchiveTree() {
  return request({
    url: '/sysDictionary/queryArchiveTree',
    method: 'get',
  })
}
//获取字典表数据
export function queryDictionaryByTypes(params) {
  return request({
    url: '/sysDictionary/queryDictionaryByTypes',
    method: 'get',
    params: params
  })
}

// 导入档案目录并保存  hwy
export function insertE01z1s(params) {
  return request({
    url: '/e01z1/insertE01z1s',
    method: 'post',
    params: params
  })
}
// 生成整本数字化任务  hwy
export function insertWfTask(params) {
  return request({
    url: '/e01z1/insertWfTask',
    method: 'post',
    params: params
  })
}

//新增材料并生成数字化任务
export function insertE01z1sAndTask(params) {
  return request({
    url: '/e01z1/insertE01z1sAndTask',
    method: 'post',
    // params: params
    data: params
  })
}
//获取库房结构
export function queryRoomArch(params) {
  return request({
    url: '/a01/queryRoomStruct',
    method: 'get',
    params: params
  })
}
//材料录入列表数据
export function queryE01z1TreeGrid(params) {
  return request({
    url: '/e01z1/queryE01z1Grid',
    method: 'post',
    params: params
  })
}
//材料录入列表数据
export function queryA01ById(params) {
  return request({
    url: '/a01/queryA01ById',
    method: 'get',
    params: params
  })
}

//获取当前登录用户
export function queryLoginUser() {
  return request({
    url: '/a01/getLoginUser',
    method: 'get',
  })
}

//获取当前登录用户
export function saveE01z1s(params) {
  return request({
    url: '/e01z1/updateE01z1s',
    method: 'put',
    data:params
  })
}

//制作excel
export function getE01z1Excel(params) {
  return request({
    url: '/e01z1/getE01z1Excel',
    method: 'get',
    params:params
  })
}

//新增任务
export function addE01z1Task(params) {
  return request({
    url: '/task/addE01z1Task',
    method: 'post',
    data:params
  })
}

//新增任务
export function createTaskByA01Id(params) {
  return request({
    url: '/e01z1/createTaskByA01Id',
    method: 'post',
    params:params
  })
}

//保存单条材料
export function insertOneArchiveData(params) {
  return request({
    url: '/e01z1/insertOneArchiveData',
    method: 'post',
    params:params
  })
}



//保存单条材料
export function moveClassify(params) {
  return request({
    url: '/e01z1/moveClassify',
    method: 'put',
    params:params
  })
}




