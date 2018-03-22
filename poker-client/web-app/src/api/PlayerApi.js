import { Component } from 'react';

const API_URL = 'http://localhost:8080/poker-manager/player';

class PlayerApi extends Component {

    test() {
        return fetch(API_URL + '/test').then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Error to connect server');
            }
        });
    }

    login(username, password) {
        return fetch(API_URL + '/login', {
            method: "post",
            body: {username: username, password: password}
        });
    }

    logout(sessionId, sessionToken) {
        return fetch(API_URL + '/logout', {
            method: "post",
            body: {sessionId: sessionId, sessionToken: sessionToken}
        });
    }

    account(sessionId, sessionToken) {
        return fetch(API_URL + '/account', {
            method: "post",
            body: {sessionId: sessionId, sessionToken: sessionToken}
        });
    }

    wallet(sessionId, sessionToken) {
        return fetch(API_URL + '/wallet', {
            method: "post",
            body: {sessionId: sessionId, sessionToken: sessionToken}
        });
    }

}

export default PlayerApi;