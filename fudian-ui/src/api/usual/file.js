import request from '@/utils/request'
// 查询电子文件列表
export function queryFileList(e01z1Id) {
  let params = {
    e01z1Id:e01z1Id
  }
  return request({
    url: '/person/querySysFileByBindingId',
    method: 'get',
    params: params
  })
}

export function deleteFile(index,bindid) {
  let params = {
    bindingId:bindid,
    fileIndex:index
  }
  return request({
    url: '/person/deleteSysFile',
    method: 'delete',
    params: params
  })
}

