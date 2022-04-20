import React, {useState , useEffect } from 'react';
import { Link } from 'react-router-dom';
import nurseApi from '../../utils/api/nurseApi.js';





const NurseList = ()=> {
    // const data = [];
    const [data, setData] = useState([]);

    const doDelete = async (nurseId) => {
        const res = await nurseApi.deleteNurse(nurseId);

        if (res) {
            alert("刪除成功");
        }

        window.location.reload(false);
    }

    useEffect(() => {
        const all = [];
        nurseApi.getAllNurse().then(res => {
            res.forEach(element => {
                const createTime = element.createTime;
                all.push(
                    <tr key={element.id}>
                        <td>{element.id}</td>
                        <td>{`${createTime[0]}/${createTime[1]}/${createTime[2]} ${createTime[3]}:${createTime[4]}:${createTime[5]}`}</td>
                        <td>
                            <Link to={`/nurseDetail/${element.id}`}>View</Link><span> </span>
                            <a href='#/nurseList' onClick={()=> doDelete(element.id)}>Del</a>
                        </td>
                    </tr>
                );
            })
            setData(all);
        })
    }, []);

    return (
        <div>
            <button><Link to="/">返回</Link></button>
            <table>
                <thead>
                    <tr>
                        <th>員邊</th>
                        <th>修改時間</th>
                        <th>動作</th>
                    </tr>
                </thead>
                <tbody>
                    {data}
                </tbody>
            </table>
        </div>
    );
}

export default NurseList;