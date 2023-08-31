import request from '@/utils/request'

//获取散材料数字化任务
export function queryTaskList(params) {
  return request({
    url: '/task/queryTaskList',
    method: 'get',
    params: params
  })
}

//更新任务状态
export function okTask(params) {
  return request({
    url: '/task/updateTaskStatus',
    method: 'put',
    params: params
  })
}

//删除任务
export function deleteTaskByIds(params) {
  return request({
    url: '/task/deleteTaskByIds',
    method: 'delete',
    params: params
  })
}

//查找当前任务下的材料
export function queryE01z1sByTaskId(params) {
  return request({
    url: '/task/queryE01z1sByTaskId',
    method: 'get',
    params: params
  })
}


//删除当前任务下的材料
export function deleteTaskE01z1ByTaskId(params) {
  return request({
    url: '/task/deleteTaskE01z1ByTaskId',
    method: 'delete',
    params: params
  })
}
// 编辑材料page页数
//删除当前任务下的材料
export function updateE01z1114ByE01z1Id(params) {
  return request({
    url: '/task/updateE01z1114ByE01z1Id',
    method: 'put',
    params: params
  })
}


