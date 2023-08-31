import request from '@/utils/request'

// 实体借阅台账
export function queryE01z9LedgerList(params) {
  return request({
    url: '/ledger/queryE01z9LedgerList',
    method: 'get',
    params: params
  })
}

// 实体借阅子集台账
export function queryE01z9RelaLedgerList(params) {
  return request({
    url: '/ledger/queryE01z9RelaLedgerList',
    method: 'get',
    params: params
  })
}

// 借阅明细台账
export function queryE0z9s(params) {
  return request({
    url: '/ledger/queryE0z9s',
    method: 'get',
    params: params
  })
}

// 删除实体借阅台账
export function deleteE01z9Ledger(e01z9Id) {
  return request({
    url: '/ledger/deleteE01z9Ledger/' + e01z9Id,
    method: 'delete',
  })
}

// 删除实体借阅子集台账
export function deleteE01z9RelaLedger(id) {
  return request({
    url: '/ledger/deleteE01z9RelaLedger/' + id,
    method: 'delete'
  })
}

// 获取年份 实体借阅
export function queryYear() {
  return request({
    url: '/ledger/query9Year',
    method: 'get',
  })
}

// 获取实体借阅的子集信息
export function queryE01z9A01s(id) {
  return request({
    url: '/ledger/queryE01z9A01s/' + id,
    method: 'get',
  })
}

// 修改实体借阅台账
export function updateByEntityBorrowAndList(params) {
  return request({
    url: '/ledger/updateByEntityBorrowAndList',
    method: 'post',
    contentType:false,
    processData:false,
    headers: {'Content-Type': 'multipart/form-data'},
    data:params
  })
}

// 获取pdf文件路径
export function getFileName9(params) {
  return request({
    url: '/ledger/getFileName9',
    method: 'get',
    params:params
  })
}
