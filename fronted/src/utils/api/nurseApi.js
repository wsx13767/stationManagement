import api from './api.js'

export default class nurseApi {
    static getAllNurse() {
        return api({url: '/nurse'});
    }

    static getNurse(id) {
        return api({url: `/nurse/${id}`});
    }

    static createNurse(id, name, stationIds = []) {
        return api({url: '/nurse', method: 'POST', data: {id, name, stationIds}});
    }

    static deleteNurse(id) {
        return api({url: `/nurse/${id}`, method: 'DELETE'});
    }

    static updateNurse({id, name, stationIds=[]}, originId) {
        return api({url: `/nurse/${originId}`, method: 'PUT', data: {id, name, stationIds}});
    }

    static findStationInfoByNurseId(id) {
        return api({url: `/nurse/${id}/stations`});
    }
}