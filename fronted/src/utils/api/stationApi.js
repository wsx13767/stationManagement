import api from './api.js'

export default class station {
    // 取得所有站點
    static getAllStation() {
        return api({url: '/station'});
    }

    // 取得某一站點
    static getStation(id) {
        return api({url: `/station/${id}`});
    }

    // 更新站點資料
    static updateStation(id, name) {
        return api({url: `/station/${id}`, method: 'PUT', data: {name}});
    }

    // 新增站點
    static insertStation(name) {
        return api({url: '/station', method: 'POST', data: {name}});
    }

    // 刪除站點
    static deleteStation(id) {
        return api({url: `/station/${id}`, method: 'DELETE'});
    }

    // 取得站點相關的所有護士
    static findNurseInfoByStationId(stationId) {
        return api({url: `/station/${stationId}/nurses`});
    }
}