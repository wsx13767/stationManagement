import { HashRouter as Router, Routes, Route} from 'react-router-dom';
import React, {Component} from 'react';
import App from '../App';
import StationCreate from '../pages/station/StationCreate'
import StationList from '../pages/station/StationList'
import StationDetail from '../pages/station/StationDetail'
import NurseCreate from '../pages/nurse/NurseCreate'
import NurseList from '../pages/nurse/NurseList'
import NurseDetail from '../pages/nurse/NurseDetail'


class StationRoute extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return(
            <div>
                <Router>
                    <Routes>
                        <Route path='/' element={<App />} />
                        <Route path='stationCreate' element={<StationCreate />} />
                        <Route path='stationList' element={<StationList />} />
                        <Route path='stationDetail/:stationId' element={<StationDetail />} />
                        <Route path='nurseCreate' element={<NurseCreate />} />
                        <Route path='nurseList' element={<NurseList/>} />
                        <Route path='nurseDetail/:nurseId' element={<NurseDetail/>} />
                        
                    </Routes>
                </Router>
            </div>
        );
    }
}

export default StationRoute;