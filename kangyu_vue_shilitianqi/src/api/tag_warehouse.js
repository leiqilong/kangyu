import request from '@/utils/request'

/**
 * 新增或修改自定义表单标签信息
 *
 * @param data 自定义表单标签信息
 */
export function addOrEditCustomFormTag(data) {
  return request({
    url: '/v1/customFormTag/addOrEditCustomFormTag',
    method: 'post',
    data
  })
}

/**
 * 查询自定义表单标签信息(分页)
 *
 * @param data tagName 标签名称<br>
 *            pageSize 每页条数<br>
 *            pageNum 当前页数<br>
 */
export function getCustomFormTagPageResult(data) {
  return request({
    url: '/v1/customFormTag/getCustomFormTagPageResult',
    method: 'post',
    data
  })
}

/**
 * 查询自定义表单标签信息(不分页)
 *
 * @param data tagType 标签类别
 */
export function getCustomFormTagList(data) {
  return request({
    url: '/v1/customFormTag/getCustomFormTagList',
    method: 'post',
    data
  })
}

/**
 * 根据id 删除自定义表单信息
 *
 * @param id id
 */
export function deleteCustomFormTagById(id) {
  return request({
    url: '/v1/customFormTag/deleteCustomFormTagById/' + id + '/' + Date.now(),
    method: 'delete'
  })
}
