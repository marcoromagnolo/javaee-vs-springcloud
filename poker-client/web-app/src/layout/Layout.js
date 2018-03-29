import React, {Component} from 'react';
import { Grid, Row, Col } from 'react-bootstrap';
import Header from './Header';
import Center from './Center';
import LeftSide from './LeftSide';
import RightSide from './RightSide';
import Footer from './Footer';

class Layout extends Component {

    render() {
        return (
            <div className="Layout">
                <Header/>
                <Grid fluid={true}>
                    <Row className="show-grid">
                        <Col xs={12} md={2}>
                            <LeftSide/>
                        </Col>
                        <Col xs={12} md={8}>
                            <Center/>
                        </Col>
                        <Col xs={12} md={2}>
                            <RightSide/>
                        </Col>
                    </Row>
                </Grid>
                <Footer/>
            </div>
        );
    }

}

export default Layout;