class Utility {

    /**
     * Get Formatted Date YYYY/MM/DD HH:mm:ss
     * @param timestamp
     * @returns {*}
     */
    static getDate(timestamp) {
        if (timestamp != null) {
            let date = new Date(parseInt(timestamp));
            let month = [];
            month[0] = "01";
            month[1] = "02";
            month[2] = "03";
            month[3] = "04";
            month[4] = "05";
            month[5] = "06";
            month[6] = "07";
            month[7] = "08";
            month[8] = "09";
            month[9] = "10";
            month[10] = "11";
            month[11] = "12";
            return date.getFullYear() + "/" + month[date.getMonth()] + "/"
                + (date.getDate() < 10 ? "0" + date.getDate() : date.getDate()) + " " + date.getHours() + ':' + (date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes())
                + ':' + (date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds());
        }
    }

    //Parse Chips and Money
    static parseNumber(value) {
        return Number(value).toFixed(2);
    }

    /**
     * Return Array coords of the Player Cards Image
     * @param value
     * @returns {*}
     */
    static getCard(value) {
        let cards = [{
            "width": 40,
            "height": 58,
            "x": 0,
            "y": 0,
            "text": "2C",
            "value": 0
        }, {
            "width": 40,
            "height": 58,
            "x": 40,
            "y": 0,
            "text": "3C",
            "value": 1
        }, {
            "width": 40,
            "height": 58,
            "x": 80,
            "y": 0,
            "text": "4C",
            "value": 2
        }, {
            "width": 40,
            "height": 58,
            "x": 120,
            "y": 0,
            "text": "5C",
            "value": 3
        }, {
            "width": 40,
            "height": 58,
            "x": 160,
            "y": 0,
            "text": "6C",
            "value": 4
        }, {
            "width": 40,
            "height": 58,
            "x": 200,
            "y": 0,
            "text": "7C",
            "value": 5
        }, {
            "width": 40,
            "height": 58,
            "x": 240,
            "y": 0,
            "text": "8C",
            "value": 6
        }, {
            "width": 40,
            "height": 58,
            "x": 280,
            "y": 0,
            "text": "9C",
            "value": 7
        }, {
            "width": 40,
            "height": 58,
            "x": 320,
            "y": 0,
            "text": "TC",
            "value": 8
        }, {
            "width": 40,
            "height": 58,
            "x": 360,
            "y": 0,
            "text": "JC",
            "value": 9
        }, {
            "width": 40,
            "height": 58,
            "x": 400,
            "y": 0,
            "text": "QC",
            "value": 10
        }, {
            "width": 40,
            "height": 58,
            "x": 440,
            "y": 0,
            "text": "KC",
            "value": 11
        }, {
            "width": 40,
            "height": 58,
            "x": 480,
            "y": 0,
            "text": "AC",
            "value": 12
        }, {
            "width": 40,
            "height": 58,
            "x": 0,
            "y": 58,
            "text": "2D",
            "value": 13
        }, {
            "width": 40,
            "height": 58,
            "x": 40,
            "y": 58,
            "text": "3D",
            "value": 14
        }, {
            "width": 40,
            "height": 58,
            "x": 80,
            "y": 58,
            "text": "4D",
            "value": 15
        }, {
            "width": 40,
            "height": 58,
            "x": 120,
            "y": 58,
            "text": "5D",
            "value": 16
        }, {
            "width": 40,
            "height": 58,
            "x": 160,
            "y": 58,
            "text": "6D",
            "value": 17
        }, {
            "width": 40,
            "height": 58,
            "x": 200,
            "y": 58,
            "text": "7D",
            "value": 18
        }, {
            "width": 40,
            "height": 58,
            "x": 240,
            "y": 58,
            "text": "8D",
            "value": 19
        }, {
            "width": 40,
            "height": 58,
            "x": 280,
            "y": 58,
            "text": "9D",
            "value": 20
        }, {
            "width": 40,
            "height": 58,
            "x": 320,
            "y": 58,
            "text": "TD",
            "value": 21
        }, {
            "width": 40,
            "height": 58,
            "x": 360,
            "y": 58,
            "text": "JD",
            "value": 22
        }, {
            "width": 40,
            "height": 58,
            "x": 400,
            "y": 58,
            "text": "QD",
            "value": 23
        }, {
            "width": 40,
            "height": 58,
            "x": 440,
            "y": 58,
            "text": "KD",
            "value": 24
        }, {
            "width": 40,
            "height": 58,
            "x": 480,
            "y": 58,
            "text": "AD",
            "value": 25
        }, {
            "width": 40,
            "height": 58,
            "x": 0,
            "y": 116,
            "text": "2H",
            "value": 26
        }, {
            "width": 40,
            "height": 58,
            "x": 40,
            "y": 116,
            "text": "3H",
            "value": 27
        }, {
            "width": 40,
            "height": 58,
            "x": 80,
            "y": 116,
            "text": "4H",
            "value": 28
        }, {
            "width": 40,
            "height": 58,
            "x": 120,
            "y": 116,
            "text": "5H",
            "value": 29
        }, {
            "width": 40,
            "height": 58,
            "x": 160,
            "y": 116,
            "text": "6H",
            "value": 30
        }, {
            "width": 40,
            "height": 58,
            "x": 200,
            "y": 116,
            "text": "7H",
            "value": 31
        }, {
            "width": 40,
            "height": 58,
            "x": 240,
            "y": 116,
            "text": "8H",
            "value": 32
        }, {
            "width": 40,
            "height": 58,
            "x": 280,
            "y": 116,
            "text": "9H",
            "value": 33
        }, {
            "width": 40,
            "height": 58,
            "x": 320,
            "y": 116,
            "text": "TH",
            "value": 34
        }, {
            "width": 40,
            "height": 58,
            "x": 360,
            "y": 116,
            "text": "JH",
            "value": 35
        }, {
            "width": 40,
            "height": 58,
            "x": 400,
            "y": 116,
            "text": "QH",
            "value": 36
        }, {
            "width": 40,
            "height": 58,
            "x": 440,
            "y": 116,
            "text": "KH",
            "value": 37
        }, {
            "width": 40,
            "height": 58,
            "x": 480,
            "y": 116,
            "text": "AH",
            "value": 38
        }, {
            "width": 40,
            "height": 58,
            "x": 0,
            "y": 174,
            "text": "2S",
            "value": 39
        }, {
            "width": 40,
            "height": 58,
            "x": 40,
            "y": 174,
            "text": "3S",
            "value": 40
        }, {
            "width": 40,
            "height": 58,
            "x": 80,
            "y": 174,
            "text": "4S",
            "value": 41
        }, {
            "width": 40,
            "height": 58,
            "x": 120,
            "y": 174,
            "text": "5S",
            "value": 42
        }, {
            "width": 40,
            "height": 58,
            "x": 160,
            "y": 174,
            "text": "6S",
            "value": 43
        }, {
            "width": 40,
            "height": 58,
            "x": 200,
            "y": 174,
            "text": "7S",
            "value": 44
        }, {
            "width": 40,
            "height": 58,
            "x": 240,
            "y": 174,
            "text": "8S",
            "value": 45
        }, {
            "width": 40,
            "height": 58,
            "x": 280,
            "y": 174,
            "text": "9S",
            "value": 46
        }, {
            "width": 40,
            "height": 58,
            "x": 320,
            "y": 174,
            "text": "TS",
            "value": 47
        }, {
            "width": 40,
            "height": 58,
            "x": 360,
            "y": 174,
            "text": "JS",
            "value": 48
        }, {
            "width": 40,
            "height": 58,
            "x": 400,
            "y": 174,
            "text": "QS",
            "value": 49
        }, {
            "width": 40,
            "height": 58,
            "x": 440,
            "y": 174,
            "text": "KS",
            "value": 50
        }, {
            "width": 40,
            "height": 58,
            "x": 480,
            "y": 174,
            "text": "AS",
            "value": 51
        }];
        for (let i = 0; i < cards.length; i++) {
            if (cards[i].value === value) {
                return cards[i];
            }
        }
    }
}

export default Utility;
