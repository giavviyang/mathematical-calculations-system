import request from '@/utils/request'
// 档案材料搜索
export function searchKeyWord(params) {
  return request({
    url: '/test/searchKeyWord',
    method: 'get',
    params: params
  })
}
//判断用户点击的材料是否有权限查看
export function judgeAuthority(params) {
  return request({
    url: '/test/judgeAuthority',
    method: 'get',
    params: params
  })
}
// 获取用户的材料分类树
export function getType(params) {
  return request({
    url: '/dataPermissions/getType',
    method: 'get',
    params: params
  })
}
// 获取用户的电子文件
export function getSysFile(params) {
  return request({
    url: '/dataPermissions/getSysFile',
    method: 'get',
    params: params
  })
}
//档案浏览权限部门树
export function getDept(params) {
  return request({
    url: '/dataPermissions/getDept',
    method: 'get',
    params: params
  })
}

//权限部门下的干部
export function getA01(params) {
  return request({
    url: '/dataPermissions/getA01',
    method: 'get',
    params: params
  })
}
