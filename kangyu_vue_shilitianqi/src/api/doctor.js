import request from '@/utils/request'

/**
 * 获取医生信息列表（分页）
 * @param query 查询条件 name
 */
export function fetchDoctorList(query) {
  return request({
    url: '/v1/doctor/getDoctorList',
    method: 'post',
    data: query || {}
  })
}

/**
 * 删除医生信息
 * @param id 医生id
 */
export function fetchDoctorDelete(id) {
  return request({
    url: '/v1/doctor/deleteDoctor/' + id + '/' + Date.now(),
    method: 'delete'
  })
}

/**
 * 新增或修改医生信息
 * @param data 医生信息
 */
export function fetchDoctorEdit(data) {
  return request({
    url: '/v1/doctor/createOrEditDoctor',
    method: 'post',
    data: data
  })
}

/**
 * 获取医生详细信息
 * @param id 医生id
 */
export function getDoctorDetail (id) {
  return request({
    url: '/v1/doctor/getDoctorById/' + id + '/' + Date.now(),
    method: 'get'
  })
}
