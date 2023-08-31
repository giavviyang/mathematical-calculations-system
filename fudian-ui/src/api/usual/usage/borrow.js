import request from '@/utils/request'
export function getUserInfoAndArchivesRecords(params) {
  return request({
    url: '/test/getUserInfoAndArchivesRecords',
    method: 'get',
    params: params
  })
}
export function saveArchiveUsing(params) {
  return request({
    url: '/test/saveArchiveUsing',
    method: 'post',
    params: params
  })
}
export function getArchivesRecords(params) {
  return request({
    url: '/test/getArchivesRecords',
    method: 'get',
    params: params
  })
}
//干部查询
export function quertA01ByName(params) {
  return request({
    url: '/test/quertA01ByName',
    method: 'get',
    params: params
  })
}

//到期续查
export function saveArchiveRenewAccess(params) {
  return request({
    url: '/test/saveArchiveRenewAccess',
    method: 'put',
    params: params
  })
}

//待审批页面的台账搜索功能
export function conditionSearchArchives(params) {
  return request({
    url: '/test/conditionSearchArchives',
    method: 'get',
    params: params
  })
}

//档案借阅到期续借
export function saveBorrowArchiveRenew(params) {
  return request({
    url: '/test/saveBorrowArchiveRenew',
    method: 'post',
    params: params
  })
}
//查借阅反馈填写
export function saveArchivesCheckFeedBack(params) {
  return request({
    url: '/test/saveArchivesCheckFeedBack',
    method: 'put',
    params: params
  })
}

//档案借阅归还
export function giveBackArchives(params) {
  return request({
    url: '/test/giveBackArchives',
    method: 'put',
    params: params
  })
}

//获取查阅申请信息   干部材料树
export function queryE01zcRelaTreeByE01zcId(e01zcId) {
  return request({
    url: '/test/querye01zctree/' + e01zcId,
    method: 'get',
  })
}

//实体档案查借阅申请
export function checkBorrowEntityFile(params){
  return request({
    url: '/test/checkBorrowEntityFile',
    method: 'post',
    contentType:false,
    processData:false,
    headers: {'Content-Type': 'multipart/form-data'},
    data:params
  })
}



//实体档案查阅记录
export function getEntityArchiveCApplyRecord(params){
  return request({
    url: '/test/getEntityArchiveCApplyRecord/',
    method: 'get',
    params:params
  })
}

//实体档案借阅记录
export function getEntityArchiveAApplyRecord(params){
  return request({
    url: '/test/getEntityArchiveAApplyRecord/',
    method: 'get',
    params:params
  })
}

//实体/电子档案查借阅到期续查
export function archiveRenewalOfMaturity(params){
  return request({
    url: '/test/archiveRenewalOfMaturity/',
    method: 'put',
    params:params
  })
}
//实体/电子档案查借阅审批
export function archiveApply(params){
  return request({
    url: '/test/archiveApply/',
    method: 'put',
    params:params
  })
}

//实体/电子档案查借阅查看详情
export function viewDetails(params){
  return request({
    url: '/test/viewDetails/',
    method: 'get',
    params:params
  })
}

//实体/电子档案查借阅查看详情
export function revertArchives(params){
  return request({
    url: '/test/revertArchive/',
    method: 'put',
    params:params
  })
}

