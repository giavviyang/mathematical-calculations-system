import request from "@/utils/request";

//根据年级查询关卡
export function getLevelTableList(params) {
  return request({
    url: '/levelTable/queryLevelTableList',
    method: 'get',
    params:params,
  })
}
//保存关卡
export function saveLevelTable(data) {
  return request({
    url: '/levelTable/saveLevelTable',
    method: 'post',
    data:data,
  })
}
//删除关卡
export function removeLevelTableById(params) {
  return request({
    url: '/levelTable/removeLevelTableById',
    method: 'delete',
    params:params,
  })
}
//获取最大关卡等级
export function getMaxLevelNum(params) {
  return request({
    url: '/levelTable/getMaxLevelNum',
    method: 'get',
    params:params
  })
}

