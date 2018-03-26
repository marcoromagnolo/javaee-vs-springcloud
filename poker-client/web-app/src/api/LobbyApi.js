import { Component } from 'react';
import config from './Config';

const API_URL = config.apiUrl + '/lobby';

class LobbyApi extends Component {

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

    show(sessionId, sessionToken) {
        let json = JSON.stringify({sessionId: sessionId, sessionToken: sessionToken});
        return fetch(API_URL + '/show', {
            method: "post",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: json
        }).then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error(response.json().errorCode);
            }
        });
    }
}

export default LobbyApi;