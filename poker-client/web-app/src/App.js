import React, { Component } from 'react';
import './App.css';
import Login from './Login';
import { Grid, Row, Col } from 'react-bootstrap';
import Session from "./util/Session";

class App extends Component {

    render() {

        if (Session.isSessionActive()) {
            return (
                <div className="App">
                    <Grid>
                        <Row className="show-grid">
                            <Col xs={12} md={3}>

                            </Col>
                            <Col xs={12} md={9}>

                            </Col>
                        </Row>
                    </Grid>
                </div>
            );
        }
        return (
            <div className="App">
                <Login/>
            </div>
        );

    }

}

export default App;
