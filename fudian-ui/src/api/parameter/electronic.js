import request from '@/utils/request'

// 电子查阅台账
export function queryE01zcLedgerList(params) {
  return request({
    url: '/ledger/queryE01zcLedgerList',
    method: 'get',
    params: params
  })
}

// 电子查阅子集台账
export function queryE01zcRelaLedgerList(params) {
  return request({
    url: '/ledger/queryE01zcRelaLedgerList',
    method: 'get',
    params: params
  })
}

// 电子明细台账
export function queryE01zcs(params) {
  return request({
    url: '/ledger/queryE01zcs',
    method: 'get',
    params: params
  })
}

// 删除电子查阅台账
export function deleteE01zcLedger(e01zcId) {
  return request({
    url: '/ledger/deleteE01zcLedger/' + e01zcId,
    method: 'delete',
  })
}

// 删除电子查阅子集台账
export function deleteE01zcRelaLedger(id) {
  return request({
    url: '/ledger/deleteE01zcRelaLedger/' + id,
    method: 'delete',
  })
}

// 获取年份
export function queryYear() {
  return request({
    url: '/ledger/queryYear',
    method: 'get',
  })
}





