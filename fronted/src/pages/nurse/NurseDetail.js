import React, {useState , useEffect } from 'react';
import { useNavigate, Link,useParams } from 'react-router-dom';
import nurseApi from '../../utils/api/nurseApi.js';
import stationApi from '../../utils/api/stationApi.js';
import { MultiSelect } from "react-multi-select-component";

let options = [];
const NurseDetail = ()=> {
    const navigate = useNavigate();
    const params = useParams();
    const nurseIdRef = React.createRef();
    const nurseNameRef = React.createRef();

    const [nurseId, changeNurseId] = useState(params.nurseId);
    const [nurseName, changeNurseName] = useState('');
    const [selected, setSelected] = useState([]);

    useEffect(async ()=>{
        
        nurseApi.getNurse(nurseId).then(data => changeNurseName(data.name));

        options = [];
        const alreadyJoin = [];
        const data = await stationApi.getAllStation();

        data.forEach(element => options.push({label: element.name, value: element.id}));

        const res = await nurseApi.findStationInfoByNurseId(nurseId);
   
        options.forEach(ele => {
            res.forEach(stationHas => {
                if (stationHas.id == ele.value) {
                    alreadyJoin.push(ele);
                }
            });
        });
        setSelected(alreadyJoin);

    }, []);

    const updateNurse = async () => {
        const res = await nurseApi.updateNurse({id: nurseId, name: nurseName, stationIds: selected.map(ele => ele.value)}, params.nurseId);
        if (res) {
            alert('修改成功');
        }
        navigate('/');
    }
    
    return (
        <div>
            <button><Link to="/">返回</Link></button>
            <button onClick={updateNurse}>儲存</button>
            <div>
                <label>員工編號</label>
                <input type="number" min="0" ref={nurseIdRef} value={nurseId} onChange={(e) => changeNurseId(e.target.value)}/>
            </div>
            <div>
                <label>護士姓名</label>
                <input type="text" ref={nurseNameRef} value={nurseName} onChange={(e) => changeNurseName(e.target.value)}/>
            </div>
            <div>
                <MultiSelect
                    options={options}
                    value={selected}
                    onChange={setSelected}
                    labelledBy="Select"
                />
                <h1>已經選擇的站點</h1>
                <ul>
                    {
                        selected.map(row => 
                            <li key={row.value}>{row.label}</li>
                        )
                    }
                </ul>
            </div>
            
        </div>
    );
}

export default NurseDetail;