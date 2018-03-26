import { Component } from 'react';
import config from './Config';

const API_URL = config.apiUrl + '/lobby';

class LobbyApi extends Component {

    test() {
        return fetch(API_URL + '/test').then(response => {
            return response.json();
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
            console.debug(response);
            return response.json();
        });
    }
}

export default LobbyApi;