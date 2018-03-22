import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import registerServiceWorker from './registerServiceWorker';

// Environment mode
console.info('Environment launched in mode: ' + process.env.NODE_ENV);
if (process.env.NODE_ENV === 'production') {
    console.log = function () {
    };
    console.debug = function () {
    };
    console.info = function () {
    };
    console.warn = function () {
    };
    console.error = function () {
    };
}

ReactDOM.render(<App />, document.getElementById('root'));
registerServiceWorker();
