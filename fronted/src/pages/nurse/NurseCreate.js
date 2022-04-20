import React, {useState , useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import nurseApi from '../../utils/api/nurseApi.js';
import stationApi from '../../utils/api/stationApi.js';
import { MultiSelect } from "react-multi-select-component";

let options = [];
const NurseCreate = ()=> {
    const navigate = useNavigate();
    const [selected, setSelected] = useState([]);

    useEffect(()=>{
        options = [];
        stationApi.getAllStation().then(data => {
            data.forEach(element => {
                const row = {label: element.name, value: element.id};
                options.push(row);
            });
        });
    }, []);
    
    

    const nurseId = React.createRef();
    const nurseName = React.createRef();

    const addNurse = async ()=> {
        if (!nurseId.current.value || nurseId.current.value.lenght === 0) {
            alert('請輸入員工編號');
            return;
        }

        if (nurseId.current.value < 0) {
            alert('員工編號需大於0');
            return;
        }

        if (!nurseName.current.value || nurseName.current.value.lenght === 0) {
            alert('請輸入護士姓名');
            return;
        }

        const data = await nurseApi.createNurse(nurseId.current.value, nurseName.current.value, selected.map(row => row.value));

        if (data)
            alert('新增成功');

        navigate('/');
        
    }
    
  return (
    <div>
        <button><Link to="/">返回</Link></button>
        <button onClick={addNurse}>新增</button>
        <div>
            <label>員工編號</label>
            <input type="number" min="0" ref={nurseId} />
        </div>
        <div>
            <label>護士姓名</label>
            <input type="text" ref={nurseName} />
        </div>
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
  );
};

export default NurseCreate;