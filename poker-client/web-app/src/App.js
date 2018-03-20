import React, { Component } from 'react';
import './App.css';
import Login from './Login';
import { Grid, Row, Col } from 'react-bootstrap';

class App extends Component {

  render() {
    return (
      <div className="App">
          <Grid>
              <Row className="show-grid">
                  <Col xs={12} md={3}>
                      <Login/>
                  </Col>
                  <Col xs={12} md={9}>

                  </Col>
              </Row>
          </Grid>
      </div>
    );
  }

}

export default App;
