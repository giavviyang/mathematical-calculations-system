import request from '@/utils/request'

// 实体查阅台账
export function queryE01z8LedgerList(params) {
  return request({
    url: '/ledger/queryE01z8LedgerList',
    method: 'get',
    params: params
  })
}

// 实体查阅子集台账
export function queryE01z8RelaLedgerList(params) {
  return request({
    url: '/ledger/queryE01z8RelaLedgerList',
    method: 'get',
    params: params
  })
}

// 实体明细台账
export function queryE0z8s(params) {
  return request({
    url: '/ledger/queryE0z8s',
    method: 'get',
    params: params
  })
}

// 删除实体查阅台账
export function deleteE01z8Ledger(e01z9Id) {
  return request({
    url: '/ledger/deleteE01z8Ledger/' + e01z9Id,
    method: 'delete',
  })
}

// 删除实体查阅子集台账
export function deleteE01z8RelaLedger(id) {
  return request({
    url: '/ledger/deleteE01z8RelaLedger/' + id,
    method: 'delete',
  })
}

// 获取年份 实体查阅
export function queryYear() {
  return request({
    url: '/ledger/query8Year',
    method: 'get',
  })
}

// 获取实体查阅的子集信息
export function queryE01z8A01s(id) {
  return request({
    url: '/ledger/queryE01z8A01s/' + id,
    method: 'get',
  })
}

// 修改实体查阅台账
export function updateByEntityAndList(params) {
  return request({
    url: '/ledger/updateByEntityAndList',
    method: 'post',
    contentType:false,
    processData:false,
    headers: {'Content-Type': 'multipart/form-data'},
    data:params
  })
}

// 获取pdf文件路径
export function getFileName(params) {
  return request({
    url: '/ledger/getFileName',
    method: 'get',
    params:params
  })
}


