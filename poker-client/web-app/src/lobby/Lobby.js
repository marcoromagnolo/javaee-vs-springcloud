import React, {Component} from 'react';
import { Grid, Row, Col } from 'react-bootstrap';
import Header from './Header';
import PlayerDetail from './PlayerDetail';
import Tables from './Tables';
import TableSettings from './TableSettings';
import Footer from './Footer';

class Lobby extends Component {

    constructor(props) {
        super(props);
        this.state = {
            tableId: null
        };
        this.handleSelectTable = this.handleSelectTable.bind(this);
    }

    handleSelectTable(tableId) {
        this.setState({tableId: tableId});
        console.debug('handleSelectTable() ' + tableId);
    }

    render() {
        return (
            <div className="Layout">
                <Header/>
                <Grid fluid={true}>
                    <Row className="show-grid">
                        <Col xs={12} md={2}>
                            <PlayerDetail/>
                        </Col>
                        <Col xs={12} md={8}>
                            <Tables onSelect={this.handleSelectTable}/>
                        </Col>
                        <Col xs={12} md={2}>
                            <TableSettings tableId={this.state.tableId}/>
                        </Col>
                    </Row>
                </Grid>
                <Footer/>
            </div>
        );
    }

}

export default Lobby;