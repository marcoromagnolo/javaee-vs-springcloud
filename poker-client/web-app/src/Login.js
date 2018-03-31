import React, { Component } from 'react';
import PlayerApi from './api/PlayerApi';
import Session from './util/Session';
import Lobby from './lobby/Lobby';
import { FormGroup, FormControl, Button, Image, Alert } from 'react-bootstrap';

class Login extends Component {

    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
            error: false,
            errorMessage: '',
            loading: false,
            isLogged: Session.isSessionActive()
        };
        this.handleUsernameChange = this.handleUsernameChange.bind(this);
        this.handlePasswordChange = this.handlePasswordChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    validateForm() {
        let error = false;
        let errorMessages = '';
        if (this.state.username.length === 0) {
            error = true;
            errorMessages += "Username cannot be empty ";
        }
        if (this.state.password.length === 0) {
            error = true;
            errorMessages += "Password cannot be empty ";
        }
        this.setState({error: error, errorMessage: errorMessages, loading: !error});
        return !error;
    }

    handleUsernameChange(event) {
        this.setState({ username: event.target.value, error: false, errorMessage: '' });
    }

    handlePasswordChange(event) {
        this.setState({password: event.target.value, error: false, errorMessage: ''});
    }

    handleSubmit(event) {
        event.preventDefault();
        this.setState({loading: true});
        console.debug(this.state);
        if (this.validateForm()) {
            let playerApi = new PlayerApi();
            playerApi.login(this.state.username, this.state.password).then(json => {
                Session.setSessionId(json.sessionId, json.sessionExpireTime);
                localStorage.setItem('sessionToken', json.sessionToken);
                localStorage.setItem('nickname', json.nickname);
                localStorage.setItem('account', JSON.stringify(json.account));
                localStorage.setItem('wallet', JSON.stringify(json.wallet));
                this.setState({error: false, errorMessage: '', loading: false, isLogged: Session.isSessionActive()});
            }).catch(error => {
                console.error(error);
                this.setState({error: true, errorMessage: error.error, loading: false});
            });
        }
    }

    imgStyle = {width: '90px', height:'90px', marginBottom: '10px'};

    render() {
        return (
            !this.state.isLogged ?
            <div className="Login container">
                <form onSubmit={this.handleSubmit}>
                    <Image src="//ssl.gstatic.com/accounts/ui/avatar_2x.png" circle responsive className="center-block" style={this.imgStyle}/>

                    <FormGroup>
                        <FormControl type="text" placeholder="Username" value={this.state.username} onChange={this.handleUsernameChange}/>
                    </FormGroup>

                    <FormGroup>
                        <FormControl type="password" placeholder="Password" value={this.state.password} onChange={this.handlePasswordChange}/>
                    </FormGroup>

                    <Button bsStyle="primary" bsSize="large" block type="submit">
                        {this.state.loading ? (<i className="fa fa-circle-o-notch fa-spin"></i>) : (null)} Login</Button>
                </form>
                <br/>
                {this.state.error
                    ? (<Alert bsStyle="danger"><strong>Error!</strong> {this.state.errorMessage}</Alert>)
                    : (null)}
            </div> : <Lobby/>
        );
    }

}

export default Login;