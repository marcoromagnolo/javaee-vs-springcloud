
class Session {

    static getSessionId() {
        let name = "sessionId=";
        let ca = document.cookie.split(';');
        for(let i = 0; i < ca.length; i++) {
            let c = ca[i];
            while (c.charAt(0) === ' ') {
                c = c.substring(1);
            }
            if (c.indexOf(name) === 0) {
                return c.substring(name.length, c.length);
            }
        }
        return "";
    }

    static setSessionId(sessionId, expireTime) {
        if (sessionId && expireTime) {
            let d = new Date();
            d.setTime(expireTime);
            document.cookie = "sessionId=" + sessionId + "; expires=" + d.toUTCString() + ";path=/";
        }
    }

    static isSessionActive() {
        let sessionId = Session.getSessionId();
        let active = sessionId && sessionId.length > 0;
        console.debug(active ? 'Session active with id: ' + sessionId : 'Session not active');
        return active;
    }

}

export default Session;