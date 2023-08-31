import request from '@/utils/request'
//台账获取
export function getArchivesCheckRecords(params) {
  return request({
    url: '/test/getArchivesCheckRecords',
    method: 'get',
    params: params
  })
}
//台账删除
export function deleteArchivesRecords(params) {
  return request({
    url: '/test/deleteArchivesRecords',
    method: 'delete',
    params: params
  })
}

//台账修改
export function updateArchivesRecords(params) {
  return request({
    url: '/test/updateArchivesRecords',
    method: 'put',
    params: params
  })
}

//台账搜索
export function searchArchivesCheckRecords(params) {
  return request({
    url: '/test/searchArchivesCheckRecords',
    method: 'get',
    params: params
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

// 删除详细表
export function deleteSonArchivesRecords(params) {
  return request({
    url: '/test/deleteSonArchivesRecords',
    method: 'delete',
    params: params
  })
}



