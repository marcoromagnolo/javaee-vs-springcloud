import { Component } from 'react';
import config from './Config';

const API_URL = config.apiUrl + '/player';

class PlayerApi extends Component {

    test() {
        return fetch(API_URL + '/test').then(response => {
            return response.json();
        });
    }

    login(username, password) {
        return new Promise(function(resolve, reject) {
            let ok;
            fetch(API_URL + '/login', {
                method: "post",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    username: username,
                    password: password})
            }).then(response => {
                console.debug(response);
                ok = response.ok;
                return response.json();
            }).then(json => {
                console.debug(json);
                return ok ? resolve(json) : reject(json);
            }).catch(error => {
                console.error(error);
                return {error: 'Connection error'};
            });
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
        }).then(response => {
            console.debug(response);
            return response.json();
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
        }).then(response => {
            console.debug(response);
            return response.json();
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
        }).then(response => {
            console.debug(response);
            return response.json();
        });
    }

}

export default PlayerApi;