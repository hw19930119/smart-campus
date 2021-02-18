function formFactory(action, data) {
    // const mockDocument = document.createDocumentFragment();
    const formEle = document.createElement('form');

    formEle.action = action;
    // formEle.method = method;
    // mockDocument.appendChild(formEle);
    document.body.appendChild(formEle);
    formDataFactory(formEle, data);

    return formEle;
}

function formDataFactory(formEle, data) {
    Object.keys(data).forEach(name => {
        const value = data[name];

        createInput(formEle, name, value);
    });
}

function createInput(formEle, name, value) {
    const type = typeof value;

    if (Array.isArray(value) || type === 'object') {
        Object.keys(value).forEach(n => {
            const v = value[n];
            const nextName = name + (Array.isArray(value) ? `[${n}]` : `.${n}`);

            createInput(formEle, nextName, v);
        });
    }
    if (type === 'string' || type === 'number') {
        const inputEle = document.createElement('input');

        inputEle.value = value;
        inputEle.name = name;
        inputEle.type = 'hidden';
        formEle.appendChild(inputEle);
    }
}
export default formFactory;
