import React, { Component } from 'react';
import ApiClient from './api/PlayerApi';
import { FormGroup, FormControl, Button, Image } from 'react-bootstrap';

class Login extends Component {

    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
            error: ''
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    validateForm() {
        if (this.state.username.length === 0) {
            throw new Error("Username cannot be empty")
        }
        if (this.state.password.length === 0) {
            throw new Error("Password cannot be empty")
        }
    }

    handleSubmit(event) {
        event.preventDefault();

        try {
            this.validateForm();
            let playerApi = new ApiClient();
            playerApi.login(this.state.username, this.state.password);
        } catch (error) {
            this.setState(error.message);
            console.error(error);
        }

    }

    imgStyle = {width: '90px', height:'90px', marginBottom: '10px'};

    render() {
        return (
            <div className="Login">
                <form onSubmit={this.handleSubmit}>
                    <Image src="//ssl.gstatic.com/accounts/ui/avatar_2x.png" circle responsive className="center-block" style={this.imgStyle}/>

                    <FormGroup>
                        <FormControl type="text" placeholder="Username"/>
                    </FormGroup>

                    <FormGroup>
                        <FormControl type="password" placeholder="Password"/>
                    </FormGroup>

                    <Button bsStyle="primary" bsSize="large" block type="submit">Login</Button>
                </form>
            </div>
        );
    }

    login() {

    }
}

export default Login;