import { Component } from 'react';
import config from './Config';

const API_URL = config.apiUrl + '/lobby';

class LobbyApi extends Component {

    test() {
        return fetch(API_URL + '/test').then(response => {
            return response.json();
        });
    }

    tables(sessionId, sessionToken) {
        return new Promise(function(resolve, reject) {
            let ok;
            fetch(API_URL + '/tables', {
                method: "post",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({sessionId: sessionId, sessionToken: sessionToken})
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

    tableSettings(tableId, sessionId, sessionToken) {
        return new Promise(function(resolve, reject) {
            let ok;
            fetch(API_URL + '/table-settings', {
                method: "post",
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({tableId: tableId, sessionId: sessionId, sessionToken: sessionToken})
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
}

export default LobbyApi;