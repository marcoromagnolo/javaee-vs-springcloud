import React, {Component} from 'react';
import LobbyApi from "../api/LobbyApi";
import Session from "../util/Session";
import { Panel, ListGroup, ListGroupItem } from 'react-bootstrap';

class TableSettings extends Component {

    constructor(props) {
        super(props);
        this.state = {
            sessionId: Session.getSessionId(),
            sessionToken: localStorage.getItem('sessionToken'),
            settings: {
                id: null,
                name: null,
                numberOfSeats: 0,
                actionTimeout: 0,
                gameType: null
            }
        };
    }

    componentWillReceiveProps(nextProps) {
        console.debug('componentWillReceiveProps() tableId: ' + nextProps.tableId);
        this.loadTableSettings(nextProps.tableId);
    }

    loadTableSettings(tableId) {
        console.debug('loadTableSettings() tableId: ' + tableId);
        let lobbyApi = new LobbyApi();
        lobbyApi.tableSettings(tableId, this.state.sessionId, this.state.sessionToken).then(json => {
            this.setState({error: false, errorMessage: '', settings: json});
        }).catch(error => {
            console.error(error);
            this.setState({error: true, errorMessage: error.error, loading: false});
        });
    }

    render() {
        const settings = this.state.settings.id ? (
            <ListGroup >
                <ListGroupItem >Id: {this.state.settings.id}</ListGroupItem >
                <ListGroupItem >Name: {this.state.settings.name}</ListGroupItem >
                <ListGroupItem >Game type: {this.state.settings.gameType}</ListGroupItem >
                <ListGroupItem >Number of seats: {this.state.settings.numberOfSeats}</ListGroupItem >
                <ListGroupItem >Action timeout: {this.state.settings.actionTimeout}</ListGroupItem >
            </ListGroup >) : 'No table selected';

        return (
            <div className="TableSettings">
                <Panel>
                    <Panel.Heading>Table Details</Panel.Heading>
                    <Panel.Body>
                        {settings}
                    </Panel.Body>
                </Panel>
            </div>
        );
    }

}

export default TableSettings;