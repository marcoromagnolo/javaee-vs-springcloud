import React, {Component} from 'react';
import LobbyApi from '../api/LobbyApi';
import Session from '../util/Session';
import { Button, Table, Panel } from 'react-bootstrap';

class Tables extends Component {

    constructor(props) {
        super(props);
        this.state = {
            sessionId: Session.getSessionId(),
            sessionToken: localStorage.getItem('sessionToken'),
            tables: [],
            tableId: null
        };
        this.handleTableIdChange = this.props.onSelect.bind(this);
        this.handleOpenTable = this.handleOpenTable.bind(this);
    }

    componentDidMount() {
        this.loadTables();
    }

    loadTables() {
        let lobbyApi = new LobbyApi();
        lobbyApi.tables(this.state.sessionId, this.state.sessionToken).then(json => {
            this.setState({error: false, errorMessage: '', tables: json.tables});
        }).catch(error => {
            console.error(error);
            this.setState({error: true, errorMessage: error.error, loading: false});
        });
    }

    handleOpenTable(tableId) {
        let options = "menubar=no,toolbar=no,location=no,resizable=no,scrollbars=no,status=no,width=1200,height=700";
        let title = "Poker - " + tableId;
        window.open("/game/" + tableId, title, options);
    }

    render() {
        let n = 1   ;
        const tables = this.state.tables.map((table) =>
            <tr key={table.id}>
                <td>{n++}</td>
                <td>{table.name}</td>
                <td><Button bsStyle="link" onClick={(e) => this.handleTableIdChange(table.id, e)}>Show Details</Button></td>
                <td><Button onClick={(e) => this.handleOpenTable(table.id, e)}>Open Table</Button></td>
            </tr>
        );
        return (
            <div className="Tables">
                <Panel>
                    <Panel.Heading>Table Games</Panel.Heading>
                    <Panel.Body>
                        <Table striped bordered condensed hover>
                            <thead>
                            <tr>
                                <th>#</th>
                                <th>Name</th>
                                <th>Table Details</th>
                                <th>Open Table</th>
                            </tr>
                            </thead>
                            <tbody>
                                {tables}
                            </tbody>
                        </Table>
                    </Panel.Body>
                </Panel>
            </div>
        );
    }
}

export default Tables;