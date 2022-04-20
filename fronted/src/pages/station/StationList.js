import React, {Component} from 'react';
import { useNavigate, Link } from 'react-router-dom';
import stationApi from '../../utils/api/stationApi.js'

class StationList extends Component {
    state = {
        rows: []
    }

    constructor(props) {
        super(props);
    }

    doDelete = async (stationId) => {
        const data = await stationApi.deleteStation(stationId);

        if (data) {
            alert("刪除成功");
        }

        window.location.reload(false);
    }

    async componentDidMount() {
        const data = await stationApi.getAllStation();
        this.setState({rows: data.map(r => <tr>
            <td>{r.name}</td>
            <td>{r.updateTime}</td>
            <td>
                <Link to={`/stationDetail/${r.id}`}>View</Link><span> </span>
                <a href='javascript:;' onClick={() => this.doDelete(r.id)}>Del</a>
            </td>
        </tr>)});
    }

    render() {
        return (
            <div>
                <button><Link to="/">返回</Link></button>
                <table>
                    <thead>
                        <tr>
                            <th>站點</th>
                            <th>修改時間</th>
                            <th>動作</th>
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
    const navigation = useNavigate();
    return <StationList {...props} navigation={navigation} />;
}