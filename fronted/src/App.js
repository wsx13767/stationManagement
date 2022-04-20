import React, {Component} from 'react';
import { Link } from "react-router-dom";
import './App.css';

class App extends Component {
  
  constructor(props) {
    super(props);
  }


  
  render(){
    return(
      <div>
        <Link to="/stationCreate">新增站點</Link><br/>
        <Link to="/stationList">站點列表</Link><br/>
        <Link to="/nurseCreate">新增護士</Link><br/>
        <Link to="/nurseList">護士列表</Link><br/>
      </div>
    );
  }
}

export default App;
