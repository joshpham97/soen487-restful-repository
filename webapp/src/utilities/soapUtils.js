export const soapOperations = {
    list: 'list',
    clear: 'clear'
};

const envelopeTags = {
    envelopeOpen: '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.soap.example.com/">',
    envelopeClose: '</soapenv:Envelope>',
    header: '<soapenv:Header/>',
    bodyOpen: '<soapenv:Body>',
    bodyClose: '</soapenv:Body>',

    listOpen: '<ser:listLog>',
    listClose: '</ser:listLog>',
    clearOpen: '<ser:clearLog>',
    clearClose: '</ser:clearLog>',
};

const responseMessageTagNames = {
    listElement: 'return',
    fault: 'S:Fault'
};

export const envelopeBuilder = (operation, params) => {
    let body = '';
    for(const p in params)
        body += '<' + p + '>' + params[p] + '</' + p + '>\n';

    const envelope =
        envelopeTags.envelopeOpen + '\n' +
        envelopeTags.header + '\n' +
            envelopeTags.bodyOpen + '\n' +
                envelopeTags[operation + 'Open'] + '\n' +
                    body +
                envelopeTags[operation + 'Close'] + '\n' +
            envelopeTags.bodyClose + '\n' +
        envelopeTags.envelopeClose;

    return envelope;
};

export const messageParser = (message) => {
    const parser = new DOMParser();
    const xml = parser.parseFromString(message, 'text/xml')

    const xmlFault = xml.getElementsByTagName(responseMessageTagNames.fault);
    if(xmlFault.length > 0) {
        throw "SOAP Fault";
    }
    const xmlListElements = xml.getElementsByTagName(responseMessageTagNames.listElement);
    const listLength = xmlListElements.length;

    let list = [];
    for(let i = 0; i < listLength; i++) {
        let obj = {};
        xmlListElements.item(i).childNodes.forEach((e) => {
            obj[e.tagName] = e.innerHTML;
        });
        list.push(obj);
    }

    return list;
};