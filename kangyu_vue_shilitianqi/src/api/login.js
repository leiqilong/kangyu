import request from '@/utils/request'

/**
 * 登录
 * @param query <br>
 *        phone 手机号
 *        password 密码
 */
export function fetchLogin(query) {
  return request({
    url: '/v1/login/login',
    method: 'post',
    data: query
  })
}

/**
 * 获取医院信息
 * @param id
 */
export function fetchHospital(id) {
  return request({
    url: '/v1/login/getHospitalById/' + id + '/' + Date.now(),
    method: 'get'
  })
}
