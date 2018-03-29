import React, {Component} from 'react';

class LeftSide extends Component {


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
            <div className="LeftSide">
                <ul>
                    <li>Balance amount: {this.state.wallet.balance}</li>
                    <li>Nickname: {this.state.nickname}</li>
                    <li>First Name: {this.state.account.firstName}</li>
                    <li>Last Name: {this.state.account.lastName}</li>
                </ul>
            </div>
        );
    }

}

export default LeftSide;