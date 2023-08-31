import request from '@/utils/request'
// 统计干部状态
export function querySummaryA01Status() {
  return request({
    url: '/summary/querySummaryA01Status',
    method: 'get'
  })
}

// 统计电子查阅
export function summaryE01zc() {
  return request({
    url: '/summary/summaryE01zc',
    method: 'get'
  })
}

// 统计实体查阅
export function summaryE01z8() {
  return request({
    url: '/summary/summaryE01z8',
    method: 'get'
  })
}

// 统计实体借阅
export function summaryE01z9() {
  return request({
    url: '/summary/summaryE01z9',
    method: 'get'
  })
}

// 统计转入
export function summaryE01z5() {
  return request({
    url: '/summary/summaryE01z5',
    method: 'get'
  })
}

// 统计转出
export function summaryTransfer() {
  return request({
    url: '/summary/summaryTransfer',
    method: 'get'
  })
}

// 材料接收
export function summaryE01z2() {
  return request({
    url: '/summary/summaryE01z2',
    method: 'get'
  })
}

