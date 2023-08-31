import request from '@/utils/request'
// 获取当前目录信息
export function queryId(id) {
  return request({
    url: '/e01z1/selectById',
    method: 'get',
    params: id
  })
}
// 查询电子文件列表
export function TestDownFile(name) {
  return request({
    url: '/fileStorage/TestDownFile',
    method: 'get',
    params: name
  })
}
// 下载 获取信息
export function downloadFile(name) {
  return request({
    url: '/img/createImgs',
    method: 'get',
    params: name
  })
}
// 下载
export function download(params) {
  return request({
    url: '/common/download',
    method: 'get',
    params: params
  })
}
// 删除
export function deleteImgInfoByE01z1Id(params) {
  return request({
    url: 'img/deleteImgInfoByE01z1Id/',
    method: 'delete',
    params: params
  })
}
// 获取某一材料下所有图片信息
export function queryImgInfoByE01z1Id(params) {
  return request({
    url: 'img/queryImgInfoByE01z1Id/' + params,
    method: 'get',
  })
}
// 下载
export function downImgs(params) {
  return request({
    url: 'img/createImgs/'+params,
    method: 'get',
  })
}
// 打印
export function printImgPdfBy(params) {
  return request({
    url: 'img/createImgPdfBy/' +params,
    method: 'get'
  })

}
//  上传 先把参数 存到服务器上
export function upDataParams(params) {
  return request({
    url: '/img/putParamsByredis',
    method: 'post',
    data:params
  })
}
// 查询某一类材料
export function queryE01z1ListByA01AndE01z101(params) {
  return request({
    url: 'e01z1/queryE01z1ListByA01AndE01z101',
    method: 'get',
    params: params
  })
}
//编辑保存
export function updateE01z1(params) {
  return request({
    url: '/e01z1/updateE01z1',
    method: 'put',
    params: params
  })
}
//成功后的处理  该状态  刷新 Grid
export function updateStatus(params) {
  return request({
    url: 'task/updateStatus',
    method: 'put',
    params: params
  })
}

