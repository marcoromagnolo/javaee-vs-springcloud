import { Component } from 'react';
import config from './Config';

const API_URL = config.apiUrl + '/player';

class PlayerApi extends Component {

    test() {
        return fetch(API_URL + '/test').then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Error to connect server');
            }
        }).catch(error => {
            throw error;
        });
    }

    login(username, password) {
        let json = JSON.stringify({username: username, password: password});
        return fetch(API_URL + '/login', {
            method: "post",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: json
        });
    }

    logout(sessionId, sessionToken) {
        let json = JSON.stringify({sessionId: sessionId, sessionToken: sessionToken});
        return fetch(API_URL + '/logout', {
            method: "post",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: json
        });
    }

    account(sessionId, sessionToken) {
        let json = JSON.stringify({sessionId: sessionId, sessionToken: sessionToken});
        return fetch(API_URL + '/account', {
            method: "post",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: json
        });
    }

    wallet(sessionId, sessionToken) {
        let json = JSON.stringify({sessionId: sessionId, sessionToken: sessionToken});
        return fetch(API_URL + '/wallet', {
            method: "post",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: json
        });
    }

}

export default PlayerApi;