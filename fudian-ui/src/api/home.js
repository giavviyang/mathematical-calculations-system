//首页统计图接口
import request from '@/utils/request'


//首页库房统计
export function getRoomA01States() {
  return request({
    url: '/store/arch/getRoomA01States',
    method: 'get',
  })
}


//首页档案状态统计
export function getA01States(){
  return request({
    url: '/a01/getAllA01States',
    method: 'get',
  })
}
