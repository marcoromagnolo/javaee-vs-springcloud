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
        });
    }

    show(sessionId, sessionToken) {
        return fetch(API_URL + '/show', {
            method: "post",
            body: {sessionId: sessionId, sessionToken: sessionToken}
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