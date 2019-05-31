import request from '@/utils/request'

export function addCustomFormTag(data) {
  return request({
    url: '/v1/customFormTag/addOrEditCustomFormTag',
    method: 'post',
    data
  })
}

export function getCustomeFormTagList(data) {
  return request({
    url: '/v1/customFormTag/getCustomFormTagList',
    method: 'post',
    data
  })
}

export function deleteCustomFormTagById(id) {
  console.log('id:', id);
  return request({
    url: '/v1/customFormTag/deleteCustomFormTagById/' + id + '/' + Date.now(),
    method: 'delete'
  })
}
