import request from '@/utils/request'

//知识点table表格新增
export function klcInsert() {
  return request({
    url: '/klc/insertKLC',
    method: 'post',
    // params: id
  })
}
//知识点table表格编辑
export function updateKLC(data) {
  return request({
    url: '/klc/updateKLC',
    method: 'put',
    params: data
  })
}
//知识点table表格删除
export function removeklc(params) {
  return request({
    url: '/klc/removeklc',
    method: 'delete',
    params:params
  })
}
//根据pid获取子知识点
export function getListByPid(params) {
  return request({
    url: '/klc/getListByPid',
    method: 'get',
    params: params
  })
}
//获取知识点左侧tree树数据
export function getTreeById(params) {
  return request({
    url: '/sysDictionary/queryTreeById',
    method: 'get',
    params:params,
  })
}
//知识点分类 右侧依据左侧树节点查询右侧数据
export function getKLCByParams(params) {
  return request({
    url: '/klc/selectKLCByParams',
    method: 'get',
    params:params,
  })
}
// er/insertEr 规则新增
// er/updateEr更新规则
// er/deleteEr删除规则
// er/selectErByPid 根据分类id查询子类数据

//知识点分类 右侧依据左侧树节点查询右侧数据
export function insertKLC(params) {
  return request({
    url: '/klc/insertKLC',
    method: 'post',
    params:params,
  })
}
//设置规则
// er/insertEr
export function insertEr(params) {
  return request({
    url: '/er/insertEr',
    method: 'post',
    params:params,
  })
}
//单击表格设置规则,获取表格当前行规则数据
//er/selectErById
export function getSelectErById(params) {
  return request({
    url: '/er/selectErById',
    method: 'get',
    params:params,
  })
}
//知识点table表格当前行规则编辑
export function updateEr(data) {
  return request({
    url: '/er/updateEr',
    method: 'put',
    params: data
  })
}
//知识点排序
export function setOrderNum(params) {
  return request({
    url: '/klc/setOrderNum',
    method: 'put',
    params: params
  })
}
