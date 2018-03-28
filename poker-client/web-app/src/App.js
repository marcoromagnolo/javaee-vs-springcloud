import React, { Component } from 'react';
import './App.css';
import Login from './Login';
import { Grid, Row, Col } from 'react-bootstrap';
// import { withCookies, Cookies } from 'react-cookie';
import Session from "./util/Session";

class App extends Component {

    // static propTypes = {
    //     cookies: instanceOf(Cookies).isRequired
    // };

    componentWillMount() {
        this.state = {
            sessionId: Session.isSessionActive()
        };
    }

    render() {
        const { sessionId } = this.state;

        // if (Session.isSessionActive()) {
            return (
                sessionId ?
                <div className="App">
                    <Grid>
                        <Row className="show-grid">
                            <Col xs={12} md={3}>

                            </Col>
                            <Col xs={12} md={9}>

                            </Col>
                        </Row>
                    </Grid>
                </div> : <div className="App">
                        <Login/>
                    </div>
            );
        // }
        // return (
        //     <div className="App">
        //         <Login/>
        //     </div>
        // );

    }

}

export default App;
