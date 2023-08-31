import request from '@/utils/request'

export function licenseKeyCheck() {
  return request({
    url: '/license/licenseKeyCheck',
    method: 'get'
  })
}

//生成licensekey
export function generatorLicenseKey(params){
  return request({
    url: '/license/generatorLicenseKey',
    method: 'post',
    data:params
  })
}
//注册license
export function regeditLicense(params){
  return request({
    url: '/license/regeditLicense',
    method: 'post',
    data:params
  })
}

