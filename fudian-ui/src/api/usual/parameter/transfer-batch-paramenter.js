import request from '@/utils/request'

//查询批次及明细数据
export function queryYear() {
  return request({
    url: '/transfer/queryYear',
    method: 'get',
  })
}

//查询批次及明细数据
export function queryTransferBillAndBatchList(params) {
    return request({
        url: '/transfer/queryTransferBillAndBatchList',
        method: 'get',
        params: params
    })
}

//修改转出批次信息
export function updateTransferBatch(params) {
    return request({
        url: '/transfer/updateTransferBatch',
        method: 'put',
        params: params
    })
}

//修改转出明细信息
export function updateTransferBillById(params) {
    return request({
        url: '/transfer/updateTransferBillById',
        method: 'put',
        params: params
    })
}

//删除转出批次信息及明细信息
export function deleteTransferBatchById(params) {
    return request({
        url: '/transfer/deleteTransferBatchById',
        method: 'delete',
        params: params
    })
}

//删除转出明细信息
export function deleteTransferBillByIndexs(params) {
    return request({
        url: '/transfer/deleteTransferBillByIndexs',
        method: 'delete',
        params: params
    })
}

//获取回执单电子文件
export function querySysFile(params) {
    return request({
        url: '/transfer/querySysFile',
        method: 'get',
        params: params
    })
}

//上传回执单电子文件(如存在，则提示是否替换)
export function uploadReceiptData(params) {
    return request({
        url: '/transfer/uploadReceiptData',
        method: 'post',
        params: params
    })
}

//根据批次ID删除回执单电子文件
export function deleteSysFileByBatchId(params) {
    return request({
        url: '/transfer/deleteSysFileByBatchId',
        method: 'delete',
        params: params
    })
}

//高级检索功能
export function advancedSearchBatch(params) {
  return request({
    url: '/transfer/advancedSearchBatch',
    method: 'get',
    params: params
  })
}

//获取图片
export function getImageFile(params) {
  return request({
    url: '/fileStorage/getFileToImage',
    method: 'get',
    params: params
  })
}
