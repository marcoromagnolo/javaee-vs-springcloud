import React, {Component} from 'react';
import { Panel } from 'react-bootstrap';

class PlayerDetail extends Component {

    constructor(props) {
        super(props);
        this.state = {
            sessionToken: localStorage.getItem('sessionToken'),
            nickname: localStorage.getItem('nickname'),
            account: JSON.parse(localStorage.getItem('account')),
            wallet: JSON.parse(localStorage.getItem('wallet'))
        }
    }

    render() {
        return (
            <div className="PlayerDetail">
                <Panel>
                    <Panel.Heading>Player</Panel.Heading>
                    <Panel.Body>
                        <ul>
                            <li>Balance amount: {this.state.wallet.balance}</li>
                            <li>Nickname: {this.state.nickname}</li>
                            <li>First Name: {this.state.account.firstName}</li>
                            <li>Last Name: {this.state.account.lastName}</li>
                        </ul>
                    </Panel.Body>
                </Panel>
            </div>
        );
    }

}

export default PlayerDetail;