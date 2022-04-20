import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import Router from './routes/StationRoute.js';
import reportWebVitals from './reportWebVitals';


ReactDOM.render(
  <div>
    <Router />
  </div>,
  document.getElementById('root')
)
// const root = ReactDOM.createRoot();
// root.render(
  
// );

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
