//库房档案管理接口
import request from '@/utils/request'

export function updateUserPwd(oldp,newp) {
  return request({
    url: '/user/updateUserPwd',
    method: 'put',
    params: {oldp:oldp,newp:newp}
  })
}

export function getLoginUserInfo() {
  return request({
    url: '/user/getLoginUserInfo',
    method: 'get',
  })
}

export function updateLoginUserInfo(params) {
  return request({
    url: '/user/updateLoginUserInfo',
    method: 'put',
    params:params
  })
}
