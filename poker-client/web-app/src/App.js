import React, {Component} from 'react';
import {BrowserRouter as Router, Route} from 'react-router-dom';
import './App.css';
import Login from './Login';
import Game from "./game/Game";

class App extends Component {

    render() {
        return (
            <Router>
                <div className="App" >
                    <Route path='/game/:tableId' component={Game}/>
                    <Route exact path='/' component={Login}/>
                </div>
            </Router>
        );
    }
}

export default App;
