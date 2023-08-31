import request from '@/utils/request'
// 查询部门树
export function queryDeptListById(params) {
  return request({
    url: '/person/queryDeptListById/' + params,
    method: 'get'
    // params: params
  })
}
// 查询人员
export function queryA01(params) {
  return request({
    url: '/person/queryA01',
    method: 'get',
    params: params
  })
}
// 干部状态
export function queryA01State() {
  return request({
    url: '/person/queryA01State',
    method: 'get'
  })
}
// 十大类
export function getTreeGrid(params) {
  return request({
    url: '/test/getTreeGrid',
    method: 'get',
    params: params
  })
}
// 删除干部材料
export function deleteE01z1ById(params) {
  return request({
    url: '/person/deleteE01z1ById',
    method: 'delete',
    params: params
  })
}
// 修改干部材料
export function updateE01z1ById(params) {
  return request({
    url: '/person/updateE01z1ById',
    method: 'put',
    params: params
  })
}
// 转移树
export function queryDeptAndA01ListById(params) {
  return request({
    url: '/person/queryDeptAndA01ListById/' + params,
    method: 'get'
  })
}
// 转移8大类
export function querySDL() {
  return request({
    url: '/person/querySDL',
    method: 'get'
  })
}
// 高级检索 确定
export function queryListByConditionList(params) {
  return request({
    url: '/person/queryListByConditionList',
    method: 'get',
    params: params
  })
}
// 基本信息查询
export function queryA01One(params) {
  return request({
    url: '/person/queryA01One/' + params,
    method: 'get'
  })
}
// 字典表
export function queryDictionaryByType(params) {
  return request({
    url: '/sysDictionary/queryDictionaryByType',
    method: 'get',
    params: params
  })
}
// 修改干部信息
export function updateA01ById(params) {
  return request({
    url: '/person/updateA01ById',
    method: 'put',
    params: params
  })
}
// 保存职务变动信息
export function insertDutyChange(params) {
  return request({
    url: '/person/insertDutyChange',
    method: 'post',
    params: params
  })
}
// 获取职务变动信息
export function queryDutyChange(params) {
  return request({
    url: '/person/queryDutyChange',
    method: 'get',
    params: params
  })
}
//更改职务变动信息
export function updateDutyChange(params) {
  return request({
    url: '/person/updateDutyChange',
    method: 'put',
    params: params
  })
}
//删除职务变动信息
export function deleteDutyChange(params) {
  return request({
    url: '/person/deleteDutyChange',
    method: 'delete',
    params: params
  })
}
//干部排序
export function sortA01(data) {
  return request({
    url: '/a01/sortA01',
    method: 'put',
    data: data
  })
}
// 删除干部
export function deletePersonById(params) {
  return request({
    url: '/a01/deletePersonById',
    method: 'delete',
    params: params
  })
}
// 部门调整
export function moveDept(params) {
  return request({
    url: '/a01/moveDept',
    method: 'put',
    params: params
  })
}
