import request from '@/utils/request'

// 查询区域列表
export function listArea(query) {
    return request({
        url: '/sys/area/list',
        method: 'get',
        params: query
    })
}
// 查询区域树
export function listAreaTree(query) {
    return request({
        url: '/sys/area/tree',
        method: 'get',
        params: query
    })
}
// 查询区域详细
export function getArea(areaId) {
    return request({
        url: '/sys/area/' + areaId,
        method: 'get'
    })
}

// 新增区域
export function addArea(data) {
    return request({
        url: '/sys/area',
        method: 'post',
        data: data
    })
}

// 修改区域
export function updateArea(data) {
    return request({
        url: '/sys/area',
        method: 'put',
        data: data
    })
}

// 删除区域
export function delArea(areaId) {
    return request({
        url: '/sys/area/' + areaId,
        method: 'delete'
    })
}