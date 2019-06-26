import request from '@/utils/request'

/**
 * 查询场景列表
 */
export function searchScenesList() {
  return request({
    url: '/v1/scenes/searchScenesList',
    method: 'get'
  })
}

/**
 * 查询场景列表分页
 */
export function searchScenesListByParams(data) {
  return request({
    url: '/v1/scenes/searchScenesListByParams',
    method: 'post',
    data
  })
}

/**
 * 保存场景基本数据
 * @param data 场景基本数据 <br>
 */
export function saveOrEditScenes(data) {
  return request({
    url: '/v1/scenes/saveOrEditScenes',
    method: 'post',
    data
  })
}

/**
 * 根据id删除  场景
 * @param scenesId 场景 id
 */
export function deleteScenesById(scenesId) {
  return request({
    url: '/v1/scenes/deleteScenesById/' + scenesId + '/' + Date.now(),
    method: 'delete',
  })
}

/**
 * 新增/修改设备
 * @param data 设备信息
 */
export function saveOrEditDevice(data) {
  return request({
    url: '/v1/scenes/saveOrEditDeviceOfScenes',
    method: 'post',
    data
  })
}

/**
 * 根据场景 id 查询 对应的设备列表
 * @param scenesId 场景 id
 */
export function searchDeviceList(scenesId) {
  return request({
    url: '/v1/scenes/searchDeviceOfScenesList/' + scenesId + '/' + Date.now(),
    method: 'get',
  })
}

/**
 * 根据设备id删除
 */
export function deleteDeviceById(deviceOfScenesId) {
  return request({
    url: '/v1/scenes/deleteDeviceOfScenes/' + deviceOfScenesId + '/' + Date.now(),
    method: 'delete',
  })
}

/**
 * 新增/修改设备规则信息
 *
 * @param data 设备规则信息
 */
export function saveJudgeStandard(data) {
  return request({
    url: '/v1/scenes/saveOrEditJudgeStandard',
    method: 'post',
    data
  })
}

/**
 * 新增/修改设备规则信息
 *
 * @param data 设备规则信息
 */
export function searchJudgeStandardList(deviceOfScenesId) {
  return request({
    url: '/v1/scenes/searchJudgeStandardList/' + deviceOfScenesId + '/' + Date.now(),
    method: 'get',
  })
}

/**
 *
 * @param judgeStandardId
 */
export function deleteJudgeStandard(judgeStandardId) {
  return request({
    url: '/v1/scenes/deleteJudgeStandard/' + judgeStandardId + '/' + Date.now(),
    method: 'delete',
  })
}

/**
 * 查询自定义表单标签信息(不分页)
 *
 * @param data tagType 标签类别
 */
export function getCustomFormTagList(data) {
  return request({
    url: '/v1/scenes/getCustomFormTagList',
    method: 'post',
    data
  })
}


