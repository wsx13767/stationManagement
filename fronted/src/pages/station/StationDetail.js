import React, {Component} from 'react';
import { useNavigate, Link , useParams } from 'react-router-dom';
import stationApi from '../../utils/api/stationApi.js'

class StationDetail extends Component {
    state = {
        rows: [],
        stationName: ''
    }

    constructor(props) {
        super(props)
        this.stationNameRef = React.createRef();
    }

    async componentDidMount() {
        const data = await stationApi.findNurseInfoByStationId(this.props.params.stationId);
        const stationData = await stationApi.getStation(this.props.params.stationId);
        this.setState({
            rows: data.map(r => <tr>
                    <td>{r.id}</td>
                    <td>{`${r.joinTime[0]}/${r.joinTime[1]}/${r.joinTime[2]} ${r.joinTime[3]}:${r.joinTime[4]}:${r.joinTime[5]}`}</td>
                </tr>),
            stationName: stationData.name
        });
    }

    goHome = ()=> {
        this.props.navigation('/');
    }

    saveStation = async (e)=> {
        e.stopPropagation();
        const data = await stationApi.updateStation(this.props.params.stationId, this.stationNameRef.current.value);

        if (data) {
            alert('更新成功');
        }

        this.props.navigation('/');
    }

    changeState = (event)=> {
        this.setState({stationName:event.target.value})
    }

    render() {
        return (
            <div>
                <button onClick={this.goHome}>返回</button>
                <button onClick={this.saveStation}>儲存</button><br/>
                <label>站點名稱</label>
                <input type="text" ref={this.stationNameRef} value={this.state.stationName}
                  onChange={this.changeState} />
                <h3>站點護士列表</h3>
                <table>
                    <thead>
                        <tr>
                            <th>員工編號</th>
                            <th>加入時間</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.rows}
                    </tbody>
                </table>
            </div>
        );
    }
}
export default function(props) {
    const params = useParams();
    const navigation = useNavigate();
    return <StationDetail  {...props} navigation={navigation} params={params}/>;
};