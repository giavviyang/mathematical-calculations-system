import request from '@/utils/request'
// 获取学生信息
export function queryStudentInfoByStudent(params) {
    return request({
        url: '/students/queryStudentInfoByStudent',
        method: 'get',
        params: params,
      // timeout:20000
    })
}
// 获取每个算法的练习总题数1
export function getAlgorithmAmount(params) {
    return request({
        url: '/analysis/getAlgorithmAmount',
        method: 'post',
        params: params,
      timeout:30000
    })
}
// 获取每个知识点的练习总题数2
export function getKnowledgeAmount(params) {
    return request({
        url: '/analysis/getKnowledgeAmount',
        method: 'post',
        params: params,
      timeout:30000
    })
}
// 获取每个知识点的练习总题数3
export function getMasterSituation(params) {
    return request({
        url: '/analysis/getMasterSituation',
        method: 'post',
        params: params,
      timeout:30000
    })
}
// 获取每个班级的知识点掌握情况4
export function getKnowledgeAnalysis(params) {
    return request({
        url: '/analysis/getKnowledgeAnalysis',
        method: 'post',
        params: params,
      timeout:30000
    })
}
// 学生闯关统计5
export function getBreakThroughAnalysis(params) {
    return request({
        url: '/analysis/getBreakThroughAnalysis',
        method: 'post',
        params: params,
      timeout:30000
    })
}
