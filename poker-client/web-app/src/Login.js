import React, { Component } from 'react';
import ApiClient from './ApiClient';
import { FormGroup, FormControl, Button, Image } from 'react-bootstrap';

class Login extends Component {

    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
            submit: false
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {

    }

    handleSubmit(event) {
        event.preventDefault();

        this.setState({ submitted: true });
        let apiClient = new ApiClient();
        apiClient.test();

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