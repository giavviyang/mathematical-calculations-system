import request from '@/utils/request'

//查询批次及明细数据
export function queryTransferBillList(params) {
    return request({
        url: '/transfer/queryTransferBillList',
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

//删除转出明细信息
export function deleteTransferBillByIndexs(params) {
    return request({
        url: '/transfer/deleteTransferBillByIndexs',
        method: 'delete',
        data: params
    })
}

//高级检索功能
export function advancedSearchDetail(params) {
  return request({
    url: '/transfer/advancedSearchDetail',
    method: 'get',
    params: params
  })
}
