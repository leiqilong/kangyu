import request from '@/utils/request'

export function addCustomeFormTag(data) {
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
