import request from '@/utils/request'

/**
 * 查询场景列表
 */
export function getAllModelTree() {
  return request({
    url: '/v2/chufang/getAllModelTree',
    method: 'get'
  })
}
