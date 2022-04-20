import React, {Component} from 'react';
import { useNavigate } from 'react-router-dom';
import stationApi from '../../utils/api/stationApi.js'

class StationCreate extends Component {
    constructor(props) {
        super(props);
        this.stationNameRef = React.createRef();
    }


    goHome = ()=> {
        this.props.navigation('/');
    }

    addStation = async (e)=> {
        e.stopPropagation();
        const data = await stationApi.insertStation(this.stationNameRef.current.value);

        if (data) {
            alert('新增成功');
        }

        this.props.navigation('/');
    }

    render(){
        return (
            <div>
                <button onClick={this.goHome}>返回</button>
                <button onClick={this.addStation}>新增</button>
                <div>
                    <label>站點名稱</label>
                    <input type="text" ref={this.stationNameRef} />
                </div>
            </div>
        );
    }
}

export default function(props) {
    const navigation = useNavigate();
    return <StationCreate {...props} navigation={navigation} />;
}